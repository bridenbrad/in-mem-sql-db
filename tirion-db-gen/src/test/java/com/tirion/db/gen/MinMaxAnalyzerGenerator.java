/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen;

import com.tirion.common.type.Type;

public final class MinMaxAnalyzerGenerator extends VelocityBase {

	private static final String PACKAGE = "com/tirion/db/store/builder/stats/analyzer/minmax/";
	private static final String PREFIX = "../tirion-db-store/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/store/min-max-analyzer-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "MinMaxAnalyzer.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "MinMaxAnalyzer.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "MinMaxAnalyzer.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "MinMaxAnalyzer.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "MinMaxAnalyzer.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "MinMaxAnalyzer.java");
	}
}
