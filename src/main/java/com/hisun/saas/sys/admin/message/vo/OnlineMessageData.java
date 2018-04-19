package com.hisun.saas.sys.admin.message.vo;



/**
 * <p>Title: OnlineMessageData.java </p>
 * <p>Package com.hisun.saas.sys.pojo </p>
 * <p>Description: 封装接口数据</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月30日 下午3:11:52 
 * @version 
 */
public class OnlineMessageData {

	private OnlineMessageType type;//消息类型
	
	private String content;//消息内容
	
	private String title;//消息标题

	private String tenantId;
	
	private String userId;
	
	public OnlineMessageType getType() {
		return type;
	}

	public void setType(OnlineMessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
