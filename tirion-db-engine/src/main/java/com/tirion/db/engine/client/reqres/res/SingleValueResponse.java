/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.res;

import com.tirion.db.engine.client.reqres.req.Request;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SingleValueResponse extends AbstractResponse {
	
	private static final long serialVersionUID = 1425608970761848859L;
	
	private final Object value;

	public SingleValueResponse(Request source, Object value) {
		super(source);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
