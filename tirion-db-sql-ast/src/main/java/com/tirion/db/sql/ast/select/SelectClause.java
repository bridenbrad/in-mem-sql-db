/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Util;
import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.SimpleField;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.common.FunctionCall;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SelectClause extends AbstractNode implements FieldRefsAware {

	private static final long serialVersionUID = 7163036855949341254L;
	
	private static final Set<Kind> ALLOWED_TYPES = new HashSet<Kind>();
	static {
		ALLOWED_TYPES.add(Kind.FIELD_REF);
		ALLOWED_TYPES.add(Kind.FUNCTION_CALL);		
		ALLOWED_TYPES.add(Kind.STAR);	
	}
	
	@JsonProperty
	private List<Node> columns = new ArrayList<Node>();
	
	public void setColumns(List<Node> columns) {
		this.columns = columns;
	}
	
	public boolean hasGroupingFunction() {
		for(Node column : columns) {
			if(column.getKind() == Kind.FUNCTION_CALL) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns fields derived from function calls.
	 */
	public List<Field> getGroupingOutputFields() {
		List<Field> fields = new ArrayList<Field>();
		int index = 0;
		for(FunctionCall call : getFunctionCalls()) {
			fields.add(new SimpleField(call.getAlias(), call.getOwner(), call.getReturnType(), index++));
		}
		return fields;
	}

	/**
	 * Returns all field references that are passed to grouping functions
	 * as parameters.
	 */
	public List<FieldRef> getGroupingFunctionParameters() {
		List<FieldRef> list = new ArrayList<FieldRef>();
		for(Node column : columns) {
			if(column.getKind() == Kind.FUNCTION_CALL) {
				FunctionCall call = (FunctionCall) column;
				for(Node param : call.getParameters()) {
					if(param.getKind() == Kind.FIELD_REF) {
						list.add((FieldRef)param);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * Will include {@link FieldRef}s passed as function parameters.
	 * GROUP BY will not include those.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		for(Node column : columns) {
			switch (column.getKind()) {
				case FIELD_REF:
					set.add((FieldRef)column);
					break;
				case FUNCTION_CALL:
					set.add(((FunctionCall)column).getRequiredFieldRefs());
					break;
				case STAR:
					throw new IllegalStateException("* should be normalized to list before calculating required field references");
				default:
					throw new IllegalStateException("Unexpected column type " + column.getKind().toString());
			}
		}
		return set;
	}

	public void append(Node column) {
		if(!ALLOWED_TYPES.contains(column.getKind())) {
			throw new IllegalArgumentException("SELECT clause can not contain node of type '" + column.getKind()+ "'");
		}
		((AbstractNode)column).setParent(this);
		columns.add(column);
	}
	
	public int getColumnCount() {
		return columns.size();
	}
	
	/**
	 * Includes those passed as arguments to functions.
	 */
	public List<FieldRef> getAllFieldRefs() {
		List<FieldRef> list = new ArrayList<FieldRef>();
		for(Node node : getColumns()) {
			if(node.getKind() == Kind.FIELD_REF) {
				list.add((FieldRef)node);
			} else if(node.getKind() == Kind.FUNCTION_CALL) {
				list.addAll(((FunctionCall)node).getFieldRefParams());
			} else {
				throw new IllegalArgumentException("Unexpected node kind '" + node.getKind() + "'");
			}
		}
		return list;
	}
	
	public List<FunctionCall> getFunctionCalls() {
		List<FunctionCall> list = new ArrayList<FunctionCall>();
		for(Node node : getColumns()) {
			if(node.getKind() == Kind.FUNCTION_CALL) {
				list.add((FunctionCall)node);
			}
		}
		return list;
	}
	
	public List<Node> getColumns() {
		return columns;
	}

	@Override
	public Kind getKind() {
		return Kind.SELECT_CLAUSE;
	}

	@Override
	public String toSql() {
		String columnsString = "";
		for(Node node : columns) {
			columnsString += node.toSql() + ",";
		}
		columnsString = Util.trimCommasDots(columnsString);
		return "SELECT " + columnsString;
	}
}
