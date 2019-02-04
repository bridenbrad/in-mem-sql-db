/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractNode implements Node {

	private static final long serialVersionUID = 5366075581730666868L;

	private Node parent;
	
	public final void setParent(Node parent) {
		this.parent = parent;
	}
	
	public final <T> T getParent(Class<T> clazz) {
		return clazz.cast(parent);
	}
}
