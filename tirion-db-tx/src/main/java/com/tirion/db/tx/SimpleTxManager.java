/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.tx;

import java.util.concurrent.atomic.AtomicLong;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.tx.Tx.Kind;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTxManager implements TxManager {

	private final AtomicLong txCounter = new AtomicLong();
	
	private Runtime runtime;
	
	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public Tx newTx(Kind kind) {
		throw new NotYetImplementedException();
//		return new SimpleTx(txCounter.getAndIncrement(), kind, runtime);
	}
}
