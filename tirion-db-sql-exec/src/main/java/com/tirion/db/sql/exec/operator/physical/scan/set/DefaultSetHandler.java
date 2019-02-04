/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.set;
//package com.tirion.db.sql.query.operator.physical.scan.set;
//
//import java.nio.IntBuffer;
//
//import com.tirion.db.store.storage.Buffer;
//import com.tirion.db.store.storage.BufferPool;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class DefaultSetHandler implements SetHandler {
//
//	private final BufferPool pool;
//
//	public DefaultSetHandler(BufferPool pool) {
//		super();
//		this.pool = pool;
//	}
//
//	@Override
//	public Buffer intersection(Buffer left, Buffer right) {
//		IntBuffer leftBuffer = left.underlying().asIntBuffer();
//		IntBuffer rightBuffer = right.underlying().asIntBuffer();
//		
//		final int leftMaxIndex = leftBuffer.remaining();
//		final int rightMaxIndex = rightBuffer.remaining();
//		
//		Buffer buffer = pool.take(Math.min(leftBuffer.remaining(), rightBuffer.remaining()) * 4); // TODO probabilistic estimate
//		IntBuffer result = buffer.underlying().asIntBuffer();
//		
//		int leftIndex = 0;
//		int rightIndex = 0;
//		while(true) {
//			if(leftIndex >= leftMaxIndex || rightIndex >= rightMaxIndex) {
//				break;
//			}
//			int leftValue = leftBuffer.get(leftIndex);
//			int rightValue = rightBuffer.get(rightIndex);
//			if(leftValue == rightValue) {
//				result.put(leftValue);
//				++leftIndex;
//				++rightIndex;
//			} else if(leftValue < rightValue) {
//				++leftIndex;
//			} else {
//				++rightIndex;
//			}
//		}
//		result.flip();
//		return buffer;
//	}
//
//	@Override
//	public Buffer union(Buffer left, Buffer right) {
//		IntBuffer leftBuffer = left.underlying().asIntBuffer();
//		IntBuffer rightBuffer = right.underlying().asIntBuffer();
//		
//		final int leftMaxIndex = leftBuffer.remaining();
//		final int rightMaxIndex = rightBuffer.remaining();
//		
//		Buffer buffer = pool.take(Math.max(leftBuffer.remaining(), rightBuffer.remaining()) * 4); // TODO probabilistic estimate
//		IntBuffer result = buffer.underlying().asIntBuffer();
//		
//		int leftIndex = 0;
//		int rightIndex = 0;
//		while(true) {
//			if(leftIndex >= leftMaxIndex || rightIndex >= rightMaxIndex) {
//				break;
//			}
//			int leftValue = leftBuffer.get(leftIndex);
//			int rightValue = rightBuffer.get(rightIndex);
//			if(leftValue == rightValue) {
//				result.put(leftValue);
//				++leftIndex;
//				++rightIndex;
//			} else if(leftValue < rightValue) {
//				result.put(leftValue);
//				++leftIndex;
//			} else {
//				result.put(rightValue);
//				++rightIndex;
//			}
//		}
//		while(leftIndex < leftMaxIndex) {
//			result.put(leftBuffer.get(leftIndex++));
//		}
//		while(rightIndex < rightMaxIndex) {
//			result.put(rightBuffer.get(rightIndex++));
//		}
//		result.flip();
//		return buffer;
//	}
//}
