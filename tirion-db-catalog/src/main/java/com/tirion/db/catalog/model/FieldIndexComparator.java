/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.util.Comparator;

/**
 * Will sort fields in increasing index order. Throws exception
 * if there are two fields with same index.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FieldIndexComparator implements Comparator<Field> {

	@Override
	public int compare(Field left, Field right) {
		if(left.getIndex() < 0 || right.getIndex() < 0) {
			throw new IllegalArgumentException("Illegal index number");
		}
		if(left.getIndex() < right.getIndex()) {
			return -1;
		} else if(left.getIndex() > right.getIndex()) {
			return 1;
		} else {			
			throw new IllegalArgumentException("Both fields have same index: " + left.toString() + "," + right.toString());
		}
	}
}
