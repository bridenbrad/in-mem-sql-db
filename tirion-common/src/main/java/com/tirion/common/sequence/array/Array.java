/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence.array;

import com.tirion.common.sequence.Sequence;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Array extends Sequence {
	
	void writeTo(Buffer buffer);
}
