/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common;

import com.tirion.common.type.Type;
import com.tirion.db.sql.ast.AbstractNode;

/**
 * Either field/column reference or constant.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class Value extends AbstractNode {

	private static final long serialVersionUID = -8820508618619128093L;

	public abstract Type getType();
}
