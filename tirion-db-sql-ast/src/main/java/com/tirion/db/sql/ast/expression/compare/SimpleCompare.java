/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.Value;
import com.tirion.db.sql.ast.common.constant.BaseConstant;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class SimpleCompare extends Compare {

	private static final long serialVersionUID = 8223038786597172731L;
	
	@JsonProperty
	private Value right;
	
	@Override
	public final SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = super.getRequiredFieldRefs();
		if(right.getKind() == Kind.FIELD_REF) {
			set.add((FieldRef)right);
		}
		return set;
	}
	
	public Value getRight() {
		return right;
	}

	public void setRight(Value right) {
		this.right = right;
		this.right.setParent(this);
	}

	@Override
	public List<FieldRef> getAllFieldRefs() {
		if(right instanceof BaseConstant) {			
			return super.getAllFieldRefs();
		} else {
			List<FieldRef> list = new ArrayList<FieldRef>(2);
			list.addAll(super.getAllFieldRefs());
			list.add((FieldRef)right);
			return list;
		}
	}
}
