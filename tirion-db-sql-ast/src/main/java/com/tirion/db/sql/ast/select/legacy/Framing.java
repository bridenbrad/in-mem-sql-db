/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select.legacy;
//package com.tirion.db.sql.ast.select;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.ast.Node;
//import com.tirion.db.sql.ast.common.constant.BaseConstant;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public class Framing implements Node {
//	
//	public static enum FramingKind {
//		ROWS,
//		RANGE,
//	}
//	
//	private FramingKind framingKind;
//	private BaseConstant preceding;
//	private BaseConstant following;
//
//	public FramingKind getFramingKind() {
//		return framingKind;
//	}
//
//	public void setFramingKind(FramingKind framingKind) {
//		this.framingKind = framingKind;
//	}
//
//	/**
//	 * In case of unbounded preceding, this is will be
//	 * equal to maximum integer value.
//	 */
//	public BaseConstant getPreceding() {
//		return preceding;
//	}
//
//	public void setPreceding(BaseConstant preceding) {
//		this.preceding = preceding;
//	}
//
//	/**
//	 * In case of unbounded follwing, this is will be
//	 * equal to maximum integer value.
//	 */
//	public BaseConstant getFollowing() {
//		return following;
//	}
//
//	public void setFollowing(BaseConstant following) {
//		this.following = following;
//	}
//
//	@Override
//	public Kind getKind() {
//		return Kind.FRAMING;
//	}
//	
//	@Override
//	public String toSql() {
//		throw new NotYetImplementedException();
//	}
//}
