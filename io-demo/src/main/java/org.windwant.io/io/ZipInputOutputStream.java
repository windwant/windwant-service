package org.windwant.io.io;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 18-5-28.
 */
public class ZipInputOutputStream extends ZipStream {
    public static void main(String[] args) throws IOException {
        testUnZipAndZip();
    }

    /**
     * 测试生成 读取 压缩文件
     * @throws IOException
     */
    public static void testReadWriteZip() throws IOException {
        //生成包含两个文件的压缩文件
        String[] texts = {"this is a test", "this is not a test"};
        String zipFile = "io-demo/s.zip";
        String[] infiles = {"s1.txt", "s2.txt"};

        ZipInputOutputStream opt = new ZipInputOutputStream();
        //生成压缩文件
        opt.generateZip(zipFile, infiles, texts);
        //读取压缩文件
        opt.loadZip(zipFile);
    }

    /**
     * 测试压缩 加压
     * @throws IOException
     */
    public static void testUnZipAndZip() throws IOException {
        ZipInputOutputStream opt = new ZipInputOutputStream();
        //删除测试文件
        opt.delete("io-demo/io-demo.zip");
        opt.delete("io-demo/io-demo-tmp");
        //压缩
        opt.zip("io-demo", "io-demo/io-demo.zip");
        //解压
        opt.unZip("io-demo/io-demo.zip", "io-demo/io-demo-tmp");
    }

    /**
     * 生成压缩文件
     * @param zipFile
     * @param infiles
     * @param contents
     * @throws IOException
     */
    public void generateZip(String zipFile, String[] infiles, String[] contents) throws IOException {
        ZipOutputStream zout = getZipOutputStream(zipFile);
        for (int i = 0; i < infiles.length; i++) {
            zout.putNextEntry(generateZipEntry(infiles[i]));
            writeString(zout, contents[i]);
        }
        zout.flush();
        zout.close();
    }

    /**
     * 生成压缩文件
     * @param zipFile
     * @param infile
     * @param content
     * @throws IOException
     */
    public void generateZip(String zipFile, String infile, String content) throws IOException {
        ZipOutputStream zout = getZipOutputStream(zipFile);
        zout.putNextEntry(generateZipEntry(infile));
        writeString(zout, content);
        zout.flush();
        zout.close();
    }

    /**
     * 载入压缩文件
     * @param zipFile
     */
    public void loadZip(String zipFile){
        try {
            readZip(getZipInputStream(zipFile));
        } catch (IOException e) {
            return;
        }
    }
}
