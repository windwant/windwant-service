package org.windwant.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.*;

/**
 * AsynchronousServerSocketChannel
 */
public class AIOServer implements Runnable{

    private int port = 8889;
    private int threadSize = 10;
    protected AsynchronousChannelGroup asynchronousChannelGroup;

    protected AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port, int threadSize) {
        this.port = port;
        this.threadSize = threadSize;
        init();
    }

    private void init(){
        try {
            asynchronousChannelGroup = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
            serverChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
            serverChannel.bind(new InetSocketAddress(port));
            System.out.println("listening on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ExecutorService pool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS, new ArrayBlockingQueue(100));

    public void run() {
        try{
           if(serverChannel == null) return;
            serverChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOServer>() {
                final ByteBuffer echoBuffer = ByteBuffer.allocateDirect(1024);

                public void completed(AsynchronousSocketChannel result, AIOServer attachment) {
                    System.out.println("==============================================================");
                    pool.submit(() -> {
                        try {
                            String tName = Thread.currentThread().getName() + " --- ";
                            System.out.println(tName + "server process begin ...");
                            System.out.println(tName + "client host: " + result.getRemoteAddress());
                            echoBuffer.clear();
                            result.read(echoBuffer).get();
                            echoBuffer.flip();
                            String received = Charset.defaultCharset().decode(echoBuffer).toString();
                            System.out.println(tName + "received : " + received);

                            int random = ThreadLocalRandom.current().nextInt(5);
                            printProcess(random);
                            System.out.println(tName + "server deal request execute: " + random + "s");

                            String msg = "server response: " + received + ", " + new Date();//Math.random();
                            System.out.println(tName + msg);
                            result.write(ByteBuffer.wrap(msg.getBytes()));
                            System.out.println(tName + "server process end ...");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                    attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
                }

                public void failed(Throwable exc, AIOServer attachment) {
                    System.out.println("received failed");
                    exc.printStackTrace();
                    attachment.serverChannel.accept(attachment, this);
                }
            });
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printProcess(int s) throws InterruptedException {
        String dot = "";
        for (int i = 0; i < s; i++) {
            Thread.sleep(1000);
            dot += ".";
            System.out.println(dot);

        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new AIOServer(8989, 19)).start();
    }
}
