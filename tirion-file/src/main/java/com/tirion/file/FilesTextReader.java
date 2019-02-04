/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.file;

import java.io.File;
import java.util.List;

import com.tirion.stats.MapStatistics;
import com.tirion.stats.Statistics;
import com.tirion.stats.Statistics.Kind;

public final class FilesTextReader implements TextReader {
	
	private static final int DEFAULT_BUFFER_SIZE = 64 * 1024 * 1024;

	private final int bufferSize;
	private final List<File> files;
	private final TextReader[] readers;
	private int index = 0;
	
	public FilesTextReader(List<File> files) {
		this(files, DEFAULT_BUFFER_SIZE);
	}
	
	public FilesTextReader(List<File> files, int bufferSize) {
		this.bufferSize = bufferSize;
		this.files = files;
		readers = new TextReader[files.size()];
	}

	@Override
	public Statistics getStatistics() {
		MapStatistics stats = new MapStatistics(Kind.FILES_TEXT_READER);
		for (int i = 0; i < readers.length; i++) {
			stats.add(files.get(i).getName(), readers[i].getStatistics());
		}
		return stats;
	}



	@Override
	public void init() {
		for (int i = 0; i < files.size(); i++) {
			readers[i] = new FileTextReader(files.get(i), bufferSize);
		}
		for(TextReader reader : readers) {
			reader.init();
		}
	}

	@Override
	public void shutdown() {
		for(TextReader reader : readers) {
			reader.shutdown();
		}
	}

	@Override
	public String next() {
		while(true) {
			String result = readers[index].next();			
			if(result != null) {
				return result;
			}
			++index;
			if(index == readers.length) {
				break;
			}
		}
		return null;
	}
}
