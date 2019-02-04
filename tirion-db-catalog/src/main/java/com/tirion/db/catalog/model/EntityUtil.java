/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.tirion.common.Util;
import com.tirion.common.file.FileUtil;
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.options.Options;

public abstract class EntityUtil {

	public static Entity parse(File file) {
		try {
			return parseInternal(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Entity parseInternal(File file) throws Exception {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			
			String entityName = null;
			List<Field> fields = new ArrayList<Field>();
			String line = null;
			int index = 0;
			while((line = reader.readLine()) != null) {
				if(entityName == null) {
					entityName = line;
					continue;
				}
				fields.add(parseField(line, entityName, index++));
			}
			SimpleEntity entity = new SimpleEntity(entityName);
			entity.append(fields);
			return entity;
		} finally {
			FileUtil.close(reader);
		}
	}
	
	private static Field parseField(String line, String owner, int index) {
		String[] parts = line.split(",");
		return new SimpleField(parts[0], 
				owner, Type.parseFromString(parts[1]), index, 
				Options.buildFrom(Boolean.parseBoolean(parts[2]), 
				Util.trimQuotes(parts[3])));
	}
	
	private EntityUtil() {
	}
}
