package org.windwant.httptest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 * Created by Administrator on 18-7-5.
 */
public class HttpUploadTest {
    /**
     * 文件上传
     */
    public static void testUploadFile(){
        File f = new File("httpserver-demo\\hehe.txt");
        FileBody fileBody = new FileBody(f);
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("myfile", fileBody);
        HttpPost hp = HttpUtils.getHttpPost("http://localhost:8888/upload");
        hp.setEntity(multipartEntity);
        CloseableHttpClient chc = HttpClients.createDefault();
        try {
            CloseableHttpResponse r = chc.execute(hp, new ResponseHandler<CloseableHttpResponse>() {
                public CloseableHttpResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    System.out.println(EntityUtils.toString(response.getEntity()));
                    return (CloseableHttpResponse) response;
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testUploadFile();
    }
}
