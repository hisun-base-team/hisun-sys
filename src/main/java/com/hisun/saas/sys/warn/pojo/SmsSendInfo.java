package com.hisun.saas.sys.warn.pojo;

/**
 * <p>类名称:SmsSendInfo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/12/9上午10:29
 * @创建人联系方式:init@hn-hisun.com
 */
public class SmsSendInfo extends AbstractSendInfo {

    /**
     * 发送内容
     */
    protected String content;

    public SmsSendInfo(){}

    public SmsSendInfo(String sendTo,String realName,String userId,String content){
        this.sendTo = sendTo;
        this.realname = realName;
        this.userId = userId;
        this.content = content;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
