/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

import com.tirion.common.sequence.Sequence;

public class SequenceSlice extends AbstractSlice {

	private final Sequence[] sequences;

	public SequenceSlice(int slice, int rowCount, Sequence[] sequences) {
		super(slice, rowCount);
		this.sequences = sequences;
	}

	public final Sequence[] getSequences() {
		return sequences;
	}
}
