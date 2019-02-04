/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TextMatrixSlice extends AbstractSlice {

	private final String[][] arrays;

	public TextMatrixSlice(int slice, int rowCount, String[][] arrays) {
		super(slice, rowCount);
		this.arrays = arrays;
	}

	public String[][] getMatrix() {
		return arrays;
	}
}
