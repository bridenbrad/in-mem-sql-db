/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select.legacy;
//package com.tirion.db.sql.ast.select;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.ast.Node;
//import com.tirion.db.sql.ast.common.FunctionCall;
//import com.tirion.db.sql.ast.common.Value;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class Window implements Node {
//
//	private FunctionCall function;
//	private Value value;
//	private PartitionBy partitionBy;
//	private OrderByClause orderBy;
//	private Framing framing;
//
//	public boolean hasPartitionBy() {
//		return partitionBy != null;
//	}
//	
//	public boolean hasOrderBy() {
//		return orderBy != null;
//	}
//	
//	public FunctionCall getFunction() {
//		return function;
//	}
//
//	public void setFunction(FunctionCall function) {
//		this.function = function;
//	}
//
//	public Value getValue() {
//		return value;
//	}
//
//	public void setValue(Value value) {
//		this.value = value;
//	}
//
//	public PartitionBy getPartitionBy() {
//		return partitionBy;
//	}
//
//	public void setPartitionBy(PartitionBy partitionBy) {
//		this.partitionBy = partitionBy;
//	}
//
//	public OrderByClause getOrderBy() {
//		return orderBy;
//	}
//
//	public void setOrderBy(OrderByClause orderBy) {
//		this.orderBy = orderBy;
//	}
//
//	public Framing getFraming() {
//		return framing;
//	}
//
//	public void setFraming(Framing framing) {
//		this.framing = framing;
//	}
//
//	@Override
//	public Kind getKind() {
//		return Kind.WINDOW;
//	}
//
//	@Override
//	public String toSql() {
//		throw new NotYetImplementedException();
//	}
//}
