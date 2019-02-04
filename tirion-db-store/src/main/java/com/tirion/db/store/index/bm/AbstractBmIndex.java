/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.index.bm;
//
//import java.util.LinkedList;
//import java.util.SortedSet;
//import java.util.TreeMap;
//
//import com.tirion.common.bitmap.Bitmap;
//import com.tirion.common.bitmap.Bitmaps;
//import com.tirion.db.store.index.bm.task2.BitmapsMasterTask;
//import com.tirion.db.store.index.bm.task2.BitmapsMasterTask.OrEwahBitmapsMasterTask;
//import com.tirion.db.store.index.bm.task2.RowIdProjectingMasterTask;
//import com.tirion.executor.Job;
//
///**
// * Value null present in the list of bitmaps for given value means
// * that all row IDs are zero.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public abstract class AbstractBmIndex<T> implements BmIndex<T> {
//
//	private int pageCount;
//	private final TreeMap<T, Bitmaps> map;
//	private final Runtime runtime;
//	
//	public AbstractBmIndex(Runtime runtime) {
//		this.runtime = runtime;
//		map = new TreeMap<T, Bitmaps>();
//		pageCount = 0;
//	}
//
//	protected final Runtime getRuntime() {
//		return runtime;
//	}
//	
//	protected final void setPageCount(int pageCount) {
//		this.pageCount = pageCount;
//	}
//
//	protected final TreeMap<T, Bitmaps> getMap() {
//		return map;
//	}
//
////	@Override
////	public final Summary getSummary() {
////		Map<String, Object> map = new HashMap<String, Object>();
////		map.put("pageCount", pageCount);
////		map.put("keys", this.map.keySet());
////		map.put("sizeInBytes", sizeInBytes());
////		return new SimpleSummary(map);
////	}
//	
//	@Override
//	public final long sizeInBytes() {
//		long bitmapSize = 0;
//		for(Bitmaps list : map.values()) {
//			bitmapSize += list.getCount() * 8;
//			for(Bitmap bitmap : list.get()) {
//				if(bitmap != null) {					
//					bitmapSize += bitmap.sizeInBytes();
//				}
//			}
//		}
//		return 4 + 8 + map.size() * 8 * 2 + bitmapSize;
//	}
//	
//	@Override
//	public final IndexKind getKind() {
//		return IndexKind.BM;
//	}
//
//	@Override
//	public final int getPageCount() {
//		return pageCount;
//	}
//
//	@Override
//	public final int getEntryCount() {
//		return map.size();
//	}
//
//	@Override
//	public final boolean hasValue(T value) {
//		return map.containsKey(value);
//	}
//	
//	@Override
//	public final void registerValue(T value) {
//		if(getEntryCount() == BmIndex.MAX_KEY_COUNT) {
//			throw new IllegalArgumentException("BM index already has " + BmIndex.MAX_KEY_COUNT + " keys, can not add '" + value + "'");
//		}
//		if(hasValue(value)) {
//			throw new IllegalArgumentException("Value '" + value + "' is already present in BM index");
//		}
//		Bitmaps bitmaps = new Bitmaps(pageCount);
//		for (int i = 0; i < pageCount; i++) {
//			bitmaps.append((Bitmap)null);
//		}
//		map.put(value, bitmaps);
//	}
//	
//	@Override
//	public final RowIdSource eq(T value, Job job) {
//		return projectRowIds(getEq(value, job), job);
//	}
//
//	@Override
//	public final RowIdSource neq(T value, Job job) {
//		return projectRowIds(getNeq(value, job), job);
//	}
//
//	@Override
//	public final RowIdSource lt(T value, Job job) {
//		return projectRowIds(getLt(value, job), job);
//	}
//
//	@Override
//	public final RowIdSource ltEq(T value, Job job) {
//		return projectRowIds(getLtEq(value, job), job);
//	}
//
//	@Override
//	public final RowIdSource gt(T value, Job job) {
//		return projectRowIds(getGt(value, job), job);
//	}
//
//	@Override
//	public final RowIdSource gtEq(T value, Job job) {
//		return projectRowIds(getGtEq(value, job), job);
//	}
//	
//	@Override
//	public final RowIdSource between(T low, T high, Job job) {
//		return projectRowIds(getBetween(low, high, job), job);
//	}
//	
//	@Override
//	public final Bitmaps getEq(T value, Job job) {
//		return map.get(value);
//	}
//
//	@Override
//	public final Bitmaps getNeq(T value, Job job) {
//		try {
//			LinkedList<Bitmaps> bitmaps = new LinkedList<Bitmaps>();
//			for(T key : map.keySet()) {
//				if(!key.equals(value)) {
//					bitmaps.add(map.get(key));
//				}
//			}
//			BitmapsMasterTask task = 
//					new OrEwahBitmapsMasterTask(job, pageCount, bitmaps);
//			return task.call();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	public final Bitmaps getLt(T value, Job job) {
//		return orMergeBitmaps(new LinkedList<Bitmaps>(map.headMap(value).values()), job);
//	}
//
//	@Override
//	public final Bitmaps getLtEq(T value, Job job) {
//		return orMergeBitmaps(new LinkedList<Bitmaps>(map.headMap(value, true).values()), job);
//	}
//
//	@Override
//	public final Bitmaps getGt(T value, Job job) {
//		return orMergeBitmaps(new LinkedList<Bitmaps>(map.tailMap(value, false).values()), job);
//	}
//
//	@Override
//	public final Bitmaps getGtEq(T value, Job job) {
//		return orMergeBitmaps(new LinkedList<Bitmaps>(map.tailMap(value, true).values()), job);
//	}
//
//	@Override
//	public final Bitmaps getBetween(T low, T high, Job job) {
//		return orMergeBitmaps(new LinkedList<Bitmaps>(map.subMap(low, true, high, true).values()), job);
//	}
//	
//	@Override
//	public final Bitmaps getIn(SortedSet<T> set, Job job) {
//		try {
//			LinkedList<Bitmaps> lists = new LinkedList<Bitmaps>();
//			for(T value : set) {
//				Bitmaps bitmaps = getEq(value, job);
//				if(bitmaps != null) {				
//					lists.add(bitmaps);
//				}
//			}
//			if(lists.isEmpty()) {
//				return null;
//			}
//			BitmapsMasterTask task = 
//					new BitmapsMasterTask.OrEwahBitmapsMasterTask(job, pageCount, lists);
//			return task.call();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	public final RowIdSource in(SortedSet<T> set, Job job) {
//		return projectRowIds(getIn(set, job), job);
//	}
//	
//	private RowIdSource projectRowIds(Bitmaps bitmaps, Job job) {
//		try {
//			if(bitmaps == null || bitmaps.getCount() == 0) {
//				return new EmptyRowIdSource();
//			}
//			RowIdProjectingMasterTask task = new RowIdProjectingMasterTask(job, bitmaps);
//			return task.call();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	private Bitmaps orMergeBitmaps(LinkedList<Bitmaps> bitmaps, Job job) {
//		try {
//			BitmapsMasterTask task = new OrEwahBitmapsMasterTask(job, pageCount, bitmaps);
//			return task.call();
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
