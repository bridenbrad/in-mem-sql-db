/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.io.File;

import junit.framework.TestCase;

public class EntityUtilTest extends TestCase {

	public void test() {
		Entity entity = EntityUtil.parse(new File("src/test/resources/trade.properties"));
		System.out.println(entity);
	}
}
