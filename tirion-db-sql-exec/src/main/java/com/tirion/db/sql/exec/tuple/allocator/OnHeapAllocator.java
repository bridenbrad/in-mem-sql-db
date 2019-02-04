/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.allocator;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OnHeapAllocator extends AbstractAllocator {

	public OnHeapAllocator(Entity entity) {
		super(entity);
	}

	@Override
	public Tuple allocate() {
		return new OnHeapTuple(getEntity().fieldCount());
	}
}
