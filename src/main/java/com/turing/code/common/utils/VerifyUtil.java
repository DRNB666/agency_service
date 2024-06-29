package com.turing.code.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class VerifyUtil {

    /**
     * 校验金额格式
     *
     * @param content
     * @return
     */
    public static boolean verifyMoney(String content) {
        return verify("^\\d+(\\.\\d{1,2})?$", content);
    }

    /**
     * 校验手机号码
     *
     * @return
     */
    public static boolean verifyPhone(String content) {
        if (null == content || "".equals(content) || content.length() != 11) {
            return false;
        }
        return verify("^1[3-9]\\d{9}$", content);
    }

    /**
     * 校验身份证 第一代与第二代
     */
    public static boolean verifyIdCard(String content) {
        return verify("(^\\d{8}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}$)|(^\\d{6}(18|19|20)\\d{2}(0\\d|10|11|12)([0-2]\\d|30|31)\\d{3}(\\d|X|x)$)", content);
    }

    /**
     * 校验整数
     *
     * @return
     */
    public static boolean verifyInteger(String content) {
        return verify("^[-\\+]?[\\d]*$", content);
    }

    /**
     * 校验数值
     *
     * @return
     */
    public static boolean verifyNumber(String content) {
        return verify("^-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?$", content);
    }

    /**
     * 自定义校验
     *
     * @param regex   正则表达式字符串
     * @param content 校验的内容
     * @return
     */
    public static boolean verify(String regex, String content) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }
    /**
     * luhm校验银行卡号是否合法
     * 从卡号最后一位数字开始，逆向将奇数位相加
     * 从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，则将其减去9），再求和
     * 奇偶相加为10的倍数表示通过
     *
     * @param bankCode 要先经过bankCode.trim()处理
     * @return
     */
    public static boolean verifyBank(String bankCode) {
        if (StringUtils.isBlank(bankCode)) {
            return false;
        }
        if (!bankCode.matches("^\\d{16,19}$")) {
            return false;
        }
        char[] chs = bankCode.toCharArray();
        int sum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int num = Integer.parseInt(String.valueOf(chs[i]));
            if (j % 2 != 0) {
                //偶数位
                num = num * 2;
                if (num > 9) {
                    num = num - 9;
                }
            }
            sum += num;
        }
        return sum % 10 == 0;
    }
}
