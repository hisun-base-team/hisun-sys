package com.hisun.saas.sys.admin.communication.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>Title: SMSTemplate.java </p>
 * <p>Package com.hisun.saas.sys.entity </p>
 * <p>Description: 短信模板实体</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月5日 上午10:16:03 
 * @version 
 */
@Entity
@Table(name = "SYS_SMS_TEMPLATE")
public class SMSTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", nullable = false, unique = true,length=32)
	private String id;//逻辑主键

	@Column(name="tpl_content",nullable=false)
	private String tplContent;//模板内容
	
	@Column(name = "tpl_id", nullable = false, length = 50)
	private String tplId;// 远程模板Id

	@Column(name = "check_status", nullable = false, length = 10)
	private String checkStatus;// 审核状态

	@Column(name = "reason", length = 50)
	private String reason;// 未通过原因

	@Column(name = "permission", nullable = false, length = 50, unique = true)
	private String permission;// 模板唯一标示，给人看

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTplContent() {
		return tplContent;
	}

	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
