/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.allocator;

import com.tirion.db.catalog.model.Entity;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractAllocator implements Allocator {

	private final Entity entity;

	public AbstractAllocator(Entity entity) {
		this.entity = entity;
	}

	protected final Entity getEntity() {
		return entity;
	}
}
