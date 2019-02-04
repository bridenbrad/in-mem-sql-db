/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key;

import java.util.List;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface KeyBuilder {
	
	/**
	 * Number of parts of each key.
	 */
	int getPartCount();
	
	/**
	 * Build a GROUP BY key.
	 */
	Key build(Tuple tuple);
	
	/**
	 * Build ROLLUP keys.
	 */
	List<Key> buildRollup(Tuple tuple);
	
	/**
	 * Build CUBE keys.
	 */
	List<Key> buildCube(Tuple tuple);
}
