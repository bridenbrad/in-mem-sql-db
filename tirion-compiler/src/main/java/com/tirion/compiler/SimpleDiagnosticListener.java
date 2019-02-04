/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler;

import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleDiagnosticListener implements DiagnosticListener<JavaFileObject> {

	private StringBuilder buffer = new StringBuilder();
	
	public void reset() {
		buffer = new StringBuilder();
	}
	
	public String getMessages() {
		return buffer.toString();
	}
	
	@Override
	public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
		buffer.append(diagnostic.getMessage(Locale.US)).append("\n");
	}
}
