package com.hisun.saas.sys.admin.communication.service;

import com.hisun.saas.sys.admin.communication.vo.MessageResult;
import com.hisun.saas.sys.admin.communication.vo.MessageResults;
import com.hisun.base.exception.GenericException;


/**
 * <p>Title: SMSService.java </p>
 * <p>Package com.hisun.saas.sys.service </p>
 * <p>Description: 云片短信接口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月29日 上午10:34:49 
 * @version 
 */
public interface SMSService {
	
	public static final String SUCCESS = "0";

	/**
	 * 通用接口发短信
	 * @param text 短信内容
	 * @param mobile 接受的手机号
	 * @return
	 */
	public MessageResult sendSms(String text, String ...mobile)  throws GenericException;
	
	/**
	 * 通过模板发送短信
	 * @param tplId 模板id
	 * @param tplValue 模板变量值
	 * @param mobile 接受的手机号
	 * @return
	 */
	public MessageResult tplSendSms(long tplId, String tplValue, String mobile)  throws GenericException;
	
	/**
	 * 获取模板
	 * @param tplId 模板id 未指定时返回所有模板
	 * @return
	 */
	public MessageResult getTpl(long tplId)  throws GenericException;
	
	/**
	 * 获取所有模板
	 * @return
	 */
	public MessageResults getAllTpl()  throws GenericException;
	
	/**
	 * 添加模板
	 * @param tplContent 模板内容，必须以带符号【】的签名结尾
	 * @param notifyType 审核结果短信通知的方式 0表示需要通知,默认; 1表示仅审核不通过时通知; 2表示仅审核通过时通知;3表示不需要通知
	 * @return
	 */
	public MessageResult addTpl(String tplContent,int notifyType)  throws GenericException;
	
	/**
	 * 修改模板
	 * @param tplId 模板id
	 * @param tplContent 模板内容，必须以带符号【】的签名结尾
	 * @return
	 */
	public MessageResult updateTpl(long tplId,String tplContent)  throws GenericException;
	
	/**
	 * 删除模板
	 * @param tplId 模板id
	 * @return
	 */
	public MessageResult delTpl(long tplId)  throws GenericException;
	
	
}
