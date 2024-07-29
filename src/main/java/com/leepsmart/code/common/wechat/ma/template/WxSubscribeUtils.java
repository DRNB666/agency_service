package com.leepsmart.code.common.wechat.ma.template;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.alibaba.fastjson.JSON;

import com.leepsmart.code.common.utils.CacheConstant;
import com.leepsmart.code.common.utils.LogUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Component
public class WxSubscribeUtils {

    @Resource
    private WxMaService wxMaService;
    public static WxMaService staticWxMaService;

    @PostConstruct
    public void init() {
        staticWxMaService = wxMaService;
    }

    //正式版为 release(默认)，体验版为 trial，开发版为 develop
    public static String STATE;

    /**
     * 售后状态提醒
     */
    public static void afterSell(String openId, Long orderId, String goodsName, String afterSell, Date time, String remark) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
                .page("pages/order/refundRecord/afterSale/afterSale?id=" + orderId)
                .templateId(WxSubscribeConstants.AFTER_SELL)
                .miniprogramState(STATE)
                .build();

        // 组装参数
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string1");
        data.setValue(orderId.toString());
        list.add(data);

        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing6");
        data.setValue(sub(goodsName));
        list.add(data);

        //售后状态
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("phrase3");
        data.setValue(afterSell);
        list.add(data);

        data = new WxMaSubscribeMessage.MsgData();
        data.setName("date4");
        data.setValue(CacheConstant.FORMAT.format(time));
        list.add(data);

        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing5");
        data.setValue(sub(remark));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            error(e, WxSubscribeConstants.AFTER_SELL, openId);
        }
    }

//    public static void afterSell(List<MerchantAccountVO> merchantAccountVOList, Long orderId, String goodsName, String afterSell, Date time, String remark){
//        for (MerchantAccountVO merchantAccountVO : merchantAccountVOList) {
//            afterSell(merchantAccountVO.getOpenId(), orderId, goodsName, afterSell, time, remark);
//        }
//    }

    /**
     * 新订单提醒
     */
    public static WxMaSubscribeMessage orderInfo(String openId, String page, Long orderId, String goodsName, BigDecimal amount, String orderInfo, Date time) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
                .page(page)
                .templateId(WxSubscribeConstants.ORDER_INFO)
                .miniprogramState(STATE)
                .build();

        // 订单号
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string13");
        data.setValue(orderId.toString());
        list.add(data);
        //商品名称
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing14");
        data.setValue(sub(goodsName));
        list.add(data);
        //订单金额
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("amount15");
        data.setValue(amount.toString());
        list.add(data);
        //订单类型
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing2");
        data.setValue(orderInfo);
        list.add(data);
        //提交时间
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("time1");
        data.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(time));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        return wxMaSubscribeMessage;
    }
    public static void orderInfoSend(WxMaSubscribeMessage wxMaSubscribeMessage) {
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            error(e, wxMaSubscribeMessage.getTemplateId(), wxMaSubscribeMessage.getToUser());
        }
    }

    /**
     * 商家发货提醒
     */
    public static void expressInfo(String openId, String page, Long orderId, String goodsName, String express, String expressNum, String shopName) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
                .page(page)
                .templateId(WxSubscribeConstants.EXPRESS_INFO)
                .miniprogramState(STATE)
                .build();

        // 订单号
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string2");
        data.setValue(orderId.toString());
        list.add(data);
        //商品名称
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing1");
        data.setValue(sub(goodsName));
        list.add(data);
        //快递公司
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing4");
        data.setValue(sub(express));
        list.add(data);
        //快递单号
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string5");
        data.setValue(sub(expressNum));
        list.add(data);
        //发货门店
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing12");
        data.setValue(sub(shopName));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            LogUtil.error(JSON.toJSONString(list));
            error(e, WxSubscribeConstants.EXPRESS_INFO, openId);
        }
    }

    /**
     * 商家发货提醒
     */
    public static void cancelExpressInfo(String openId, String page, Long orderId, String goodsName, String express, String expressNum,
                                    String shopName) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
                .page(page)
                .templateId(WxSubscribeConstants.CANCEL_EXPRESS_INFO)
                .miniprogramState(STATE)
                .build();

        // 订单号
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string1");
        data.setValue(orderId.toString());
        list.add(data);
        //商品名称
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing2");
        data.setValue(sub(goodsName));
        list.add(data);
        //快递公司
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing3");
        data.setValue(sub(express));
        list.add(data);
        //快递单号
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("character_string4");
        data.setValue(sub(expressNum));
        list.add(data);
        //发货门店
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing5");
        data.setValue(sub(shopName));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            LogUtil.error(JSON.toJSONString(list));
            error(e, WxSubscribeConstants.CANCEL_EXPRESS_INFO, openId);
        }
    }

    /**
     * 竞价成功通知
     */
    public static void bidSuccess(String openId, String goodsName, String orderType, String remark, Date time) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
//                .page("page")
                .templateId(WxSubscribeConstants.BID_SUCCESS)
                .miniprogramState(STATE)
                .build();

        // 商品名
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing2");
        data.setValue(sub(goodsName));
        list.add(data);
        //订单类型
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing5");
        data.setValue(sub(orderType));
        list.add(data);
        //订单状态
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing4");
        data.setValue(sub(remark));
        list.add(data);
        //参与时间 xxxx年xx月xx日 xx:xx:xx
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("time3");
        data.setValue(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(time));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            error(e, WxSubscribeConstants.BID_SUCCESS, openId);
        }
    }

    /**
     * 竞价被超越通知
     */
    public static void bidPass(String openId, String goodsName, String amount, String remark, Date time) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
                .templateId(WxSubscribeConstants.BID_PASS)
                .miniprogramState(STATE)
                .build();

        // 商品名,拍品
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing1");
        data.setValue(sub(goodsName));
        list.add(data);
        //当前价
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("amount2");
        data.setValue(amount);
        list.add(data);
        //时间 xxxx年xx月xx日 xx:xx
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("time3");
        data.setValue(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(time));
        list.add(data);
        //订单状态
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing4");
        data.setValue(sub(remark));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            error(e, WxSubscribeConstants.BID_PASS, openId);
        }
    }

    /**
     * 竞价失败通知
     */
    public static void bidFail(String openId, String goodsName, Date time, String remark) {
        // 实例化订阅消息接口
        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
                .toUser(openId)
//                .page("page")
                .templateId(WxSubscribeConstants.BID_FAIL)
                .miniprogramState(STATE)
                .build();

        // 商品名
        List<WxMaSubscribeMessage.MsgData> list = new ArrayList<>();
        WxMaSubscribeMessage.MsgData data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing2");
        data.setValue(sub(goodsName));
        list.add(data);
        //参与时间 xxxx年xx月xx日 xx:xx:xx
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("time3");
        data.setValue(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(time));
        list.add(data);
        //订单状态
        data = new WxMaSubscribeMessage.MsgData();
        data.setName("thing4");
        data.setValue(sub(remark));
        list.add(data);
        wxMaSubscribeMessage.setData(list);
        try {
            staticWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
        } catch (WxErrorException e) {
            error(e, WxSubscribeConstants.BID_FAIL, openId);
        }
    }

    private static void error(WxErrorException e, String id, String openId) {
        if (e.getMessage().contains("43101")) {
            LogUtil.error("拒收微信模板消息，ID：{}，OPENID：{}", id, openId);
        } else {
            e.printStackTrace();
        }
    }

    //超过20个字会报错
    public static String sub(String thing) {
        return thing.length() > 20 ? thing.substring(0, 18) + ".." : thing;
    }
}
