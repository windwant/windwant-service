package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Administrator on 18-8-23.
 */
public class IDEA {
    public static void main(String[] args) {
        try {
            byte[] key = initIDEAKey();
            byte[] en = encryptIDEA("test".getBytes(), key);
            System.out.println(Hex.encodeHexString(en));
            System.out.println(new String(decryptIDEA(en, key)));
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
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initIDEAKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        //实例化秘钥生成器
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("IDEA");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 使用自定义秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initCustomIDEAKey(String key) throws NoSuchAlgorithmException {
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
    private static Key toIDEAKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        //生成秘钥
        SecretKey secretKey = new SecretKeySpec(key, "IDEA");
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
    private static byte[] encryptIDEA(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toIDEAKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("IDEA/ECB/PKCS5Padding");
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
    private static byte[] decryptIDEA(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Key k = toIDEAKey(key);
        //实例化加密套件
        Cipher cipher = Cipher.getInstance("IDEA/ECB/PKCS5Padding");
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }
}
