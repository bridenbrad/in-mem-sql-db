/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import com.tirion.common.Lifecycle;
import com.tirion.db.sql.exec.tuple.source.TupleSource;

/**
 * Capable to build tuples from row IDs. Returns null when done.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Projector extends Lifecycle, TupleSource {
}
