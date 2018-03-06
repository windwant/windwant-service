package org.windwant.netty.protocol.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.windwant.netty.protocol.MessageType;
import org.windwant.netty.protocol.struct.Header;
import org.windwant.netty.protocol.struct.NettyMessage;

/**
 * HeartBeatRespHandler
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter{
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()){
            System.out.println("receive heartbeat message: " + message);
            NettyMessage resp = buildResp();
            System.out.println("server response: " + resp);
            ctx.writeAndFlush(resp);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildResp(){
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }
}
