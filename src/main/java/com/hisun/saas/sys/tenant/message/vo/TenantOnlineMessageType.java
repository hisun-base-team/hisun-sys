package com.hisun.saas.sys.tenant.message.vo;

/**
 * <p>类名称：TenantOnlineMessageType</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:25
 * @创建人联系方式：lihm_gz@30wish.net
 */
public enum TenantOnlineMessageType {

    /**
     * 普通
     */
    INFO(Short.valueOf("3"),"普通"),
    /**
     * 警告
     */
    WARNING(Short.valueOf("2"),"警告"),
    /**
     * 严重
     */
    BOLT(Short.valueOf("1"),"严重");


    private short value;

    private String description;

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private TenantOnlineMessageType(short value, String description) {
        this.value = value;
        this.description = description;
    }

}
