package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Administrator on 18-8-23.
 */
public class AES {
    public static void main(String[] args) {
        try {
            byte[] key = initAESKey();
            byte[] en = encryptAES("test".getBytes(), key);
            System.out.println(Hex.encodeHexString(en));
            System.out.println(new String(decryptAES(en, key)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initAESKey() throws NoSuchAlgorithmException {
        //实例化秘钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);//128 192 256
        SecretKey secretKey = keyGenerator.generateKey();
        //二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 使用自定义秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initCustomDESedeKey(String key) throws NoSuchAlgorithmException {
        //实例化秘钥生成器
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "BC");
        return secretKey.getEncoded();
    }

    /**
     * 转换秘钥
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static Key toAESKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
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
    private static byte[] encryptAES(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toAESKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
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
    private static byte[] decryptAES(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toAESKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
