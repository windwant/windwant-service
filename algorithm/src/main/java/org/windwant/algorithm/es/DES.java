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
public class DES {
    public static void main(String[] args) {
        try {
            byte[] key = initDESKey();
            byte[] en = encryptDES("test".getBytes(), key);
            System.out.println(Hex.encodeHexString(en));
            System.out.println(new String(decryptDES(en, key)));
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
    private static byte[] initDESKey() throws NoSuchAlgorithmException {
        //实例化秘钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 生成64位秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] init64DESKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        //实例化秘钥生成器 BC Boucy Calstle 安全提供者,使用Boucy Calstle提供的DES 64位算法
        //等同于 Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES", "BC");
        keyGenerator.init(64);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 使用自定义秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initCustomDESKey(String key) throws NoSuchAlgorithmException {
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
    private static Key toDESKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        //实例化秘钥材料
        DESKeySpec desKeySpec = new DESKeySpec(key);
        //实例化秘钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        //生成秘钥
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
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
    private static byte[] encryptDES(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toDESKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
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
    private static byte[] decryptDES(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toDESKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
