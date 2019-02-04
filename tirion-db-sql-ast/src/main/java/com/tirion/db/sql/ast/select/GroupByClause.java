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

import org.codehaus.jackson.annotate.JsonProperty;

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
public final class GroupByClause extends AbstractNode implements FieldRefsAware {
	
	private static final long serialVersionUID = -7135475736953843620L;

	@JsonProperty
	private final List<FieldRef> fields = new ArrayList<FieldRef>();
	
	@Enriched
	@JsonProperty
	private Fields input;
	
	@Enriched
	@JsonProperty
	private Fields output;
	
	/**
	 * This does not include fields used in functions in SELECT
	 * clause. Only grouping fields are included.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		set.add(fields);
		return set;
	}

	public void append(FieldRef fieldRef) {
		fields.add(fieldRef);
	}
	
	public int getFieldCount() {
		return fields.size();
	}
	
	public List<FieldRef> getFields() {
		return fields;
	}
	
	public Fields getInput() {
		return input;
	}

	public Fields getOutput() {
		return output;
	}

	public void setInput(Fields input) {
		this.input = input;
	}

	public void setOutput(Fields output) {
		this.output = output;
	}

	@Override
	public Kind getKind() {
		return Kind.GROUP_BY_CLAUSE;
	}

	@Override
	public String toSql() {
		String columnsString = "";
		for(FieldRef fieldRef : fields) {
			columnsString += ( fieldRef.hasOwner() ? fieldRef.getOwner() + "." : "") 
								+ fieldRef.getName();
			columnsString += ",";
		}
		columnsString = Util.trimCommasDots(columnsString);
		return "GROUP BY " + columnsString;
	}
}
