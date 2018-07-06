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

    private AsynchronousChannelGroup group;
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
                group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newFixedThreadPool(5), 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void send(){
        try {
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open(group);
            client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
                public void completed(Void result, Object attachment) {
                    String msg = "client test msg-" + Math.random();
                    client.write(ByteBuffer.wrap(msg.getBytes()));
                    System.out.println(Thread.currentThread().getName() + " client send data:" + msg);

                    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    client.read(byteBuffer, this, new CompletionHandler<Integer, Object>() {
                        public void completed(Integer result, Object attachment) {
                            System.out.println(result);
                            System.out.println(Thread.currentThread().getName() + " client read data: " + new String(byteBuffer.array()));
                            try {
                                byteBuffer.clear();
                                if (client != null) client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        public void failed(Throwable exc, Object attachment) {
                            System.out.println("read faield");
                        }
                    });
                }

                public void failed(Throwable exc, Object attachment) {
                    System.out.println("client send field...");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            send();
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
