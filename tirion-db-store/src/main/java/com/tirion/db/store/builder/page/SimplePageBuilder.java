/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.page;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.common.sequence.Sequence.Kind;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.Page;
import com.tirion.db.store.page.header.Header;
import com.tirion.db.store.page.header.HeaderSource;
import com.tirion.db.store.page.offheap.NonTokenizedCompressedOffHeapPage;
import com.tirion.db.store.page.offheap.NonTokenizedUnCompressedOffHeapPage;
import com.tirion.db.store.page.offheap.TokenizedCompressedOffHeapPage;
import com.tirion.db.store.page.offheap.TokenizedUnCompressedOffHeapPage;
import com.tirion.db.store.page.onheap.NonTokenizedCompressedOnHeapPage;
import com.tirion.db.store.page.onheap.NonTokenizedUnCompressedOnHeapPage;
import com.tirion.db.store.page.onheap.TokenizedCompressedOnHeapPage;
import com.tirion.db.store.page.onheap.TokenizedUnCompressedOnHeapPage;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimplePageBuilder extends AbstractPageBuilder {

	public SimplePageBuilder(boolean isOnHeap, Column column, Runtime runtime, HeaderSource headerSource) {
		super(isOnHeap, column, runtime, headerSource);
	}
	
	@Override
	public Page build(Sequence sequence, DataStats stats) {
		return buildPage(sequence, stats);
	}
	
	private Page buildPage(Sequence sequence, DataStats stats) {
		final Header header = getHeaderSource().next(sequence.getCount());
		
		if(sequence.getKind() == Kind.ARRAY) {
			Array array = (Array) sequence;
			if(isTokenized()) {
				if(sequence.isCompressed()) {
					return new TokenizedCompressedOnHeapPage(header, getOwner(), getRuntime(), array, stats);
				} else {
					return new TokenizedUnCompressedOnHeapPage(header, getOwner(), getRuntime(), array, stats);
				}
			} else {
				if(sequence.isCompressed()) {					
					return new NonTokenizedCompressedOnHeapPage(header, getOwner(), getRuntime(), array, stats);	
				} else {
					return new NonTokenizedUnCompressedOnHeapPage(header, getOwner(), getRuntime(), array, stats);	
				}
			}
		} else {
			Buffer buffer = (Buffer) sequence;
			if(isTokenized()) {
				if(sequence.isCompressed()) {
					return new TokenizedCompressedOffHeapPage(header, getOwner(), getRuntime(), buffer, stats);
				} else {
					return new TokenizedUnCompressedOffHeapPage(header, getOwner(), getRuntime(), buffer, stats);
				}
			} else {
				if(sequence.isCompressed()) {		
					return new NonTokenizedCompressedOffHeapPage(header, getOwner(), getRuntime(), buffer, stats);	
				} else {
					return new NonTokenizedUnCompressedOffHeapPage(header, getOwner(), getRuntime(), buffer, stats);	
				}
			}
		}
	}
}
