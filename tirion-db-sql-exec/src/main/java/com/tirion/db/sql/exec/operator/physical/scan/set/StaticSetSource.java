/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.set;

import java.util.Set;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class StaticSetSource<T> implements SetSource<T> {

	private final Set<T> set;
	
	public StaticSetSource(Set<T> set) {
		this.set = set;
	}

	@Override
	public Set<T> getSet() {
		return set;
	}
}
