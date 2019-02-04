/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.select.SelectStatement;

/**
 * Node that represents physical or virtual relations.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Relation extends Node {
	
	/**
	 * True {@link SelectStatement}, false if {@link EntityRef}.
	 */
	boolean isSelect();
}
