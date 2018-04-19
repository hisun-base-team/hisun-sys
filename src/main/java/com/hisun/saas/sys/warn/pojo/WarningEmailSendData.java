package com.hisun.saas.sys.warn.pojo;

import com.hisun.saas.sys.warn.WarningSendEnum;

import java.util.List;

/**
 * <p>类名称:WarningEmailSendData</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/12/9上午10:17
 * @创建人联系方式:init@hn-hisun.com
 */
public class WarningEmailSendData implements WarningSendDataInterface{

    protected List<EmailSendInfo> emailSendList;

    private WarningSendEnum sendType = WarningSendEnum.EMAIL;

    public WarningEmailSendData(List<EmailSendInfo> emailSendList){
        this.emailSendList = emailSendList;
    }

    @Override
    public void getSendInfo() {

    }
}
