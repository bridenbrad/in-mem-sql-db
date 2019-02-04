/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical;

import com.tirion.db.sql.exec.operator.Operator;
import com.tirion.db.sql.exec.tuple.source.TupleSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PhysicalOperator extends Operator, TupleSource {
	
	public static enum Kind {
		ORDER_BY,
		LIMIT,
		GROUP_BY,
		FILTER,
		ROOT,
		SCAN,
		WINDOW,
		JOIN,
	}
	
	Kind getKind();
	
	/**
	 * Each operator in the tree will have unique
	 * ID for profiling purposes.
	 */
	int getId();
}
