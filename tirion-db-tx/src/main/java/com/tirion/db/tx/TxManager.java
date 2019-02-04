/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.tx;

import com.tirion.common.Lifecycle;
import com.tirion.db.tx.Tx.Kind;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TxManager extends Lifecycle {

	Tx newTx(Kind kind);
}
