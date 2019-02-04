/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen;

import com.tirion.common.type.Type;

public class RangeAnalyzerGenerator extends VelocityBase {

	private static final String PACKAGE = "com/tirion/db/store/builder/stats/analyzer/range/";
	private static final String PREFIX = "../tirion-db-store/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/store/range-analyzer-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "RangeAnalyzer.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "RangeAnalyzer.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "RangeAnalyzer.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "RangeAnalyzer.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "RangeAnalyzer.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "RangeAnalyzer.java");
	}
}
