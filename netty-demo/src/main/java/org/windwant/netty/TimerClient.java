package org.windwant.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.logging.Logger;

/**
 * TimerClient
 */
public class TimerClient {
    public static void main(String[] args) {
        new TimerClient().connect(8888, "127.0.0.1");
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
//            ch.pipeline().addLast(new FixedLengthFrameDecoder(46));
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(label.getBytes())));
//            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new TimeClientHandler());
        }
    }
}

class TimeClientHandler extends ChannelHandlerAdapter{

    public static final Logger logger = Logger.getLogger(TimerClient.class.getName());

    private int counter;

    private byte[] req;

    private static final String separator = System.getProperty("line.separator");

    private static final String label = "$";

    private static final String fixedLength = "";

    TimeClientHandler(){
//        String order = "QUERY TIME ORDER" + separator;
        String order = "QUERY TIME ORDER" + fixedLength;
        System.out.println("execution order: " + order);
        req = order.getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf firstMessager;
        for (int i = 0; i < 100; i++) {
            firstMessager = Unpooled.buffer(req.length);
            firstMessager.writeBytes(req);
            ctx.writeAndFlush(firstMessager);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] resp = new byte[buf.readableBytes()];
//        buf.readBytes(resp);
//        String curTime = new String(resp);
        String curTime = (String) msg;
        System.out.println("NOW IS: " + curTime + "; counter: " + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("unexepected exception: " + cause.getMessage());
        ctx.close();
    }


}
