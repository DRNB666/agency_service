package com.turing.code.common.utils.encrypt;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.turing.code.common.config.KeyConfig;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES 对称加密算法
 *
 * @author turing
 */
public class AESCoderUtil {

    // 加密秘钥
    private static final String ivStr = "aga$6^11.90Q13e=";

    public static String encrypt(String strKey, String strIn) throws Exception {
        if (StringUtils.isEmpty(strKey)) {
            throw new Exception("decrypt string can't null or empty");
        }
        if (StringUtils.isEmpty(strIn)) {
            throw new Exception("decrypt string can't null or empty");
        }

        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes(StandardCharsets.UTF_8));
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encrypted);
    }

    public static String decrypt(String strKey, String strIn) throws Exception {
        if (StringUtils.isEmpty(strKey)) {
            throw new Exception("decrypt string can't null or empty");
        }
        if (StringUtils.isEmpty(strIn)) {
            throw new Exception("decrypt string can't null or empty");
        }
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encrypted1 = decoder.decodeBuffer(strIn);
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original, StandardCharsets.UTF_8);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return new SecretKeySpec(arrB, "AES");
    }

    public static void main(String[] args) throws Exception {
//        String Code = "中文ABc123";
//        String codE = AESCoderUtil.encrypt(KeyConfig.KEY_AES, Code);
//        LogUtil.info("原文：" + Code);
//        LogUtil.info("密文：" + codE);
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            LogUtil.info("解密：" + AESCoderUtil.decrypt(KeyConfig.KEY_AES, codE));
//        }
//        long endTime = System.currentTimeMillis();
//        LogUtil.info(endTime - startTime);
        String str = "1";
        String encrypt = AESCoderUtil.encrypt(KeyConfig.KEY_AES, str);
        String decrypt = AESCoderUtil.decrypt(KeyConfig.KEY_AES, encrypt);
        System.out.println(str.equals(decrypt));

        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();
        System.out.println("randomKey: " + randomKey);
        // 随机密钥加密
        String result = AES.encrypt("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8", randomKey);

        System.out.println("npw: " + result);
    }

}
