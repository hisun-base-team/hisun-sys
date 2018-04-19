package com.hisun.saas.sys.warn.pojo;

import com.hisun.saas.sys.warn.WarningSendEnum;

import java.util.List;

/**
 * <p>类名称:WarningSmsSendData</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/12/9上午10:18
 * @创建人联系方式:init@hn-hisun.com
 */
public class WarningSmsSendData  implements WarningSendDataInterface{

    protected List<SmsSendInfo> smsSendList;

    private WarningSendEnum sendType = WarningSendEnum.SMS;

    public WarningSmsSendData(List<SmsSendInfo> smsSendList){
        this.smsSendList = smsSendList;
    }

    @Override
    public void getSendInfo() {

    }
}
