/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.index.bm.task2;
//
//import com.googlecode.javaewah.EWAHCompressedBitmap;
//import com.googlecode.javaewah.IntIterator;
//import com.tirion.common.bitmap.Bitmap;
//import com.tirion.common.bitmap.Bitmaps;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class RowIdProjectingSlaveTask extends RowIdProjectingTask<Void> {
//
//	private final long startRowId;
//	private final int start;
//	private final int end;
//	private final RowIdSink rowIdSink;
//	
//	public RowIdProjectingSlaveTask(Job job, long startRowId, int start, int end,
//			Bitmaps bitmaps, RowIdSink rowIdSink) {
//		super(job, bitmaps);
//		this.startRowId = startRowId;
//		this.start = start;
//		this.end = end;
//		this.rowIdSink = rowIdSink;
//	}
//
//	@Override
//	protected Void callInternal() throws Exception {
//		if(bitmaps == null || bitmaps.isEmpty()) {
//			return null;
//		}
//		long rowId = startRowId;
//		for (int i = start; i < end; i++) {
//			Bitmap bitmap =  bitmaps.get(i);
//			if(bitmap != null) {				
//				EWAHCompressedBitmap ewah = bitmap.getUnderlying();
//				IntIterator it = ewah.intIterator();
//				while(it.hasNext()) {
//					rowIdSink.onRowId(it.next() + rowId);
//				}
//			}
//			rowId += Constants.ROWS_PER_PAGE;
//		}
//		return null;
//	}
//}
