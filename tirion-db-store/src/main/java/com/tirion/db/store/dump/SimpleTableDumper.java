/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.dump;

import java.io.File; 

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.store.table.Table;

public final class SimpleTableDumper implements TableDumper {

	@Override
	public void dumpBasic(Table table, File directory) {
		try {
			dumpBasicInternal(table, directory);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void dumpTokenMaps(Table table, File directory) {
		throw new NotYetImplementedException();
	}

	private void dumpBasicInternal(Table table, File directory) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_DETECT_GETTERS, false);
		mapper.configure(Feature.AUTO_DETECT_IS_GETTERS, false);
		mapper.configure(Feature.AUTO_DETECT_FIELDS, false);
		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.writeValue(new File(directory, table.getName() + ".json"), table);		
	}
}
