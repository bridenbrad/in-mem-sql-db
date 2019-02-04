/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

import java.util.List;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface ListAware<T> {

	void append(T value);
	
	void append(List<T> values);
	
	/**
	 * Number of elements in the list.
	 */
	int getCount();
	
	boolean isEmpty();
	
	List<T> get();
	
	/**
	 * 0 index based.
	 */
	T get(int index);
	
	boolean has(int index);
}
