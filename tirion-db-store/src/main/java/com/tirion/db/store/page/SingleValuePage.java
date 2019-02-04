/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.page;
//
//import java.util.Arrays;
//import java.util.List;
//
//import com.tirion.db.common.runtime.Runtime;
//import com.tirion.db.store.bitmap.Bitmap;
//import com.tirion.db.store.builder.stats.Stats;
//import com.tirion.db.store.column.Column;
//import com.tirion.db.store.page.Page.Kind;
//import com.tirion.db.store.page.header.Header;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SingleValuePage extends AbstractPage {
//
//	private final Object value;
//	
//	public SingleValuePage(Header header, Column owner, Bitmap nullBitmap, Runtime runtime, Object value) {
//		super(header, owner, nullBitmap, runtime);
//		this.value = value;
//	}
//	
//	@Override
//	public Kind getKind() {
//		return Kind.SINGLE_VALUE;
//	}
//
//	@Override
//	public Mode getMode() {
//		return Mode.ON_HEAP;
//	}
//
//	@Override
//	public Object getUnderlying() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Object getUnderlying(boolean detokenize) {
//		return getUnderlying();
//	}
//
//	@Override
//	public boolean hasStats() {
//		return false;
//	}
//
//	@Override
//	public Stats getStats() {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Object getValue(long rowId) {
//		return value;
//	}
//	
//	@Override
//	public List<Object> getValues(List<Long> rowIds) {
//		Object[] values = new Object[rowIds.size()];
//		Arrays.fill(values, value);
//		return Arrays.asList(values);
//	}
//
//	@Override
//	public long sizeInBytes() {
//		return super.getAbstractSize() + 8;
//	}
//}
