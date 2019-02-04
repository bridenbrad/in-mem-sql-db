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
public abstract class AbstractResponse implements Response {

	private static final long serialVersionUID = 3264736048659132970L;
	
	private final Status status;
	private final String message;
	private final Throwable exception;
	private final Request source;
		
	/**
	 * Normal status.
	 */
	public AbstractResponse(Request source) {
		this(source, Status.OK, null, null);
	}
	
	public AbstractResponse(Request source, Status status) {
		this(source, status, null, null);
	}

	public AbstractResponse(Request source, Status status, String message) {
		this(source, status, message, null);
	}

	public AbstractResponse(Request source, Status status, String message, Throwable exception) {
		super();
		this.status = status;
		this.message = message;
		this.exception = exception;
		this.source = source;
	}

	@Override
	public final Status getStatus() {
		return status;
	}

	@Override
	public final String getMessage() {
		return message;
	}

	@Override
	public final Throwable getException() {
		return exception;
	}

	@Override
	public final Request getSource() {
		return source;
	}
}
