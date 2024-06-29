package com.turing.code.common.wechat.mp.template;

import com.turing.code.common.utils.CommUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class WxTemplateMsgUtils {

    public static WxMpService staticWxMpService;
    @Resource
    private WxMpService wxMpService;

    /**
     * 注册提醒（示例）
     *
     * @param openId   接收方openId
     * @param usId     注册用户id
     * @param nickName 注册用户昵称
     * @param type     1 本人提醒 2 上级提醒
     * @return true 发送成功 false 发送失败
     */
    public static boolean sendRegister(String openId, Integer usId, String nickName, Integer type) {
        boolean verifyParams = !CommUtil.checkNull(openId, usId, nickName, type) || (type != 1 && type != 2);
        if (verifyParams) {
            return false;
        }
        WxMpTemplateData data1 = new WxMpTemplateData("first", "恭喜您，注册成功", "#7f7f7f");
        WxMpTemplateData data2 = new WxMpTemplateData("keyword1", usId.toString(), "#177dd4");
        WxMpTemplateData data3 = new WxMpTemplateData("keyword2", nickName, "#177dd4");
        WxMpTemplateData data4 = new WxMpTemplateData("keyword3", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "#177dd4");
        WxMpTemplateData data5 = new WxMpTemplateData("remark", "进入网站查看更多", "#177dd4");
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder().templateId(WxTemplateMsgConstants.TEMP_REGISTER).toUser(openId).data(Arrays.asList(data1, data2, data3, data4, data5)).build();
        try {
            staticWxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @PostConstruct
    public void init() {
        staticWxMpService = wxMpService;
    }
}
