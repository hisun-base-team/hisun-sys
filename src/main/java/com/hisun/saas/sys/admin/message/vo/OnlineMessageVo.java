package com.hisun.saas.sys.admin.message.vo;


/**
 * <p>Title: OnlineMessageVo.java </p>
 * <p>Package com.hisun.saas.sys.vo </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年6月1日 下午4:26:58 
 * @version 
 */
public class OnlineMessageVo {

	private String id;//主键
	
	private short type;//消息类型
	
	private short status ;//消息状态  默认0未读 ,1待处理,2已读
	
	private String content;//消息内容
	
	private String title;//消息标题
	
	private String createUserName;
	
	private String createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
