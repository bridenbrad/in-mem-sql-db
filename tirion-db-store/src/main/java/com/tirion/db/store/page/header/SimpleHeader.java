/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.header;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.common.Constants;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleHeader implements Header {

	@JsonProperty
	private final int pageId;
	@JsonProperty
	private final long startRowId;
	@JsonProperty
	private final long actualEndRowId;
	
	public SimpleHeader(int pageId, long startRowId, long actualEndRowId) {
		this.pageId = pageId;
		this.startRowId = startRowId;
		this.actualEndRowId = actualEndRowId;
		if(startRowId >= actualEndRowId) {
			throw new IllegalArgumentException("Start row ID " + startRowId + " must be lower than actual end row ID " + actualEndRowId);
		}
	}

	@Override
	public long getStartRowId() {
		return startRowId;
	}

	@Override
	public long getEndRowId() {
		return getStartRowId() + Constants.ROWS_PER_PAGE;
	}
	
	@Override
	public long getActualEndRowId() {
		return actualEndRowId;
	}

	@Override
	public int getCount() {
		return (int)(actualEndRowId - startRowId);
	}

	@Override
	public int getPageId() {
		return pageId;
	}
	
	@Override
	public long sizeInBytes() {
		return 4 + 8 + 8;
	}
}
