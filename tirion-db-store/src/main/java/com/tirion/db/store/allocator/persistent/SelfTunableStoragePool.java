/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator.persistent;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.allocator.StoragePool;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SelfTunableStoragePool implements StoragePool {

	@Override
	public void init() {
		throw new NotYetImplementedException();
	}

	@Override
	public void shutdown() {
		throw new NotYetImplementedException();
	}
	
	@Override
	public Buffer allocate(Spec spec) {
		throw new NotYetImplementedException();
	}
}
