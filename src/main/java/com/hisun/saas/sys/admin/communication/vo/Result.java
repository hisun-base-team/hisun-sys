package com.hisun.saas.sys.admin.communication.vo;

/**
 * <p>Title: Result.java </p>
 * <p>Package com.hisun.saas.sys.pojo </p>
 * <p>Description: 短信接口result</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月29日 上午10:57:36 
 * @version 
 */
public class Result {
	
	// 成功发送的短信个数
	private String count;
	
	// 扣费条数，70个字一条，超出70个字时按每67字一条计
	private String fee;
	
	// 短信id；群发时以该id+手机号尾号后8位作为短信id
	private String sid;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
