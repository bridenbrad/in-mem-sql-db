/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.res;

import java.io.Serializable;

import com.tirion.db.engine.client.reqres.req.Request;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Response extends Serializable {
	
	public static enum Status {
		OK,
		WARN,
		ERROR,
	}
	
	Status getStatus();

	/**
	 * Such as error message.
	 */
	String getMessage();
	
	Throwable getException();
	
	/**
	 * Source of this response.
	 */
	Request getSource();
}
