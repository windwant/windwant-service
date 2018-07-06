package org.windwant.netty.serialize.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * SubreqProtoServer
 */
public class SubreqProtoServer {
    public static void main(String[] args) {
        new SubreqProtoServer().bind(8888);
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
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(SubscripReqProto.SubscribReq.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new SubReqProtoServerHandler());
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

class SubReqProtoServerHandler extends ChannelHandlerAdapter{

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscripReqProto.SubscribReq req = (SubscripReqProto.SubscribReq) msg;
        if(req.getSubReqID() % 2 == 0){
            System.out.println("server accept client subscrib req: ["
            + req.toString() + "]");
            ctx.writeAndFlush(getSubresp(req.getSubReqID()));
        }
    }

    private SubscripRespProto.SubscripResp getSubresp(int id){
        SubscripRespProto.SubscripResp.Builder builder = SubscripRespProto.SubscripResp.newBuilder();
        builder.setSubReqID(id);
        builder.setRespCode(0);
        builder.setDesc("Subscrib sucess");
        return builder.build();
    }
}
