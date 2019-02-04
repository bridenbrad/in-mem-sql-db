/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler;

import com.tirion.common.Lifecycle;
import com.tirion.compiler.source.Source;

/**
 * Underlying file system should be RAM-mounted file
 * system.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Compiler extends Lifecycle {
	
	/**
	 * Dump given source code into Java source file on file system,
	 * then compile source, and finally inject compiled class
	 * into class loader.
	 */
	Result compile(Source source);
}
