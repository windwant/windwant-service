package org.windwant.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

import java.io.IOException;

/**
 * ChannelBufferByteOutput
 */
public class ChannelBufferByteOutput implements ByteOutput {

    public ByteBuf getBuffer() {
        return buffer;
    }

    private final ByteBuf buffer;

    public ChannelBufferByteOutput(ByteBuf buffer){
        this.buffer = buffer;
    }

    public void write(int b) throws IOException {
        buffer.writeByte(b);
    }

    public void write(byte[] b) throws IOException {
        buffer.writeBytes(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        buffer.writeBytes(b, off, len);
    }

    public void close() throws IOException {

    }

    public void flush() throws IOException {

    }
}
