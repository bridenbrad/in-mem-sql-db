/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

import java.io.Serializable;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Pair<First, Second> implements Serializable {

	private static final long serialVersionUID = 8153238588665065176L;
	
	private final First first;
	private final Second second;
	
	public Pair(First first, Second second) {
		this.first = first;
		this.second = second;
	}

	public First getFirst() {
		return first;
	}

	public Second getSecond() {
		return second;
	}
}
