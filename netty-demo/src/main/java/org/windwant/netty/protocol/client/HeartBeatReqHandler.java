package org.windwant.netty.protocol.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.windwant.netty.protocol.MessageType;
import org.windwant.netty.protocol.struct.Header;
import org.windwant.netty.protocol.struct.NettyMessage;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * HeartBeatReqHandler
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader() != null){
            if(message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
                heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HearBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
            }else if(message.getHeader().getType() == MessageType.HEARTBEAT_RESP.value()){
                System.out.println("client receive server heartbeat message: " + message);
            }
        }else{
            ctx.fireChannelRead(msg);
        }
    }
    private class HearBeatTask implements Runnable{

        private final ChannelHandlerContext ctx;

        public HearBeatTask(final ChannelHandlerContext ctx){
            this.ctx = ctx;
        }

        public void run() {
            NettyMessage heartBeat = buildHearBeat();
            System.out.println("client send heartbeat message: " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        }

        private NettyMessage buildHearBeat(){
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            return message;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(heartBeat != null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        ctx.fireExceptionCaught(cause);
    }
}
