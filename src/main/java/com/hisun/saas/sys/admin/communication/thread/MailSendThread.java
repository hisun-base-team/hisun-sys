package com.hisun.saas.sys.admin.communication.thread;

import com.hisun.saas.sys.admin.communication.service.EmailTemplateService;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailSendMutipltVo;
import com.hisun.saas.sys.admin.communication.vo.MailSendSingleVo;
import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.util.ApplicationContextUtil;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Properties;

/**
 * 
 *<p>类名称：</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年4月1日
 *@创建人联系方式：
 *@version
 */
public class MailSendThread extends Thread {
	
	private static Logger logger = Logger.getLogger(MailSendThread.class);

	protected String msg;
	
	protected String emailTitle;

	protected String permission;

	protected MailSendVo emailSendvo;

	public MailSendThread(MailSendSingleVo vo, String permission, String emailTitle, String msg){
		init(vo, permission, vo.getParamMap(), emailTitle);
	}
	
	public MailSendThread(MailSendMutipltVo vo, String permission, String emailTitle, String msg){
		init(vo, permission, vo.getParamMap(), emailTitle);
	}

	public void init(MailSendVo vo,String permission,Map<String,String> paramMap,String emailTitle ){
		this.emailTitle = emailTitle;
		this.permission = permission;
		this.emailSendvo = vo;
		String isWebApi = ApplicationContextUtil.getBean("resourcesProperties", Properties.class).getProperty("communication.mail.isWebApi");
		if(!Boolean.valueOf(isWebApi)){
			this.msg = ApplicationContextUtil.getBean(EmailTemplateService.class).getTplMessage(permission, paramMap);
		}
	}

	/**
	 * 获取完整的文本
	 * @param permission
	 * @param paramMap
	 * @return
	 */
	protected String createText(String permission, Map<String,String> paramMap){
		String text = ApplicationContextUtil.getBean(EmailTemplateService.class).getTplMessage(permission,paramMap);
		return text;
	}
	
	@Override
	public void run() {
		MailService mailService = ApplicationContextUtil.getBean(MailService.class);
		try{
			mailService.sendEmail(permission,emailTitle, msg,emailSendvo,false);
		}catch(Exception e){
			logger.error("发送邮件失败", e);
		}
		
	}

}
