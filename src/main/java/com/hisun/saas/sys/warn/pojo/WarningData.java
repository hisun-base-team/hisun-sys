package com.hisun.saas.sys.warn.pojo;

import java.util.Date;
import java.util.List;

import com.hisun.saas.sys.warn.WarnLevelEnum;

/**
 * 
 *<p>类名称：</p>
 *<p>类描述: 其他模块需要发送告警信息的数据类</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年5月7日
 *@创建人联系方式：
 *@version
 */
public class WarningData {
	
	private String serverId;
	
	private String ip;
	
	/**
	 * 告警发生所在的CI，如果没有可以不传
	 */
	private String ciId;
	
	/**
	 * 告警描述
	 */
	private String description;

	/**
	 * 租户ID，如果没有可以空。如果为空，程序默认会拿一下当前租户，如果还是没有，继续是空
	 */
	private String tenantId;

	/**
	 * 发生时间，可以由调用方自己设置，不设置告警保存时自动NEW一个时间
	 */
	private Date createTime;
	
	/**
	 * 告警级别
	 */
	private WarnLevelEnum level;

	private String category;

	private String subcategory;

	private String typeCode;

	private String sourceCode;

	private String inChargeUserId;

	private String inChargeUserName;

	private String instanceWarnId;

	private String hrefJson;

	/**
	 * 发送邮件
	 */
	private List<EmailSendInfo> emailSendList;

	/**
	 * 发送短信
	 */
	private List<SmsSendInfo> smsSendList;
	
	public WarningData(){}
	
	public WarningData(String serverId,String ip,String ciId,String description, WarnLevelEnum level,String tenantId){
		this.serverId = serverId;
		this.ip = ip;
		this.ciId = ciId;
		this.description =description;
		this.level = level;
		this.tenantId = tenantId;
	}
	
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public WarnLevelEnum getLevel() {
		return level;
	}

	public void setLevel(WarnLevelEnum level) {
		this.level = level;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCiId() {
		return ciId;
	}

	public void setCiId(String ciId) {
		this.ciId = ciId;
	}

	public List<EmailSendInfo> getEmailSendList() {
		return emailSendList;
	}

	public void setEmailSendList(List<EmailSendInfo> emailSendList) {
		this.emailSendList = emailSendList;
	}

	public List<SmsSendInfo> getSmsSendList() {
		return smsSendList;
	}

	public void setSmsSendList(List<SmsSendInfo> smsSendList) {
		this.smsSendList = smsSendList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getInChargeUserId() {
		return inChargeUserId;
	}

	public void setInChargeUserId(String inChargeUserId) {
		this.inChargeUserId = inChargeUserId;
	}

	public String getInChargeUserName() {
		return inChargeUserName;
	}

	public void setInChargeUserName(String inChargeUserName) {
		this.inChargeUserName = inChargeUserName;
	}

	public String getInstanceWarnId() {
		return instanceWarnId;
	}

	public void setInstanceWarnId(String instanceWarnId) {
		this.instanceWarnId = instanceWarnId;
	}

	public String getHrefJson() {
		return hrefJson;
	}

	public void setHrefJson(String hrefJson) {
		this.hrefJson = hrefJson;
	}
}
