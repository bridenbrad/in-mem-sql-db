/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.res;

import java.util.List;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.engine.client.reqres.req.Request;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;

/**
 * Should be used when {@link TupleListener} is not provided.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BatchSelectResponse extends AbstractResponse {

	private static final long serialVersionUID = -4142138082535462853L;
	
	private final Entity entity;
	private final List<Object[]> tuples;
	
	public BatchSelectResponse(Request source, Status status, Entity entity, List<Object[]> tuples) {
		this(source, status, null, null, entity, tuples);
	}
	
	public BatchSelectResponse(Request source, Status status, String message, 
			Exception exception, Entity entity, List<Object[]> tuples) {
		super(source, status, message, exception);
		this.entity = entity;
		this.tuples = tuples;
	}

	public Entity getEntity() {
		return entity;
	}

	public List<Object[]> getTuples() {
		return tuples;
	}
	
	public int getTupleCount() {
		return tuples.size();
	}
}
