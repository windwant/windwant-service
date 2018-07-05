package org.windwant.httpserver;

import org.windwant.HTTPConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 返回对象
 * Created by windwant on 2016/6/12.
 */
public class Response {
    private static final int BUFFER_SIZE = 1024;

    public Response(){
    }

    public void response(OutputStream out, Request request){
        byte[] b = new byte[BUFFER_SIZE];
        File file = new File(HttpServer.WEB_ROOT, request.getPath());
        try {
            StringBuilder sb = new StringBuilder();
            if(file.exists()){
                FileInputStream fi = new FileInputStream(file);
                int ch = 0;
                while ((ch = fi.read(b, 0, BUFFER_SIZE)) > 0){
                    out.write(b, 0, ch);
                }
                out.flush();
            }else{
                sb.append("HTTP/1.1 404 File Not Found \r\n");
                sb.append("Content-Type: text/html\r\n");
                sb.append("Content-Length: 24\r\n" );
                sb.append("\r\n" );
                sb.append("<h1>File Not Found!</h1>");
                out.write(sb.toString().getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * nio server 处理返回内容
     * @throws IOException
     */
    public void response(SocketChannel channel, Request request) throws IOException {
        if("upload".equals(request.getPath())){

            return;
        }
        File file = new File(HttpServer.WEB_ROOT, request.getPath());
        if(file != null && file.exists()){
            dealFileWrite(channel, file);//处理请求资源
        }else{
            notFound(channel);//请求资源不存在
        }
    }

    protected void dealUpload(SocketChannel channel){

    }
    /**
     * 处理请求资源
     * @param channel
     * @param file
     * @throws IOException
     */
    protected void dealFileWrite(SocketChannel channel, File file) throws IOException {
        StringBuffer sb = new StringBuffer();
        byte[] b = new byte[BUFFER_SIZE];
        FileInputStream fi = new FileInputStream(file);
        String contentType = HTTPConstants.CONTENT_TYPE.get(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        sb.append("HTTP/1.1 200 success \r\n");
        sb.append("Content-Type: " + contentType + "\r\n");
        sb.append("Content-Length: " + fi.getChannel().size() + "\r\n");
        sb.append("\r\n");
        channel.write(ByteBuffer.wrap(sb.toString().getBytes()));
        int ch = 0;
        while ((ch = fi.read(b, 0, BUFFER_SIZE)) > 0){
            channel.write(ByteBuffer.wrap(b));
            b = new byte[BUFFER_SIZE];
        }
    }

    /**
     * 请求资源不存在
     * @param channel
     * @throws IOException
     */
    protected void notFound(SocketChannel channel) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 404 File Not Found \r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("Content-Length: 24\r\n" );
        sb.append("\r\n" );
        sb.append("<h1>File Not Found!</h1>");
        channel.write(ByteBuffer.wrap(sb.toString().getBytes()));
    }
}
