package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 18-8-23.
 */
public class DH {
    public static void main(String[] args) {
        try {
            String msg = "RSA TEST MSG";
            Map<String, Object> xkeys = initDHKey();
            System.out.println("x public key: \n" + Base64.encodeBase64String(getPublicKey(xkeys)));
            System.out.println("x private key: \n" + Base64.encodeBase64String(getPrivateKey(xkeys)));
            Map<String, Object> ykeys = initDHKey(getPublicKey(xkeys));
            System.out.println("y public key: \n" + Base64.encodeBase64String(getPublicKey(ykeys)));
            System.out.println("y private key: \n" + Base64.encodeBase64String(getPrivateKey(ykeys)));
            byte[] xlocal = getSecretKey(getPublicKey(ykeys), getPrivateKey(xkeys));
            System.out.println("x local secret: \n" + Base64.encodeBase64String(xlocal));
            byte[] ylocal = getSecretKey(getPublicKey(xkeys), getPrivateKey(ykeys));
            System.out.println("y local secret: \n" + Base64.encodeBase64String(ylocal));

            byte[] codex = encrypt(msg.getBytes(), xlocal);
            System.out.println("x send msg: \n" + msg);
            System.out.println("x send msg encrypt: \n" + Base64.encodeBase64String(codex));
            byte[] ydcode = decrypt(codex, ylocal);
            System.out.println("y decode msg: \n" + new String(ydcode));

            byte[] codey = encrypt(msg.getBytes(), ylocal);
            System.out.println("y send msg: \n" + msg);
            System.out.println("y send msg encrypt: \n" + Base64.encodeBase64String(codey));
            byte[] xdcode = decrypt(codey, xlocal);
            System.out.println("x decode msg: \n" + new String(xdcode));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getPublicKey(Map<String, Object> map){
        Key key = (Key) map.get("DHPublicKey");
        return key.getEncoded();
    }

    private static byte[] getPrivateKey(Map<String, Object> map){
        Key key = (Key) map.get("DHPrivateKey");
        return key.getEncoded();
    }

    /**
     * 初始化甲方秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, Object> initDHKey() throws NoSuchAlgorithmException {
        //秘钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(512);//默认秘钥长度 1024，秘钥长度必须为64倍数，范围在512~1024之间
        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        //私有
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        return new HashMap(){{put("DHPublicKey", publicKey);put("DHPrivateKey", privateKey);}};
    }

    /**
     * 初始化乙方秘钥
     * @param key 甲方公钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, Object> initDHKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        //解析甲方公钥 转换公钥材料
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //实例化秘钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        //产生公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        DHParameterSpec dhParameterSpec = ((DHPublicKey)publicKey).getParams();
        //秘钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(publicKey.getAlgorithm());
        keyPairGenerator.initialize(dhParameterSpec);
        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        DHPublicKey spublicKey = (DHPublicKey) keyPair.getPublic();
        //私有
        DHPrivateKey sprivateKey = (DHPrivateKey) keyPair.getPrivate();
        return new HashMap(){{put("DHPublicKey", spublicKey);put("DHPrivateKey", sprivateKey);}};
    }

    /**
     * 转换秘钥
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static SecretKey toDHKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }

    /**
     * 构建秘钥
     * @param publicKey
     * @param privateKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        keyAgreement.init(priKey);
        keyAgreement.doPhase(pubKey, true);
        //生成本地秘钥
        SecretKey secretKey = keyAgreement.generateSecret("AES");
        return secretKey.getEncoded();
    }

    /**
     * 加密
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        SecretKey k = toDHKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(k.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        SecretKey k = toDHKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(k.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
