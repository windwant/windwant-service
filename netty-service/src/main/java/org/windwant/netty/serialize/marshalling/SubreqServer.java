package org.windwant.netty.serialize.marshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.windwant.netty.serialize.SubscribReq;
import org.windwant.netty.serialize.SubscribResp;


/**
 * SubreqServer
 */
public class SubreqServer {
    public static void main(String[] args) {
        new SubreqServer().bind(8888);
    }
    public void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(MarshallingFactory.getMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingFactory.getMarshallingEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class SubReqServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg:");
        SubscribReq req = (SubscribReq) msg;
        if(req.getSubReqID() % 2 == 0){
            System.out.println("server accept client subscrib req: ["
                    + req.toString() + "]");
        }
        ctx.writeAndFlush(getSubresp(req.getSubReqID()));
    }

    private SubscribResp getSubresp(int id){
        SubscribResp s = new SubscribResp();
        s.setSubReqID(id);
        s.setRespCode(0);
        s.setDesc("Subscrib sucess");
        return s;
    }
}

