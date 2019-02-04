/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.compiler.registry.repository.Entry;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LocalInMemoryRegistry implements Registry {

	private static final Logger log = LoggerFactory.getLogger(LocalInMemoryRegistry.class);
	
	private final Map<String, Entry> classes;
	
	public LocalInMemoryRegistry() {
		this.classes = new ConcurrentHashMap<String, Entry>();
	}

//	@Override
//	public Summary getSummary() {
//		Map<String, Object> map = new ConcurrentHashMap<String, Object>();
//		map.put("entries", classes.keySet());
//		return new SimpleSummary(map);
//	}
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void register(Entry entry) {
		if(log.isTraceEnabled()) {			
			log.trace("Registering entry for class '" + entry.getFullClassName() + "'");
		}
		if(entry.getBytecode() == null) {
			throw new NullPointerException("byte[] can not be null in entry '" + entry.getFullClassName() + "'");
		}
		Entry old = classes.put(entry.getFullClassName(), entry);
		if(old != null) {
			log.warn("Replacing old entry under class name '" + entry.getFullClassName() + "'");
		}
	}

	@Override
	public Entry find(String className) {
		if(log.isTraceEnabled()) {			
			if(className.startsWith("com.tirion.db.generated")) {
				log.trace("Searching for class '" + className + "'");			
			}
		}
		return classes.get(className);
	}

	@Override
	public Entry remove(String className) {
		return classes.remove(className);
	}
}
