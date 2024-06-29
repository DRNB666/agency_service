package com.turing.code.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.turing.code.admin.pojo.AdInfo;
import com.turing.code.admin.pojo.AdRole;
import com.turing.code.admin.pojo.vo.AdInfoVO;
import com.turing.code.admin.service.AdInfoService;
import com.turing.code.admin.tools.enums.AdInfoStatus;
import com.turing.code.common.annotation.parameterverify.ParameterVerify;
import com.turing.code.common.annotation.repeat.PreventRepeat;
import com.turing.code.common.config.KeyConfig;
import com.turing.code.common.mybatisplus.structure.CustomQueryWrapper;
import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.SignDecodeUtil;
import com.turing.code.common.utils.encrypt.RSAUtil;
import com.turing.code.common.utils.page.pojo.PageInfo;
import com.turing.code.common.utils.page.pojo.PageResult;
import com.turing.code.common.utils.page.util.PageUtil;
import com.turing.code.common.utils.returnbody.Code;
import com.turing.code.common.utils.returnbody.ResultCodeInfo;
import com.turing.code.common.utils.returnbody.ReturnBody;
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
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

/**
 * @author turing generator
 * @since 2021-02-03
 */
@Api(tags = "管理员-账号信息")
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


    @ApiOperation("添加账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "account", value = "账号", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleId", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注")
    })
    @PostMapping("add")
    @ParameterVerify(notNull = {"account", "password", "roleId"})
    @PreventRepeat
    public String add(String account, String password, Integer roleId, String remark) throws Exception {
        // rsa 解密
        password = RSAUtil.decryptByPrivateKey(password);
        if (!CommUtil.checkNull(password)) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        QueryWrapper<AdInfo> queryWrapper = new CustomQueryWrapper<AdInfo>().eq(AdInfo.ACCOUNT, account);
        AdInfo adInfo = adInfoService.getOne(queryWrapper);

        if (null != adInfo) {
            return ReturnBody.error("该账号已存在");
        }

        adInfo = new AdInfo()
                .setAccount(account)
                .setPassword(encoder.encode(password + KeyConfig.KEY_PWD))
                .setRoleId(roleId)
                .setRemark(remark);

        return adInfoService.add(adInfo);
    }


    @ApiOperation("删除账号")
    @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "账号ID", required = true)
    @PostMapping("delete")
    @ParameterVerify(notNull = "id")
    public String delete(Integer id) {
        return adInfoService.delete(id);
    }


    @ApiOperation("修改账号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "账号ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "account", value = "账号", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "password", value = "密码"),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "roleId", value = "角色ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "remark", value = "备注")
    })
    @PostMapping("update")
    @ParameterVerify(notNull = {"id", "account", "roleId"})
    public String update(Integer id, String account, String password, Integer roleId, String remark) throws Exception {
        // 是否修改密码
        if (CommUtil.checkNull(password)) {
            // rsa 解密
            password = RSAUtil.decryptByPrivateKey(password);
            if (!CommUtil.checkNull(password)) {
                return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
            }
            if (password.length() > 20 || password.length() < 6) {
                return ReturnBody.error("密码长度必须是6-20位");
            }
        }
        AdInfo adInfo = new AdInfo()
                .setId(id)
                .setAccount(account)
                .setRoleId(roleId)
                .setRemark(remark);

        if (CommUtil.checkNull(password)) {
            adInfo.setPassword(encoder.encode(password + KeyConfig.KEY_PWD));
        }

        return adInfoService.updateInfo(adInfo);
    }


    @ApiOperation("冻结或解冻操作")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "id", value = "账号ID", required = true),
            @ApiImplicitParam(paramType = "query", dataTypeClass = Integer.class, name = "status", value = "账号状态", required = true)
    })
    @PostMapping("freezeOrThaw")
    @ParameterVerify(notNull = {"id", "status"})
    public String freezeOrThaw(Integer id, Integer status) {
        if (!status.equals(AdInfoStatus.FREEZE.getValue()) && !status.equals(AdInfoStatus.NORMAL.getValue())) {
            return ReturnBody.error(ResultCodeInfo.PARAM_ERROR);
        }
        AdInfo adInfo = new AdInfo()
                .setId(id)
                .setStatus(status);
        if (!adInfoService.updateById(adInfo)) {
            return ReturnBody.error();
        }
        return ReturnBody.success();
    }

}
