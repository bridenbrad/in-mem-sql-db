/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.builder.filter;

import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.exec.operator.physical.scan.filter.page.PageFilter;
import com.tirion.db.sql.plan.builder.Builder;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageFilterBuilder extends Builder {

	PageFilter build(String tableName, Compare compare);
}
