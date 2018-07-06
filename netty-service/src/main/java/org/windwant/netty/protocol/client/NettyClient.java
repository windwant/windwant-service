package org.windwant.netty.protocol.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.windwant.netty.protocol.NettyConstant;
import org.windwant.netty.protocol.codec.NettyMessageDecoder;
import org.windwant.netty.protocol.codec.NettyMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * NettyClient
 */
public class NettyClient {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect(int port, String host){
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeOutHandler", new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("loginAuthHandler", new LoginAuthReqHandler());
                            ch.pipeline().addLast("heartbeatHandler", new HeartBeatReqHandler());
                        }
                    });
            ChannelFuture cf = b.connect(new InetSocketAddress(host, port),
                    new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 所有资源释放完成之后，清空资源，再次发起重连操作
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        try {
                            connect(NettyConstant.PORT, NettyConstant.REMOTEIP);// 发起重连操作
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new NettyClient().connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
    }

}
