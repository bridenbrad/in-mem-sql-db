/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.index.bm;
//
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.tirion.common.bitmap.Bitmap;
//import com.tirion.common.bitmap.Bitmaps;
//import com.tirion.db.store.appender.Appender;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class OnHeapBmIndex<T> extends AbstractBmIndex<T> {
//
//	public OnHeapBmIndex(Runtime runtime) {
//		super(runtime);
//	}
//
//	@Override
//	public Appender<Map<T, Bitmaps>> getAppender() {
//		return new OnHeapBmIndexAppender();
//	}
//
//	/**
//	 * Will registed all bitmaps for new values automatically.
//	 */
//	private final class OnHeapBmIndexAppender implements Appender<Map<T, Bitmaps>> {
//
//		@Override
//		public void begin() {
//		}
//
//		@Override
//		public void rollback() {
//		}
//
//		@Override
//		public void commit() {
//		}
//
//		@Override
//		public void append(Map<T, Bitmaps> newMap) {
//			int newPageCount = 0;
//			for(java.util.Map.Entry<T, Bitmaps> entry : newMap.entrySet()) {
//				Bitmaps list = getMap().get(entry.getKey());
//				if(list == null) {
//					registerValue(entry.getKey());
//					list = getMap().get(entry.getKey());
//				}
//				for(Bitmap bitmap : entry.getValue().get()) {
//					list.append((Bitmap)bitmap);
//				}
//				if(newPageCount == 0) {
//					newPageCount = entry.getValue().getCount();
//				}
//			}
//			setPageCount(getPageCount() + newPageCount);
//			
//			// now adjust page count for all entries that were not impacted by this append
//			for(Entry<T, Bitmaps> entry : getMap().entrySet()) {
//				Bitmaps list = entry.getValue();
//				while(list.getCount() < getPageCount()) {
//					list.append((Bitmap)null);
//				}
//			}
//		}
//	}
//}
