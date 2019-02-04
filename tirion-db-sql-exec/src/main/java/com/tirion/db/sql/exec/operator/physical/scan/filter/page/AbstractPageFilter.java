/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.page;

import java.util.List;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class AbstractPageFilter implements PageFilter {

	protected final PageAwareRowIdSink rowIdSink;
	protected final Page page;
	protected final List<Long> filterRowIds;
	protected final int position;  // used by ByteBuffer backed pages
	
	public AbstractPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
		super();
		this.rowIdSink = rowIdSink;
		this.page = page;
		this.filterRowIds = filterRowIds;
		this.position = position;
	}
	
	protected final boolean isTokenized() {
		return page.getOwner().isTokenized();
	}
}
