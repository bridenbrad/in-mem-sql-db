/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.gen.misc;

import com.tirion.common.type.Type;
import com.tirion.db.gen.VelocityBase;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MapperCodeGenerator extends VelocityBase {

	private static final String PREFIX = "./src/main/java/com/tirion/db/common/buffer/mapper/";
	
	@Override
	protected String getTemplateFileName() {
		return "./src/main/velocity/misc/mapper-template.vm";
	}

	public void test() throws Exception {
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "Mapper.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "Mapper.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "Mapper.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "Mapper.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "Mapper.java");
	}
}
