package com.leepsmart.code.common.websocket.util;

import com.leepsmart.code.common.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WsSendUtil {

    private static SimpMessagingTemplate staticMessageTemplate;
    /**
     * spring提供的发送消息模板
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public static void sendMessage(String destination, Object content) {
        LogUtil.info("destination: {}, content: {}", destination, content);
        //发送消息
        staticMessageTemplate.convertAndSend(destination, content);
    }

    public static void sendMessage(String user, String destination, Object content) {
        //发送消息
        staticMessageTemplate.convertAndSendToUser(user, destination, content);
    }

    @PostConstruct
    public void init() {
        staticMessageTemplate = messagingTemplate;
    }
}
