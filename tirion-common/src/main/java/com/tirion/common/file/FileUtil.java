/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class FileUtil {

	public static void ensureDirExists(File directory) {
		if(!directory.isDirectory()) {
			throw new IllegalArgumentException("Path '" + directory.getAbsolutePath() + "' is not a directory");
		}
		if(!directory.exists()) {
			throw new IllegalArgumentException("Directory '" + directory.getAbsolutePath()+  "' does not exist");
		}
	}
	
	/**
	 * All files.
	 */
	public static final FileFilter FILE_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile();
		}
	};
	
	/**
	 * All directories.
	 */
	public static final FileFilter DIRECTORY_FILE_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};
	
	public static List<File> asFiles(Collection<String> fileNames) {
		List<File> files = new ArrayList<File>();
		for(String fileName : fileNames) {
			File file = new File(fileName);
			if(!file.exists()) {
				throw new IllegalArgumentException("File '" + fileName + "' does not exist");
			}
			files.add(file);
		}
		return files;
	}
	
	/**
	 * Add File.separator between each two strings, and
	 * append one at the end.
	 */
	public static String buildPath(String...strings) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			buffer.append(strings[i]).append(File.separator);
		}
		return buffer.toString();
	}

	/**
	 * Replace dots with File.separator. <p>
	 * 
	 * com.tirion.db => com/tirion on Linux
	 */
	public static String asFileSystemPath(String packageName) {
		return packageName.replaceAll("\\.", File.separator);
	}
	
	public static void copyTextFile(File input, File output) {
		writeTextFileFully(output, readTextFileFully(input));
	}
	
	public static byte[] readBinaryFileFully(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(true) {
				int bite = is.read();
				if(bite == -1) {
					break;
				}
				baos.write(bite);
			}
			return baos.toByteArray();
		} catch(IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(is);
		}
	}
	
	public static List<String> readTextLines(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			List<String> lines = new LinkedList<String>();
			while((line = reader.readLine()) != null) {
				lines.add(line);
			}
			return lines;
		} catch(IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(reader);
		}
	}
	
	/**
	 * Will skip lines starting with //
	 */
	public static String readTextFileFully(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder buffer = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				if(!line.startsWith("//")) {					
					buffer.append(line).append('\n');
				}
			}
			return buffer.toString();
		} catch(IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(reader);
		}
	}
	
	public static void writeTextFileFully(File file, String value) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(value);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			close(writer);
		}
	}
	
	public static void close(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			} catch (Throwable e) {
				// swallow
			}
		}
	}
	
	private FileUtil() {
	}
}
