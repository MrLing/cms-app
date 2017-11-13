package cn.gson.crm.common;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : 上传文件类型,规定图片所属模块</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月12日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public enum AttachmentType {
    AVATAR("头像"), PUBLIC("公共目录");

    public String alias;

    AttachmentType(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return alias;
    }
}
