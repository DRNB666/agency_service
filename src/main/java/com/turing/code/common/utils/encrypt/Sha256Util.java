package com.turing.code.common.utils.encrypt;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.utils.LogUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Sha256Util {

    /**
     * 签名算法规则
     * 1. 对请求参数的 key 进行升序排序
     * 2. 将排序后的参数 按照 key+value 的方式拼接
     * 3. 最后拼接 时间戳 和 secret
     * 4. 将拼接后的数据 进行 sha256 加密
     *
     * @param params
     * @param appSecret
     * @return
     */
    public static String sign(JSONObject params, Long timestamp, String appSecret) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        for (String key : keys) {
            String value = params.getString(key);
            query.append(key).append(value);
        }
        query.append(timestamp);
        query.append(appSecret);
        LogUtil.info(query.toString());
        return getSHA256(query.toString()).toUpperCase();
    }

    /**
     * 验签
     *
     * @param params
     * @return
     */
    public static boolean verifySign(JSONObject params, String appSecret, long timestamp, String sign) {
        String sign1 = sign(params, timestamp, appSecret);
        LogUtil.info(sign1);
        LogUtil.info(sign);
        return sign.equals(sign1);
    }


    /**
     * SHA256加密
     *
     * @param str 加密内容
     * @return 密文
     */
    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) throws InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test", "123");
        jsonObject.put("api", "11122");
        long timestamp = System.currentTimeMillis();
        jsonObject.put("timestamp", timestamp);
        System.out.println(timestamp);
        // api11122test123timestamp16004947399902524ad2699c67074c0980e09701faa30c55d69b04cdda9372c6db6ca3af2a9aa
        // 6904C269424477E428070393818D3DF442F4F0171F5C5545DCCE31DBB68B893A
        System.out.println(sign(jsonObject, timestamp, "5bae47a42e1ca63588c7fca57829bc1d3c4f03efa0b9826406601cfb7d4d3e8a"));
    }

}
