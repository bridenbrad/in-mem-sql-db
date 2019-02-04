/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.page;

import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.page.Page;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageBuilder {

	Page build(Sequence sequence, DataStats stats);
}
