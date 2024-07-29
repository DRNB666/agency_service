package com.leepsmart.code.common.wechat.ma.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leepsmart.code.common.annotation.parameterverify.ParameterVerify;
import com.leepsmart.code.common.ex.ServiceException;
import com.leepsmart.code.common.utils.CommUtil;
import com.leepsmart.code.common.utils.LogUtil;
import com.leepsmart.code.common.utils.returnbody.ResultCodeInfo;
import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import com.leepsmart.code.common.wechat.ma.service.WxMaUserService;
import com.leepsmart.code.user.pojo.UserInfo;
import com.leepsmart.code.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api(tags = "微信小程序-用户接口")
@RestController
@RequestMapping("/wx/ma/user")
public class WxMaUserController {

    @Resource
    private WxMaService wxMaService;
    @Resource
    private WxMaUserService wxMaUserService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("授权登录")
    @PostMapping("public/login")
    public String login(String code, Integer parentId) {
        if (StringUtils.isBlank(code)) {
            return ReturnBody.error("参数错误");
        }
        WxMaJscode2SessionResult session = null;
        try {
            session = wxMaService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            throw new ServiceException("授权失败：" + e.getMessage());
        }
        Map<String, Object> map = wxMaUserService.login(session.getSessionKey(), session.getOpenid(), parentId);
        if (!CommUtil.checkNull(map)) {
            return ReturnBody.error("登录失败，请稍后再试");
        }
        return ReturnBody.success(map);
    }



    @ApiOperation("绑定手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "微信获取手机号api获得的code", required = false),
    })
    @PostMapping("/phone")
    public String phone(String code) throws WxErrorException {
        UserInfo info = (UserInfo) request.getAttribute("info");
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(code);
        wxMaUserService.savePhone(info, phoneNoInfo);
        return ReturnBody.success();
    }

    @ApiOperation("编辑用户信息")
    @PostMapping("editInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nick", value = "昵称姓名", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像", required = true),
    })
    @ParameterVerify(notNull = {"nick","avatar"})
    public String editInfo(String nick,String avatar){
        Long rId = (Long) request.getAttribute("id");
        UserInfo userInfo = userInfoService.getById(rId.longValue());
        userInfo.setNick(nick).setName(nick).setAvatar(avatar);
        if (!userInfoService.updateById(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }

    @ApiOperation("编辑真实姓名")
    @PostMapping("editName")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "name", value = "真实姓名", required = true),
     })
    @ParameterVerify(notNull = "name")
    public String editName(String name){
        Long rId = (Long) request.getAttribute("id");
        UserInfo userInfo = userInfoService.getById(rId);
        userInfo.setName(name);
        if (!userInfoService.updateById(userInfo)) {
            return ReturnBody.error(ResultCodeInfo.SERVICE_EXCEPTION);
        }
        return ReturnBody.success();
    }



}
