package cn.gson.crm.controller.websocket;

import cn.gson.crm.common.AjaxResult;
import cn.gson.crm.common.SocketMessage;
import cn.gson.crm.handler.WebSocketHandler;
import cn.gson.crm.model.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : IM控制器</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月04日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Controller
public class ImController {

    @Autowired
    WebSocketHandler webSocketHandler;

    /**
     * 发送信息给指定用户
     *
     * @param uid
     * @param content
     * @param member
     */
    @RequestMapping("/im/send")
    @ResponseBody
    public void send(Long uid, String content, @SessionAttribute("s_member") Member member) {
        Map<String, Object> message = new HashMap<>();
        message.put("fromUid", member.getId());
        message.put("realName", member.getRealName());
        message.put("message", content);
        webSocketHandler.sendMessageToUser(uid, new SocketMessage("message", message).toTextMessage());
    }

    /**
     * 获取在线用户列表
     *
     * @return
     */
    @RequestMapping("/im/user/list")
    @ResponseBody
    public AjaxResult userList() {
        return new AjaxResult().setData(webSocketHandler.getUsers());
    }
}
