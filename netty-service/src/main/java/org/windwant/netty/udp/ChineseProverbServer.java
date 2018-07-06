package org.windwant.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * ChineseProverbServer
 */
public class ChineseProverbServer {

    public static void main(String[] args) {
        new ChineseProverbServer().bind(8888);
    }

    public void bind(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new ChineseProverbServerHandler());
            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}

class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    private static final String[] PROVERB = {"BIRD FLY", "THE YELLOW RIVER", "WE DO WE CAN"};

    private String getNextProverb(){
        int id = ThreadLocalRandom.current().nextInt(PROVERB.length);
        return PROVERB[id];
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String req = msg.content().toString(CharsetUtil.UTF_8);
        System.out.println("request: " + req);

        if("proverb search".equals(req)){
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("search resutlt: " + getNextProverb(), CharsetUtil.UTF_8), msg.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
