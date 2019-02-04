/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler.source;

import com.tirion.common.file.FileUtil;

/**
 * This wrapper around source's string & class name. <p>
 * 
 * All generated classes are under 'com.tirion.db.generated' package.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Source {
	
	public static final String PACKAGE_NAME = "com.tirion.db.generated";

	private final String source;
	private final String simpleClassName;
	private final String fullClassName;
	

	public Source(String simpleClassName, String source) {
		this.source = source;
		this.simpleClassName = simpleClassName;
		this.fullClassName = PACKAGE_NAME + "." + simpleClassName;
	}
	
	public String getSimpleClassName() {
		return simpleClassName;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	/**
	 * In dots format.
	 */
	public String getPackageName() {
		return PACKAGE_NAME;
	}
	
	/**
	 * In file system format.
	 */
	public String getNormalizedPackageName() {
		return FileUtil.asFileSystemPath(getPackageName());
	}

	public String getSource() {
		return source;
	}
}
