/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.bool;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.common.TableAware;
import com.tirion.db.sql.ast.expression.Expression;
import com.tirion.db.sql.ast.expression.compare.InCompare;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BoolExpression extends Expression implements FieldRefsAware, TableAware {

	private static final long serialVersionUID = 3501750495260587342L;
	
	/**
	 * Returns all IN-based sub-select statements. Valid only in WHERE clause.
	 */
	public abstract List<InCompare> getSubSelects();
	
	/**
	 * Must return list since same logical field reference could
	 * be used in two different sub-expressions.
	 */
	public abstract List<FieldRef> getAllFieldRefs();

	/**
	 * Only those field references that match given owner.
	 */
	public final List<FieldRef> getFieldRefs(String owner, boolean shouldMatch) {
		List<FieldRef> result = new ArrayList<FieldRef>();
		for(FieldRef fieldRef : getAllFieldRefs()) {
			boolean equal = fieldRef.getOwner().equals(owner);
			if(equal && shouldMatch) {				
				result.add(fieldRef);
			} else if(!equal && !shouldMatch) {
				result.add(fieldRef);
			}
		}
		return result;
	}
}
