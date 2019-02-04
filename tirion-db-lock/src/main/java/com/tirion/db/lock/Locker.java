/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.lock;

import java.util.SortedSet;

import com.tirion.common.Lifecycle;

/** 
 * Works for reads/writes to tables. Does not work for dropping 
 * table. Callers should take locks in ascending table name order
 * in order to prevent deadlock.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Locker extends Lifecycle {
	
	void readLock(String tableName);
	
	void readUnlock(String tableName);

	void writeLock(String tableName);
	
	void writeUnlock(String tableName);
	
	// multi-table locking...
	
	void readLock(SortedSet<String> tableNames);
	
	void readUnlock(SortedSet<String> tableNames);

	void writeLock(SortedSet<String> tableNames);
	
	void writeUnlock(SortedSet<String> tableNames);
}
