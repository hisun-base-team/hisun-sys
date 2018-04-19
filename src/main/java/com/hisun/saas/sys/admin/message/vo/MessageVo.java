package com.hisun.saas.sys.admin.message.vo;


/**
 * <p>Title: MessageVo.java </p>
 * <p>Package com.hisun.saas.sys.vo </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月29日 下午8:40:13 
 * @version 
 */
public class MessageVo {

	private String id;//主键
	
	private boolean noticeEmail ;//是否开启邮件通知
	
	private boolean noticeSmart ;//是否开启免打扰模式
	
	private boolean noticeSMS;//是否开启短信通知
	
	private boolean noticeExpress ;//是否开启每月速递
	
	//private boolean noticeDailyMail ;//是否开启每日邮件提醒
	
	//private boolean noticeWeeklyMail ;//是否开启每周邮件提醒

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isNoticeEmail() {
		return noticeEmail;
	}

	public void setNoticeEmail(boolean noticeEmail) {
		this.noticeEmail = noticeEmail;
	}

	public boolean isNoticeSmart() {
		return noticeSmart;
	}

	public void setNoticeSmart(boolean noticeSmart) {
		this.noticeSmart = noticeSmart;
	}

	public boolean isNoticeExpress() {
		return noticeExpress;
	}

	public void setNoticeExpress(boolean noticeExpress) {
		this.noticeExpress = noticeExpress;
	}

	public boolean isNoticeSMS() {
		return noticeSMS;
	}

	public void setNoticeSMS(boolean noticeSMS) {
		this.noticeSMS = noticeSMS;
	}

}
