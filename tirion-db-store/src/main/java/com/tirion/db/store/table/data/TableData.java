/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.table.data;

import java.util.List;

import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.column.ColumnData;
import com.tirion.db.store.column.token.ProxyTokenMap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TableData {

	List<SequenceSlice> getSlices();
	
	ProxyTokenMap<Object, Object>[] getTokenMaps();
	
	ColumnData build(int columnIndex);
}
