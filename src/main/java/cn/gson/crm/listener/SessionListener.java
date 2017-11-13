package cn.gson.crm.listener;

import cn.gson.crm.common.Constants;
import cn.gson.crm.handler.WebSocketHandler;
import cn.gson.crm.model.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.listener</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月04日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Autowired
    WebSocketHandler webSocketHandler;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Member member = (Member) session.getAttribute(Constants.SESSION_MEMBER_KEY);
        if (member != null) {
            webSocketHandler.offLine(member.getId());
        }
    }
}
