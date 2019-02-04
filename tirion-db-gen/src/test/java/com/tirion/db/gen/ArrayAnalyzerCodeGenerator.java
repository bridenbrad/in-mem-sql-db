/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.codegen;
//
//import org.apache.velocity.VelocityContext;
//
//import com.tirion.common.type.Type;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class ArrayAnalyzerCodeGenerator extends VelocityBase {
//
//	private static final String PREFIX = "./src/main/java/com/tirion/store/loader/stats/";
//	
//	@Override
//	protected String getTemplateFileName() {
//		return "./src/main/velocity/store/array-analyzer-template.vm";
//	}
//	
//	public void test() throws Exception {
//		generate(Type.BYTE, "./src/main/java/com/tirion/store/loader/stats/ByteAnalyzer.java");
//		generate(Type.BYTE.getLargeTypeName(), "short", "Short", "SHORT", "./src/main/java/com/tirion/store/loader/stats/ShortAnalyzer.java");
//		generate(Type.BYTE.getLargeTypeName(), "int", "Integer", "INT", "./src/main/java/com/tirion/store/loader/stats/IntegerAnalyzer.java");
//		generate(Type.BYTE.getLargeTypeName(), "long", "Long", "LONG", "./src/main/java/com/tirion/store/loader/stats/LongAnalyzer.java");
//		generate("Float", "float", "Float", "FLOAT", "./src/main/java/com/tirion/store/loader/stats/FloatAnalyzer.java");
//		generate("Double", "double", "Double", "DOUBLE", "./src/main/java/com/tirion/store/loader/stats/DoubleAnalyzer.java");
//	}
//}
