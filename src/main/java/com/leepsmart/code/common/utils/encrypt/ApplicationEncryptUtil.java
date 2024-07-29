package com.leepsmart.code.common.utils.encrypt;

import com.baomidou.mybatisplus.core.toolkit.AES;

/**
 * application.yaml 配置密文生成工具
 *
 * @author leepsmart
 */
public class ApplicationEncryptUtil {

    public static void main(String[] args) {
        String randomKey = "ed51196b3beed811";

        String content = "123123";

        String result = AES.encrypt(content, randomKey);

        System.out.println(result);
    }
}
