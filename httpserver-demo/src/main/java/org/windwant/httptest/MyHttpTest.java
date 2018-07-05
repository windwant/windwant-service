package org.windwant.httptest;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/6/5.
 */
public class MyHttpTest {
    public static final String URL_PNG = "http://pic1.win4000.com/wallpaper/b/594dfcbbf2b24.jpg";
    public static void main(String[] args) {
        testGetFile();
    }

    public static void testGetFileMap(){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet hp = new HttpGet(URL_PNG);
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(hp);
            RandomAccessFile rf = new RandomAccessFile("httpserver-demo\\head.png", "rw");
            FileChannel f = rf.getChannel();
            MappedByteBuffer mbb = null;
            byte[] bbf = new byte[2048];
            InputStream in = closeableHttpResponse.getEntity().getContent();
            int pos = 0;
            while (in.read(bbf) != -1){
                mbb = f.map(FileChannel.MapMode.READ_WRITE, pos, 2048);
                mbb.put(bbf);
                bbf = new byte[2048];
                pos += 2048;
            }
            HttpUtils.unmap(mbb);
            f.close();
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testGetFile(){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet hp = new HttpGet("http://localhost:8888/index.html");
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(hp);
            RandomAccessFile rf = new RandomAccessFile("httpserver-demo\\head.png", "rw");
            FileChannel f = rf.getChannel();
            ByteBuffer bf = ByteBuffer.allocate(2048);
            byte[] bbf = new byte[2048];
            InputStream in = closeableHttpResponse.getEntity().getContent();
            while (in.read(bbf) != -1){
                bf.put(bbf);
                bf.flip();
                f.write(bf);
                bf.clear();
//                bbf = new byte[2048];
            }
            f.close();
            rf.close();
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testMgr(){
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

            Registry<ConnectionSocketFactory> registry = registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();
            PoolingHttpClientConnectionManager pconn = new PoolingHttpClientConnectionManager(registry);
            pconn.setMaxTotal(100);
            pconn.setDefaultMaxPerRoute(10);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).build();
            pconn.setDefaultSocketConfig(socketConfig);

            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(pconn).build();
            HttpGet hp = new HttpGet("http://www.baidu.com");
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(hp);
            System.out.println(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpClientConnectionManager hccm = new BasicHttpClientConnectionManager();

    }

    public static void testProxy(){
        HttpHost hp = new HttpHost("http://www.baidu.com");
        CloseableHttpClient chc = HttpClients.custom().setProxy(hp).build();
    }

    public static void testSSL_TSL(){
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager trustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            System.out.println(sslContext.getProtocol());

            SSLSocketFactory sslSocketFactory = SSLContext.getDefault().getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket();
            sslSocket.setEnabledCipherSuites(new String[]{"SSL_RSA_WITH_RC4_128_MD5"});
            sslSocket.connect(new InetSocketAddress("localhost", 8003));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testRetryAndIntercept() {
        HttpRequestRetryHandler hry = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if(executionCount > 5){
                    return false;
                }
                if(exception instanceof NoHttpResponseException){
                    return true;
                }
                if(exception instanceof SSLHandshakeException){
                    return false;
                }

                HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
                if(!(request instanceof HttpEntityEnclosingRequest)){
                    return true;
                }
                return false;
            }
        };

        HttpRequestInterceptor hi = new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                Integer ai = (Integer) context.getAttribute("count");
                request.addHeader("count", String.valueOf(ai++));

                Header[] hs = request.getAllHeaders();
                for (Header h : hs) {
                    System.out.println(h.toString());
                }
                System.out.println(request.getRequestLine());
                System.out.println(request.getProtocolVersion());
            }
        };
        CloseableHttpClient chc = HttpClients.custom().setRetryHandler(hry).addInterceptorFirst(hi).build();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("VendorBusinessType", "2"));
        CloseableHttpResponse chr = null;
        try {
            UrlEncodedFormEntity uf = new UrlEncodedFormEntity(params, "UTF-8");
            HttpPost hp = new HttpPost("http://localhost:8003/test/postfile");
            hp.setEntity(uf);
//            HttpGet hp = HttpUtils.getHttpGet("localhost", 8003, "/test/postfile", null, params);
            HttpContext hc = new BasicHttpContext();
            hc.setAttribute("count", 0);
            chr = chc.execute(hp, hc);
            System.out.println(chr.getStatusLine());
            System.out.println(EntityUtils.toString(chr.getEntity()));
            EntityUtils.consume(chr.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                chr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件上传
     */
    public static void testUploadFile(){
        File f = new File("hehe.txt");
        FileBody fileBody = new FileBody(f);
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("myfile", fileBody);
        HttpPost hp = HttpUtils.getHttpPost("http://localhost:8006/test/postfile");
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

    public static void testFormSubmit() {
        CloseableHttpClient chc = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("VendorBusinessType", "2"));
        CloseableHttpResponse chr = null;
        try {
            HttpGet get = HttpUtils.getHttpGet("localhost", 8006, "/test/postfile", null, params);
            chr = chc.execute(get);
            System.out.println(chr.getStatusLine());
            HttpEntity e = chr.getEntity();
            System.out.println(EntityUtils.toString(e));
            EntityUtils.consume(e);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                chr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testGet() {
        CloseableHttpClient chc = HttpClients.createDefault();
//        HttpGet get = HttpUtils.getHttpGet("http://yongchetest.aachuxing.com/v3/resource/cities");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("VendorBusinessType", "2"));
        HttpGet get = HttpUtils.getHttpGet("localhost", 8004, "/ctrip/resource/day/service_price", null, params);
        CloseableHttpResponse chr = null;
        try {
            chr = chc.execute(get);
            System.out.println(chr.getStatusLine());
            HttpEntity e = chr.getEntity();
            System.out.println(EntityUtils.toString(e));
            EntityUtils.consume(e);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                chr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void testPost() {
        CloseableHttpClient chc = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://www.baidu.com");
        CloseableHttpResponse chr = null;
        try {
            chr = chc.execute(post);
            System.out.println(chr.getStatusLine());
            HttpEntity e = chr.getEntity();
            System.out.println(EntityUtils.toString(e));
            EntityUtils.consume(e);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                chr.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void test1(){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
            // Expected to be an absolute URI
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
