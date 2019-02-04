/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler.registry.repository;

/**
 * Has class name with package name, byte[] of class definition plus 
 * {@link Class} object itself.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Entry {

	private final String fullClassName;
	private byte[] bytecode;
	private Class<?> clazz;
	
	public Entry(String fullClassName) {
		this.fullClassName = fullClassName;
	}
	
	/**
	 * Without package name.
	 */
	public String getFullClassName() {
		return fullClassName;
	}
	
	public byte[] getBytecode() {
		return bytecode;
	}
	
	public void setBytecode(byte[] bytecode) {
		this.bytecode = bytecode;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
