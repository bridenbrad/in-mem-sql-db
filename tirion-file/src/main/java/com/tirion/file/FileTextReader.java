/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.stats.Statistics;

public final class FileTextReader implements TextReader {
	
	private static final Logger log = LoggerFactory.getLogger(FileTextReader.class);
	
	private final File file;
	private final int bufferSize;
	
	private BufferedReader reader;
	
	private int lineCount;
	private long charCount;
	
	public FileTextReader(File file, int bufferSize) {
		this.file = file;
		this.bufferSize = bufferSize;
	}
	
	@Override
	public Statistics getStatistics() {
		return new FileTextStatistics(lineCount, charCount);
	}

	@Override
	public void init() {
		try {
			reader = new BufferedReader(new FileReader(file), bufferSize);
			log.info("Opened DSV reader for file '" + file.getAbsolutePath() + "'");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutdown() {
		try {
			reader.close();
			log.info("Closed DSV reader for file '" + file.getAbsolutePath() + "'");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String next() {
		try {
			String result = reader.readLine();
			if(result != null) {
				++lineCount;
				charCount += result.length();
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Exception while reading line from file '" 
					+ file.getAbsolutePath() + "'", e);
		}
	}
}
