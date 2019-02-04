/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
///**
// * Copyright © 2013, drekthar
// * All rights reserved.
// *
// * No portion of this file may be reproduced in any form, or by any means, without the prior written
// * consent of the author.
// */
//package com.tirion.db.store.builder.transformation.transformer;
//
//import com.tirion.common.runtime.Runtime;
//import com.tirion.common.sequence.array.Array;
//import com.tirion.db.catalog.model.Entity;
//import com.tirion.db.store.builder.indexer.BmIndexer;
//import com.tirion.db.store.builder.transformation.slice.ArraySlice;
//import com.tirion.db.store.builder.transformation.slice.Slice;
//import com.tirion.executor.job.Job;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class BmIndexTransformer extends AbstractTransformer {
//
//	private final BmIndexer<?>[] indexers;
//	
//	public BmIndexTransformer(Entity entity, BmIndexer<?>[] indexers, Runtime runtime, Job job) {
//		super(entity, runtime, job);
//		this.indexers = indexers;
//	}
//
//	@Override
//	public Slice transform(Slice slice) {
//		return transformInternal((ArraySlice)slice);
//	}
//
//	private Slice transformInternal(ArraySlice slice) {
//		final Array[] arrays = slice.getArrays();
//		for (int i = 0; i < indexers.length; i++) {
//			BmIndexer<?> indexer = indexers[i];
//			if(indexer != null) {
//				indexer.build(slice.getId(), arrays[i]);
//			}
//		}
//		return slice;
//	}
//}
