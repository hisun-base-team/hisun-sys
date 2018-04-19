package com.hisun.saas.sys.warn.pojo;

import java.util.Map;

/**
 * <p>类名称:EmailSendInfo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/12/9上午10:22
 * @创建人联系方式:init@hn-hisun.com
 */
public class EmailSendInfo extends AbstractSendInfo {

    /**
     * 邮件模板唯一标识，请参考邮件发送
     */
    protected String permission;

    /**
     * 模板中变量键值对
     */
    protected Map<String,String> paramMap;

    public EmailSendInfo(){}

    public EmailSendInfo(String sendTo,String realName,String userId,String permission,Map<String,String> paramMap){
        this.sendTo = sendTo;
        this.realname = realName;
        this.userId = userId;
        this.permission = permission;
        this.paramMap = paramMap;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }
}
