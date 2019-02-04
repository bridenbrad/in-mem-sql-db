/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter;

/**
 * Used for column-oriented layout of data.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Filter {

	void apply();
}
