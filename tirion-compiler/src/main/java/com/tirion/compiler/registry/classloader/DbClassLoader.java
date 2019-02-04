/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler.registry.classloader;

import com.tirion.compiler.registry.Registry;
import com.tirion.compiler.registry.repository.Entry;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbClassLoader extends ClassLoader {

	private final Registry registry;
	
	public DbClassLoader(Registry registry) {
		super(DbClassLoader.class.getClassLoader());
		this.registry = registry;
	}

	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		try {
			return super.loadClass(className);
		} catch (ClassNotFoundException cnfe) {
			return findClass(className);
		}
	}

	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		Entry entry = registry.find(className);
		if(entry == null) {
			throw new ClassNotFoundException("Repository does not contain class '" + className + "'");
		}
		if(entry.getClazz() != null) {
			return entry.getClazz();
		}
		if(entry.getBytecode() != null) {
			defineClassFromBytes(entry);
			return entry.getClazz();
		}
		throw new IllegalArgumentException("Entry for class '" + className + "' does not have byte[]");
	}

	private void defineClassFromBytes(Entry entry) {
		entry.setClazz(defineClass(entry.getFullClassName(), entry.getBytecode(), 0, entry.getBytecode().length));
	}
}
