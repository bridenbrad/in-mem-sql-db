/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine;

import java.util.Set;

import com.tirion.db.engine.client.Session;
import com.tirion.db.engine.client.reqres.req.Request;
import com.tirion.db.engine.client.reqres.res.Response;

/**
 * All requests should be processed via engine so
 * that we can do proper coordination (i.e. locking,
 * consistency etc).
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface LeaderEngine extends Engine {
	
	Session connect(String name);
	
	Set<String> getTableNames();
	
	/**
	 * Will inspect request and delegate it
	 * to some specific request handler method.
	 * Will rethrow any underlying exception.
	 */
	Response execute(Request request);
}
