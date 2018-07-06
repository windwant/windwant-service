package org.windwant.netty.protocol.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.windwant.netty.protocol.NettyConstant;
import org.windwant.netty.protocol.codec.NettyMessageDecoder;
import org.windwant.netty.protocol.codec.NettyMessageEncoder;

/**
 * NettyServer
 */
public class NettyServer {

    public void bind(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0)); //为源码里面会会把参数2和3加起来，会使frameLength过大而抛异常
                        ch.pipeline().addLast(new NettyMessageEncoder());
                        ch.pipeline().addLast("readTimeOutHandler", new ReadTimeoutHandler(50));
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast("heartbeatHandler", new HeartBeatRespHandler());
                    }
                });
        try {
            b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync().channel().closeFuture().sync();
            System.out.println("server ok: " + NettyConstant.REMOTEIP + ":" +NettyConstant.PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().bind();
    }
}
