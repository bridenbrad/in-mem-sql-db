/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.dump;

import java.io.File;

import com.tirion.db.store.table.Table;

public interface TableDumper {

	void dumpBasic(Table table, File directory);
	
	void dumpTokenMaps(Table table, File directory);
}
