package com.hisun.saas.sys.admin.communication.vo;

/**
 * <p>Title: Template.java </p>
 * <p>Package com.hisun.cloud.sys.pojo </p>
 * <p>Description: 模板操作的返回结果</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月29日 上午11:02:39 
 * @version 
 */
public class Template {

	// 模板id
	private String tpl_id;
	
	// 模板内容
	private String tpl_content;
	
	// 审核状态：CHECKING/SUCCESS/FAIL
	private String check_status;
	
	// 审核未通过的原因
	private String reason;

	public String getTpl_id() {
		return tpl_id;
	}

	public void setTpl_id(String tpl_id) {
		this.tpl_id = tpl_id;
	}

	public String getTpl_content() {
		return tpl_content;
	}

	public void setTpl_content(String tpl_content) {
		this.tpl_content = tpl_content;
	}

	public String getCheck_status() {
		return check_status;
	}

	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
