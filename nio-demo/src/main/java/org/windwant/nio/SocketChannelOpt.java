package org.windwant.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by windwant on 2016/10/27.
 */
public class SocketChannelOpt {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;

    private static ExecutorService read = Executors.newFixedThreadPool(5);
    private static ExecutorService write = Executors.newFixedThreadPool(5);

    public static void main(String[] args){
        ServerSocketChannel serverSocketChannel = null;
        ServerSocket serverSocket = null;
        Selector selector = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();//工厂方法创建ServerSocketChannel
            serverSocket = serverSocketChannel.socket(); //获取channel对应的ServerSocket
            serverSocket.bind(new InetSocketAddress(HOST, PORT)); //绑定地址
            serverSocketChannel.configureBlocking(false); //设置ServerSocketChannel非阻塞模式
            selector = Selector.open();//工厂方法创建Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//通道注册选择器，接受连接就绪状态。
            while (true){//循环检查
                if(selector.select() == 0){//阻塞检查，当有就绪状态发生，返回键集合
                    continue;
                }

                Iterator<SelectionKey> it = selector.selectedKeys().iterator(); //获取就绪键遍历对象。
                while (it.hasNext()){
                    SelectionKey selectionKey = it.next();
                    //处理就绪状态
                    if (selectionKey.isAcceptable()){
                        ServerSocketChannel schannel = (ServerSocketChannel) selectionKey.channel();//只负责监听，阻塞，管理，不发送、接收数据
                        SocketChannel socketChannel = schannel.accept();//就绪后的操作，刚到达的socket句柄
                        if(null == socketChannel){
                            continue;
                        }
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ); //告知选择器关心的通道，准备好读数据
                    }else if(selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(4*1024);

                        StringBuilder result = new StringBuilder();
                        while (socketChannel.read(byteBuffer) > 0){//确保读完
                            byteBuffer.flip();
                            result.append(new String(byteBuffer.array()));
                            byteBuffer.clear();//每次清空 对应上面flip()
                        }

                        System.out.println("server receive: " + result.toString());
                        socketChannel.register(selector, SelectionKey.OP_WRITE);

                    }else if(selectionKey.isWritable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        String sendStr = "server send data: " + Math.random();
                        ByteBuffer send = ByteBuffer.wrap(sendStr.getBytes());
                        while (send.hasRemaining()){
                            socketChannel.write(send);
                        }
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(sendStr);
                    }
                    it.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
