package cn.gson.crm.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.crm.common</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月04日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class ImUser {

    private long uid;

    private String realName;

    public ImUser() {
    }

    public ImUser(long uid, String realName) {
        this.uid = uid;
        this.realName = realName;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ImUser user = (ImUser) o;

        return new EqualsBuilder()
                .append(uid, user.uid)
                .append(realName, user.realName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uid)
                .append(realName)
                .toHashCode();
    }
}
