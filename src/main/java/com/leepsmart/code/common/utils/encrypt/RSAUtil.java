package com.leepsmart.code.common.utils.encrypt;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    /**
     * 签名算法
     */
    public static final String SIGNATURE_INSTANCE = "SHA1WithRSA";
    /**
     * 缺省的2048位密钥对,可处理245个字节(81个汉字)的数据
     */
    public static String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKJWkOqbc5SWDk8PqeV6c/25NaiTcLFBZxvtJpvtDvZblbHl+jxUmNyhSEx3zeLMdYI7aLgx0rsSURLmvwH0+OBWjGVLhc36T+Vnj7xeFjjEOCQUt3XB5jc0rVcsyFavKeONu4r83ylD8le8CL84CTMN8diLsLv/bNco0sqLsB3BAgMBAAECgYAR+JFMV28hGMd2ux4suDSU9ubYxZGpHbtjXQp0IncQkKzup63e9eKCMhyF0pFpNQchJqB+G43fhbSR6hdlCRBv36YPla1yPCICEw5m2HMEzCaoYBUh1YMdaLTGccE0N4t3S4DqkFg8aDv37vh6l5xLQZPK0pJ/nxbmVCf26z8sAQJBAOwD3qkvyktBAOKm8vidw15yQZY53UKh7C6Wp9+hC40aDZAgetqXAB2z1oYtKLcD4Pj+g3KRGK6JGpMpn6GaoSECQQCwFZdVaVGlPXZ+wLSMLyAj+9qQXNpsiB2U631IS+LWIzas5i8/OTVOdPr8C3N/QrANkvPENtBkCBEFpfQ2r8ihAkArAn2GKW5dDmiB5Vir09wxjvLC1KKsrONkjhHjn+6B9Th5hmG9Cf18bDRw2TPtPii2V3NO4oAz9kpAfTpY8dvhAkBXB9TsmkmIaoCO1aBnSUORhYHZqIss6Xn1iQ87FIbhYDY97uebUCNQy/Dhcqd7VvK9QszJRm5pgrMnH4c9IFUBAkBDCgBdXZpWA0WaEdPAtuJqBkcWeTaWVEWDMzd7mR6fZaVxsW+oE0xMQFEzqE8i2ObZ7wp3dnGDy7tD/iCBo+a4";
    /**
     * 公钥
     */
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiVpDqm3OUlg5PD6nlenP9uTWok3CxQWcb7Sab7Q72W5Wx5fo8VJjcoUhMd83izHWCO2i4MdK7ElES5r8B9PjgVoxlS4XN+k/lZ4+8XhY4xDgkFLd1weY3NK1XLMhWrynjjbuK/N8pQ/JXvAi/OAkzDfHYi7C7/2zXKNLKi7AdwQIDAQAB";
    /**
     * 字符集
     */
    public static String CHARSET = "utf-8";

    /**
     * 生成密钥对
     *
     * @param keyLength
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥字符串转PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥字符串转PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] encryptByPublicKey(byte[] content) throws Exception {
        return encryptByPublicKey(content, getPublicKey(PUBLIC_KEY));
    }

    public static String encryptByPublicKey(String content, String publicKey) throws Exception {
        return new String(Base64.getEncoder().encode(encryptByPublicKey(content.getBytes(CHARSET), getPublicKey(publicKey))));

    }

    public static String encryptByPublicKey(String content) throws Exception {
        return new String(Base64.getEncoder().encode(encryptByPublicKey(content.getBytes(CHARSET))));
    }

    /**
     * 私钥解密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] decryptByPrivateKey(byte[] content) throws Exception {
        return decryptByPrivateKey(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String decryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(content), getPrivateKey(privateKey)), CHARSET);

    }

    public static String decryptByPrivateKey(String content) throws Exception {
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(content)), CHARSET);
    }

    /**
     * 私钥加密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] encryptByPrivateKey(byte[] content) throws Exception {
        return encryptByPrivateKey(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String encryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(encryptByPrivateKey(content.getBytes(CHARSET), getPrivateKey(privateKey)), CHARSET);
    }

    public static String encryptByPrivateKey(String content) throws Exception {
        return new String(encryptByPrivateKey(content.getBytes(CHARSET)), CHARSET);
    }

    /**
     * 公钥解密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] decrypByPublicKey(byte[] content) throws Exception {
        return decrypByPublicKey(content, getPublicKey(PUBLIC_KEY));
    }

    public static String decrypByPublicKey(String content, String publicKey) throws Exception {
        return new String(decrypByPublicKey(Base64.getDecoder().decode(content), getPublicKey(publicKey)), CHARSET);

    }

    public static String decrypByPublicKey(String content) throws Exception {
        return new String(decrypByPublicKey(Base64.getDecoder().decode(content)), CHARSET);
    }

    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public static byte[] sign(byte[] content) throws Exception {
        return sign(content, getPrivateKey(PRIVATE_KEY));
    }

    public static String sign(String content, String privateKey) throws Exception {
        return new String(Base64.getEncoder().encode(sign(content.getBytes(CHARSET), getPrivateKey(privateKey))), CHARSET);
    }

    public static String sign(String content) throws Exception {
        return new String(Base64.getEncoder().encode(sign(content.getBytes(CHARSET))), CHARSET);
    }

    /**
     * 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initVerify(publicKey);
        signature.update(content);
        return signature.verify(sign);
    }

    public static boolean verify(byte[] content, byte[] sign) throws Exception {
        return verify(content, sign, getPublicKey(PUBLIC_KEY));
    }

    public static boolean verify(String content, String sign, String publicKey) throws Exception {
        return verify(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), getPublicKey(publicKey));
    }

    public static boolean verify(String content, String sign) throws Exception {
        return verify(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), getPublicKey(PUBLIC_KEY));
    }


    public static void main(String[] args) throws Exception {
        KeyPair keyPair = getKeyPair(1024);
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        System.out.println("公钥: " + publicKey);
        System.out.println("私钥: " + privateKey);
        // 公钥加密
        String content = "admin";
        String pwdText = encryptByPublicKey(content);
        System.out.println("加密后的密文: " + pwdText);
        // 私钥解密
        long startTime = System.currentTimeMillis();
        System.out.println("解密后的内容: " + decryptByPrivateKey(pwdText));
//        System.out.println("解密后的内容: "+decryptByPrivateKey(privateKey, PRIVATE_KEY));
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime));

        // 公钥加密
        String content1 = "123123";
        String pwdText1 = encryptByPublicKey(content1);
        System.out.println("加密后的密文: " + pwdText1);
        // 私钥解密
        System.out.println("解密后的内容: " + decryptByPrivateKey(pwdText1));
    }


}
