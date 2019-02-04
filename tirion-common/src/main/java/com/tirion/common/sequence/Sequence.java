/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.SizeAware;
import com.tirion.common.bitmap.NullBitmapAware;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Sequence extends NullBitmapAware, SizeAware {

	public static enum Kind {
		BUFFER, 
		ARRAY,
	}
	
	Spec getSpec();
	
	/**
	 * How many bytes. Does not include overhead fields.
	 * Do not confuse with {@link SizeAware}.
	 */
	@JsonProperty
	int size();
	
	/**
	 * Number of entries of given type.
	 */
	int getCount();
	
	Kind getKind();
	
	boolean isCompressed();
	
	/**
	 * 
	 */
	Type getType();
	
	<T> T getUnderlying();
}
