/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.type.Type;
import com.tirion.compressor.Compressor;
import com.tirion.db.store.builder.tokenizer.Tokenizer;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractPage implements Page {
	
	@JsonProperty
	private final Header header;
	private final Column owner;
	private final Runtime runtime;
		
	public AbstractPage(Header header, Column owner, Runtime runtime) {
		super();
		this.header = header;
		this.owner = owner;
		this.runtime = runtime;
	}
	
	protected final Compressor getCompressor() {
		return runtime.compressor();
	}

	protected final Tokenizer getTokenizer() {
		return getOwner().getTokenizer();
	}

	@Override
	public final Column getOwner() {
		return owner;
	}
	
	@Override
	public final boolean isNull(long rowId) {
		if(!hasNullBitmap()) {
			return false;
		}
		return getNullBitmap().isSet((int)(rowId - getStartRowId()));
	}

	@Override
	public final Type getType() {
		return getOwner().getType();
	}

	@Override
	public final int getId() {
		return header.getPageId();
	}

	@Override
	public final long getStartRowId() {
		return header.getStartRowId();
	}

	@Override
	public final long getEndRowId() {
		return header.getActualEndRowId();
	}
	
	@Override
	public final int getCount() {
		return header.getCount();
	}
	
	@Override
	public final boolean belongs(long rowId) {
		return getStartRowId() <= rowId && rowId < getEndRowId();
	}
	
	protected final void assertValidRowId(long rowId) {
		if(!belongs(rowId)) {
			throw new IllegalArgumentException("Row ID " + rowId + " is not in the page's range " + getStartRowId() + "," + getEndRowId());
		}
	}
	
	protected final void assertValidRowIds(List<Long> rowIds) {
		for(Long rowId : rowIds) {
			assertValidRowId(rowId);
		}
	}
	
	protected final int getArrayIndex(long rowId) {
		return (int)(rowId - getStartRowId());
	}
	
	protected long getAbstractSize() {
//		return 4 * 8 + header.sizeInBytes();;
		throw new NotYetImplementedException();
	}
	
//	/**
//	 * Number of rows times the width of singe value.
//	 */
//	private long getUncompressedRawSize() {
//		int width = 0;
//		if(getOwner().isTokenized()) {
//			width = getOwner().getTokenType().getWidth();
//		} else {
//			width = getType().getWidth();
//		}
//		return (getEndRowId() - getStartRowId()) * width;
//	}
}
