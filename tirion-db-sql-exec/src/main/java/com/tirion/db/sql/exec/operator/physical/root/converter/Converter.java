/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.root.converter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Converts internal representation of values of certain type
 * into external ones.<p>
 * 
 * SHORT => {@link Date}
 * INT => {@link Time}
 * LONG => {@link Timestamp}
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Converter {

	void convert(Tuple tuple);
}
