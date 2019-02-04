/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.tx;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class SimpleTx extends AbstractTx {
//
//	public SimpleTx(long id, Kind kind, Runtime runtime) {
//		super(id, kind, runtime);
//	}
//
//	@Override
//	protected void beginInternal() {
//		for(Scope scope : getScopes()) {
//			String tableName = scope.getTableName();
//			if(getKind() == Kind.READ) {
//				getRuntime().locker().readLock(tableName);				
//			} else {
//				getRuntime().locker().writeLock(tableName);
//			}
//		}
//		throw new NotYetImplementedException();
//	}
//
//	@Override
//	protected void commitInternal() {
//		unlock();
//		throw new NotYetImplementedException();
//	}
//
//	@Override
//	protected void rollbackInternal() {
//		unlock();
//		throw new NotYetImplementedException();
//	}
//	
//	private void unlock() {
//		for(Scope scope : getScopes()) {
//			String tableName = scope.getTableName();
//			if(getKind() == Kind.READ) {
//				getRuntime().locker().readUnlock(tableName);				
//			} else {
//				getRuntime().locker().writeUnlock(tableName);
//			}
//		}
//	}
//}
