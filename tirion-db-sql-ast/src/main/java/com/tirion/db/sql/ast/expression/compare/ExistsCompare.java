/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;
//package com.tirion.db.sql.ast.expression.compare;
//
//import java.util.Collections;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.ast.common.FieldReference;
//import com.tirion.db.sql.ast.context.Context;
//import com.tirion.db.sql.ast.select.SelectStatement;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class ExistsCompare extends Compare {
//
//	private static final long serialVersionUID = 4569571096709611521L;
//	
//	private boolean hasNot;
//	private SelectStatement select;
//	
//	@Override
//	public Set<FieldReference> getNeedSet() {
//		return new LinkedHashSet<FieldReference>();
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public Set<String> getTables() {
//		return Collections.EMPTY_SET;
//	}
//
//	@Override
//	public void verifyAndEnrich(Context context) {
//		select.verifyAndEnrich(context);
//		throw new NotYetImplementedException("EXISTS operator is not implemented");
//	}
//
//	public boolean hasNot() {
//		return hasNot;
//	}
//
//	public void setHasNot(boolean hasNot) {
//		this.hasNot = hasNot;
//	}
//
//	public SelectStatement getSelect() {
//		return select;
//	}
//
//	public void setSelect(SelectStatement select) {
//		this.select = select;
//		this.select.setParent(this);
//	}
//
//	@Override
//	public Kind getKind() {
//		return Kind.EXISTS_OPERATOR;
//	}
//
//	@Override
//	public String toSql() {
//		return (hasNot() ? "NOT " : "") + "EXISTS (" + select.toSql() + ")"	;
//	}
//}
