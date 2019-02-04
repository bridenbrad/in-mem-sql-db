/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler.registry;

import com.tirion.common.Lifecycle;
import com.tirion.compiler.registry.repository.Entry;

/**
 * Wrapper around thread-safe hash map where key is class name
 * without package and value is {@link Entry}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Registry extends Lifecycle {

	void register(Entry entry);
	
	/**
	 * @param className without package name
	 */
	Entry remove(String className);
	
	/**
	 * @param className without package name
	 */
	Entry find(String className);
}
