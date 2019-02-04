/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.demo;

import com.tirion.db.engine.client.Session;
import com.tirion.db.engine.client.reqres.req.AbstractRequest;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LoadTradeRequest extends AbstractRequest {

	private static final long serialVersionUID = 2706766112921343124L;
	
	private final int pageCount;

	public LoadTradeRequest(Session session, int pageCount) {
		super(session);
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return pageCount;
	}
}
