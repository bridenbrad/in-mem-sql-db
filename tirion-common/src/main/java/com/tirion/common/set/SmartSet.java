/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.set;

import java.util.List;
import java.util.Set;

/**
 * Iteration order is equal to insertion order. Won't
 * insert entries if they are already present.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface SmartSet<T> {

	Set<T> get();
	
	void add(T value);
	
	void add(Set<T> set);
	
	void add(SmartSet<T> set);
	
	void add(List<T> list);
}
