package org.windwant.io.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Vector;

/**
 * 合并流
 * Created by Administrator on 18-5-31.
 */
public class SequenceStream {

    public static void main(String[] args) throws IOException {
        testSeq();
        testSeqE();
    }

    public static void testSeq() throws IOException {
        InputStream in1 = new FileInputStream("io-demo/hehe.txt");
        InputStream in2 = new FileInputStream("io-demo/hello.txt");

        SequenceInputStream sin = new SequenceInputStream(in1, in2);
        byte[] b = new byte[1024];
        ByteBuffer buf = ByteBuffer.allocate(in1.available() + in2.available());
        int i;
        while ((i = sin.read(b)) != -1){
            buf.put(b, 0, i);
        }
        System.out.println(new String(buf.array(), Charset.forName("UTF-8")));
        in1.close();
        in2.close();
        sin.close();
    }

    public static void testSeqE() throws IOException {
        InputStream in1 = new FileInputStream("io-demo/hehe.txt");
        InputStream in2 = new FileInputStream("io-demo/hello.txt");
        InputStream in3 = new FileInputStream("io-demo/README.md");

        Vector<InputStream> ins = new Vector();
        ins.add(new FileInputStream("io-demo/hehe.txt"));
        ins.add(new FileInputStream("io-demo/hello.txt"));
        ins.add(new FileInputStream("io-demo/README.md"));

        SequenceInputStream vin = new SequenceInputStream(ins.elements());
        byte[] b = new byte[1024];
        ByteBuffer buf = ByteBuffer.allocate(in1.available() + in2.available() + in3.available());
        int i;
        while ((i = vin.read(b)) != -1){
            buf.put(b, 0, i);
        }
        b = null;
        System.out.println(new String(buf.array(), Charset.forName("UTF-8")));
        in1.close();
        in2.close();
        in3.close();
        vin.close();
    }
}
