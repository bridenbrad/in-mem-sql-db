/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.filter;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Used for join.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class TuplesFilter {

	/**
	 * Return true if tuples satisfy filter.
	 */
	public abstract boolean matches(Tuple left, Tuple right);
}
