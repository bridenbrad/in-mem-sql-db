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

public abstract class AbstractSequence implements Sequence {

	@JsonProperty
	private final Spec spec;	
	
	public AbstractSequence(Spec spec) {
		super();
		this.spec = spec;
	}
	
	@Override
	public final Spec getSpec() {
		return spec;
	}

	@Override
	public final int getCount() {
		return spec.getCount();
	}

	@Override
	public final boolean isCompressed() {
		return spec.isCompressed();
	}

	@Override
	public final Type getType() {
		return spec.getType();
	}

	@Override
	public final boolean hasNullBitmap() {
		return spec.getNullBitmap() != null;
	}

	@Override
	public final Bitmap getNullBitmap() {
		return spec.getNullBitmap();
	}
}
