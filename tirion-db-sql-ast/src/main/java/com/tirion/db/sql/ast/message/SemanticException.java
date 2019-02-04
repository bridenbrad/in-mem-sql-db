/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.message;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SemanticException extends RuntimeException {

	private static final long serialVersionUID = 4060515724666506871L;

	public SemanticException() {
		super();
	}

	public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SemanticException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemanticException(String message) {
		super(message);
	}

	public SemanticException(Throwable cause) {
		super(cause);
	}
}
