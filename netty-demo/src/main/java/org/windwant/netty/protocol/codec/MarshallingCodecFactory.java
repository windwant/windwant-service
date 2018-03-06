package org.windwant.netty.protocol.codec;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * MarshallingCodecFactory
 */
public final class MarshallingCodecFactory {

    protected static Marshaller buildMarshalling(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration mcfg = new MarshallingConfiguration();
        mcfg.setVersion(5);
        try {
            return marshallerFactory.createMarshaller(mcfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Unmarshaller buildUnMarshalling(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration mcfg = new MarshallingConfiguration();
        mcfg.setVersion(5);
        try {
            return marshallerFactory.createUnmarshaller(mcfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
