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
public final class BmIndexerCodeGenerator extends VelocityBase {

	private static final String PREFIX = "./src/main/java/com/tirion/db/store/loader/indexer/";
	
	@Override
	protected String getTemplateFileName() {
		return "./src/main/velocity/index/bm-index-builder-template.vm";
	}

	public void test() throws Exception {
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "BmIndexer.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "BmIndexer.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "BmIndexer.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "BmIndexer.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "BmIndexer.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "BmIndexer.java");
//		generate(Type.STRING, PREFIX + Type.STRING.getLargeTypeName() + "BmIndexer.java");
	}
}
