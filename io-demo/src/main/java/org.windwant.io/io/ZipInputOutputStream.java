package org.windwant.io.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 18-5-28.
 */
public class ZipInputOutputStream {
    public static void main(String[] args) throws IOException {
        //生成包含两个文件的压缩文件
        String text = "this is a test";
        String text1 = "this is not a test";
        String zipFile = "io-demo/s.zip";
        String inFile1 = "s1.txt";
        String inFile2 = "s2.txt";
        //压缩输出流 包装文件输出流
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
        //添加第一个文件
        zout.putNextEntry(new ZipEntry(inFile1));
        //向第一个文件写入内容
        zout.write(text.getBytes());
        //添加第二个文件
        zout.putNextEntry(new ZipEntry(inFile2));
        //向第二个文件写入内容
        zout.write(text1.getBytes());
        zout.flush();
        zout.close();
        //压缩文件输入流
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        //获取第一个文件
        zin.getNextEntry();
        //读取总承载缓存
        ByteBuffer buf = ByteBuffer.allocate(1024*1024);
        //读取第一个文件内容
        System.out.println(new String(readZip(zin, buf), Charset.forName("UTF-8")));
        zin.getNextEntry();
        //读取第二个文件内容
        System.out.println(new String(readZip(zin, buf), Charset.forName("UTF-8")));
        zin.close();
        buf = null;
    }

    //读取压缩文件内容
    private static byte[] readZip(ZipInputStream in, ByteBuffer buffer) throws IOException {
        buffer.clear();
        byte[] bytes = new byte[1024];
        int size = 0;
        int i = 0;
        while ((i = in.read(bytes)) != -1){
            buffer.put(bytes);
            bytes = new byte[1024];
            size += i;
        }
        bytes = new byte[size];
        buffer.flip();
        buffer.get(bytes, 0, size);
        return bytes;
    }
}
