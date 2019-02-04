/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.message;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.sql.ast.message.Message.MessageKind;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleMessageListener implements MessageListener {

	private final List<Message> messages;
	
	public SimpleMessageListener() {
		super();
		messages = new ArrayList<Message>();
	}

	@Override
	public void onMessage(String message, MessageKind messageKind) {	
		messages.add(new SimpleMessage(message, messageKind));
		if(messageKind == MessageKind.ERROR) {
			throw new SemanticException(message);
		}
	}

	@Override
	public List<Message> getMessages() {
		return messages;
	}
}
