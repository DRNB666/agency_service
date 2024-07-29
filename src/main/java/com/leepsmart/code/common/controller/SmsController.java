package com.leepsmart.code.common.controller;


import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.annotation.repeat.PreventRepeat;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
import com.leepsmart.code.common.utils.SignDecodeUtil;
import com.leepsmart.code.common.utils.encrypt.AESCoderUtil;
import com.leepsmart.code.common.utils.returnbody.Code;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.common.utils.sms.SmsSendConfig;
import com.leepsmart.code.common.utils.sms.SmsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "sms/public", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
@Api(tags = "系统-短信接口")
public class SmsController {

    @Resource
    private SmsUtil smsUtil;
    /**
     * 验证码模板
     */
    @Value("${ali.sms.template.code}")
    private String templateCode;


    @ApiOperation(value = "获取短信验证码", notes = "输入准确手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "phone", value = "手机号码", required = true, example = "13111111111"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifyCode", value = "图片验证码", required = true, example = "1234"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifySign", value = "图片验证码密钥", required = true),
    })
    @PostMapping("sendCode")
    @PreventRepeat
    @ParameterVerify(notNull = {"phone", "verifyCode", "verifySign"}, isPhone = "phone")
    public String sendCode(String phone, String verifyCode, String verifySign) throws Exception {
        // 校验图片验证码
        JSONObject verifyResult = SignDecodeUtil.codeMatches(verifyCode, verifySign, 60000);
        if (!verifyResult.getBoolean("isSuccess")) {
            return ReturnBody.error(Code.IMAGE_CODE_ERROR, verifyResult.getString("errorMessage"));
        }
        // 发送短信验证码
        String result = this.sendCode(phone);
        if (!CommUtil.checkNull(result)) {
            return ReturnBody.error();
        }
        // 返回签名
        long time = System.currentTimeMillis();
        String sign = AESCoderUtil.encrypt(KeyConfig.KEY_AES, time + "_" + phone + result);
        return ReturnBody.success(sign);
    }


    @ApiOperation(value = "获取短信验证码", notes = "输入准确手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phone", value = "手机号码", required = true, example = "13111111111"),
    })
    @PostMapping("sendCodePhoneOnly")
    @PreventRepeat
    @ParameterVerify(notNull = "phone", isPhone = "phone")
    public String sendCodePhoneOnly(String phone) throws Exception {
        // 发送短信验证码
        String result = this.sendCode(phone);
        if (!CommUtil.checkNull(result)) {
            return ReturnBody.error();
        }
        // 返回签名
        long time = System.currentTimeMillis();
        String sign = AESCoderUtil.encrypt(KeyConfig.KEY_AES, time + "_" + phone + result);
        return ReturnBody.success(sign);
    }


    // 发送验证码
    public String sendCode(String phone) {
        // 获取随机数
        String code = CommUtil.createRandom(true, 6);

        SmsSendConfig<String> smsSendConfig = new SmsSendConfig<String>(phone) {
            @Override
            public void setSmsConfig(SendSmsRequest request) {
                // 短信模板
                request.setTemplateCode(templateCode);
                request.setTemplateParam("{\"code\":\"" + code + "\"}");
            }

            @Override
            public String success(String env) {
                // 成功阶段逻辑
                if (env.equals("pro")) {
                    return code;
                }
                LogUtil.info(code);
                return "123456";
            }

            @Override
            public String error() {
                // 处理异常
                return null;
            }
        };

        String result = smsUtil.send(smsSendConfig);
        return result;
    }

}
