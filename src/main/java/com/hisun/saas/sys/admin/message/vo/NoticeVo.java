package com.hisun.saas.sys.admin.message.vo;

import java.util.Date;

/**
 * <p>Title: NoticeVo.java </p>
 * <p>Package com.hisun.saas.sys.message.vo </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年9月25日 下午4:13:31 
 * @version 
 */
public class NoticeVo {

	private String id;
	
	private String noticeTitle;//通知标题
	
	private String noticeContent;//通知内容
	
	private short noticeLevel;//通知等级
	
	private short pushWay;//推送方式  0'短信',1'邮件',2'在线'

	private boolean status; //公告状态 true显示,false不显示
	
	private Date startDate =  new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public short getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(short noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	public short getPushWay() {
		return pushWay;
	}

	public void setPushWay(short pushWay) {
		this.pushWay = pushWay;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
