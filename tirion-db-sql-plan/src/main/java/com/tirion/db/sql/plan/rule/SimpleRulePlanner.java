/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.ast.message.SimpleMessageListener;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.executor.job.Job;

/**
 * For now plans only SELECT statements.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleRulePlanner implements RulePlanner {

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
		
	@Override
	public PhysicalOperator plan(Job jobContext, Node node) {
		if(node.getKind() == Kind.SELECT_STATEMENT) {
			return planSelectStatement(jobContext, (SelectStatement) node);
		}
		throw new IllegalArgumentException(node.getKind().toString());
	}
	
	private PhysicalOperator planSelectStatement(Job job, SelectStatement select) {
		SimpleMessageListener listener = new SimpleMessageListener();
//		SimpleContext ctx = new SimpleContext(jobContext.getRuntime().catalog(), listener);
//		MainVisitor visitor = new MainVisitor(jobContext.getRuntime());
//		visitor.visit(select, ctx);
//		
//		OperatorBuilder transformer = new SimpleOperatorBuilder(jobContext);
//		PhysicalOperator operator = transformer.build(select);
		return null;	
	}
}
