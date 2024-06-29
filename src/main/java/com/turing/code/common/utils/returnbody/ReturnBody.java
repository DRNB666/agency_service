package com.turing.code.common.utils.returnbody;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.utils.LogUtil;

/**
 * 返回数据实体
 *
 * @author Turing
 */
public class ReturnBody {

    public static String message(Message message, ResultCodeInfo codeInfo) {
        ResultInfo resultInfo = new ResultInfo(message.getValue(), codeInfo.getCode(), codeInfo.getResult());
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(resultInfo));
        return jsonObject.toString();
    }

    public static String message(Message message, Code code, Object result) {
        ResultInfo resultInfo = new ResultInfo(message.getValue(), code.getValue(), result);
//        System.out.println(JSONObject.toJSONString(result));
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(resultInfo));
        LogUtil.info(jsonObject.toString());
        return jsonObject.toString();
    }

    public static String message(Message message, Integer code, Object result) {
        ResultInfo resultInfo = new ResultInfo(message.getValue(), code, result);
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(resultInfo));
        return jsonObject.toString();
    }

    public static String success() {
        return message(Message.SUCCESS, ResultCodeInfo.SUCCESS);
    }

    public static String success(Object result) {
        return message(Message.SUCCESS, Code.SUCCESS, result);
    }

    public static String success(Code code, Object result) {
        return message(Message.SUCCESS, code, result);
    }

    public static String error() {
        return message(Message.ERROR, ResultCodeInfo.ERROR);
    }

    public static String error(ResultCodeInfo resultCodeInfo) {
        return message(Message.ERROR, resultCodeInfo);
    }

    public static String error(Object result) {
        return message(Message.ERROR, Code.ERROR, result);
    }

    public static String error(Code code, Object result) {
        return message(Message.ERROR, code, result);
    }

    public static String error(Integer code, Object result) {
        return message(Message.ERROR, code, result);
    }

}
