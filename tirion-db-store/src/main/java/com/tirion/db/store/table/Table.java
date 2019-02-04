/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.table;

import com.tirion.common.SizeAware;
import com.tirion.db.store.appender.Appendable;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.table.data.TableData;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Table extends Appendable<TableData>, SizeAware {
		
	String getName();
	
	/**
	 * Approximation, actual row count may be
	 * lower than this if loads were not page
	 * size aligned. Returned value will be as
	 * if all loads were page size aligned.
	 */
	long getRowCount();
	
	/**
	 * Number of pages per column.
	 */
	int getPageCount();
	
	void truncate();
	
	Column[] getColumns();
	
	Column getColumn(String columnName);
}

