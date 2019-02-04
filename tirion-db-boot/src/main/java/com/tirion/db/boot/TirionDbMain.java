/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class TirionDbMain {
	
	private static final String DEFAULT_CONFIG_FILE_NAME = "tirion-db-conf.xml";
	
	public static void main(String[] args) throws Exception {
		Thread.currentThread().setName("naxx-main");
		
		registerShutdownHook();
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(DEFAULT_CONFIG_FILE_NAME);
		Thread.sleep(Long.MAX_VALUE); // listen to come global hook etc
		ctx.close();
	}
	
	private static void registerShutdownHook() {
		Thread thread = new Thread(new ShutdownHookTask());
		thread.setName("tirion-db-shutdown-hook");
		Runtime.getRuntime().addShutdownHook(thread);
	}
	
	private TirionDbMain() {
	}
}
