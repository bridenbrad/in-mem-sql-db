/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.lock;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleLocker implements Locker {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleLocker.class);
	
	private final ConcurrentHashMap<String, ReentrantReadWriteLock> map = new ConcurrentHashMap<String, ReentrantReadWriteLock>();
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void readLock(String tableName) {
		getLock(tableName).readLock().lock();
	}

	@Override
	public void readUnlock(String tableName) {
		getLock(tableName).readLock().unlock();
	}

	@Override
	public void writeLock(String tableName) {
		getLock(tableName).writeLock().lock();
	}

	@Override
	public void writeUnlock(String tableName) {
		getLock(tableName).writeLock().unlock();
	}
	
	@Override
	public void readLock(SortedSet<String> tableNames) {
		for(String tableName : tableNames) {
			readLock(tableName);
		}
	}

	@Override
	public void readUnlock(SortedSet<String> tableNames) {
		for(String tableName : tableNames) {
			readUnlock(tableName);
		}
	}

	@Override
	public void writeLock(SortedSet<String> tableNames) {
		for(String tableName : tableNames) {
			writeLock(tableName);
		}
	}

	@Override
	public void writeUnlock(SortedSet<String> tableNames) {
		for(String tableName : tableNames) {
			writeUnlock(tableName);
		}
	}

	private ReentrantReadWriteLock getLock(String tableName) {
		ReentrantReadWriteLock lock = map.get(tableName);
		if(lock == null) {
			lock = new ReentrantReadWriteLock();
			ReentrantReadWriteLock old = map.putIfAbsent(tableName, lock);
			if(old != null) {
				lock = old;
			} else {				
				log.info("Initialized lock for table '" + tableName + "'");
			}
		}
		return lock;
	}
}
