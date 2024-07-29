package com.leepsmart.code.common.utils;


import com.alibaba.fastjson2.JSONObject;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.utils.encrypt.AESCoderUtil;

/**
 * 验证码签名校验
 *
 * @author leepsmart
 */
public class SignDecodeUtil {

    /**
     * 验证码秘钥校验
     *
     * @param code 验证码
     * @param sign 签名
     * @return boolean
     */
    public static JSONObject codeMatches(String code, String sign, long expirationTime) {
        JSONObject result = new JSONObject();
        result.put("isSuccess", false);
        result.put("errorMessage", "验证码错误");
        if (!CommUtil.checkNull(code, sign)) {
            LogUtil.error("code 或 sign 参数为空");
            result.put("errorMessage", "验证码参数错误");
            return result;
        }
        // 解密
        String content;
        try {
            content = AESCoderUtil.decrypt(KeyConfig.KEY_AES, sign);
        } catch (Exception e) {
            return result;
        }
        if (!content.contains("_")) {
            return result;
        }
        String[] str = content.split("_");
        long time = Long.parseLong(str[0]);
        // 校验该验证码有效期
        if (System.currentTimeMillis() - time > expirationTime) {
            LogUtil.error("验证码已过期");
            result.put("errorMessage", "验证码已过期");
            return result;
        }
        // 比对验证码
        if (!str[1].equals(code)) {
            LogUtil.error("验证码错误");
            return result;
        }
        result.put("isSuccess", true);
        result.remove("errorMessage");
        result.put("message", "校验成功");
        return result;
    }
}
