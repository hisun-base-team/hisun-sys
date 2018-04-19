package com.hisun.saas.sys.warn.pojo;
/**
 * 
 *<p>类名称：</p>
 *<p>类描述: 先已USERID判断，USERID为空在直接去发送账号</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年5月11日
 *@创建人联系方式：
 *@version
 */
public class AbstractSendInfo {

	/**
	 * 发给谁，根据类型填不同的东西，邮件就填邮箱，短信就填手机，如此类推
	 */
	protected String sendTo;

	/**
	 * 发送人真实名字，可不填，查询时候就为空
	 */
	protected String realname;

	/**
	 * TenantUser租户用户ID，如果这项存在就取查表进行发送，如果没有就用sendTo进行发送
	 */
	protected String userId;
	
	public AbstractSendInfo(){}
	
	public AbstractSendInfo(String sendTo, String realname, String userId){
		this.sendTo = sendTo;
		this.realname = realname;
		this.userId = userId;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
