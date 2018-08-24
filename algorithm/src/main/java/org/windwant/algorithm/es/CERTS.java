package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.*;

/**
 * Created by Administrator on 18-8-24.
 */
public class CERTS {

    public static void main(String[] args) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        System.out.println(getPublicKey(getCertificate("D:\\tmp\\cert\\windwant.cer")));

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
        try(InputStream in = new FileInputStream(path)) {
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
}
