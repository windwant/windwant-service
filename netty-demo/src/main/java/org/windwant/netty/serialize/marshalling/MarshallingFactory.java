package org.windwant.netty.serialize.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * MarshallingFactory
 */
public class MarshallingFactory {

    public static MarshallingDecoder getMarshallingDecoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        UnmarshallerProvider un = new DefaultUnmarshallerProvider(marshallerFactory, config);
        return new MarshallingDecoder(un);
    }
    public static MarshallingEncoder getMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        MarshallerProvider un = new DefaultMarshallerProvider(marshallerFactory, config);
        return new MarshallingEncoder(un);
    }
}
