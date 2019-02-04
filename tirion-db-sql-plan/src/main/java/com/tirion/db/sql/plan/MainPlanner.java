/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.db.sql.plan.rule.RulePlanner;
import com.tirion.db.sql.plan.rule.SimpleRulePlanner;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MainPlanner implements Planner {

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public PhysicalOperator plan(Job job, Node node) {
		RulePlanner rulePlanner = new SimpleRulePlanner();
		if(node.getKind() != Kind.SELECT_STATEMENT) {
			throw new IllegalArgumentException("Planning is applicable to SELECT statement only");
		}
		return rulePlanner.plan(job, node);
	}
}
