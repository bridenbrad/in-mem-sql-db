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
public final class PageFilterGenerator extends VelocityBase {

	private static final String PACKAGE = "com/tirion/db/sql/exec/operator/physical/scan/filter/page/";
	private static final String PREFIX = "../tirion-db-sql-exec/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/filter/page-filter-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "PageFilter.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "PageFilter.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "PageFilter.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "PageFilter.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "PageFilter.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "PageFilter.java");
	}
}
