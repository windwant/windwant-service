package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法
 * Created by Administrator on 18-8-23.
 */
public class MD {
    public static void main(String[] args) {
        try {
            byte[] key = initHmacMD5Key();
            byte[] data = encodeHmacMD5("test".getBytes(), key);
            System.out.println(Hex.encodeHexString(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化HmacMD5秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] initHmacMD5Key() throws NoSuchAlgorithmException {
        //初始化
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        //产生秘钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得秘钥
        return secretKey.getEncoded();
    }

    /**
     * HmacMD5消息摘要
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static byte[] encodeHmacMD5(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        //恢复秘钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
        //初始化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
}
