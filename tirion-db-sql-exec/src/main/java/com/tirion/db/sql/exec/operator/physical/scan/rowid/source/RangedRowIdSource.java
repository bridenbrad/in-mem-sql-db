/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;
//
//
///**
// * Will produce row IDs between given start(inclusive) 
// * and end(exclusive) markers.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//@Deprecated
//public final class RangedRowIdSource implements RowIdSource {
//
//	private final long end;
//	
//	private long current;
//	
//	/**
//	 * @param start inclusive
//	 * @param end exclusive
//	 */
//	public RangedRowIdSource(long start, long end) {
//		this.end = end;
//		current = start;
//	}
//	
//	@Override
//	public boolean isEmpty() {
//		return false;
//	}
//
//	@Override
//	public boolean hasNext() {
//		return current < end;
//	}
//
//	@Override
//	public long next() {
//		return current++;
//	}
//}
