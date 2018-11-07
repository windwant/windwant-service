package org.windwant.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * Netty Telnet server
 * Created by Administrator on 18-11-7.
 */
public class NettyTelnetServer {
    // 指定端口号
    private static final int PORT = 9999;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {

        serverBootstrap = new ServerBootstrap();
        // 指定socket的一些属性
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyTelnetInitializer());

        // 绑定对应的端口号,并启动开始监听端口上的连接
        Channel ch = serverBootstrap.bind(PORT).sync().channel();

        // 等待关闭,同步端口
        ch.closeFuture().sync();
    }
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        NettyTelnetServer nettyTelnetServer = new NettyTelnetServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            nettyTelnetServer.close();
        }
    }
}

class NettyTelnetInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        //基于分隔符解码 设定行分隔符
        //pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //基于行解码
        pipeline.addLast(new LineBasedFrameDecoder(1024));

        //String 编解码
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        // 业务处理
        pipeline.addLast(new NettyTelnetHandler());

    }
}

class NettyTelnetHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String request) throws Exception {
        StringBuilder response = new StringBuilder();
        boolean close = false;
        if (request.isEmpty()) {
            response.append("Please type something.\r\n");
        } else if ("bye".equals(request.toLowerCase())) {
            response.append("Have a good day!\r\n");
            close = true;
        } else {
            response.append("Input: '" + request + "'?\r\n");
            if("time".equals(request.toLowerCase()) || "date".equals(request.toLowerCase())) {
                response.append("Answer: '" + new Date() + "'!\r\n");
            }else if("who".equals(request.toLowerCase())){
                response.append("Answer: '" + InetAddress.getLocalHost().getHostName() + "'!\r\n");
            }else {
                response.append("Answer: I am not quite clear about your words! \r\n");
            }
        }

        ChannelFuture future = ctx.write(response);
        ctx.flush();
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}

