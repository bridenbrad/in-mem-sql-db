/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

import java.util.List;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TextLinesSlice extends AbstractSlice {

	private final List<String> lines;

	public TextLinesSlice(int slice, List<String> lines) {
		super(slice, lines.size());
		this.lines = lines;
	}

	public List<String> getLines() {
		return lines;
	}
}
