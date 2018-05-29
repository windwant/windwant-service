package org.windwant.io.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 18-5-28.
 */
public class ZipStream extends FileStream{
    public ZipInputStream getZipInputStream(String zipFile){
        try {
            return new ZipInputStream(new FileInputStream(zipFile));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public ZipInputStream getZipInputStream(File zipFile){
        try {
            return new ZipInputStream(new FileInputStream(zipFile));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public ZipOutputStream getZipOutputStream(String zipFile){
        try {
            return new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public ZipOutputStream getZipOutputStream(File zipFile){
        try {
            return new ZipOutputStream(new FileOutputStream(zipFile));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public ZipEntry generateZipEntry(String fileName){
        ZipEntry entry = new ZipEntry(fileName);
        entry.setComment("zip file " + fileName);
        return entry;
    }

    public void writeString(ZipOutputStream zout, String content){
        try {
            zout.write(content.getBytes());
        } catch (IOException e) {
            return;
        }
    }

    /**
     * 读取压缩文件内容
     * @param in
     * @throws IOException
     */
    public void readZip(ZipInputStream in) throws IOException {
        if(in == null) return;
        ZipEntry entry = in.getNextEntry();
        //读取总承载缓存
        ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);
        byte[] bytes = new byte[1024];
        int num = 1;
        while (entry != null){
            System.out.println("zip file " + num + ": " + entry.getName());
            if(!entry.isDirectory()) {
                if (entry.getName().endsWith(".txt")
                        || entry.getName().endsWith(".TXT")
                        || entry.getName().endsWith(".xml")
                        || entry.getName().endsWith(".XML")) {
                    buf.clear();
                    int size = 0;
                    int i = 0;
                    while ((i = in.read(bytes)) != -1) {
                        buf.put(bytes);
                        bytes = new byte[1024];
                        size += i;
                    }
                    bytes = new byte[size];
                    buf.flip();
                    buf.get(bytes, 0, size);
                    System.out.println("内容：" + new String(bytes, Charset.forName("UTF-8")));
                }
            }
            entry = in.getNextEntry();
            num++;
        }
        buf = null;
        bytes = null;
    }

    /**
     * 解压文件
     * @param fileName
     * @throws IOException
     */
    public void zip(String fileName, String des) throws IOException {
        File forZip = new File(fileName);
        if(!forZip.exists()){
            return;
        }
        long cur = System.currentTimeMillis();
        String zipFile;
        if(des != null && des != "") {
            zipFile = des;
        }else {
            int dotIndex = fileName.lastIndexOf(".");
            zipFile = dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
            zipFile += ".zip";
        }

        ZipOutputStream out = getZipOutputStream(zipFile);
        writeContent(out, forZip, "");//压缩包里文件根路径为被压缩文件名
        out.flush();
        out.close();
        System.out.println("execute zip: " + (System.currentTimeMillis() - cur) / 1000);
    }

    /**
     * 添加压缩文件
     * @param zout 压缩输出流
     * @param file 添加文件
     * @param parentPath 相对父路径
     * @throws IOException
     */
    public void writeContent(ZipOutputStream zout, File file, String parentPath) throws IOException {
        String path = parentPath;
        if(file.isDirectory()){
            path = parentPath + file.getName() + "/"; //文件夹必须加末尾 “/”
            zout.putNextEntry(new ZipEntry(path));
            File[] files = file.listFiles();
            for (File f : files) {
                writeContent(zout, f, path);
            }
        }else {
            path += file.getName();
            zout.putNextEntry(new ZipEntry(path));
            byte[] bytes = new byte[1024];
            int i;
            FileInputStream fin = new FileInputStream(file);
            while ((i = fin.read(bytes)) != -1) {
                zout.write(bytes, 0, i);
            }
            fin.close();
            bytes = null;
        }
        System.out.println("添加文件：" + path);
        zout.closeEntry();
    }


    /**
     * 解压文件
     * @throws IOException
     */
    public void unZip(String zipFile, String des) throws IOException {
        if(!new File(zipFile).exists()){
            return;
        }
        long cur = System.currentTimeMillis();
        String basePath;
        if(des != null && des != ""){
            basePath = des;
        }else {
            int separatorIndex = zipFile.lastIndexOf(".");
            basePath = separatorIndex == -1 ? "/" : zipFile.substring(0, separatorIndex);
        }

        //根文件夹创建
        new File(basePath).mkdirs();

        //压缩输入流
        ZipInputStream in = getZipInputStream(zipFile);
        ZipEntry entry = in.getNextEntry();
        byte[] bytes = new byte[1024];
        int num = 1;
        while (entry != null){
            System.out.println("zip file " + num + ": " + entry.getName());
            String tempFilePath = basePath + "/" + entry.getName();
            //文件夹创建
            if(entry.isDirectory()){
                new File(tempFilePath).mkdir();
            }else{
                //文件复制
                RandomAccessFile file = new RandomAccessFile(basePath + "/" + entry.getName(), "rw");
                int i = 0;
                while ((i = in.read(bytes)) != -1){
                    file.getChannel().write(ByteBuffer.wrap(bytes, 0, i));
                }
                file.close();
            }
            entry = in.getNextEntry();
            num++;
        }
        bytes = null;
        System.out.println("execute unzip: " + (System.currentTimeMillis() - cur)/1000);
    }
}
