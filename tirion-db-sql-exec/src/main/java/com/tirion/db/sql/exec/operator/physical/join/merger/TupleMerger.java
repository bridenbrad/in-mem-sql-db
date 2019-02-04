/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join.merger;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Merge two tuples into one. Used during join.
 */
public interface TupleMerger {

	/**
	 * Left can not be null. Right might be a null.
	 */
	Tuple merge(Tuple left, Tuple right);
}
