/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.type.Type;

/**
 * Specification of the sequence.
 */
public final class Spec {

	@JsonProperty
	private final Type type;
	@JsonProperty
	private final int count;
	@JsonProperty
	private final boolean isCompressed;
	private final Bitmap nullBitmap;
	
	public Spec(Type type, int count, boolean isCompressed, Bitmap nullBitmap) {
		super();
		this.type = type;
		this.count = count;
		this.isCompressed = isCompressed;
		this.nullBitmap = nullBitmap;
	}

	public Type getType() {
		return type;
	}

	public int getCount() {
		return count;
	}

	public boolean isCompressed() {
		return isCompressed;
	}

	public Bitmap getNullBitmap() {
		return nullBitmap;
	}
}
