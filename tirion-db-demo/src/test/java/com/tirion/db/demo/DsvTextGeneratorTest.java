/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.demo;

import junit.framework.TestCase;

import com.tirion.db.demo. models.Trade;

public class DsvTextGeneratorTest extends TestCase {

	public void test() {
		DsvTextGenerator generator = new DsvTextGenerator();
		generator.generate(Trade.ENTITY, 100);
	}
}
