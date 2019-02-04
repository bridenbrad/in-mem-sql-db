/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.generator;

import com.tirion.compiler.source.Source;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.exec.operator.physical.filter.TupleFilter;
import com.tirion.db.sql.exec.operator.physical.filter.TuplesFilter;

/**
 * Used to generate {@link TupleFilter} & {@link TuplesFilter}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface FilterGenerator {

	/**
	 * Builds {@link TupleFilter}.
	 */
	Source generateTupleFilter(Fields virtEntity, BoolExpression expression);
	
	/**
	 * Builds {@link TuplesFilter}.
	 */
	Source generateTuplesFilter(Fields leftVirtEntity, Fields rightVirtEntity, BoolExpression expression);
}
