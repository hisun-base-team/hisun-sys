package com.hisun.saas.sys.admin.communication.service;

import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.saas.sys.admin.communication.vo.MailResult;

/**
 * <p>Title: MailService.java </p>
 * <p>Package com.hisun.cloud.sys.service </p>
 * <p>Description: 邮件发送服务</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月5日 下午3:21:35 
 * @version 
 */
public interface MailService {

	public MailResult sendEmail(String permission, String title, String text, MailSendVo vo, boolean type) throws Exception;
	
	//public MessageResult sendEmailForgetPass(String title,String text,String ...emailTo) throws UnsupportedEncodingException;


	/**
	 * 获取所有模板
	 * @return
	 */
	public MailResult getAllTpl();

	/**
	 api_user		子账号
	 api_key		密码
	 invoke_name	邮件模板调用名称
	 name	邮件模板名称
	 html	html格式内容
	 subject 模板标题
	 email_type 模板类型
	 */
	public MailResult addTpl(String invokeName,String name,String html,String subject,int emailType);

	/**
	 * 修改模板
	 * @return
	 */
	public MailResult updateTpl(String invokeName,String name,String html,String subject);

	/**
	 * 删除模板
	 * @param invokeName 模板名称
	 * @return
	 */
	public MailResult delTpl(String invokeName);


}