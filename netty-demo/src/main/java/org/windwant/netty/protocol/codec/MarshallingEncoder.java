package org.windwant.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * MarshallingEncoder
 */
public class MarshallingEncoder {
    Marshaller marshaller;

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    public MarshallingEncoder(){
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out){
        int lenght = out.writerIndex();
        out.writeBytes(LENGTH_PLACEHOLDER);
        ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
        try {
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lenght, out.writerIndex() - lenght - 4);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
