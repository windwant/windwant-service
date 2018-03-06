package org.windwant.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.Selector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by windwant on 2016/5/13.
 */
public class NIOOpt {

    public static void main(String[] args) {
        try {
            MappedPrivateChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MapMode.PRIVATE 写时拷贝（copy-on-write）映射：通过put()修改的任何修改，会导致产生一个私有的数据
     * 拷贝，宝贝中的数据只有MappedByteBuffer实例可以看到。不会对底层文件做任何修改。若缓冲区被回收，修改丢
     * 失，read/write方式建立通道。
     * 做修改，拷贝副本前，其它方式的映射区的修改，会反映到当前区域。映射相互的修改不可见
     * 允许父子进程共享内存页
     * 处理一个文件多个映射场景。
     * 关闭通道，映射会保持。除非丢弃缓冲区本身。
     * MappedByteBuffer 对象是直接的，占用的内存位于jvm堆栈之外。
     */
    public static void MappedPrivateChannel() throws Exception {
        // Create a temp file and get a channel connected to it
        File tempFile = File.createTempFile ("mmaptest", null);
        RandomAccessFile file = new RandomAccessFile (tempFile, "rw");
        FileChannel channel = file.getChannel( );
        ByteBuffer temp = ByteBuffer.allocate (100);
        // Put something in the file, starting at location 0
        temp.put ("This is the file content".getBytes( ));
        temp.flip( );
        channel.write (temp, 0);
        // Put something else in the file, starting at location 8192.
        // 8192 is 8 KB, almost certainly a different memory/FS page.
        // This may cause a file hole, depending on the
        // filesystem page size.
        temp.clear( );
        temp.put ("This is more file content".getBytes( ));
        temp.flip( );
        channel.write (temp, 8192);
        // Create three types of mappings to the same file
        MappedByteBuffer ro = channel.map (
                FileChannel.MapMode.READ_ONLY, 0, channel.size( ));
        MappedByteBuffer rw = channel.map (
                FileChannel.MapMode.READ_WRITE, 0, channel.size( ));
        MappedByteBuffer cow = channel.map (
                FileChannel.MapMode.PRIVATE, 0, channel.size( ));
        // the buffer states before any modifications
        System.out.println ("Begin");
        showBuffers (ro, rw, cow);
        // Modify the copy-on-write buffer
        cow.position (8);
        cow.put ("COW".getBytes( ));
        System.out.println ("Change to COW buffer");
        showBuffers (ro, rw, cow);
        // Modify the read/write buffer92
        rw.position (9);
        rw.put (" R/W ".getBytes( ));
        rw.position (8194);
        rw.put (" R/W ".getBytes( ));
        rw.force( );
        System.out.println ("Change to R/W buffer");
        showBuffers (ro, rw, cow);
        // Write to the file through the channel; hit both pages
        temp.clear( );
        temp.put ("Channel write ".getBytes( ));
        temp.flip( );
        channel.write (temp, 0);
        temp.rewind( );
        channel.write (temp, 8202);
        System.out.println ("Write on channel");
        showBuffers (ro, rw, cow);
        // Modify the copy-on-write buffer again
        cow.position (8207);
        cow.put (" COW2 ".getBytes( ));
        System.out.println ("Second change to COW buffer");
        showBuffers (ro, rw, cow);
        // Modify the read/write buffer
        rw.position (0);
        rw.put (" R/W2 ".getBytes( ));
        rw.position (8210);
        rw.put (" R/W2 ".getBytes( ));
        rw.force( );
        System.out.println ("Second change to R/W buffer");
        showBuffers (ro, rw, cow);
        // cleanup
        channel.close( );
        file.close( );
        tempFile.delete( );
    }

    // Show the current content of the three buffers
    public static void showBuffers (ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) throws Exception{
        dumpBuffer ("R/O", ro);
        dumpBuffer ("R/W", rw);
        dumpBuffer ("COW", cow);
        System.out.println ("");
    }
    // Dump buffer content, counting and skipping nulls
    public static void dumpBuffer (String prefix, ByteBuffer buffer) throws Exception {
        System.out.print (prefix + ": '");
        int nulls = 0;
        int limit = buffer.limit( );
        for (int i = 0; i < limit; i++) {
            char c = (char) buffer.get (i);
            if (c == '\u0000') {
                nulls++;
                continue;
            }
            if (nulls != 0) {
                System.out.print ("|[" + nulls
                        + " nulls]|");
                nulls = 0;
            }
            System.out.print (c);
        }
        System.out.println ("'");
    }

    /**
     * channel Gather/Scatter 矢量IO
     */
    public static void channelGatherScatter(){
        ByteBuffer head = ByteBuffer.allocate(4);
        ByteBuffer body = ByteBuffer.allocate(100);
        RandomAccessFile afile = null;
        RandomAccessFile bfile = null;
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        try {
            afile = new RandomAccessFile("hello.txt", "r");
            bfile = new RandomAccessFile("hehe.txt", "rw");
            readWriteLock.readLock().lock();
            FileChannel fileChannel = afile.getChannel();
            ByteBuffer[] buffers = {head, body};
            while (fileChannel.read(buffers) != -1){
            }
            head.flip();
            body.flip();
            System.out.println(new String(head.array()));
            System.out.println(new String(body.array()));
            readWriteLock.readLock().unlock();
            fileChannel.close();
            afile.close();

            readWriteLock.writeLock().lock();
            FileChannel bfileChannel = bfile.getChannel();

            while (bfileChannel.write(buffers) > 0){
            }

            bfileChannel.position(bfileChannel.position() + 10);
            bfileChannel.write(ByteBuffer.wrap("THIS IS THE TEST TEXT!".getBytes()));
            bfileChannel.truncate(3); //改变文件大小
            bfileChannel.force(true); //写入磁盘文件 参数 是否更新文件元数据（所有者、访问权限等）
            readWriteLock.writeLock().unlock();
            bfileChannel.close();
            bfile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 基于MappedFileChannle的文件复制
     * 文件锁
     */
    public static void mappedFileChannelLock(){
        RandomAccessFile afile = null;
        RandomAccessFile bfile = null;
        FileChannel fc = null;
        FileChannel fcb = null;
        try {
            afile = new RandomAccessFile("hello.txt", "rw");
            fc = afile.getChannel();
            long length = fc.size();
            FileLock fileLock = fc.tryLock(0, length, true);//true共享锁 false 独占锁 从开始 锁定全部内容 如果获取不到锁会返回null
            if(null != fileLock) {
                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
                byte[] fbo = new byte[(int) length];
                mbb.get(fbo);
                System.out.println(new String(fbo, "UTF-8"));
                fileLock.release();
                bfile = new RandomAccessFile("hehe.txt", "rw");
                fcb = bfile.getChannel();
                fileLock = fcb.tryLock(0, length, false);
                MappedByteBuffer mbbb = fcb.map(FileChannel.MapMode.READ_WRITE, 0, length);

                for (int i = 0; i < length; i++) {
                    mbbb.put(i, fbo[i]);
                }
                mbbb.flip();
                mbbb.force();
                fileLock.release();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fc.close();
                fcb.close();
                afile.close();
                bfile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * MappedByteBuffer map(MapMode mode, long position, long size)
     * size大于文件大小，文件会做扩充
     * MappedByteBuffer 内存映射缓冲池
     * 基于MappedFileChannle的文件复制
     * 读写锁
     * 直接读取，修改磁盘上的文件。
     * 自动缓存内存页，比较高效。
     */
    public static void mappedFileChannel(){
        RandomAccessFile afile = null;
        RandomAccessFile bfile = null;
        FileChannel fc = null;
        FileChannel fcb = null;
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        try {
            afile = new RandomAccessFile("hello.txt", "rw");
            readWriteLock.readLock().lock();
            fc = afile.getChannel();
            long length = fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
            byte[] fbo = new byte[(int) length];
            mbb.get(fbo);
            System.out.println(new String(fbo));
            readWriteLock.readLock().unlock();
            bfile = new RandomAccessFile("hehe.txt", "rw");
            readWriteLock.writeLock().lock();
            fcb = bfile.getChannel();
            MappedByteBuffer mbbb = fcb.map(FileChannel.MapMode.READ_WRITE, 0, length);

            for (int i = 0; i < length; i++) {
                mbbb.put(i, fbo[i]);
            }
            mbbb.flip();
            mbbb.force();
            readWriteLock.writeLock().unlock();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fc.close();
                fcb.close();
                afile.close();
                bfile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FileChannel文件读取
     */
    public static void fileChannel(){
        try {
            RandomAccessFile afile = new RandomAccessFile("hello.txt", "rw");
            FileChannel fc = afile.getChannel();
            ByteBuffer bb = ByteBuffer.allocate(48);
            int byteRead;
            while ((byteRead = fc.read(bb)) != -1){//确保读完
                System.out.println("read:" + byteRead);
                bb.flip();//翻转为读状态
                while (bb.hasRemaining()){//直到没有可读的字节
                    System.out.println(String.valueOf(bb.get()));
                }
                bb.clear();
            }
            fc.close();
            afile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于FileChannel transferTo transferFrom 方法文件复制
     */
    public static void fileTransfer(){
        try {
            RandomAccessFile afile = new RandomAccessFile("hello.txt", "rw");
            RandomAccessFile bfile = new RandomAccessFile("hehe.txt", "rw");
            FileChannel ac = afile.getChannel();
            FileChannel bc = bfile.getChannel();
            long position = 0;
            long count = ac.size();
//            bc.transferFrom(ac, position, count);
            ac.transferTo(position, count, bc);
            ac.close();
            afile.close();
            bc.close();
            bfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileSelector(){
        try {
            RandomAccessFile afile = new RandomAccessFile("hello.txt", "rw");
            Channel c = afile.getChannel();
            Selector s = Selector.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于基本channel buffer的文件复制操作
     */
    public static void fileTransferByNormal() {
        try {
            RandomAccessFile afile = new RandomAccessFile("hello.txt", "rw");
            RandomAccessFile bfile = new RandomAccessFile("hehe.txt", "rw");
            FileChannel ac = afile.getChannel();
            FileChannel bc = bfile.getChannel();

            ByteBuffer bf = ByteBuffer.allocateDirect(16 * 1024);
            while (ac.read(bf) != -1) {
                bf.flip();
                while (bf.hasRemaining()) {//确保写完
                    bc.write(bf);
                }
                bf.clear();
            }
            ac.close();
            afile.close();
            bc.close();
            bfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
