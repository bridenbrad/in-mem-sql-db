/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser;

import java.io.File;

import junit.framework.TestCase;

import com.tirion.common.file.FileUtil;
import com.tirion.db.sql.ast.Node;

public class SimpleParserTest extends TestCase {

	public void test() {
		Parser parser = new SimpleParser();
		parser.init();
		
		String text = FileUtil.readTextFileFully(new File("src/test/resources/statements.sql"));
		String[] statements = text.split(";");
		for(String statement : statements) {
			statement = statement.trim();
			if(statement.equals("")) {
				continue;
			}
			statement = statement + ";";
			try {
				Node node = parser.parse(statement);
				assertNotNull(node);
			} catch (Exception e) {
				throw new RuntimeException("Exception while parsing '" + statement + "'", e);
			}
		}
	}
}
