/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.index.bm;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.googlecode.javaewah.EWAHCompressedBitmap;
//import com.tirion.common.bitmap.Bitmap;
//import com.tirion.common.bitmap.Bitmaps;
//import com.tirion.common.bitmap.CompressedBitmap;
//import com.tirion.common.bitmap.LazyCompressedBitmap;
//import com.tirion.common.io.ByteBufferDataOutput;
//import com.tirion.common.type.Type;
//import com.tirion.db.store.appender.Appender;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class OffHeapBmIndex<T> extends AbstractBmIndex<T> {
//	
//	public OffHeapBmIndex(Runtime runtime) {
//		super(runtime);
//	}
//	
//	@Override
//	public Appender<Map<T, Bitmaps>> getAppender() {
//		return new OffHeapBmIndexAppender();
//	}
//
//	/**
//	 * Will registed all bitmaps for new values automatically.
//	 */
//	private final class OffHeapBmIndexAppender implements Appender<Map<T, Bitmaps>> {
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
//				Bitmaps bitmaps = getMap().get(entry.getKey());
//				if(bitmaps == null) {
//					registerValue(entry.getKey());
//					bitmaps = getMap().get(entry.getKey());
//				}
//				for(Bitmap bitmap : entry.getValue().get()) {
//					if(bitmap == null) {
//						bitmaps.append((Bitmap)null);
//						continue;
//					}
//					EWAHCompressedBitmap ewah = null;
//					if(bitmap.getKind() == Kind.ARRAY) {
//						ewah = new CompressedBitmap((boolean[])bitmap.getUnderlying()).getUnderlying();
//					} else {
//						ewah = bitmap.getUnderlying();
//					}
////					byte[] data = serialize(ewah);
////					Buffer buffer = getRuntime().allocator().allocate(Type.BYTE, data.length);
////					ByteBuffer underlying = buffer.getUnderlying();
////					for (int i = 0; i < data.length; i++) { // TODO make this faster
////						underlying.put(buffer.getPosition() + i, data[i]);
////					}
//					Buffer buffer = getRuntime().allocator().allocate(Type.BYTE, ewah.serializedSizeInBytes());
//					serialize(ewah, buffer);
//					bitmaps.append(new LazyCompressedBitmap(buffer));
//				}
//				if(newPageCount == 0) {
//					newPageCount = entry.getValue().getCount();
//				}
//			}
//			setPageCount(getPageCount() + newPageCount);
//			
//			// now adjust page count for all entries that were not impacted by this append
//			for(Entry<T, Bitmaps> entry : getMap().entrySet()) {
//				Bitmaps bitmaps = entry.getValue();
//				while(bitmaps.getCount() < getPageCount()) {
//					bitmaps.append((Bitmap)null);
//				}
//			}
//		}
//	}
//	
//	private void serialize(EWAHCompressedBitmap ewah, Buffer buffer) {
//		try {
//			ewah.serialize(new ByteBufferDataOutput(buffer.getPosition(), buffer.getUnderlying()));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
////	private byte[] serialize(EWAHCompressedBitmap ewah) {
////		try {
////			ByteArrayOutputStream baos = new ByteArrayOutputStream(ewah.serializedSizeInBytes());
////			DataOutputStream dos = new DataOutputStream(baos);
////			ewah.serialize(dos);
////			return baos.toByteArray();
////		} catch (IOException e) {
////			throw new RuntimeException(e);
////		}
////	}
//}
