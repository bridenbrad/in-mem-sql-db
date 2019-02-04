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
public final class LoadSpotTradeRequest extends AbstractRequest {
	
	private static final long serialVersionUID = -3697001476743767480L;
	
	private final int pageCount;
	
	public LoadSpotTradeRequest(Session session, int pageCount) {
		super(session);
		this.pageCount = pageCount;
	}
	
	public int getPageCount() {
		return pageCount;
	}
}
