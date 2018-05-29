package org.windwant.io.io;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 18-5-28.
 */
public class DataInputOutputStream {
    public static void main(String[] args) throws IOException {
        readTypes();
    }

    /**
     * 写入 读取 java 原始类型 二进制数据
     * 给定类型的每个值，所需空间相同
     * 读写速度快
     */
    public static void readTypes() throws IOException {
        String textFile = "io-demo/s.dat";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bout);//new FileOutputStream(textFile));
        out.writeShort(1);//写入内容
        out.writeInt(2);
        out.writeDouble(3);
        out.writeLong(4);
        out.writeBoolean(false);
        out.writeChar(Character.valueOf('A'));
        out.write(textFile.getBytes());
        out.flush();
        out.close();
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(bout.toByteArray()));//new FileInputStream(textFile));
        System.out.println(di.readShort());
        System.out.println(di.readInt());
        System.out.println(di.readDouble());
        System.out.println(di.readLong());
        System.out.println(di.readBoolean());
        System.out.println(di.readChar());
        byte[] b = new byte[di.available()];
        while ((di.read(b)) != -1){}
        System.out.print(new String(b, Charset.defaultCharset()));
        di.close();
        bout.close();
    }


    /**
     * 一般不用
     * 读取由修订过的UTF-8格式字符构成的字符串
     * 必须在开头吸入字节长度 writeShort（字节长度）
     * @throws IOException
     */
    public static void readUTF() throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(new File("s.txt")));
        String text = "this is a test";
        out.writeShort(text.length());//写入长度
        out.write(text.getBytes());//写入内容
        out.flush();
        out.close();
        DataInputStream di = new DataInputStream(new FileInputStream("s.txt"));
        System.out.println(di.readUTF());
        di.close();
    }
}
