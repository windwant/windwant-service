package org.windwant.httptest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.windwant.HTTPConstants;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Administrator on 18-7-5.
 */
public class HttpNioServerTest {
    public static final String TEST_URL = "http://localhost:8888/";

    public static void testHttpNIOServerRequest(){
        try (CloseableHttpResponse closeableHttpResponse = HttpUtils.httpGet(TEST_URL + HTTPConstants.RESOURCES.get(ThreadLocalRandom.current().nextInt(7)))){
            if(closeableHttpResponse == null) return;

            System.out.println(closeableHttpResponse.getStatusLine());
            System.out.println("byte: " + closeableHttpResponse.getEntity().getContent().available());
            if(HTTPConstants.CONTENT_TYPE.get("html").equals(closeableHttpResponse.getEntity().getContentType().getValue())){
                System.out.println(EntityUtils.toString(closeableHttpResponse.getEntity()));
            }else if(HTTPConstants.CONTENT_TYPE.get("png").equals(closeableHttpResponse.getEntity().getContentType().getValue())) {
                byte[] bbf = new byte[2048];
                InputStream in = closeableHttpResponse.getEntity().getContent();
                int pre = 0;
                int pos;
                MappedByteBuffer mbb = null;
                FileChannel fc = new RandomAccessFile("httpserver-demo\\head" +  + System.currentTimeMillis() + ".png", "rw").getChannel();
                while ((pos = in.read(bbf)) != -1){
                    mbb = fc.map(FileChannel.MapMode.READ_WRITE, pre, pos);
                    mbb.put(bbf, 0, pos);
                    bbf = new byte[2048];
                    pre = pos;
                }
                System.out.println("save request image size: " + fc.size() + "");
                HttpUtils.unmap(mbb);
            }

            System.out.println(Thread.currentThread().getName() + ": execute ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testHttpNIOServerRequest();
    }
}
