/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.bitmap;

import com.tirion.common.SizeAware;

/**
 * 0 based indexing. Maximum size is equal to maximum page size.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Bitmap extends SizeAware {
	
	public static enum Kind {
		ARRAY,
		EWAH,
	}
	
	Kind getKind();
	
	/**
	 * True if all zeros.
	 */
	boolean isEmpty();
	
	/**
	 * True if all ones.
	 */
	boolean isFull();
	
	boolean isSet(int index);
	
	void set(int index);
	
	/**
	 * Either Java boolean array or
	 * {@link EWAHCompressedBitmap}.
	 */
	<T> T getUnderlying();
}
