/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class StringArrayListTest extends TestCase {

	public void test() {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"a", "a"});
		list.add(new String[]{"a", "a"});
		list.add(new String[]{"a", "a"});
		list.add(new String[]{"a", "a"});
		list.add(new String[]{"a", "a"});
		
		String[][] matrix = list.toArray(new String[][]{});
		System.out.println(matrix.length);
		System.out.println(matrix[0].length);
	}
}
