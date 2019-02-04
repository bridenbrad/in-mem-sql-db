/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser;

import java.util.List;

import com.tirion.common.Lifecycle;
import com.tirion.db.sql.ast.Node;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Parser extends Lifecycle {

	Node parse(String statement);
	
	List<Node> parse(String[] statements);
}
