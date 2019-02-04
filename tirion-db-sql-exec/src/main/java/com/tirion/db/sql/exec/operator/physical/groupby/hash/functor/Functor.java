/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor;

import com.tirion.common.Lifecycle;
import com.tirion.common.WidthAware;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;

/** 
 * If value is null, it is silently skipped.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Functor extends Lifecycle, TupleListener, WidthAware {

	void writeStateTo(Tuple tuple);
	
	/**
	 * Create new instance of this functor
	 * with default initial state.
	 */
	Functor cloneMe();
}
