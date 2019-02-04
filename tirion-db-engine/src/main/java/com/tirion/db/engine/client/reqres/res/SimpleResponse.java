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
public final class SimpleResponse extends AbstractResponse {
	
	private static final long serialVersionUID = -3449682346703607480L;

	/**
	 * OK status.
	 */
	public SimpleResponse(Request source) {
		super(source);
	}
	
	public SimpleResponse(Request source, Status status) {
		super(source, status);
	}
	
	public SimpleResponse(Request source, Status status, String message) {
		super(source, status, message);
	}
	
	public SimpleResponse(Request source, Status status, String message, Throwable exception) {
		super(source, status, message, exception);
	}
}
