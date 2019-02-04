/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

import com.tirion.common.Util;
import com.tirion.common.file.FileUtil;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.demo.nullify.PercentNullDecider;
import com.tirion.db.demo.source.GenericTextSource;
import com.tirion.db.demo.source.TextSource;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DsvTextGenerator {

	private static final String RESOURCE_NAME = "dsv-generator.properties";

	public void generate(Entity entity, int rowCount) {
		try {
			generateInternal(entity, rowCount);
		} catch (Exception e) {
			throw new RuntimeException("Exception while generating DSV file for entity '" 
					+ entity.getName() + "' with row count '" + rowCount + "'", e);
		}
	}
	
	private void generateInternal(Entity entity, int rowCount) throws Exception {
		Properties props = Util.loadProps(RESOURCE_NAME);

		String nullString = props.getProperty("null.string");
		String valueSeparator = props.getProperty("value.separator");
		File directory = new File(props.getProperty("output.directory"));
		String middleString = props.getProperty("middle.string");
		int nullPercent = Integer.parseInt(props.getProperty("null.percent"));

		FileUtil.ensureDirExists(directory);

		TextSource textSource = new GenericTextSource(rowCount, entity,
				middleString, new PercentNullDecider(nullPercent),
				valueSeparator, nullString);

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(directory, entity.getName() + ".txt")));
			while (true) {
				String line = textSource.next();
				if (line == null) {
					break;
				}
				writer.write(line + "\n");
			}
		} finally {
			FileUtil.close(writer);
		}		
	}
}
