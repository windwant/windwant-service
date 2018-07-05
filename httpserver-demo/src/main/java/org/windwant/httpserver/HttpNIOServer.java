package org.windwant.httpserver;

import java.io.IOException;
import java.net.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by windwant on 2016/6/13.
 */
public class HttpNIOServer {

    private ServerSocketChannel serverSocketChannel;

    private ServerSocket serverSocket;

    private Selector selector;

    Request request;

    private ExecutorService es;

    private static final Integer SERVER_PORT = 8888;

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    private boolean shutdown = false;


    public static void main(String[] args) {
        HttpNIOServer server = new HttpNIOServer();
        server.start();
        System.exit(0);
    }

    HttpNIOServer(){
        try {
            es = Executors.newFixedThreadPool(5);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            serverSocket.setSoTimeout(30000);
            serverSocket.bind(new InetSocketAddress(SERVER_PORT));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("server init...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            while (!shutdown){
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeySet.iterator();
                while (it.hasNext()){
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    if(!selectionKey.isValid()){
                        continue;
                    }
                    handleRequest(selectionKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRequest(SelectionKey selectionKey){
        System.out.println("request channel: " + selectionKey.channel());
        ServerSocketChannel ssc = null;
        SocketChannel ss = null;
        try {
            if(selectionKey.isAcceptable()){
                ssc = (ServerSocketChannel) selectionKey.channel();
                ss = ssc.accept();//开始等待请求
                ss.configureBlocking(false);
                ss.register(selector, SelectionKey.OP_READ);
            }else if(selectionKey.isReadable()){
                ss = (SocketChannel) selectionKey.channel();
                request = new Request();
                request.read(ss);//请求处理
                ss.register(selector, SelectionKey.OP_WRITE);
            }else if(selectionKey.isWritable()){
                ss = (SocketChannel) selectionKey.channel();
                Response response = new Response();
                response.setRequest(request);
                response.responseNIO(ss);
                ss.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {//客户端断开异常处理
            selectionKey.cancel();
            try {
                ss.close();
            } catch (IOException e1) {
                System.out.println("close the channel");
            }
        }
    }
}