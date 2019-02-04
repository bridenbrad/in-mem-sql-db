/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.sink;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;

/**
 * First row ID has index 0.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface RowIdSink {

	void onRowId(long rowId);
	
	/**
	 * @param start inclusive
	 * @param end exclusive
	 */
	void onRowIdRange(long start, long end);
	
	/**
	 * Convert it into source.
	 */
	RowIdSource asRowIdSource();
}
