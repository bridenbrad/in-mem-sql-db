/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.window.framer;

import java.util.List;

import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Framer {

	void frame(List<Tuple> tuples, List<Functor> functors);
}
