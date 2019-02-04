/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.exec.operator.physical.scan.rowid.sink;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
//
///**
// * If filter is 'bad' (returns all rows), then large amount of
// * memory would be needed to store every possible row ID.
// * This class should discover at runtime if entire page worth
// * of row IDs has matched, and then store only start/end of 
// * row IDs.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SmartRowIdSink extends NullAwareRowIdSink {
//
//	@Override
//	public RowIdSource asRowIdSource() {
//		throw new NotYetImplementedException();
//	}
//
//	@Override
//	protected void onNonNullValue(long rowId) {
//		throw new NotYetImplementedException();
//	}
//}
