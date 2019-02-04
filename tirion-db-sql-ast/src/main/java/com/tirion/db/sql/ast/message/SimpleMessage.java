/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.message;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleMessage implements Message {

	private final String message;
	private final MessageKind kind;
	
	public SimpleMessage(String message, MessageKind messageKind) {
		super();
		this.message = message;
		this.kind = messageKind;
	}

	@Override
	public MessageKind getKind() {
		return kind;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
