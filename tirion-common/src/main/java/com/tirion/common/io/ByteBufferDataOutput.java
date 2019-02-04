/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.io;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.tirion.common.NotYetImplementedException;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ByteBufferDataOutput implements DataOutput {

	private int position;
	private final ByteBuffer buffer;
	
	public ByteBufferDataOutput(int position, ByteBuffer buffer) {
		super();
		this.position = position;
		this.buffer = buffer;
	}

	@Override
	public void writeByte(int v) throws IOException {
		buffer.put(position, (byte)v);
		position += 1;
	}

	@Override
	public void writeShort(int v) throws IOException {
		buffer.putShort(position, (short)v);
		position += 2;
	}

	@Override
	public void writeInt(int v) throws IOException {
		buffer.putInt(position, v);
		position += 4;
	}

	@Override
	public void writeLong(long v) throws IOException {
		buffer.putLong(position, v);
		position += 8;
	}

	@Override
	public void writeFloat(float v) throws IOException {
		buffer.putFloat(position, v);
		position += 4;
	}

	@Override
	public void writeDouble(double v) throws IOException {
		buffer.putDouble(position, v);
		position += 8;
	}

	@Override
	public void writeBytes(String s) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void writeChars(String s) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void writeUTF(String s) throws IOException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public void write(int b) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void write(byte[] b) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		throw new NotYetImplementedException();
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public void writeChar(int v) throws IOException {
		throw new NotYetImplementedException();
	}
}
