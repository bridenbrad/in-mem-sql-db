/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.io;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.tirion.common.NotYetImplementedException;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ByteBufferDataInput implements DataInput {
	
	private int position;
	private final ByteBuffer buffer;
	
	public ByteBufferDataInput(int position, ByteBuffer buffer) {
		this.position = position;
		this.buffer = buffer;
	}

	@Override
	public byte readByte() throws IOException {
		byte result = buffer.get(position);
		position += 1;
		return result;
	}

	@Override
	public short readShort() throws IOException {
		short result = buffer.getShort(position);
		position += 2;
		return result;
	}

	@Override
	public int readInt() throws IOException {
		int result = buffer.getInt(position);
		position += 4;
		return result;
	}

	@Override
	public long readLong() throws IOException {
		long result = buffer.getLong(position);
		position += 8;
		return result;
	}

	@Override
	public float readFloat() throws IOException {
		float result = buffer.getFloat(position);
		position += 4;
		return result;
	}

	@Override
	public double readDouble() throws IOException {
		double result = buffer.getDouble(position);
		position += 8;
		return result;
	}

	@Override
	public String readLine() throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public String readUTF() throws IOException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public void readFully(byte[] b) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public int skipBytes(int n) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean readBoolean() throws IOException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public int readUnsignedShort() throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public char readChar() throws IOException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public int readUnsignedByte() throws IOException {
		throw new NotYetImplementedException();
	}
}
