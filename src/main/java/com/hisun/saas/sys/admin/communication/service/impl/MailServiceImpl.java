package com.hisun.saas.sys.admin.communication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.service.MailConfigService;
import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.admin.communication.entity.MailConfig;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService{
	
	@Resource
	private MailConfigService mailConfigService;

	
	@Resource
	private UserService userService;
	
	//@Resource
	//private MessageService messageService;
	
	@Value("${communication.mail.on}")
	private boolean mailOn;

	private  static final  String FROM_EMAIL = "30SCloud@30wish.net";
	private String mailServer;
	private String apiUser;
	private String apiKey;
	private String send;
	private String sendTemplate;
	private String addTemplate;
	private String deleteTemplate;
	private String updateTemplate;
	private String getTemplate;
	private String version;

	@Value("${communication.mail.isWebApi}")
	private boolean isWebApi;

	public void  init() {
        if (StringUtils.isBlank(apiKey)) {
            MailConfig config = this.mailConfigService.getMailConfig(Boolean.FALSE);
            if (StringUtils.isNotBlank(config.getMailServer())) {
				//isWebApi = Boolean.TRUE;
				mailServer = config.getMailServer();
                apiUser = config.getApiUser();
                apiKey = config.getApiKey();
                send = config.getSend();
                sendTemplate = config.getSendTemplate();
                addTemplate = config.getAddTemplate();
                deleteTemplate = config.getDeleteTemplate();
                updateTemplate = config.getUpdateTemplate();
                getTemplate = config.getGetTemplate();
                version = config.getVersion();
            }
        }
	}

	@Override
	public MailResult sendEmail(String permission, String title, String text, MailSendVo vo, boolean type)
			throws Exception {
        init();
		MailResult mailResult = new MailResult();

		if(mailOn){
			if(isWebApi){
				if(type){
					MailConfig config = this.mailConfigService.getMailConfig(Boolean.TRUE);
					if (StringUtils.isNotBlank(config.getMailServer())){
						mailServer = config.getMailServer();
						apiUser = config.getApiUser();
						apiKey = config.getApiKey();
						send = config.getSend();
						sendTemplate = config.getSendTemplate();
						addTemplate = config.getAddTemplate();
						deleteTemplate = config.getDeleteTemplate();
						updateTemplate = config.getUpdateTemplate();
						getTemplate = config.getGetTemplate();
						version = config.getVersion();
					}


				}
				StringBuilder sb = new StringBuilder();
				sb.append(mailServer).append("/").append(version).append("/")
						.append(sendTemplate);
				Map<String, String> params = Maps.newHashMap();
				params.put("api_user", apiUser);
				params.put("api_key", apiKey);
				params.put("from", FROM_EMAIL);
				params.put("template_invoke_name", permission);
				//params.put("to",emailTo);
				params.put("substitution_vars", JSONObject.toJSONString(vo));
				//params.put("html",text);
				params.put("subject", title);
				mailResult = HttpClientUtil.post(sb.toString(), params, MailResult.class);
				return mailResult;
			}else{
				MailConfig mailConfig = mailConfigService.getMailConfig(Boolean.FALSE);

				String host = mailConfig.getEmailServer();
				String from = mailConfig.getEmail();
				String username = mailConfig.getAccount();
				String password = mailConfig.getPassword();

				Properties props = System.getProperties();

				props.put("mail.smtp.host", host);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.setProperty("mail.smtp.timeout","5000");//超时时间

				JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
				senderImpl.setJavaMailProperties(props);
				senderImpl.setUsername(username);
				senderImpl.setPassword(password);
				senderImpl.setHost(host);
				//senderImpl.setPort(465);
				// 建立邮件消息
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				// 设置收件人，寄件人 用数组发送多个邮件
				mailMessage.setTo(vo.getTo().toArray(new String[vo.getTo().size()]));
				mailMessage.setFrom(from);
				mailMessage.setSubject(title);
				mailMessage.setText(text);

				// 发送邮件
				senderImpl.send(mailMessage);
				mailResult.setMessage("0");
			}
		}else{
			mailResult.setMessage("1");
			mailResult.setErrors(ImmutableList.of("邮件已禁用!"));
		}
		
		return mailResult;
	   
	}

	@Override
	public MailResult getAllTpl() {
        init();
        StringBuilder sb = new StringBuilder();
		sb.append(mailServer).append("/").append(version).append("/")
				.append(getTemplate);
		Map<String, String> params = Maps.newHashMap();
		params.put("api_user",apiUser);
		params.put("api_key",apiKey);
		MailResult result = HttpClientUtil.post(sb.toString(),params,MailResult.class);
		return result;
	}

	@Override
	public MailResult addTpl(String invokeName, String name, String html, String subject, int emailType) {
        init();
        StringBuilder sb = new StringBuilder();
		sb.append(mailServer).append("/").append(version).append("/")
				.append(addTemplate);
		Map<String, String> params = Maps.newHashMap();
		params.put("api_user",apiUser);
		params.put("api_key",apiKey);
		params.put("invoke_name",invokeName);
		params.put("name",name);
		params.put("html",html);
		params.put("subject",subject);
		params.put("email_type","0");
		MailResult result = HttpClientUtil.post(sb.toString(),params,MailResult.class);
		return result;
	}

	@Override
	public MailResult updateTpl(String invokeName, String name, String html, String subject) {
        init();
        StringBuilder sb = new StringBuilder();
		sb.append(mailServer).append("/").append(version).append("/")
				.append(updateTemplate);
		Map<String, String> params = Maps.newHashMap();
		params.put("api_user",apiUser);
		params.put("api_key",apiKey);
		params.put("invoke_name",invokeName);
		params.put("name",name);
		params.put("html",html);
		params.put("subject",subject);
		//params.put("email_type","0");
		MailResult result = HttpClientUtil.post(sb.toString(),params,MailResult.class);
		return result;
	}

	@Override
	public MailResult delTpl(String invokeName) {
        init();
        StringBuilder sb = new StringBuilder();
		sb.append(mailServer).append("/").append(version).append("/")
				.append(deleteTemplate);
		Map<String, String> params = Maps.newHashMap();
		params.put("api_user",apiUser);
		params.put("api_key",apiKey);
		params.put("invoke_name",invokeName);
		MailResult result = HttpClientUtil.post(sb.toString(),params,MailResult.class);
		return result;
	}

}