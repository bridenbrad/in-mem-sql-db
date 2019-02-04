/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

public abstract class SequencePage extends AbstractPage {

	@JsonProperty
	private final Sequence sequence;
	
	public SequencePage(Header header, Column owner, Runtime runtime, Sequence sequence) {
		super(header, owner, runtime);
		this.sequence = sequence;
	}

	protected final Sequence getSequence() {
		return sequence;
	}

	@Override
	public final boolean hasNullBitmap() {
		return sequence.hasNullBitmap();
	}

	@Override
	public final Bitmap getNullBitmap() {
		return sequence.getNullBitmap();
	}
}
