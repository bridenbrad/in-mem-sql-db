/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Pair;
import com.tirion.common.Util;
import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OrderByClause extends AbstractNode implements FieldRefsAware {
	
	private static final long serialVersionUID = 806251888754911105L;

	@JsonProperty
	private final List<OrderKind> orderKinds = new ArrayList<OrderKind>();
	@JsonProperty
	private final List<FieldRef> fields = new ArrayList<FieldRef>();
	
	@Enriched
	@JsonProperty
	private Fields input; 
		
	public Fields getInput() {
		return input;
	}
	
	public void setInput(Fields input) {
		this.input = input;
	}
	
	/**
	 * Returns ordering field references.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		set.add(fields);
		return set;
	}

	public void append(FieldRef fieldRef, OrderKind orderKind) {
		fields.add(fieldRef);
		orderKinds.add(orderKind);
	}
	
	public int getOrderFieldCount() {
		return fields.size();
	}
	
	public List<FieldRef> getFields() {
		return fields;
	}

	public List<Pair<FieldRef, OrderKind>> getSmartFields() {
		List<Pair<FieldRef, OrderKind>> list = new LinkedList<Pair<FieldRef, OrderKind>>();
		for (int i = 0; i < fields.size(); i++) {
			list.add(new Pair<FieldRef, OrderKind>(fields.get(i), orderKinds.get(i)));
		}
		return list;
	}
	
	@Override
	public Kind getKind() {
		return Kind.ORDER_BY_CLAUSE;
	}

	@Override
	public String toSql() {
		String columnsString = "";
		for (int i = 0; i < fields.size(); i++) {			
			FieldRef fieldRef = fields.get(i);
			columnsString += ( fieldRef.hasOwner() ? fieldRef.getOwner() + "." : "") 
					+ fieldRef.getName();
			columnsString += " " + orderKinds.get(i).toString();
			columnsString += ",";
		}
		columnsString = Util.trimCommasDots(columnsString);
		return "ORDER BY " + columnsString;
	}
}
