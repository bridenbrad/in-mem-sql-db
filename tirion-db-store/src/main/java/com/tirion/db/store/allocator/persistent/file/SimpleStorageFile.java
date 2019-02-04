/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator.persistent.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.file.FileUtil;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleStorageFile implements StorageFile {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleStorageFile.class);

	private final File file;
	private final boolean deleteOnExit;
	private final int fileSize;
	
	private RandomAccessFile raf;
	private MappedByteBuffer mappedBuffer;
	private int index;
	
	public SimpleStorageFile(File file, boolean deleteOnExit, int fileSize) {
		this.file = file;
		this.deleteOnExit = deleteOnExit;
		this.fileSize = fileSize;
		index = 0;
	}

	@Override
	public void init() {
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.setLength(fileSize);
			mappedBuffer = raf.getChannel().map(MapMode.READ_WRITE, 0, fileSize);
			log.info("Initialized storage file '" + getFileName() + "'");
		} catch (Exception e) {
			throw new RuntimeException("Exception while initializing storage file '" + file.getAbsolutePath() + "'", e);
		}
	}

	@Override
	public void shutdown() {
		FileUtil.close(raf);
		if(deleteOnExit) {
			file.delete();
		}
		log.info("Closed storage file '" + getFileName() + "'");
	}
	
	@Override
	public String getFileName() {
		return file.getAbsolutePath();
	}
	
	@Override
	public Buffer allocate(Spec spec) {
		final int size = spec.getType().getWidth() * spec.getCount();
		if((size + index) > fileSize) {
			return null;
		}
		Buffer buffer = new Buffer(spec, index, size, mappedBuffer);
		index += size;
		return buffer;
	}
}
