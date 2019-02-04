/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.req;

import com.tirion.db.engine.client.AbstractSession;
import com.tirion.db.engine.client.Session;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractRequest implements Request {

	private static final long serialVersionUID = -8748953609453687582L;
	
	private final long id;
	private final Session session;
	
	public AbstractRequest(Session session) {
		this.session = session;
		id = ((AbstractSession)session).nextRequestId();
	}
	
	@Override
	public final long getId() {
		return id;
	}

	@Override
	public final Session getSession() {
		return session;
	}
}
