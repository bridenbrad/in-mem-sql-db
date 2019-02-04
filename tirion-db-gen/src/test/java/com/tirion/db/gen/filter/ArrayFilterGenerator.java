/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen.filter;

import com.tirion.common.type.Type;
import com.tirion.db.gen.VelocityBase;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ArrayFilterGenerator extends VelocityBase {
	
	private static final String PACKAGE = "com/tirion/db/sql/exec/operator/physical/scan/filter/array/";
	private static final String PREFIX = "../tirion-db-sql-exec/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/filter/array-filter-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "ArrayFilter.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "ArrayFilter.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "ArrayFilter.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "ArrayFilter.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "ArrayFilter.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "ArrayFilter.java");
	}
}
