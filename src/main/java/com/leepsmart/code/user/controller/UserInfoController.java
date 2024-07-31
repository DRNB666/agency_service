package com.leepsmart.code.user.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import com.leepsmart.code.common.alipay.service.AliPayService;
import com.leepsmart.code.common.annotation.limiter.RequestLimiter;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.liebao.service.LieBaoService;
import com.leepsmart.code.common.redis.utils.RedisUtil;
import com.leepsmart.code.common.utils.*;
import com.leepsmart.code.common.utils.encrypt.RSAUtil;

import com.leepsmart.code.common.utils.returnbody.Code;

import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;

import com.leepsmart.code.system.service.SysLieBaoAccountService;
import com.leepsmart.code.system.service.SysParamsService;

import com.leepsmart.code.user.pojo.*;
import com.leepsmart.code.user.service.*;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author leepsmart generator
 */
@Api(tags = "用户-用户信息")
@ApiResponses({
        @ApiResponse(code = 200, message = "请求成功"),
        @ApiResponse(code = 401, message = "无用户权限"),
        @ApiResponse(code = 403, message = "无资源权限"),
        @ApiResponse(code = 404, message = "找不到接口"),
})
@RestController
@RequestMapping(value = "user/userInfo", produces = "text/plain;charset=utf-8")
public class UserInfoController {

    @Resource
    private HttpServletRequest request;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private SysParamsService sysParamsService;
    @Resource
    private WxMaService wxMaService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private JavaMailSender emailSender;
    @Resource
    private LieBaoService lieBaoService;
    @Resource
    private SysLieBaoAccountService sysLieBaoAccountService;
    @Resource
    private AliPayService aliPayService;

    @Resource
    private RedisUtil redisUtil;
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${callBack}")
    private String callBack;

    @ApiOperation("校验手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "phone", value = "电话号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "code", value = "验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "verifySign", value = "验证签名", required = true)
    })
    @ParameterVerify(notNull = {"phone", "code", "verifySign"})
    @PostMapping("checkPhone")
    public String checkPhone(String phone, String code, String verifySign) {
        //验证码验证
        JSONObject verifyResult = SignDecodeUtil.codeMatches(phone + code, verifySign, 600000);
        if (!verifyResult.getBoolean("isSuccess")) {
            return ReturnBody.error(Code.SMS_CODE_ERROR, verifyResult.getString("errorMessage"));
        }
        return ReturnBody.success(new HashMap<String, Object>(10) {{
            put("checkType", true);
        }});
    }

    @ApiOperation("获取基本信息")
    @PostMapping("getUserInfo")
    public String getUserInfo() {
        JSONObject result = new JSONObject();
        UserInfo info = (UserInfo) request.getAttribute("info");
        return ReturnBody.success(result);
    }

    @ApiOperation("上传头像")
    @PostMapping("uploadAvatar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "file", required = true),
    })
    public String uploadAvatar(MultipartFile file) {
        String path = FileUtil.upload("/user/", file, "image", request);
        return ReturnBody.success(path);
    }


    @ApiOperation("注册、登录账号")
    @PostMapping("public/registerOrLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true),
    })
    @RequestLimiter(qps = 5D)
    @ParameterVerify(notNull = {"email","code"})
    public String registerOrLogin(String email,String code) throws Exception {
            email  = RSAUtil.decryptByPrivateKey(email);
            code  = RSAUtil.decryptByPrivateKey(code);
        Object o = redisUtil.get("USER_"+email);
        if(!CommUtil.checkNull(o)){
            return ReturnBody.error("请先发送邮箱验证码，如在收件箱未收到邮件请检查邮件垃圾箱");
        }
        if (!o.toString().equals(code)){
            return ReturnBody.error("验证码错误，请检查邮箱");
        }
        JSONObject result = userInfoService.registerOrLogin(email);
        return ReturnBody.success(result);
    }

    @ApiOperation("获取猎豹accessToken")
    @PostMapping("getLieBaoAc")
    public String getLieBaoAc(){
        String result = lieBaoService.lieBaoAccessToken();
        return ReturnBody.success(result);
    }

    @ApiOperation("注销")
    @PostMapping("logout")
    public String logout(){
        Long rId = (Long) request.getAttribute("id");
        UserInfo userInfo = userInfoService.getById(rId);
        if (!CommUtil.checkNull(userInfo)){
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        //下线
        userInfo.setLoginStatus(0);
        if (!userInfoService.updateById(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("猎豹充值(获取二维码)")
    @PostMapping("public/recharge")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "amount", value = "金额", required = true),
            @ApiImplicitParam(name = "accountId", value = "猎豹账户id", required = true),
    })
    @ParameterVerify(notNull = {"amount","accountId"})
    @RequestLimiter(qps = 1D)
    public String recharge(BigDecimal amount, Long accountId, HttpServletResponse response){
        //查询账户列表是否存在该账户
//        Long rId = (Long) request.getAttribute("id");
//        QueryWrapper<SysLieBaoAccount> eq =
//                new QueryWrapper<SysLieBaoAccount>().eq(SysLieBaoAccount.USER_ID,rId).eq(SysLieBaoAccount.ACCOUNT_ID,accountId);
//        SysLieBaoAccount sysLieBaoAccount = sysLieBaoAccountService.getOne(eq);
//        if (!CommUtil.checkNull(sysLieBaoAccount)){
//            return ReturnBody.error("账户不存在");
//        }
        //生成支付宝充值二维码
        try {
            String orderMobile = aliPayService.createOrderMobile();
//            AlipayClient alipayClient = new DefaultAlipayClient(payConfig.getGateway(),payConfig.getAppId(),payConfig.getPrivateKey(),format,charset,alipayPublicKey,signType)
//            Factory.setOptions(new Config());
//            AlipayTradePrecreateResponse payResponse = Factory.Payment.FaceToFace().preCreate("订单主题：Mac笔记本", "LS123qwe123", "19999");
//            String httpBodyStr = payResponse.getHttpBody();
//            JSONObject jsonObject = JSONObject.parseObject(httpBodyStr);
//            String qrUrl = jsonObject.getJSONObject("alipay_trade_precreate_response").get("qr_code").toString();
            QrCodeUtil.generate(orderMobile, 300, 300, "png", response.getOutputStream());
//            LogUtil.info("pay:{}",payConfig);
        } catch (Exception e) {
            LogUtil.error("生成支付宝二维码失败:{}",e);
            return ReturnBody.error("生成支付宝二维码失败");
        }
        return null;
    }
    







}
