package org.windwant.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * ChineseProverbClient
 */
public class ChineseProverbClient {

    public static void main(String[] args) {
        new ChineseProverbClient().run(8888);
    }

    public void run(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseProverbClientHandler());

            Channel ch = b.bind(0).sync().channel();
            InetSocketAddress ip = new InetSocketAddress("255.255.255.255", port);
            for (int i = 0; i < 10; i++) {
                ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("proverb search", CharsetUtil.UTF_8), ip)).sync();
            }

            if(!ch.closeFuture().await(15000)){
                System.out.println("time out");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}

class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String resp = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println("search result: " + resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
