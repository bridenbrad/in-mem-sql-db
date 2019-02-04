/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.common.runtime;
//
//import java.util.concurrent.atomic.AtomicLong;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.tx.Tx;
//import com.tirion.profiler.statistics.Statistics;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SimpleJob implements TxJob {
//
//	private final long id;
//	private final AtomicLong taskId = new AtomicLong();
//	private final Runtime runtime;
//	private Tx tx;
//	
//	public SimpleJob(long id, Runtime runtime) {
//		super();
//		this.id = id;
//		this.runtime = runtime;
//	}
//	
//	public SimpleJob(long id, Tx tx, Runtime runtime) {
//		super();
//		this.id = id;
//		this.runtime = runtime;
//		this.tx = tx;
//	}
//	
//	@Override
//	public void register(Statistics stats) {
//		throw new NotYetImplementedException();
//	}
//
//	@Override
//	public long getId() {
//		return id;
//	}
//
//	@Override
//	public long nextTaskId() {
//		return taskId.getAndIncrement();
//	}
//
//	@Override
//	public Runtime getRuntime() {
//		return runtime;
//	}
//
//	@Override
//	public Tx getTx() {
//		if(tx == null) {
//			throw new IllegalStateException("Job context is not transaction");
//		}
//		return tx;
//	}
//}
