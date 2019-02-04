/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.window.framer;

import java.util.List;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class RangeFramer extends AbstractFramer {

	@Override
	public void frame(List<Tuple> tuples, List<Functor> functors) {
		throw new NotYetImplementedException();
	}
}
