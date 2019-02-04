/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.message;

import java.util.List;

import com.tirion.db.sql.ast.message.Message.MessageKind;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface MessageListener {

	void onMessage(String message, MessageKind messageKind);
	
	List<Message> getMessages();
}
