/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.builder.operation;

import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.exec.operator.physical.scan.exec.MainOperation;
import com.tirion.db.sql.plan.builder.Builder;

/**
 * Builds tree of {@link Operation}s.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface OperationBuilder extends Builder {

	/**
	 * Assumes that all {@link FieldRef}s within expression exist
	 * within given table.
	 */
	MainOperation build(String tableName, BoolExpression expression);
}
