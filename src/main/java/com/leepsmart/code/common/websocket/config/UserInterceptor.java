package com.leepsmart.code.common.websocket.config;

import com.leepsmart.code.common.utils.LogUtil;
import com.leepsmart.code.common.websocket.pojo.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.Map;

public class UserInterceptor implements ChannelInterceptor {

    /**
     * 获取包含在stomp中的用户信息
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SEND.equals(accessor.getCommand()))) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("name");
                if (name instanceof LinkedList) {
                    // 设置当前访问器的认证用户
                    accessor.setUser(new User(((LinkedList) name).get(0).toString()));
                }
            }
        }
        return message;
    }

    /**
     * 监听连接状态，处理自定义头部信息
     *
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // 获取 sessionId
        Object sessionId = message.getHeaders().get(SimpMessageHeaderAccessor.SESSION_ID_HEADER);
        // 获取 ws 连接头信息
        Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
        if (sha.getCommand() == null) {
            return;
        }
        Object us;
        Object roleId;
        if (raw instanceof Map) {
            // 获取自定义头部信息
            Map map = ((Map) raw);
            us = map.get("id");
            roleId = map.get("roleId");
            if (us instanceof LinkedList) {
                us = ((LinkedList) us).get(0);
                if (null != roleId) {
                    roleId = ((LinkedList) roleId).get(0);
                }
                //判断客户端的连接状态0
                switch (sha.getCommand()) {
                    // 连接成功
                    case CONNECT:
                        if (sessionId != null) {
                            connect(us, sessionId.toString());
                        }
                        break;
                    case CONNECTED:
                        break;
                    // 断开连接
                    case DISCONNECT:
                        if (sessionId != null) {
                            disconnect(sessionId.toString());
                        }
                        break;
                    case SUBSCRIBE:
                        subscribe(us, map.get("destination"));
                    default:
                        break;
                }

            }
        }
    }

    /**
     * 连接成功
     */
    private void connect(Object us, Object sessionId) {
        LogUtil.info("连接成功");
    }

    /**
     * 断开连接
     */
    private void disconnect(String sessionId) {
        LogUtil.info("断开连接");
    }

    private void subscribe(Object id, Object roleId) {
        LogUtil.info("监听");
        LogUtil.info(id + ", " + roleId);
    }
}
