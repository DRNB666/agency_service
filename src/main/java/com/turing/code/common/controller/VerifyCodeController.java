package com.turing.code.common.controller;

import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.LogUtil;
import com.turing.code.common.utils.VerifyCodeUtil;
import com.turing.code.common.utils.encrypt.AESCoderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("public/verifyCode")
@Api(tags = "图片验证码接口")
public class VerifyCodeController {

    @Value("${spring.profiles.active}")
    private String env;

    @ApiOperation("获取图片验证码")
    @GetMapping
    public void verifyCode(HttpServletResponse response) throws Exception {
        String code = "1234";
        if (!env.equals("dev")) {
            code = CommUtil.createRandom(true, 4);
        }
        LogUtil.info(code);
        long time = System.currentTimeMillis();
        response.setHeader("sign", AESCoderUtil.encrypt(KeyConfig.KEY_AES, time + "_" + code));
        VerifyCodeUtil.outputImage(140, 60, response.getOutputStream(), code);
    }

}

