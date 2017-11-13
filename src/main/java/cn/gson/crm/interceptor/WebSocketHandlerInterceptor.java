package cn.gson.crm.interceptor;

import cn.gson.crm.common.Constants;
import cn.gson.crm.common.ImUser;
import cn.gson.crm.model.domain.Member;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : 用于WebSocket记录用户信息</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月05日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class WebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            if (session != null) {
                Member member = (Member) session.getAttribute(Constants.SESSION_MEMBER_KEY);
                attributes.put(Constants.WEB_SOCKET_USERNAME, new ImUser(member.getId(), member.getRealName()));
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
