package com.leepsmart.code.admin.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leepsmart.code.admin.pojo.AdInfo;
import com.leepsmart.code.admin.pojo.AdRole;
import com.leepsmart.code.admin.pojo.vo.AdInfoVO;
import com.leepsmart.code.admin.service.AdInfoService;
import com.leepsmart.code.admin.tools.enums.AdInfoStatus;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.annotation.repeat.PreventRepeat;
import com.leepsmart.code.common.config.KeyConfig;
import com.leepsmart.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.leepsmart.code.common.redis.utils.RedisUtil;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.SignDecodeUtil;
import com.leepsmart.code.common.utils.encrypt.RSAUtil;
import com.leepsmart.code.common.utils.page.pojo.PageInfo;
import com.leepsmart.code.common.utils.page.pojo.PageResult;
import com.leepsmart.code.common.utils.page.util.PageUtil;
import com.leepsmart.code.common.utils.returnbody.Code;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.user.pojo.UserInfo;
import com.leepsmart.code.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author leepsmart generator
 */
@Api(tags = "后台-账号信息")
@RestController
@RequestMapping("admin/adInfo")
public class AdInfoController {

    @Resource
    BCryptPasswordEncoder encoder;
    @Resource
    private HttpServletRequest request;
    @Resource
    private AdInfoService adInfoService;
    @Value("${spring.profiles.active}")
    private String env;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;


    @ApiOperation("登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "account", value = "账号", required = true, example = "iPQNRhwOwzP5iNvxWrpvkdV+FfJJpbguDGPTW4eqDtIePRbpLbYr85M54ZnatE3PiLLG5oJz8sGxX01JVk+V7PED8Q37djLGbdMk3fJ3dQmwhwoyuH5/gPcFg87Wh/dLTH2p/+uzq9XGGfJ11IPO2BM2zAhwrOhc7/QpFOWV/eA="),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "password", value = "密码", required = true, example = "ehaSzQoym9RWQD874xomzXf95N338kQTv9FJqqPa/+3WyK6irnDrP3VjP8AMM6h22OznUsj9LUC/X1e9MYwSG/Br1OGdTvOBX7vn3m4tSKHldUnfAYYbaVYnqrD2F2AvkOvD44D1e6T9nFh3fJ+IOPDufmA66SEpFFKGEhvUWcE="),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifyCode", value = "图片验证码", required = true, example = "1234"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifySign", value = "图片验证码密钥", required = true, example = "bOENDfoXAhyXyi8wZ/FsfQxWso6hhErukzPz+s3xQq4=")
    })
    @PostMapping("public/authority")
    @ParameterVerify(notNull = {"account", "password"})
    public String authority(String account, String password, String verifyCode, String verifySign) throws Exception {
        if ("dev".equals(env)) {
            try {
                account = RSAUtil.decryptByPrivateKey(account);
                password = RSAUtil.decryptByPrivateKey(password);
            } catch (Exception ignored) {
            }
        } else {
            account = RSAUtil.decryptByPrivateKey(account);
            password = RSAUtil.decryptByPrivateKey(password);
            JSONObject verifyResult = SignDecodeUtil.codeMatches(verifyCode, verifySign, 60000);
            if (!verifyResult.getBoolean("isSuccess")) {
                return ReturnBody.error(Code.IMAGE_CODE_ERROR, verifyResult.getString("errorMessage"));
            }
        }
        return adInfoService.authority(account, password);
    }


    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "oldPassword", value = "旧密码", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "newPassword", value = "新密码", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifyCode", value = "图片验证码", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "verifySign", value = "图片验证码密钥", required = true)
    })
    @PostMapping("updatePassword")
    @ParameterVerify(notNull = {"oldPassword", "newPassword", "verifyCode", "verifySign"})
    public String updatePassword(String oldPassword, String newPassword, String verifyCode, String verifySign) throws Exception {
        oldPassword = RSAUtil.decryptByPrivateKey(oldPassword);
        newPassword = RSAUtil.decryptByPrivateKey(newPassword);
        //验证码验证
        JSONObject verifyResult = SignDecodeUtil.codeMatches(verifyCode, verifySign, 60000);
        if (!verifyResult.getBoolean("isSuccess")) {
            return ReturnBody.error(Code.IMAGE_CODE_ERROR, verifyResult.getString("errorMessage"));
        }
        //验证旧密码
        Integer id = (Integer) request.getAttribute(AdInfo.ID);
        AdInfo adInfo = adInfoService.getById(id);
        if (adInfo == null || !encoder.matches(oldPassword + KeyConfig.KEY_PWD, adInfo.getPassword())) {
            return ReturnBody.error("原密码错误");
        }
        adInfo.setPassword(encoder.encode(newPassword + KeyConfig.KEY_PWD));
        //修改为新密码
        if (!adInfoService.updateById(adInfo)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }


    @ApiOperation("账号列表")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "账号状态")
    @PostMapping("list")
    public String list(PageInfo pageInfo, Integer status) {
        // 执行分页辅助工具
        PageResult<AdInfoVO> pageResult = new PageUtil<AdInfoVO>(pageInfo).startPage((page, queryWrapper) -> {
            queryWrapper
                    .select(AdInfo.ID, AdInfo.ACCOUNT, AdInfo.ROLE_ID, AdInfo.REMARK, AdInfo.STATUS, AdInfo.LOGIN_TIME, AdInfo.CREATE_TIME, AdRole.ROLE_NAME)
                    .eq(AdInfo.STATUS, status);
            adInfoService.selectAndRole(page, queryWrapper);
        });
        return ReturnBody.success(pageResult);
    }

    @ApiOperation("邮箱登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true),
    })
    @PostMapping("public/emailAuthority")
    @ParameterVerify(notNull = {"email", "code"})
    public String authority(String email, String code) throws Exception {
        email = RSAUtil.decryptByPrivateKey(email);
        code = RSAUtil.decryptByPrivateKey(code);
        Object o = redisUtil.get("ADMIN_"+email);
        if(!CommUtil.checkNull(o)){
            return ReturnBody.error("请先发送邮箱验证码，如在收件箱未收到邮件请检查邮件垃圾箱");
        }
        if (!o.toString().equals(code)){
            return ReturnBody.error("验证码错误，请检查邮箱");
        }
        QueryWrapper<AdInfo> eq = new QueryWrapper<AdInfo>().eq(AdInfo.EMAIL, email);
        AdInfo adInfo = adInfoService.getOne(eq);
        return adInfoService.authority(adInfo.getAccount(), "123123");
    }








}
