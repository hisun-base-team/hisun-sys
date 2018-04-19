package com.hisun.saas.sys.warn;
/**
 * 
 *<p>类名称：</p>
 *<p>类描述: 发送方式枚举类</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年5月7日
 *@创建人联系方式：
 *@version
 */
public enum WarningSendEnum {
	
	EMAIL(0,"邮件"),SMS(1,"短信"),WX(2,"微信");
	
	private String sendType;

	private int code;

	private WarningSendEnum(int code,String sendType){
		this.code = code;
		this.sendType = sendType;
	}
	
	public String getValue(){
		return sendType;
	}

	public int getCode(){
		return code;
	}
}
