/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.query;

import java.io.File;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class JsonLoggingQueryListener implements QueryListener {

	private static final Logger log = LoggerFactory.getLogger(JsonLoggingQueryListener.class);
	
	private final File dir;
	private final ObjectMapper mapper;
	
	public JsonLoggingQueryListener(String dirName) {
		dir = new File(dirName);
		dir.mkdirs();
		log.info("Using " + dir.getAbsolutePath() + " to log query plans");
		mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_DETECT_GETTERS, false);
		mapper.configure(Feature.AUTO_DETECT_IS_GETTERS, false);
		mapper.configure(Feature.AUTO_DETECT_FIELDS, false);
//		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	public void onQuery(Query query) {
		String fileName = query.getNiceName() + ".json"; // query-plan-sessionid-requestid
		File file = new File(dir, fileName);
		if(file.exists()) {
			log.warn("File '" + file.getAbsolutePath() + "' already exists, gonna overwrite");
		}
		writeToFile(file, query);
	}
	
	private void writeToFile(File file, Query query) {
		try {
			mapper.writeValue(file, query);
		} catch (Throwable e) {
			log.warn("Exception while writing query plan for '" + query.getQueryId()
					+ "' to file '" + file.getAbsolutePath() + "'", e);
		}
	}
}
