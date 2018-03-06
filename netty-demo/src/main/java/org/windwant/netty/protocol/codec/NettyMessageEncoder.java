package org.windwant.netty.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.windwant.netty.protocol.struct.NettyMessage;

import java.util.Map;

/**
 * NettyMessageEncoder
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> { //MessageToMessageEncoder<NettyMessage>{ //

    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder(){
        this.marshallingEncoder = new MarshallingEncoder();
    }

//    @Override
//    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
//        if(msg == null || msg.getHeader() == null){
//            throw new Exception("The encode message is null");
//        }
//
//        ByteBuf sendBuf = Unpooled.buffer();
//        sendBuf.writeInt(msg.getHeader().getCrcCode());
//        sendBuf.writeInt(msg.getHeader().getLength());
//        sendBuf.writeLong(msg.getHeader().getSessionID());
//        sendBuf.writeByte(msg.getHeader().getType());
//        sendBuf.writeByte(msg.getHeader().getPriority());
//        sendBuf.writeInt(msg.getHeader().getAttachment().size());
//
//        String key = null;
//        byte[] keyArray = null;
//        Object value = null;
//        for(Map.Entry<String, Object> param: msg.getHeader().getAttachment().entrySet()){
//            key = param.getKey();
//            keyArray = key.getBytes("UTF-8");
//            sendBuf.writeInt(keyArray.length);
//            sendBuf.writeBytes(keyArray);
//            value = param.getValue();
//            marshallingEncoder.encode(value, sendBuf);
//        }
//        key = null;
//        keyArray = null;
//        value = null;
//        if(msg.getBody() != null){
//            marshallingEncoder.encode(msg.getBody(), sendBuf);
//        }
//
////      sendBuf.writeInt(0);
//        // 在第4个字节出写入Buffer的长度
//        int readableBytes = sendBuf.readableBytes();
//        sendBuf.setInt(4, readableBytes);
//
//        // 把Message添加到List传递到下一个Handler
//        out.add(sendBuf);
//    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if(msg == null || msg.getHeader() == null){
            throw new Exception("message is null");
        }

        //依次写入当前读写位的相应类型值 int long byte
        out.writeInt((msg.getHeader().getCrcCode()));
        out.writeInt((msg.getHeader().getLength()));
        out.writeLong((msg.getHeader().getSessionID()));
        out.writeByte((msg.getHeader().getType()));
        out.writeByte((msg.getHeader().getPriority()));
        out.writeInt((msg.getHeader().getAttachment().size()));
        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment()
                .entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            out.writeInt(keyArray.length);//写入key bytes值长度
            out.writeBytes(keyArray);//写入key bytes
            value = param.getValue();
            marshallingEncoder.encode(value, out); //序列化map value值并写入输出
        }
        key = null;
        keyArray = null;
        value = null;
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), out);//序列化消息体
        } else
            out.writeInt(0);
        out.setInt(4, out.readableBytes());//消息长度
    }
}
