/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.req;

import java.io.Serializable;

import com.tirion.db.engine.client.Session;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Request extends Serializable {
	
	long getId();

	/**
	 * Session to which this request belongs to.
	 */
	Session getSession();
}
