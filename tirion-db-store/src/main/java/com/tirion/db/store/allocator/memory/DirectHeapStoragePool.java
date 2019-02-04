/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator.memory;

import java.nio.ByteBuffer;

import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.allocator.AbstractStoragePool;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DirectHeapStoragePool extends AbstractStoragePool {

	@Override
	protected Buffer allocateInternal(Spec spec) {
		try {
			final int size = spec.getType().getWidth() * spec.getCount();
			ByteBuffer buffer = ByteBuffer.allocateDirect(size);
			return new Buffer(spec, 0, size, buffer);
		} catch (Throwable e) {
			throw new RuntimeException("Out of memory for direct byte buffers. Increase value for XX:MaxDirectMemorySize command line parameter");
		}
	}
}
