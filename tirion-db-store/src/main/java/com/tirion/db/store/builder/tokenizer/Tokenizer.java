/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.tokenizer;

import java.nio.ByteBuffer;

import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Tokenizer {
	
	/**
	 * Input and output are native Java array.
	 */
	Array tokenize(Array array);
	
	/**
	 * Input and output are native Java array..
	 */
	Array detokenize(Array array);

	/**
	 * Input is {@link ByteBuffer}, output
	 * is native Java array.
	 */
	Array detokenize(Buffer buffer);
}
