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
public final class GroupingPartCodeGenerator extends VelocityBase {

	private static final String PREFIX = "./src/main/java/com/tirion/db/sql/command/select/operator/physical/common/key/part/";

	@Override
	protected String getTemplateFileName() {
		return "./src/main/velocity/grouping-part-template.vm";
	}
	
	public void test() throws Exception {
//		generate(Type.BOOLEAN, PREFIX + Type.BOOLEAN.getLargeTypeName() + "Part.java");
		generate(Type.BYTE, PREFIX + Type.BYTE.getLargeTypeName() + "Part.java");
		generate(Type.SHORT, PREFIX + Type.SHORT.getLargeTypeName() + "Part.java");
		generate(Type.INT, PREFIX + Type.INT.getLargeTypeName() + "Part.java");
		generate(Type.LONG, PREFIX + Type.LONG.getLargeTypeName() + "Part.java");
		generate(Type.FLOAT, PREFIX + Type.FLOAT.getLargeTypeName() + "Part.java");
		generate(Type.DOUBLE, PREFIX + Type.DOUBLE.getLargeTypeName() + "Part.java");
//		generate(Type.STRING, PREFIX + Type.STRING.getLargeTypeName() + "Part.java");
	}
}
