/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CloseAwareSession extends AbstractSession {
	
	private static final long serialVersionUID = -1899057549755980950L;

	private final List<SessionCloseListener> listeners = new CopyOnWriteArrayList<SessionCloseListener>();
	
	public CloseAwareSession(long id) {
		super(id);
	}
	
	public void register(SessionCloseListener listener) {
		listeners.add(listener);
	}

	@Override
	public void close() {
		for(SessionCloseListener listener : listeners) {
			listener.onClose(this);
		}
	}
}
