package org.windwant.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Logger;
/**
 * WebSocketServer
 */
public class WebSocketServer {

    public static void main(String[] args) {
        new WebSocketServer().bind(8888);
    }

    public void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-codec", new HttpServerCodec());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("websockethandler", new WebSocketServerHandler());
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

class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());

    private WebSocketServerHandshaker webSocketServerHandshaker;
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if (msg instanceof WebSocketFrame){
            handleWebsocketFrame(ctx, (WebSocketFrame) msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req){
        if(!req.getDecoderResult().isSuccess()
                || !"websocket".equals(req.headers().get("upgrade"))){
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory wf = new WebSocketServerHandshakerFactory("ws://localhost:8888/websocket", null, false);
        webSocketServerHandshaker = wf.newHandshaker(req);
        if(webSocketServerHandshaker == null){
            wf.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            webSocketServerHandshaker.handshake(ctx.channel(), req);
        }

    }

    private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        if(frame instanceof CloseWebSocketFrame){
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }

        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException();
        }

        String request = ((TextWebSocketFrame) frame).text();
        System.out.println(ctx.channel() + " receive: " + request);

        //ctx.write();
        ctx.channel().write(new TextWebSocketFrame(request
                + ", welcome to use websocket, now time is: "
                + new Date().toString()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp){
        if(resp.getStatus().code() != 200){
            ByteBuf bf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(bf);
            bf.release();
            HttpHeaders.setContentLength(resp, resp.content().readableBytes());
        }

        ChannelFuture cf = ctx.writeAndFlush(resp);
        if(HttpHeaders.isKeepAlive(req) || resp.getStatus().code() != 200){
            cf.addListener(ChannelFutureListener.CLOSE);
        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}