package com.hisun.saas.sys.admin.communication.thread;

import java.util.ArrayList;
import java.util.List;

import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.util.ApplicationContextUtil;

import org.apache.log4j.Logger;

/**
 * 
 *<p>类名称：MeetingCreateSendThread</p>
 *<p>类描述: 创建会议后通知线程类</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年3月24日
 *@创建人联系方式：
 *@version
 */
public class SmsSendThread extends Thread{
	
	protected static Logger logger = Logger.getLogger(SmsSendThread.class);
	
	protected List<String> phoneList;
	
	protected String text;
	
	public SmsSendThread(List<String> phoneList, String text){
		this.phoneList = phoneList;
		this.text = text;
	}
	
	public SmsSendThread(String phone, String text){
		this.phoneList = new ArrayList<String>();
		phoneList.add(phone);
		this.text = text;
	}
	
	@Override
	public void run() {
		SMSService smsService = ApplicationContextUtil.getBean(SMSService.class);
		String[] phones = new String[phoneList.size()];
		try{
			smsService.sendSms(text, phoneList.toArray(phones));
		}catch(Exception e){
			logger.error("发送短信失败", e);
		}
	}
}
