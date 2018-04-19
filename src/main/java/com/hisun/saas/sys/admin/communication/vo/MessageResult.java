package com.hisun.saas.sys.admin.communication.vo;


/**
 * <p>Title: MessageResult.java </p>
 * <p>Package com.hisun.saas.sys.pojo </p>
 * <p>Description: 通用接口返回结果类</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月29日 上午10:50:24 
 * @version 
 */
public class MessageResult {

	public final static String CODE_SUCCESS = "0";

	/**
	 * 返回码
	 */
	private String code;
	
	/**
	 * 错误描述
	 */
	private String msg;
	
	/**
	 * 具体错误描述或解决方法
	 */
	private String detail;
	
	/**
	 * 成功后才有此值
	 */
	private Result result;

	/**
	 * 模板操作的返回结果
	 */
	private Template template;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

}
