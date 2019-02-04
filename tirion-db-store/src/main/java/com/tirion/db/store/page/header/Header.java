/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.header;

import com.tirion.common.SizeAware;

/**
 * Wrapper around page ID, start row ID,
 * end row ID and number of elements within 
 * page.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Header extends SizeAware {

	int getPageId();

	long getStartRowId();

	/**
	 * Equal to start + {@link Constants}.ROWS_PER_PAGE.
	 */
	long getEndRowId();
	
	/**
	 * Might be smaller than start + {@link Constants}.ROWS_PER_PAGE
	 * in case loads were not page size aligned.
	 */
	long getActualEndRowId();
	
	/**
	 * Actual number of entries in the page.
	 * This value might be smaller than system
	 * wide page size in loads were not page size
	 * aligned. See {@link Constants}.ROWS_PER_PAGE.
	 */
	int getCount();
}
