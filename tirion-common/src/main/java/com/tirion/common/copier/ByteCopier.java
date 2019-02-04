/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.copier;

import java.nio.ByteBuffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ByteCopier implements Copier {

	// TODO this aint fast way to copy; find faster approach
	@Override
	public Object toArray(ByteBuffer buffer, int position, int count) {
		byte[] result = new byte[count];
		for (int i = 0; i < count; i++) {
			result[i] = buffer.get(position++);
		}
		return result;
	}
}
