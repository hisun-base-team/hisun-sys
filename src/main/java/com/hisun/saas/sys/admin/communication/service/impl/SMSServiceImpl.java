package com.hisun.saas.sys.admin.communication.service.impl;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.entity.SMSConfig;
import com.hisun.saas.sys.admin.communication.service.SMSConfigService;
import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.saas.sys.admin.communication.vo.MessageResult;
import com.hisun.saas.sys.admin.communication.vo.MessageResults;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SMSServiceImpl.java </p>
 * <p>Package com.hisun.saas.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月29日 上午11:33:05 
 * @version 
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService {

	private static final Logger logger = Logger.getLogger(SMSServiceImpl.class);

	private String baseUrl;
	private String apikey;
	private String version;
	private String sendUri;
	private String tplSendUri;
	private String getTpl;
	private String addTpl;
	private String updateTpl;
	private String delTpl;


	@Resource
	private SMSConfigService smsConfigService;

	//@PostConstruct
	public void  init(){
        if(StringUtils.isBlank(baseUrl)) {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and("status =:status", "status", Boolean.TRUE));
            List<SMSConfig> configs = smsConfigService.list(query, null);
            if (configs.size() > 0) {
                SMSConfig config = configs.get(0);
                baseUrl = config.getSmsServer();
                apikey = config.getApikey();
                version = config.getVersion();
                sendUri = config.getSend();
                tplSendUri = config.getTplSend();
                getTpl = config.getGetTpl();
                addTpl = config.getAddTpl();
                updateTpl = config.getUpdateTpl();
                delTpl = config.getDelTpl();
            }
        }
	}
	
	@Value("${communication.sms.on}")
	private boolean smsOn;
	
	@Override
	public MessageResult sendSms(String text, String... mobile)  throws GenericException {
        init();
		if(smsOn){
			StringBuilder sb = new StringBuilder();
			sb.append(baseUrl).append("/").append(version).append("/")
			.append(sendUri);
			Map<String, String> params = Maps.newHashMap();
			params.put("apikey", apikey);
			params.put("text", text);
			params.put("mobile", mobile[0]);
			MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
			if(result==null){
				logger.error("网络异常,无法请求云片网！");
				//return null;
				throw new GenericException("网络异常,无法请求云片网！");
			}else{
				if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
					logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
				}
				return result;
			}
		}else{
			return null;
		}
	}

	// 设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
	// long tpl_id = 1;
	// 设置对应的模板变量值
	// 如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
	// 如code值是#1234#,需作如下编码转换
	// String codeValue = URLEncoder.encode("#1234#", ENCODING);
	// String tpl_value = "#code#=" + codeValue + "&#company#=云片网";
	@Override
	@Deprecated
	public MessageResult tplSendSms(long tplId, String tplValue, String mobile)  throws GenericException {
        init();
		if(smsOn){
			StringBuilder sb = new StringBuilder();
			sb.append(baseUrl).append("/").append(version).append("/")
					.append(tplSendUri);
			Map<String, String> params = Maps.newHashMap();
			params.put("apikey", apikey);
	        params.put("tpl_id", String.valueOf(tplId));
	        params.put("tpl_value", tplValue);
	        params.put("mobile", mobile);
			MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
			if(result==null){
		    	logger.error("网络异常,无法请求云片网！");
				throw new GenericException("网络异常,无法请求云片网！");
			}else{
		    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
		    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
		    	}
		    	return result;
		    }
		}else{
			return null;
		}
	}

	@Override
	public MessageResult getTpl(long tplId)  throws GenericException {
        init();
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("/").append(version).append("/")
				.append(getTpl);
		Map<String, String> params = Maps.newHashMap();
		params.put("tpl_id", tplId+"");
		params.put("apikey", apikey);
		MessageResult result = HttpClientUtil.get(sb.toString(), params,MessageResult.class);
		if(result==null){
	    	logger.error("网络异常,无法请求云片网！");
			throw new GenericException("网络异常,无法请求云片网！");
		}else{
	    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
	    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	    	}
	    	return result;
	    }
	}

	@Override
	public MessageResult addTpl(String tplContent, int notifyType)  throws GenericException {
        init();
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("/").append(version).append("/")
				.append(addTpl);
		Map<String, String> params = Maps.newHashMap();
		//模板内容，必须以带符号【】的签名结尾
		params.put("tpl_content", tplContent);
		params.put("apikey", apikey);
		params.put("notify_type", notifyType+"");
		
		MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
		if(result==null){
	    	logger.error("网络异常,无法请求云片网！");
			throw new GenericException("网络异常,无法请求云片网！");
		}else{
	    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
	    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	    	}
	    	return result;
	    }
	}

	@Override
	public MessageResult updateTpl(long tplId, String tplContent)  throws GenericException {
        init();
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("/").append(version).append("/")
				.append(updateTpl);
		Map<String, String> params = Maps.newHashMap();
		//模板内容，必须以带符号【】的签名结尾
		params.put("tpl_content", tplContent);
		params.put("apikey", apikey);
		params.put("tpl_id",  tplId+"");
		
		MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
		if(result==null){
	    	logger.error("网络异常,无法请求云片网！");
			throw new GenericException("网络异常,无法请求云片网！");
	    }else{
	    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
	    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	    	}
	    	return result;
	    }
	}

	@Override
	public MessageResult delTpl(long tplId)  throws GenericException {
        init();
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("/").append(version).append("/")
				.append(delTpl);
		Map<String, String> params = Maps.newHashMap();
		//模板内容，必须以带符号【】的签名结尾
		params.put("apikey", apikey);
		params.put("tpl_id",  tplId+"");
		
		MessageResult result = HttpClientUtil.post(sb.toString(), params,MessageResult.class);
		if(result==null){
	    	logger.error("网络异常,无法请求云片网！");
			throw new GenericException("网络异常,无法请求云片网！");
	    }else{
	    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
	    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	    	}
	    	return result;
	    }
	}

	@Override
	public MessageResults getAllTpl()  throws GenericException{
        init();
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("/").append(version).append("/")
				.append(getTpl);
		Map<String, String> params = Maps.newHashMap();
		params.put("tpl_id", "");
		params.put("apikey", apikey);
		MessageResults result = HttpClientUtil.get(sb.toString(), params,MessageResults.class);
		if(result==null){
	    	logger.error("网络异常,无法请求云片网！");
			throw new GenericException("网络异常,无法请求云片网！");
	    }else{
	    	if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
	    		logger.error(ToStringBuilder.reflectionToString(result,ToStringStyle.SHORT_PREFIX_STYLE));
	    	}
	    	return result;
	    }
	}
	
}
