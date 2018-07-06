package org.windwant.netty.serialize;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * SubreqClient
 */
public class SubreqClient {

    public static void main(String[] args) {
        new SubreqClient().connect(8888, "127.0.0.1");
    }

    public void connect(int port, String host){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new childHandler());

            ChannelFuture cf = b.connect(host, port).sync();
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    private class childHandler extends ChannelInitializer<SocketChannel> {

        private static final String label = "$";
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
            ch.pipeline().addLast(new ObjectEncoder());
            ch.pipeline().addLast(new SubreqClientHandler());
        }
    }
}

class SubreqClientHandler extends ChannelHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.write(getSubReq(i));
        }
        ctx.flush();
    }

    private SubscribReq getSubReq(int id){
        SubscribReq q = new SubscribReq();
        q.setSubReqID(id);
        q.setUserName(String.valueOf(id));
        q.setProductName("Product1");
        q.setPhoneNumber("18xxxxxxxxx");
        q.setAddress("address:xxxxxx");
        return q;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive server response: [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
