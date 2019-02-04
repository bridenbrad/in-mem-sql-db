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
public final class TokenizerGenerator extends VelocityBase {

	private static final String PACKAGE = "com/tirion/db/store/builder/tokenizer/";
	private static final String PREFIX = "../tirion-db-store/src/main/java/" + PACKAGE;
	
	@Override
	protected String getTemplateFileName() {
		return "./src/test/resources/store/tokenizer-template.vm";
	}

	public void test() throws Exception {
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "Tokenizer.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "Tokenizer.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "Tokenizer.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "Tokenizer.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "Tokenizer.java");
	}
}
