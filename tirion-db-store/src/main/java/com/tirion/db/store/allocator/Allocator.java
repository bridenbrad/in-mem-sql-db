/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator;

import com.tirion.common.Lifecycle;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Allocator extends Lifecycle {
	
	Buffer allocate(Spec spec);
}
