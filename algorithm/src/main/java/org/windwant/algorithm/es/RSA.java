package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 18-8-23.
 */
public class RSA {
    public static void main(String[] args) {
        try {
            String msg = "RSA TEST MSG";
            Map<String, Object> keys = initRSAKey();
            System.out.println("public key: \n" + Base64.encodeBase64String(getPublicKey(keys)));
            System.out.println("private key: \n" + Base64.encodeBase64String(getPrivateKey(keys)));

            byte[] code = encryptByPrivateKey(msg.getBytes(), getPrivateKey(keys));
            System.out.println("private send msg: \n" + msg);
            System.out.println("private send msg encrypt: \n" + Base64.encodeBase64String(code));
            byte[] dcode = decryptByPublicKey(code, getPublicKey(keys));
            System.out.println("public decrypt: \n" + new String(dcode));

            code = encryptByPublicKey(msg.getBytes(), getPublicKey(keys));
            System.out.println("public send msg: \n" + msg);
            System.out.println("public send msg encrypt: \n" + Base64.encodeBase64String(code));
            dcode = decryptByPrivateKey(code, getPrivateKey(keys));
            System.out.println("private send msg decrypt: \n" + new String(dcode));
        } catch (NoSuchAlgorithmException e) {
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
        Key key = (Key) map.get("RSAPublicKey");
        return key.getEncoded();
    }

    private static byte[] getPrivateKey(Map<String, Object> map){
        Key key = (Key) map.get("RSAPrivateKey");
        return key.getEncoded();
    }

    /**
     * 初始化秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, Object> initRSAKey() throws NoSuchAlgorithmException {
        //秘钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512); //RSA默认秘钥长度为124，秘钥长度必须为64的倍数，范围在512~65536之间
        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私有
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new HashMap(){{put("RSAPublicKey", publicKey);put("RSAPrivateKey", privateKey);}};
    }

    /**
     * 转换秘钥
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PublicKey toPublicKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 转换秘钥
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static PrivateKey toPrivateKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
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
    private static byte[] encryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PublicKey publicKey = toPublicKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
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
    private static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        PrivateKey privateKey = toPrivateKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
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
    private static byte[] decryptByPublicKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        PublicKey publicKey = toPublicKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
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
    private static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        PrivateKey privateKey = toPrivateKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
}
