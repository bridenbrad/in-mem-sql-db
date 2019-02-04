/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.common.TableAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class JoinClause extends AbstractNode implements FieldRefsAware, TableAware {
	
	private static final long serialVersionUID = 3929032786467747339L;

	@JsonProperty
	private final List<Join> joins;
	
	public JoinClause() {
		super();
		joins = new ArrayList<Join>();
	}
	
	public int getJoinCount() {
		return joins.size();
	}
	
	@Override
	public SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		for(Join join : joins) {
			set.addAll(join.getTableNames());		
		}
		return set;
	}

	/**
	 * Fields from all join conditions.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		for(Join join : joins) {
			set.add(join.getRequiredFieldRefs());
		}
		return set;
	}

	/**
	 * Number of joins.
	 */
	public int joinCount() {
		return joins.size();
	}
	
	public List<Join> getJoins() {
		return joins;
	}

	public void append(Join join) {
		joins.add(join);
	}

	@Override
	public Kind getKind() {
		return Kind.JOIN_CLAUSE;
	}

	@Override
	public String toSql() {
		String result = "";
		for(Join join : joins) {
			result += join.toSql();
		}
		return result;
	}
}
