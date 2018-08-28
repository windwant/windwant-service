package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.*;

/**
 *
 * tomcat server.xml
 *  <Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
 *    maxThreads="150" SSLEnabled="true" sslProtocal="TLS" keystoreFile="conf/windwant.store" keystorePass="123456">
 *    <!-- <SSLHostConfig>
 *    <Certificate certificateKeystoreFile="D:/tmp/cert/windwant.cer"
 *    type="RSA" />
 *    </SSLHostConfig> -->
 *  </Connector>
 * Created by Administrator on 18-8-24.
 */
public class CERTS {
    public static String keyStorePath = "windwant.store";
    public static String keyStorePathx = "ca.p12";

    public static void main(String[] args) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        try {
            //使用证书构造https请求
//            HttpsURLConnection conn = (HttpsURLConnection) new URL("https://org.windwant.com:8443/").openConnection();
//            SSLSocketFactory sslSocketFactory = getSSLSocketFactory(keyStorePath, keyStorePath, "123456");

            HttpsURLConnection conn = (HttpsURLConnection) new URL("https://localhost:8443/").openConnection();
            SSLSocketFactory sslSocketFactory = getSSLSocketFactory(keyStorePathx, keyStorePathx, "123456");
            conn.setSSLSocketFactory(sslSocketFactory);
            byte[] data = new byte[1024];
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            System.out.println(dis.available());
            int i = 0;
            OutputStream out = new FileOutputStream("algorithm/index.html");
            while ((i = dis.read(data)) != -1)
                out.write(data, 0, i);
            conn.disconnect();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    public static PublicKey getPublicKey(X509Certificate certificate){
        return certificate.getPublicKey();
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        //获取私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("org.windwant", "123456".toCharArray());
        System.out.println("private key: \n" + Base64.encodeBase64String(privateKey.getEncoded()));
        return privateKey;
    }

    public static X509Certificate getCertificate(KeyStore keyStore) throws KeyStoreException {
        //获取证书
        X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate("windwant");
        System.out.println("certificate: \n" + x509Certificate);
        return x509Certificate;
    }

    public static X509Certificate getCertificate(String path) {
        try(InputStream in = new FileInputStream(path)) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(in);
            System.out.println("certificate: \n" + certificate);
            return certificate;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Signature getSignature(X509Certificate certificate) throws NoSuchAlgorithmException {
        //获取签名
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        System.out.println("signature: \n" + signature);
        return signature;
    }
    /**
     * 加载获取秘钥库
     * @param path
     * @param password
     * @return
     */
    public static KeyStore getKeyStore(String path, String password) {
        try(InputStream in = CERTS.class.getClassLoader().getResourceAsStream(path)) {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(in, password.toCharArray());
            return keyStore;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取SSLSocketFactory
     * @param keyStorePath
     * @param trustStorePath
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws KeyManagementException
     */
    public static SSLSocketFactory getSSLSocketFactory(String keyStorePath, String trustStorePath, String password) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        //秘钥库管理工厂
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        keyManagerFactory.init(keyStore, password.toCharArray());
        //信任库管理工厂
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore trustStore = getKeyStore(trustStorePath, password);
        trustManagerFactory.init(trustStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }

}
