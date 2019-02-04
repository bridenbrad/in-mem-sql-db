/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.builder.operator;

import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.exec.operator.physical.PhysicalOperator;
import com.tirion.db.sql.plan.builder.Builder;

/**
 * Builds tree of {@link PhysicalOperator}s.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface OperatorBuilder extends Builder {

	/**
	 * Returns root operator of query operator tree.
	 */
	PhysicalOperator build(SelectStatement select);
}
