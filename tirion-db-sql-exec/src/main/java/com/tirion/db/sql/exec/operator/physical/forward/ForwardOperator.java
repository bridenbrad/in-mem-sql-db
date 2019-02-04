/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.forward;

import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;

/**
 * Operator which resides on data nodes and forwards tuples to master
 * node who will assemble final result set.
 */
public abstract class ForwardOperator extends SingleSourcePhysicalOperator {
}
