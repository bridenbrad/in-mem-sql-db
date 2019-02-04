/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tirion.stats.Statistics;

public final class FileTextStatistics implements Statistics {

	@JsonProperty
	private final int lineCount;
	@JsonProperty
	private final long charCount;
	
	public FileTextStatistics(int lineCount, long charCount) {
		super();
		this.lineCount = lineCount;
		this.charCount = charCount;
	}
	
	@Override
	public Kind getKind() {
		return Kind.FILE_TEXT_READER;
	}

	public int getLineCount() {
		return lineCount;
	}

	public long getCharCount() {
		return charCount;
	}
}
