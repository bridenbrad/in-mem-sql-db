/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.visitor;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare;
import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
import com.tirion.db.sql.ast.select.FromClause;
import com.tirion.db.sql.ast.select.GroupByClause;
import com.tirion.db.sql.ast.select.HavingClause;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.JoinClause;
import com.tirion.db.sql.ast.select.LimitClause;
import com.tirion.db.sql.ast.select.OrderByClause;
import com.tirion.db.sql.ast.select.SelectClause;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.select.WhereClause;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Visitor {

	void visit(Node node, Context ctx);
	
	void visit(SelectStatement select, Context ctx);
	
	void visit(SelectClause select, Context ctx);
	
	void visit(FromClause from, Context ctx);
	
	void visit(JoinClause joinClause, Context ctx);
	
	void visit(Join join, Context ctx);
	
	void visit(WhereClause where, Context ctx);
	
	void visit(GroupByClause group, Context ctx);
	
	void visit(HavingClause having, Context ctx);
	
	void visit(OrderByClause order, Context ctx);
	
	void visit(LimitClause limit, Context ctx);
	
	void visit(BoolExpression expression, Context ctx);
	
	void visit(Compare compare, Context ctx);	
	
	void visit(SimpleCompare compare, Context ctx);
	
	void visit(InCompare in, Context ctx);
	
	void visit(BetweenCompare between, Context ctx);

	void visit(NullCompare nullCompare, Context ctx);
	
	void visit(Star star, Context ctx);
	
	void visit(FieldRef fieldRef, Context ctx);
	
	void visit(EntityRef entityRef, Context ctx);
	
	void visit(FunctionCall call, Context ctx);
	
	void visit(Constant constant, Context ctx);
	
	void visit(ConstantRange range, Context ctx);
	
	void visit(ConstantSet set, Context ctx);
}
