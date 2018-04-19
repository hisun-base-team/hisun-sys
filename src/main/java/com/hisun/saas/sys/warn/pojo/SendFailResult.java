package com.hisun.saas.sys.warn.pojo;

import com.hisun.saas.sys.warn.WarningSendEnum;

/**
 * 
 *<p>类名称：</p>
 *<p>类描述: 即时发送后失败对象</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年5月8日
 *@创建人联系方式：
 *@version
 */
public class SendFailResult {

	private String sendTo;
	
	private WarningSendEnum sendType;
	
	public SendFailResult(){}
	
	public SendFailResult(String sendTo, WarningSendEnum sendType){
		this.sendTo = sendTo;
		this.sendType = sendType;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public WarningSendEnum getSendType() {
		return sendType;
	}

	public void setSendType(WarningSendEnum sendType) {
		this.sendType = sendType;
	}
}
