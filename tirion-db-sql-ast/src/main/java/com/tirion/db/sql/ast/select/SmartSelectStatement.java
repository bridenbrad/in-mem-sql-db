/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.FieldIndexComparator;
import com.tirion.db.catalog.model.SimpleField;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.SimpleFields;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SmartSelectStatement extends SelectStatement {

	private static final long serialVersionUID = 8376140832842898998L;

	/**
	 * Key is alias.
	 */
	@Enriched
	private Map<String, String> aliasMap;
	
	/**
	 * Key is alias.
	 */
	@Enriched
	private Map<String, Entity> entityMap;
	
	/**
	 * Key is name of relation, value is list of fields that have
	 * to be projected.
	 */
	private final Map<String, List<Field>> projectionMap = new HashMap<String, List<Field>>();
	
	public void setAliasMap(Map<String, String> aliasMap) {
		this.aliasMap = aliasMap;
	}

	public void setEntityMap(Map<String, Entity> entityMap) {
		this.entityMap = entityMap;
	}
	
	public Map<String, List<Field>> getProjectionMap() {
		return projectionMap;
	}

	/**
	 * Build input & output {@link Fields}s and projection map.
	 */
	@Override
	public void optimize(Context ctx) {
		buildProjections(ctx);
		buildAllInputOutputs();
	}
	
	/**
	 * Builds input/output {@link Fields}s for
	 * various clauses.
	 */
	private void buildAllInputOutputs() {
		Fields left = new SimpleFields(getProjectionsFor(getFromClause().getAlias()));
		if(hasJoinClause()) {			
			for(Join join : getJoinClause().getJoins()) {
				// left input
				Fields right = new SimpleFields(getProjectionsFor(join.getAlias()));
				Fields output = left.merge(right);
				join.setLeftInput(left);
				join.setRightInput(right);
				join.setOutput(output);
				left = output;
			}
		}
		if(hasGroupByClause()) {
			getGroupByClause().setInput(left);
			
			SimpleFields output = new SimpleFields();
			for(FieldRef fieldRef : getGroupByClause().getFields()) {
				output.append(left.getField(fieldRef));
			}
			for(FunctionCall call : getSelectClause().getFunctionCalls()) {
				output.append(new SimpleField(call.getAlias(), call.getOwner(), call.getReturnType(), output.getFieldCount()));
			}
			getGroupByClause().setOutput(output);
			left = output;
			
			if(hasHavingClause()) {
				getHavingClause().setInput(getGroupByClause().getOutput());
			}
		}
		if(hasOrderByClause()) {
			getOrderByClause().setInput(left);
		}
		if(!hasGroupByClause()) {
			setInput(left);
		}
	}
	
	/**
	 * Must be executed after pushability of filters in WHERE clause
	 * has been determined since we need to know if filter will execute
	 * inside or outside of scan.
	 */
	private void buildProjections(Context ctx) {
		
		// sub-selects are treated in special manner:
		// even if WHERE clause filter are pushable into
		// sub-select, we still treat that as projection;
		// order is important...
//		LinkedHashSet<FieldRef> map = new LinkedHashSet<FieldRef>();
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		
		set.add(getSelectClause().getRequiredFieldRefs());
		if(hasJoinClause()) {
			set.add(getJoinClause().getRequiredFieldRefs());
		}
		if(hasGroupByClause()) {
			// HAVING clause is excluded;
			// ORDER BY, if present, is assumed as subset of GROUP BY
			// since we do not want to include derived fields
			set.add(getGroupByClause().getRequiredFieldRefs());
		} else if(hasOrderByClause()) {
			// GROUP BY is not present, hence no fields are derived
			set.add(getOrderByClause().getRequiredFieldRefs());
		}
		
		// for WHERE clause it is more tricky since we need to
		// take into account pushability of filters...
		if(hasWhereClause()) {
			if(getWhereClause().filtersArePushable()) {
				// FROM clause first
				BoolExpression expression = getWhereClause().getPushableFiltersFor(getFromClause().getAlias());
				if(expression != null) {
					if(getFromClause().isSubSelect()) {
						set.add(expression.getRequiredFieldRefs());
					} else {
						// we will execute Operations
					}
				}
				// JOIN clause next
				if(hasJoinClause()) {
					for(Join join : getJoinClause().getJoins()) {
						expression = getWhereClause().getPushableFiltersFor(join.getAlias());
						if(expression != null) {							
							if(!(join.isInner() && !join.isSubSelect())) {
								// we dont have to project only if JOIN is inner
								// and it references physical table
								set.add(expression.getRequiredFieldRefs());
							}
						}
					}
				}
			} else {
				// WHERE is executed after projection
				set.add(getWhereClause().getRequiredFieldRefs());
			}
		}
		
		// now lets build projection map;
		for(FieldRef fieldRef : set.get()) {
			String owner = fieldRef.getOwner();
			List<Field> fields = projectionMap.get(owner);
			if(fields == null) {
				fields = new ArrayList<Field>();
				projectionMap.put(owner, fields);
			}
			Field field = entityMap.get(owner).getField(fieldRef.getName());
			fields.add(field);
		}
		for(List<Field> list : projectionMap.values()) {
			Collections.sort(list, new FieldIndexComparator());
		}
	}
	
	@Override
	public List<Field> getProjectionsFor(String relationName) {
		List<Field> result = projectionMap.get(relationName);
		if(result == null) {
			throw new NullPointerException("No projections found for relation '" + relationName + "'");
		}
		return result;
	}

	@Override
	public SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		set.addAll(getFromClause().getTableNames());
		if(hasJoinClause()) {
			set.addAll(getJoinClause().getTableNames());
		}
		if(hasWhereClause()) {
			set.addAll(getWhereClause().getTableNames());
		}
		return set;
	}
}
