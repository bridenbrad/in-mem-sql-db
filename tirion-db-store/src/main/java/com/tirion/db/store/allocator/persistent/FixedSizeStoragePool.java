/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator.persistent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.allocator.AbstractStoragePool;
import com.tirion.db.store.allocator.persistent.file.SimpleStorageFile;
import com.tirion.db.store.allocator.persistent.file.StorageFile;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FixedSizeStoragePool extends AbstractStoragePool {
	
	private static final Logger log = LoggerFactory.getLogger(FixedSizeStoragePool.class);
	
	private File rootDir;
	private int fileCount;
	private int fileSize;
	private boolean deleteOnExit;
	
	private List<StorageFile> freeFiles;
	private List<StorageFile> fullFiles;

	private int fileNameCounter;
	
	public void setRootDir(String rootDir) {
		this.rootDir = new File(rootDir);
		this.rootDir.mkdir();
		if(!this.rootDir.exists()) {
			throw new IllegalStateException("Directory '" + this.rootDir.getAbsolutePath() + "' does not exist");
		}
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	/**
	 * In bytes.
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public void setDeleteOnExit(boolean deleteOnExit) {
		this.deleteOnExit = deleteOnExit;
	}

	@Override
	public void init() {
		super.init();
		fileNameCounter = 0;
		freeFiles = new ArrayList<StorageFile>();
		fullFiles = new ArrayList<StorageFile>();
		for (int i = 0; i < fileCount; i++) {
			freeFiles.add(new SimpleStorageFile(nextFileName(), deleteOnExit, fileSize));
		}
		for(StorageFile file : freeFiles) {
			file.init();
		}
	}

	@Override
	public void shutdown() {
		for(StorageFile file : freeFiles) {
			file.shutdown();
		}
		for(StorageFile file : fullFiles) {
			file.shutdown();
		}
		super.shutdown();
	}

	@Override
	protected synchronized Buffer allocateInternal(Spec spec) {
		while(true) {
			if(freeFiles.isEmpty()) {
				throw new IllegalStateException("No free blocks in memory-mapped files based storage pool. You need to allocate more memory mapped "
						+ "files in order to load this much data. Server reboot is required. Self-tunable storage pool is work in progress.");
			}
			StorageFile file = freeFiles.get(0);
			Buffer buffer = file.allocate(spec);
			if(buffer != null) {
				if(log.isTraceEnabled()) {			
					log.trace("Allocated buffer for type '" + spec.getType() + "' and count '" + spec.getCount() + "' from storage file '" + file.getFileName() + "'");
				}
				return buffer;
			}
			rollover();
		}
	}
	
	private File nextFileName() {
		return new File(rootDir, "storage-file-" + fileNameCounter++ + ".pool");
	}
	
	private void rollover() {
		StorageFile file = freeFiles.remove(0);
		if(log.isTraceEnabled()) {
			log.trace("File '" + file.getFileName() + "' is full, moving it full list");
		}
		fullFiles.add(file);
	}
}
