/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.page;

import com.tirion.db.sql.exec.operator.physical.scan.filter.Filter;

/**
 * For columns which do not have an index. Page filters can be
 * brute-force (scan all pages from first to last) or can be
 * parametrized with list of row IDs. <p>
 * 
 * Should be aware of null values.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface PageFilter extends Filter {
}
