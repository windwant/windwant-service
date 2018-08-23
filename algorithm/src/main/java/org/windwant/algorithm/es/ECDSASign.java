package org.windwant.algorithm.es;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.eac.ECDSAPublicKey;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 18-8-23.
 */
public class ECDSASign {
    public static void main(String[] args) {
        try {
            String msg = "ECDSA SIGN MSG";
            Map<String, Object> keys = initKey();
            System.out.println("public key: \n" + Base64.encodeBase64String(getPublicKey(keys)));
            System.out.println("private key: \n" + Base64.encodeBase64String(getPrivateKey(keys)));

            byte[] code = sign(msg.getBytes(), getPrivateKey(keys));
            System.out.println("sign msg: \n" + msg);
            System.out.println("msg sign: \n" + Base64.encodeBase64String(code));

            boolean verify = verify(msg.getBytes(), getPublicKey(keys), code);
            System.out.println("verify: \n" + verify);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getPublicKey(Map<String, Object> map){
        Key key = (Key) map.get("ECDSAPublicKey");
        return key.getEncoded();
    }

    private static byte[] getPrivateKey(Map<String, Object> map){
        Key key = (Key) map.get("ECDSAPrivateKey");
        return key.getEncoded();
    }

    /**
     * 初始化秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, Object> initKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastlePQCProvider());
        BigInteger p = new BigInteger("12444444444444444444444444444444444444444444444444444");
        ECFieldFp ecFieldFp = new ECFieldFp(p);
        BigInteger a = new BigInteger("7ffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
        BigInteger b = new BigInteger("fsfafsdfffffffwewrwerwrwwwwwwwwwwwwwfffffffffffffffff", 16);

        ECParameterSpec ecParameterSpec = new ECParameterSpec(new EllipticCurve(ecFieldFp, a, b), ECPoint.POINT_INFINITY, a, 3);
        //秘钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA");
        keyPairGenerator.initialize(ecParameterSpec, new SecureRandom()); //RSA默认秘钥长度为124，秘钥长度必须为64的倍数，范围在512~65536之间
        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        //私有
        ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
        return new HashMap(){{put("ECDSAPublicKey", publicKey);put("ECDSAPrivateKey", privateKey);}};
    }

    /**
     * 签名
     * @param data
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    private static byte[] sign(byte[] data, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException {
        PrivateKey priKey = toPrivateKey(privateKey);
        Signature signature = Signature.getInstance("SHA512withECDSA");
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 校验
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    private static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException {
        PublicKey pubKey = toPublicKey(publicKey);
        Signature signature = Signature.getInstance("SHA512withECDSA");
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
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
        Security.addProvider(new BouncyCastlePQCProvider());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA");
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
        Security.addProvider(new BouncyCastlePQCProvider());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
}
