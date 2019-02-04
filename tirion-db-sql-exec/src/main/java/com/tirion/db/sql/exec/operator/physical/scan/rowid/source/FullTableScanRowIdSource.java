/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.List;

import com.tirion.db.common.Constants;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split.SplittableRowIdSource;
import com.tirion.db.store.page.Page;
import com.tirion.db.store.page.source.PageSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FullTableScanRowIdSource implements SplittableRowIdSource {

	private final int maxAllowedPageId;
	private final PageSource pageSource;
	
	private long rowId;
	private Page current;
	private int maxSeenPageId = 0;
	
	/**
	 * @param maxAllowedPageId exclusive
	 */
	public FullTableScanRowIdSource(PageSource pageSource, int maxAllowedPageId) {
		this.pageSource = pageSource;
		this.maxAllowedPageId = maxAllowedPageId;
	}
	
	@Override
	public int size() {
		return maxAllowedPageId * Constants.ROWS_PER_PAGE;
	}

	@Override
	public List<BatchRowIdSource> splitBatch(int splitsCountHint) {
		int count = maxAllowedPageId / splitsCountHint;
		if(count == 0) {
			count = 1;
		}
		if(count == 1) {
			
		} 
		
		return null;
	}
	
	@Override
	public List<Long> asList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		return hasNext();
	}

	@Override
	public boolean hasNext() {
		if(current != null) {
			return true;
		}
		if(maxSeenPageId > maxAllowedPageId) {
			return false;
		}
		return pageSource.hasNext();
	}

	@Override
	public long next() {
		return nextInternal(1);
	}
	
	private long nextInternal(int inc) {
		if(current == null) {
			current = nextPage();
			rowId = current.getStartRowId();
		}
		long result = rowId;
		rowId += inc;
		if(rowId == current.getEndRowId()) {
			current = null;
		}
		return result;
	}
	
	private Page nextPage() {
		Page page = pageSource.next();
		maxSeenPageId = page.getId();
		return page;
	}
}
