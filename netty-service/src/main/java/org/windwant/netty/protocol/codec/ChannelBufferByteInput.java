package org.windwant.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteInput;

import java.io.IOException;

/**
 * ChannelBufferByteInput
 */
public class ChannelBufferByteInput implements ByteInput{
    private final ByteBuf buffer;

    public ChannelBufferByteInput(ByteBuf buffer){
        this.buffer = buffer;
    }

    public int read() throws IOException {
        if (buffer.isReadable()) {
            return buffer.readByte() & 0xff;
        }
        return -1;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }

        len = Math.min(available, len);
        buffer.readBytes(b, off, len);
        return len;
    }

    public int available() throws IOException {
        return buffer.readableBytes();
    }

    public long skip(long n) throws IOException {
        int readable = buffer.readableBytes();
        if (readable < n) {
            n = readable;
        }
        buffer.readerIndex((int) (buffer.readerIndex() + n));
        return n;
    }

    public void close() throws IOException {

    }
}
