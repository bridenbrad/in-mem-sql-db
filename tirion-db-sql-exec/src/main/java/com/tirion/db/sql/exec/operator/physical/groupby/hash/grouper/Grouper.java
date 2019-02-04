/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper;

import java.util.LinkedList;

import com.tirion.db.sql.exec.operator.physical.common.key.Key;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.StatisticsAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Grouper extends StatisticsAware {	
	
	void onTuple(Tuple tuple, Key key);
	
	LinkedList<Tuple> getResult();
}
