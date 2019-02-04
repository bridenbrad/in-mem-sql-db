/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client;

import java.io.Serializable;

import com.tirion.db.common.Config;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Session extends Serializable {
	
	long getId();
	
	Config getConfig();
	
	/**
	 * Should be called when client disconnects.
	 */
	void close();
}
