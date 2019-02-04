/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.sink;

import com.tirion.db.store.page.Page;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageAwareRowIdSink extends RowIdSink {

	/**
	 * Will be called before page is processed.
	 */
	void before(Page page);
	
	/**
	 * Will be called after page is processed.
	 */
	void after(Page page);
}
