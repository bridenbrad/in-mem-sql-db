/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NotYetImplementedException extends RuntimeException {

	private static final long serialVersionUID = -7175118574678457557L;

	public NotYetImplementedException() {
		super();
	}

	public NotYetImplementedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotYetImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotYetImplementedException(String message) {
		super(message);
	}

	public NotYetImplementedException(Throwable cause) {
		super(cause);
	}
}
