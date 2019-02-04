/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.req;

import com.tirion.db.engine.client.Session;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TupleListenerAwareTextRequest extends TextRequest {
	
	private static final long serialVersionUID = -9111667431335019169L;
	
	private final TupleListener listener;

	public TupleListenerAwareTextRequest(Session session,
			String text, TupleListener listener) {
		super(session, text);
		this.listener = listener;
	}

	public TupleListener getListener() {
		return listener;
	}	
}
