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
//import com.tirion.db.sql.ast.Node;
//import com.tirion.db.sql.ast.common.EntityReference;
//import com.tirion.db.sql.ast.expression.bool.BoolExpression;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class MergeStatement implements Node {
//	
//	private EntityReference onEvent;
//	private EntityReference targetTable;
//	private BoolExpression matchExpression;
//	
//	private final List<MatchingClause> matchingClauses;
//	
//	public MergeStatement() {
//		super();
//		matchingClauses = new ArrayList<MatchingClause>();
//	}
//	
//	public EntityReference getOnEvent() {
//		return onEvent;
//	}
//
//	public void setOnEvent(EntityReference onEvent) {
//		this.onEvent = onEvent;
//	}
//
//	public EntityReference getTargetTable() {
//		return targetTable;
//	}
//
//	public void setTargetTable(EntityReference targetTable) {
//		this.targetTable = targetTable;
//	}
//
//	public BoolExpression getMatchExpression() {
//		return matchExpression;
//	}
//
//	public void setMatchExpression(BoolExpression matchExpression) {
//		this.matchExpression = matchExpression;
//	}
//
//	public List<MatchingClause> getMatchingClauses() {
//		return matchingClauses;
//	}
//	
//	public void append(MatchingClause matchingClause) {
//		matchingClauses.add(matchingClause);
//	}
//
//	@Override
//	public Kind getKind() {
//		return Kind.MERGE_STATEMENT;
//	}
//	
//	@Override
//	public String toSql() {
//		throw new NotYetImplementedException();
//	}
//}
