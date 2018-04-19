package com.hisun.saas.sys.admin.log.vo;

import java.util.Date;

/**
 * <p>Title: Log.java </p>
 * <p>Package com.hisun.cloud.sys.entity </p>
 * <p>Description: 日志记录表</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月20日 下午7:45:28 
 * @version 
 */
public class LogVo{

	private String id;
	
	private String userId;//操作人
	
	private String userName;
	
	private Date createTime;//操作时间
	
	private String ip;
	
	private short type;//操作类型 1新增 2修改 3删除 4登录 5登出 6异常
	
	private String content;//日志内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
