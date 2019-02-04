/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.merge;
//package com.tirion.db.sql.ast.merge;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.ast.common.FieldReference;
//import com.tirion.db.sql.ast.common.Value;
//import com.tirion.db.sql.ast.expression.bool.BoolExpression;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class InsertMatchAction extends MatchAction {
//
//	private BoolExpression filter;
//	private final List<FieldReference> targetFields;
//	private final List<Value> inputValues;
//	
//	public InsertMatchAction() {
//		super();
//		targetFields = new ArrayList<FieldReference>();
//		inputValues = new ArrayList<Value>();
//	}
//
//	public BoolExpression getFilter() {
//		return filter;
//	}
//
//	public void setFilter(BoolExpression filter) {
//		this.filter = filter;
//	}
//	
//	public List<FieldReference> getTargetFields() {
//		return targetFields;
//	}
//
//	public List<Value> getInputValues() {
//		return inputValues;
//	}
//
//	public void appendTargetField(FieldReference fieldReference) {
//		targetFields.add(fieldReference);
//	}
//	
//	public void appendInputValue(Value value) {
//		inputValues.add(value);
//	}
//
//	@Override
//	public Kind getKind() {
//		return Kind.INSERT_MATCH_ACTION;
//	}
//	
//	@Override
//	public String toSql() {
//		throw new NotYetImplementedException();
//	}
//}
