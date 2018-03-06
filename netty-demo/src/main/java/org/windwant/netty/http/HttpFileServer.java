package org.windwant.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * HttpFileServer 文件服务器，文件目录及下载
 * 测试请求：http://localhost:8888/src/
 */
public class HttpFileServer {

    public void run(int port, final String url){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-codec", new HttpServerCodec()); //A combination of {@link HttpRequestDecoder} and {@link HttpResponseEncoder}
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
//                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("httpFileServerHandler", new HttpFileServerHandler(url));
                        }
                    });

            ChannelFuture cf = b.bind("127.0.0.1", port).sync();
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HttpFileServer().run(8888, "/src/");
    }
}

class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    private final String url;

    HttpFileServerHandler(String url){
        this.url = url;
    }


    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("request receive---------------");
        if(!msg.getDecoderResult().isSuccess()){
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        System.out.println("decoded result: " + msg.getDecoderResult().isSuccess());

        if(msg.getMethod() != HttpMethod.GET){
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        System.out.println("request method: " + msg.getMethod());

        final String path = sanilizeUrl(msg.getUri());
        if(path == null){
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        System.out.println("request uri: " + msg.getUri());
        System.out.println("request file path: " + path);

        File file = new File(path);
        if(file.isHidden() || !file.exists()){
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        if(file.isDirectory()){
            if(msg.getUri().endsWith("/")){
                sendListing(ctx, file);
            }else{
                sendRedirect(ctx, msg.getUri() + "/");
                System.out.println("request redirect: " + msg.getUri() + "/");
            }
            return;
        }

        if(!file.isFile()){
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        long fl = randomAccessFile.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, fl);
        setContentTypeHeader(response, file);
        if(HttpHeaders.isKeepAlive(msg)){ //下载文件必须设置
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
        ChannelFuture sendFuture = null;
        sendFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fl, 8192), ctx.newProgressivePromise());
        sendFuture.addListener(new ChannelProgressiveFutureListener(){

            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("transfer completed");
            }

            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if(total < 0){
                    System.err.println("transfer progress: " + progress);
                }else{
                    System.out.println("transfer progress: " + progress + "/" + total);
                }
            }
        });

        ChannelFuture lastf = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!HttpHeaders.isKeepAlive(msg)){
            lastf.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    private static final Pattern INSECURE_URL = Pattern.compile(".*[<>&\"].*");

    private String sanilizeUrl(String uri){
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        if(!uri.startsWith(url) || !uri.startsWith("/")){
            return null;
        }
        uri = uri.replace("/", File.separator);
        if(uri.contains(File.separator + ".")
                || uri.contains("." + File.separator)
                || uri.startsWith(".")
                || uri.endsWith(".")
                || INSECURE_URL.matcher(uri).matches()){
            return null;
        }

        return System.getProperty("user.dir") + File.separator + uri;
    }

    private static final Pattern ALLOWED_FILE_PATTERN = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*]");

    private void sendListing(ChannelHandlerContext ctx, File dir){
        System.out.println("make file list...");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        String dirPath = dir.getPath();
        sb.append("<!DOCTYPE html>\r\n");
        sb.append("<html><head><title>");
        sb.append(dirPath);
        sb.append("目录：");
        sb.append("</title></head><body>\r\n");
        sb.append("<h3>");
        sb.append(dirPath).append(" 目录：");
        sb.append("</h3>\r\n");
        sb.append("<ul>");
        sb.append("<li>链接：<a href=\"../\"></a></li>\r\n");
        for(File f:dir.listFiles()){
            if(f.isHidden() || !f.canRead()){
                continue;
            }
            String name = f.getName();
            if(ALLOWED_FILE_PATTERN.matcher(name).matches()){
                continue;
            }
            sb.append("<li>链接：<a href=\"");
            sb.append(name);
            sb.append("\">");
            sb.append(name);
            sb.append("</a></li>\r\n");
        }
        sb.append("</ul></body></html>");

        System.out.println("return: " + sb.toString());

        ByteBuf bf = Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8);
        response.content().writeBytes(bf);
        bf.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        System.out.println("make over");
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUrl){
        FullHttpResponse response =  new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaders.Names.LOCATION, newUrl);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContentTypeHeader(HttpResponse response, File file){
        MimetypesFileTypeMap mfm = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, mfm.getContentType(file));
    }
}
