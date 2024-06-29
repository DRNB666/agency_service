package com.turing.code.common.wechat.mp.controller;

import com.turing.code.common.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * jsapi 演示接口的 controller.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2020-04-25
 */
@RestController
@RequestMapping("/wx/mp/jsapi")
@Api(tags = "微信公众号-获取Ticket接口")
public class WxJsapiController {

    @Resource
    private WxMpService wxMpService;

    /**
     * 获取ticket
     *
     * @return
     * @throws WxErrorException
     */
    @ApiOperation("获取ticket")
    @GetMapping("/public/getJsapiTicket")
    public String getJsapiTicket(String pageUrl) throws WxErrorException {
        final WxJsapiSignature jsapiSignature = this.wxMpService.createJsapiSignature(pageUrl);
        LogUtil.info(jsapiSignature.toString());
        return this.wxMpService.getJsapiTicket(true);
    }
}
