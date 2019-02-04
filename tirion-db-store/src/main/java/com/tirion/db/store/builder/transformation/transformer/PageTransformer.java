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
//import com.tirion.common.sequence.Sequence;
//import com.tirion.common.sequence.Sequence.Kind;
//import com.tirion.common.sequence.array.Array;
//import com.tirion.common.sequence.buffer.Buffer;
//import com.tirion.db.catalog.model.Entity;
//import com.tirion.db.store.builder.transformation.slice.CompressionAwareSlice;
//import com.tirion.db.store.builder.transformation.slice.PageSlice;
//import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
//import com.tirion.db.store.builder.transformation.slice.Slice;
//import com.tirion.db.store.page.Page;
//import com.tirion.db.store.page.offheap.NonTokenizedCompressedOffHeapPage;
//import com.tirion.db.store.page.offheap.NonTokenizedUnCompressedOffHeapPage;
//import com.tirion.db.store.page.offheap.TokenizedCompressedOffHeapPage;
//import com.tirion.db.store.page.offheap.TokenizedUnCompressedOffHeapPage;
//import com.tirion.db.store.page.onheap.NonTokenizedCompressedOnHeapPage;
//import com.tirion.db.store.page.onheap.NonTokenizedUnCompressedOnHeapPage;
//import com.tirion.db.store.page.onheap.TokenizedCompressedOnHeapPage;
//import com.tirion.db.store.page.onheap.TokenizedUnCompressedOnHeapPage;
//import com.tirion.executor.job.Job;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class PageTransformer extends AbstractTransformer {
//
//	public PageTransformer(Entity entity, Runtime runtime, Job job) {
//		super(entity, runtime, job);
//	}
//
//	@Override
//	public Slice transform(Slice slice) {
////		return transformInternal((SequenceSlice)slice);
//	}
//	
//	private PageSlice transformInternal(SequenceSlice slice) {
//		Sequence[] sequences = slice.getSequences();
//		Page[] pages = new Page[sequences.length];
//		for (int i = 0; i < sequences.length; i++) {
//			Sequence sequence = sequences[i];
//			if(sequence.getKind() == Kind.ARRAY) {
//				pages[i] = transformInternalOnHeap(i, (Array)sequence, slice);
//			} else {
//				pages[i] = transformInternalOffHeap(i, (Buffer)sequence, slice);
//			}
//		}
//		return new PageSlice(slice.getId(), pages);
//	}
//	
//	private Page transformInternalOnHeap(int index, Array array, CompressionAwareSlice slice) {
//		Page page = null;
//		if(shouldBeTokenized(index)) {
//			if(slice.isCompressed(index)) {
//				page = new TokenizedCompressedOnHeapPage(null, array.getNullBitmap(), (byte[]) array.getUnderlying());
//			} else {
//				page = new TokenizedUnCompressedOnHeapPage(null, array.getNullBitmap(), array);
//			}
//		} else {
//			if(slice.isCompressed(index)) {
//				page = new NonTokenizedCompressedOnHeapPage(null, array.getNullBitmap(), (byte[]) array.getUnderlying());
//			} else {
//				page = new NonTokenizedUnCompressedOnHeapPage(null, array.getNullBitmap(), array);
//			}
//		}
//		return page;
//	}
//	
//	private Page transformInternalOffHeap(int index, Buffer buffer, CompressionAwareSlice slice) {
//		Page page = null;
//		if(shouldBeTokenized(index)) {
//			if(slice.isCompressed(index)) {
//				page = new TokenizedCompressedOffHeapPage(null, buffer.getNullBitmap(), buffer);
//			} else {
//				page = new TokenizedUnCompressedOffHeapPage(null, buffer.getNullBitmap(), buffer);
//			}
//		} else {
//			if(slice.isCompressed(index)) {
//				page = new NonTokenizedCompressedOffHeapPage(null, buffer.getNullBitmap(), buffer);
//			} else {
//				page = new NonTokenizedUnCompressedOffHeapPage(null, buffer.getNullBitmap(), buffer);
//			}
//		}
//		return page;
//	}
//}
