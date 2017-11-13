package cn.gson.crm.handler;

import cn.gson.crm.common.Constants;
import cn.gson.crm.common.ImUser;
import cn.gson.crm.common.SocketMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.handler</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月05日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final HashMap<Long, WebSocketSession> users;
    private static Logger logger = Logger.getLogger(WebSocketHandler.class);

    static {
        users = new HashMap<>();
    }

    public WebSocketHandler() {
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ImUser member = (ImUser) session.getAttributes().get(Constants.WEB_SOCKET_USERNAME);
        users.put(member.getUid(), session);
        System.out.println("connect to the websocket success......当前数量:" + users.size());
        //成功后推送给所有人你上线了
        sendMessageToUsers(new SocketMessage("online", member).toTextMessage());
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        ImUser member = (ImUser) session.getAttributes().get(Constants.WEB_SOCKET_USERNAME);
        System.out.println("用户" + member.getRealName() + "已退出！");
        users.remove(member.getUid());
        System.out.println("剩余在线用户" + users.size());

        //下线
        sendMessageToUsers(new SocketMessage("offline", member).toTextMessage());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("不能主动推送群发消息");
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }

        logger.debug("websocket connection closed......");

        ImUser member = (ImUser) session.getAttributes().get(Constants.WEB_SOCKET_USERNAME);
        users.remove(member.getUid());
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param id
     * @param message
     */
    public void sendMessageToUser(Long id, TextMessage message) {
        if (users.containsKey(id)) {
            WebSocketSession user = users.get(id);
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                logger.error("发送消息失败！", e);
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        users.entrySet().forEach(entry -> {
            try {
                if (entry.getValue().isOpen()) {
                    entry.getValue().sendMessage(message);
                }
            } catch (IOException e) {
                logger.error("群发消息失败！", e);
            }
        });
    }

    /**
     * 获取所有在线用户
     *
     * @return
     */
    public List<ImUser> getUsers() {
        List<ImUser> members = new ArrayList<>();

        users.entrySet().forEach(entry -> {
            if (entry.getValue().isOpen()) {
                ImUser member = (ImUser) entry.getValue().getAttributes().get(Constants.WEB_SOCKET_USERNAME);
                members.add(member);
            }
        });

        return members;
    }

    /**
     * 判断用户是否在线
     *
     * @param uid
     * @return
     */
    public boolean isOnline(Long uid) {
        return users.containsKey(uid);
    }

    /**
     * 下线用户
     *
     * @param uid
     */
    public void offLine(Long uid) {
        try {
            WebSocketSession session = users.remove(uid);
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            logger.info("用户下线失败！");
        }
    }
}
