/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan;

import com.tirion.common.Lifecycle;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Planner extends Lifecycle {

	PhysicalOperator plan(Job job, Node node);
}
