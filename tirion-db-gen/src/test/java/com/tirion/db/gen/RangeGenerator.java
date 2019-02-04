/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen;

import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class RangeGenerator extends VelocityBase {

	private static final String PACKAGE = "com/tirion/db/store/builder/stats/model/range/";
	private static final String PREFIX = "../tirion-db-store/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/store/range-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "Range.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "Range.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "Range.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "Range.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "Range.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "Range.java");
	}
}
