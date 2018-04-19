package com.hisun.saas.sys.tenant.message.vo;

import java.io.Serializable;

/**
 * <p>类名称：TenantOnlineMessageData</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:24
 * @创建人联系方式：lihm_gz@30wish.net
 */
public class TenantOnlineMessageData implements Serializable {

    private static final long serialVersionUID = 1077529864853922621L;

    private TenantOnlineMessageType type;//消息类型

    private String content;//消息内容

    private String title;//消息标题

    private String tenantId;

    private String userId;

    public TenantOnlineMessageType getType() {
        return type;
    }

    public void setType(TenantOnlineMessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
