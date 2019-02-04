/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client.reqres.req;

import com.tirion.db.engine.client.Session;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public class TextRequest extends AbstractRequest {
	
	private static final long serialVersionUID = 119264985084892099L;
	
	private final String text;
	
	public TextRequest(Session session, String text) {
		super(session);
		this.text = text;
	}

	public final String getText() {
		return text;
	}
}
