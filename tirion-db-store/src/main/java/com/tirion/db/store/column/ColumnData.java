/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column;

import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface ColumnData {

	Sequence[] getSequences();
	
	DataStats[] getStats();
	
	ProxyTokenMap<Object, Object> getTokenMap();
}
