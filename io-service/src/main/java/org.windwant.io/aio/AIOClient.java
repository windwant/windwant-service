package org.windwant.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AsynchronousSocketChannel
 */
public class AIOClient implements Runnable{

    private AsynchronousChannelGroup group;   //异步通道组 封装处理异步通道的网络IO操作
    private String host;
    private int port;
    public AIOClient(String host, int port) {
        this.host = host;
        this.port = port;
        initGroup();
    }

    private void initGroup(){
        if(group == null) {
            try {
                group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5), 5); //使用固定线程池实例化组
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void send(int i){
        try {
            //异步流式socket通道 open方法创建 并绑定到组 group
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open(group);
            //连接
            client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
                public void completed(Void result, Object attachment) {
                    String msg = " client asks time, times: " + i;
                    client.write(ByteBuffer.wrap(msg.getBytes()));
                    System.out.println(Thread.currentThread().getName() + " client send data:" + msg);

                    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer, this, new CompletionHandler<Integer, Object>() {
                        public void completed(Integer result, Object attachment) {
                            System.out.println(Thread.currentThread().getName() + " client get response: " + new String(byteBuffer.array()) + ". " + i);
                            try {
                                byteBuffer.clear();
                                if (client != null) client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        public void failed(Throwable exc, Object attachment) {
                            System.out.println(Thread.currentThread().getName() + " read faield");
                        }
                    });
                }

                public void failed(Throwable exc, Object attachment) {
                    System.out.println(Thread.currentThread().getName() + " client send field...");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            send(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        group.awaitTermination(10000, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        try {
            new Thread(new AIOClient("127.0.0.1", 8989)).start();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
