package com.turing.code.common.utils.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.turing.code.common.utils.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author turing
 * 短信模板工具
 */
@Component
public class SmsUtil {
    /**
     * 短信API产品名称
     */
    private final String product = "Dysmsapi";

    /**
     * 短信API产品域名
     */
    private final String domain = "dysmsapi.aliyuncs.com";

    /**
     * 区域Id
     */
    private final String REGION_ID = "cn-hangzhou";

    /**
     * 短信环境
     */
    @Value("${ali.sms.env}")
    private String env;

    /**
     * accessKeyId
     */
    @Value("${ali.sms.accessKeyId}")
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    @Value("${ali.sms.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 短信模板签名
     */
    @Value("${ali.sms.signName}")
    private String signName;


    public <T> T send(SmsSendConfig<T> smsConfig) {
        if (env.equals("pro")) {
            // 生产环境
            try {
                // 设置超时时间-可自行调整
                System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                System.setProperty("sun.net.client.defaultReadTimeout", "10000");

                // 初始化ascClient,暂时不支持多region（请勿修改）
                IClientProfile profile = DefaultProfile.getProfile(REGION_ID, accessKeyId, accessKeySecret);

                DefaultProfile.addEndpoint(REGION_ID, REGION_ID, product, domain);

                // 设置请求参数
                IAcsClient acsClient = new DefaultAcsClient(profile);
                SendSmsRequest request = new SendSmsRequest();
                smsConfig.setSmsConfig(request);
                request.setMethod(MethodType.POST);
                request.setPhoneNumbers(smsConfig.getPhone());
                request.setSignName(signName);

                // 发送请求
                SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

                // 校验结果
                if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                    // 发送成功
                    return smsConfig.success(env);
                } else {
                    // 发送失败
                    LogUtil.error(MessageFormat.format("短信发送异常, 手机号码: {0}, code: {1}, message: {2}", smsConfig.getPhone(), sendSmsResponse.getCode(), sendSmsResponse.getMessage()));
                    return smsConfig.error();
                }
            } catch (ClientException e) {
                e.printStackTrace();
                LogUtil.error(MessageFormat.format("短信异常, 手机号码: {0}, code: {1}, message: {2}", smsConfig.getPhone(), e.getErrCode(), e.getMessage()));
                return smsConfig.error();
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.error(MessageFormat.format("短信异常, 手机号码: {0}", smsConfig.getPhone()));
                return smsConfig.error();
            }
        } else {
            // 其他环境
            return smsConfig.success(env);
        }
    }

}
