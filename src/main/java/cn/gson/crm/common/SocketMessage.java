package cn.gson.crm.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.common</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月05日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class SocketMessage {

    private String tag;
    private Object data;

    public SocketMessage(String tag, Object data) {
        this.tag = tag;
        this.data = data;
    }

    /**
     * 转换成TextMessage进行发送
     *
     * @return
     */
    public TextMessage toTextMessage() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new TextMessage(mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
