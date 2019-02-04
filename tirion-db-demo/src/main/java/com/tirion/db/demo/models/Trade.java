/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.demo.models;

import java.text.SimpleDateFormat;

import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.SimpleEntity;
import com.tirion.db.catalog.model.SimpleField;
import com.tirion.db.catalog.model.options.Options;
import com.tirion.db.common.Config;

public final class Trade {
	
	public static final String NAME = "trade";
	
	private static final SimpleDateFormat ISO_DATE_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_DATE_PATTERN);
	private static final SimpleDateFormat ISO_TIMESTAMP_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_TIMESTAMP_PATTERN);
	private static final SimpleDateFormat ISO_TIME_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_TIME_PATTERN);
	
	// not final so that we can change it on the fly
	public static final long BOOL1_CARDINALITY 	 	= 2;
	public static final long BOOL2_CARDINALITY 	 	= 2;
	
	public static final long BYTE1_CARDINALITY 	 	= 10;
	public static final long BYTE2_CARDINALITY 	 	= 100;
	
	public static final long SHORT1_CARDINALITY 	= 1000;
	public static final long SHORT2_CARDINALITY 	= 30000;
	
	public static final long INT1_CARDINALITY 	 	= 8000;
	public static final long INT2_CARDINALITY 	 	= Long.MAX_VALUE;
	
	public static final long LONG1_CARDINALITY 	 	= 40000;
	public static final long LONG2_CARDINALITY 	 	= Long.MAX_VALUE;
	
	public static final long FLOAT1_CARDINALITY 	= Long.MAX_VALUE;
	public static final long FLOAT2_CARDINALITY 	= Long.MAX_VALUE;
	
	public static final long DOUBLE1_CARDINALITY 	= Long.MAX_VALUE;
	public static final long DOUBLE2_CARDINALITY 	= Long.MAX_VALUE;
	
	public static final long DATE1_CARDINALITY 	 	= 30;
	public static final long DATE2_CARDINALITY 	 	= 300;
	
	public static final long TIMESTAMP1_CARDINALITY = 1000 * 60 * 60 * 24;
	public static final long TIMESTAMP2_CARDINALITY = 1000 * 60 * 60 * 24 * 10;
	
	public static final long TIME1_CARDINALITY 		= 60 * 60 * 24;
	public static final long TIME2_CARDINALITY 		= Long.MAX_VALUE;

	public static final long STRING1_CARDINALITY 	= 5;
	public static final long STRING2_CARDINALITY 	= 10;
	public static final long STRING3_CARDINALITY 	= 15;
	public static final long STRING4_CARDINALITY 	= 30;
	public static final long STRING5_CARDINALITY 	= 50;
	public static final long STRING6_CARDINALITY 	= 100;
	public static final long STRING7_CARDINALITY 	= 250;
	public static final long STRING8_CARDINALITY 	= 500;
	public static final long STRING9_CARDINALITY 	= 1000;
	public static final long STRING10_CARDINALITY 	= 5 * 1000;
	public static final long STRING11_CARDINALITY 	= 10 * 1000;
	public static final long STRING12_CARDINALITY 	= 100 * 1000;
	public static final long STRING13_CARDINALITY 	= 1000 * 1000;
	public static final long STRING14_CARDINALITY 	= Long.MAX_VALUE;

	public static final SimpleEntity ENTITY = new SimpleEntity("trade");
	static {
		ENTITY.append(new SimpleField("bool_1", "trade", Type.BOOLEAN, 0, 			new Options(true,false, false,false, false, true, false, BOOL1_CARDINALITY)));
		ENTITY.append(new SimpleField("bool_2", "trade", Type.BOOLEAN, 1, 			new Options(true,false, false, false,false, true, false, BOOL2_CARDINALITY)));
		ENTITY.append(new SimpleField("byte_1", "trade", Type.BYTE, 2, 				new Options(true,false, false, false,false, true, false, BYTE1_CARDINALITY)));
		ENTITY.append(new SimpleField("byte_2", "trade", Type.BYTE, 3, 				new Options(true,false, false, false,false, true, false, BYTE2_CARDINALITY)));
		ENTITY.append(new SimpleField("short_1", "trade", Type.SHORT, 4, 			new Options(true,false, false,false, false, true, false, SHORT1_CARDINALITY)));
		ENTITY.append(new SimpleField("short_2", "trade", Type.SHORT, 5, 			new Options(true,false, false,false, false, true, false, SHORT2_CARDINALITY)));
		ENTITY.append(new SimpleField("int_1", "trade", Type.INT, 6, 				new Options(true,false, false,false, false, true, false, INT1_CARDINALITY)));		
		ENTITY.append(new SimpleField("int_2", "trade", Type.INT, 7, 				new Options(true,false, false,false, false, true, false, INT2_CARDINALITY)));
		ENTITY.append(new SimpleField("long_1", "trade", Type.LONG, 8, 				new Options(true,false, false, false,false, true, false, LONG1_CARDINALITY)));
		ENTITY.append(new SimpleField("long_2", "trade", Type.LONG, 9, 				new Options(true,false, false, false,false, true, false, LONG2_CARDINALITY)));
		ENTITY.append(new SimpleField("float_1", "trade", Type.FLOAT, 10, 			new Options(true,false, false,false, false, true, false, FLOAT1_CARDINALITY)));
		ENTITY.append(new SimpleField("float_2", "trade", Type.FLOAT, 11, 			new Options(true,false, false,false, false, true, false, FLOAT2_CARDINALITY)));
		ENTITY.append(new SimpleField("double_1", "trade", Type.DOUBLE, 12, 		new Options(true,false, false,false, false, true, false, DOUBLE1_CARDINALITY)));
		ENTITY.append(new SimpleField("double_2", "trade", Type.DOUBLE, 13, 		new Options(true,false, false,false, false, true, false, DOUBLE2_CARDINALITY)));
		ENTITY.append(new SimpleField("date_1", "trade", Type.DATE, 14, 			new Options(true,true, false, false, false,true, false, DATE1_CARDINALITY)));
		ENTITY.append(new SimpleField("date_2", "trade", Type.DATE, 15, 		  	new Options(true,true, false, false, true, false,false, DATE2_CARDINALITY)));
		ENTITY.append(new SimpleField("timestamp_1", "trade", Type.TIMESTAMP, 16, 	new Options(true,false, false, false, false,true, false, TIMESTAMP1_CARDINALITY)));
		ENTITY.append(new SimpleField("timestamp_2", "trade", Type.TIMESTAMP, 17, 	new Options(true,false, false, false, false,true, false, TIMESTAMP2_CARDINALITY)));
		ENTITY.append(new SimpleField("time_1", "trade", Type.TIME, 18, 			new Options(true,false, false, false, false,true, false, TIME1_CARDINALITY)));
		ENTITY.append(new SimpleField("time_2", "trade", Type.TIME, 19, 			new Options(true,false, false, false, false,true, false, TIME2_CARDINALITY)));
		ENTITY.append(new SimpleField("string_1", "trade", Type.VARCHAR, 20,		new Options(true,true, false, false, false, true, false, STRING1_CARDINALITY)));
		ENTITY.append(new SimpleField("string_2", "trade", Type.VARCHAR, 21, 		new Options(true,true, false, false, false,true, false, STRING2_CARDINALITY)));
		ENTITY.append(new SimpleField("string_3", "trade", Type.VARCHAR, 22, 		new Options(true,true, false, false, false,true, false, STRING3_CARDINALITY)));
		ENTITY.append(new SimpleField("string_4", "trade", Type.VARCHAR, 23, 		new Options(true,true, false, false, false,true, false, STRING4_CARDINALITY)));
		ENTITY.append(new SimpleField("string_5", "trade", Type.VARCHAR, 24, 		new Options(true,true, false, false, false,true, false, STRING5_CARDINALITY)));
		ENTITY.append(new SimpleField("string_6", "trade", Type.VARCHAR, 25, 		new Options(true,true, false, false, false,true, false, STRING6_CARDINALITY)));
		ENTITY.append(new SimpleField("string_7", "trade", Type.VARCHAR, 26, 		new Options(true,true, false, false, false,true, false, STRING7_CARDINALITY)));
		ENTITY.append(new SimpleField("string_8", "trade", Type.VARCHAR, 27, 		new Options(true,true, false, false,false, true, false, STRING8_CARDINALITY)));
		ENTITY.append(new SimpleField("string_9", "trade", Type.VARCHAR, 28, 		new Options(true,true, false, false, false,true, false, STRING9_CARDINALITY)));
		ENTITY.append(new SimpleField("string_10", "trade", Type.VARCHAR, 29, 		new Options(true,true, false, false, false,true, false, STRING10_CARDINALITY)));
		ENTITY.append(new SimpleField("string_11", "trade", Type.VARCHAR, 30, 		new Options(true,true, false, false, false,true, false, STRING11_CARDINALITY)));
		ENTITY.append(new SimpleField("string_12", "trade", Type.VARCHAR, 31, 		new Options(true,false, false, false, false,true, false, STRING12_CARDINALITY)));
		ENTITY.append(new SimpleField("string_13", "trade", Type.VARCHAR, 32, 		new Options(true,false, false, false,false,true, false, STRING13_CARDINALITY)));
		ENTITY.append(new SimpleField("string_14", "trade", Type.VARCHAR, 33, 		new Options(true,false, false, false, false,true, false, STRING14_CARDINALITY)));
	}

	private final boolean bool1;
	private final boolean bool2;
	private final byte byte1;
	private final byte byte2;
	private final short short1;
	private final short short2;
	private final int int1;
	private final int int2;
	private final long long1;
	private final long long2;
	private final float float1;
	private final float float2;
	private final double double1;
	private final double double2;
	private final short date1;
	private final short date2;
	private final long timestamp1;
	private final long timestamp2;
	private final int time1;
	private final int time2;
	private final String str1;
	private final String str2;
	private final String str3;
	private final String str4;
	private final String str5;
	private final String str6;
	private final String str7;
	private final String str8;
	private final String str9;
	private final String str10;
	private final String str11;
	private final String str12;
	private final String str13;
	private final String str14;

	public Trade(boolean bool1, boolean bool2, byte byte1, byte byte2,
			short short1, short short2, int int1, int int2, long long1,
			long long2, float float1, float float2, double double1,
			double double2, short date1, short date2, long timestamp1,
			long timestamp2, int time1, int time2, String str1, String str2,
			String str3, String str4, String str5, String str6, String str7,
			String str8, String str9, String str10, String str11, String str12,
			String str13, String str14) {
		super();
		this.bool1 = bool1;
		this.bool2 = bool2;
		this.byte1 = byte1;
		this.byte2 = byte2;
		this.short1 = short1;
		this.short2 = short2;
		this.int1 = int1;
		this.int2 = int2;
		this.long1 = long1;
		this.long2 = long2;
		this.float1 = float1;
		this.float2 = float2;
		this.double1 = double1;
		this.double2 = double2;
		this.date1 = date1;
		this.date2 = date2;
		this.timestamp1 = timestamp1;
		this.timestamp2 = timestamp2;
		this.time1 = time1;
		this.time2 = time2;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.str4 = str4;
		this.str5 = str5;
		this.str6 = str6;
		this.str7 = str7;
		this.str8 = str8;
		this.str9 = str9;
		this.str10 = str10;
		this.str11 = str11;
		this.str12 = str12;
		this.str13 = str13;
		this.str14 = str14;
	}

	public boolean isBool1() {
		return bool1;
	}

	public boolean isBool2() {
		return bool2;
	}

	public byte getByte1() {
		return byte1;
	}

	public byte getByte2() {
		return byte2;
	}

	public short getShort1() {
		return short1;
	}

	public short getShort2() {
		return short2;
	}

	public int getInt1() {
		return int1;
	}

	public int getInt2() {
		return int2;
	}

	public long getLong1() {
		return long1;
	}

	public long getLong2() {
		return long2;
	}

	public float getFloat1() {
		return float1;
	}

	public float getFloat2() {
		return float2;
	}

	public double getDouble1() {
		return double1;
	}

	public double getDouble2() {
		return double2;
	}

	public short getDate1() {
		return date1;
	}

	public short getDate2() {
		return date2;
	}

	public long getTimestamp1() {
		return timestamp1;
	}

	public long getTimestamp2() {
		return timestamp2;
	}

	public int getTime1() {
		return time1;
	}

	public int getTime2() {
		return time2;
	}

	public String getStr1() {
		return str1;
	}

	public String getStr2() {
		return str2;
	}

	public String getStr3() {
		return str3;
	}

	public String getStr4() {
		return str4;
	}

	public String getStr5() {
		return str5;
	}

	public String getStr6() {
		return str6;
	}

	public String getStr7() {
		return str7;
	}

	public String getStr8() {
		return str8;
	}

	public String getStr9() {
		return str9;
	}

	public String getStr10() {
		return str10;
	}

	public String getStr11() {
		return str11;
	}

	public String getStr12() {
		return str12;
	}

	public String getStr13() {
		return str13;
	}

	public String getStr14() {
		return str14;
	}
	
	public String toCsvLine(String separator) {
		StringBuilder builder = new StringBuilder();
		builder.append(bool1).append(separator);
		builder.append(bool2).append(separator);
		builder.append(byte1).append(separator);
		builder.append(byte2).append(separator);
		builder.append(short1).append(separator);
		builder.append(short2).append(separator);
		builder.append(int1).append(separator);
		builder.append(int2).append(separator);
		builder.append(long1).append(separator);
		builder.append(long2).append(separator);
		builder.append(float1).append(separator);
		builder.append(float2).append(separator);
		builder.append(double1).append(separator);
		builder.append(double2).append(separator);
		builder.append(ISO_DATE_FORMATTER.format(date1)).append(separator);
		builder.append(ISO_DATE_FORMATTER.format(date2)).append(separator);
		builder.append(ISO_TIMESTAMP_FORMATTER.format(timestamp1)).append(separator);
		builder.append(ISO_TIMESTAMP_FORMATTER.format(timestamp2)).append(separator);
		builder.append(ISO_TIME_FORMATTER.format(time2)).append(separator);
		builder.append(ISO_TIME_FORMATTER.format(time2)).append(separator);
		builder.append(str1).append(separator);
		builder.append(str2).append(separator);
		builder.append(str3).append(separator);
		builder.append(str4).append(separator);
		builder.append(str5).append(separator);
		builder.append(str6).append(separator);
		builder.append(str7).append(separator);
		builder.append(str8).append(separator);
		builder.append(str9).append(separator);
		builder.append(str10).append(separator);
		builder.append(str11).append(separator);
		builder.append(str12).append(separator);
		builder.append(str13).append(separator);
		builder.append(str14);
		return builder.toString();
	}

	@Override
	public String toString() {
		return "SpotTrade [bool1=" + bool1 + ", bool2=" + bool2 + ", byte1="
				+ byte1 + ", byte2=" + byte2 + ", short1=" + short1
				+ ", short2=" + short2 + ", int1=" + int1 + ", int2=" + int2
				+ ", long1=" + long1 + ", long2=" + long2 + ", float1="
				+ float1 + ", float2=" + float2 + ", double1=" + double1
				+ ", double2=" + double2 + ", date1=" + date1 + ", date2="
				+ date2 + ", timestamp1=" + timestamp1 + ", timestamp2="
				+ timestamp2 + ", str1=" + str1 + ", str2=" + str2 + ", str3="
				+ str3 + ", str4=" + str4 + ", str5=" + str5 + ", str6=" + str6
				+ ", str7=" + str7 + ", str8=" + str8 + ", str9=" + str9
				+ ", str10=" + str10 + ", str11=" + str11 + ", str12=" + str12
				+ ", str13=" + str13 + ", str14=" + str14 + "]";
	}
}
