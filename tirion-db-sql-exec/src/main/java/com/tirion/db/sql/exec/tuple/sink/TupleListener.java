/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.sink;

import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TupleListener {

	void onTuple(Tuple tuple);
	
	void onTuples(Tuples tuples);
}
