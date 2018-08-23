package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Administrator on 18-8-23.
 */
public class DESede {
    public static void main(String[] args) {
        try {
            byte[] key = initDESedeKey();
            byte[] en = encryptDESede("test".getBytes(), key);
            System.out.println(Hex.encodeHexString(en));
            System.out.println(new String(decryptDESede(en, key)));
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
    private static byte[] initDESedeKey() throws NoSuchAlgorithmException {
        //实例化秘钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(168);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 生成64位秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] init128DESedeKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        //实例化秘钥生成器 BC Boucy Calstle 安全提供者,使用Boucy Calstle提供的DES 64位算法
        //等同于 Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede", "BC");
        keyGenerator.init(128);//192
        SecretKey secretKey = keyGenerator.generateKey();
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
    private static Key toDESedeKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        //实例化秘钥材料
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
        //实例化秘钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
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
    private static byte[] encryptDESede(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toDESedeKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
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
    private static byte[] decryptDESede(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toDESedeKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
