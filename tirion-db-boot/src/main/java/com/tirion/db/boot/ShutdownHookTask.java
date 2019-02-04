/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ShutdownHookTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(ShutdownHookTask.class);

	@Override
	public void run() {
		log.info("Shutdown hook invoked");
	}
}