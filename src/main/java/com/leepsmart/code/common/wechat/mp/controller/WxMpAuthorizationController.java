package com.leepsmart.code.common.wechat.mp.controller;

import com.leepsmart.code.common.utils.returnbody.ReturnBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Edward
 */
@Api(tags = "微信公众号-网页授权接口")
@RestController
@RequestMapping(value = "/wx/mp/authorization", produces = "text/plain;charset=utf-8")
public class WxMpAuthorizationController {

    @Resource
    private WxMpService wxMpService;
    @Resource
    private HttpServletRequest request;

    @ApiOperation("获取授权页面URL")
    @ApiImplicitParam(paramType = "query", dataTypeClass = String.class, name = "params", value = "自定义参数，回调时会原样返回")
    @PostMapping("/public/getAuthoUrl")
    public String getAuthoUrl(String params) {
        String url = request.getRequestURL().toString();
        url = url.substring(0, url.lastIndexOf("/"));
        String requestUrl;
        try {
            requestUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(url + "/authoNotify", "snsapi_userinfo", params != null ? URLEncoder.encode(params, "UTF-8") : null);
        } catch (IOException e) {
            e.printStackTrace();
            return ReturnBody.error();
        }
        return ReturnBody.success(requestUrl);
    }

    @ApiIgnore
    @ApiOperation("授权成功回调")
    @GetMapping("public/authoNotify")
    public void authoNotify(String code, String state) {
        try {
            if (!"authdeny".equals(state)) {
                WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
                WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
                // TODO 授权成功后的业务操作
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
