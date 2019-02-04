/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Key for hash based GROUP BY & JOIN. Should implement hashcode
 * & equals methods.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Key {

	boolean isNull();
	
	void writeStateTo(Tuple tuple);
}
