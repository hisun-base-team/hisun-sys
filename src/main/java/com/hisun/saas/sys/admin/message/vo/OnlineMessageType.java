package com.hisun.saas.sys.admin.message.vo;
/**
 * <p>Title: OnlineMessageType.java </p>
 * <p>Package com.hisun.saas.sys </p>
 * <p>Description: 在线消息类型</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月29日 上午11:48:29 
 * @version 
 */
public enum OnlineMessageType {

    /**
     * 提示
     */
    INFORMATIONAL(Short.valueOf("0"),"提示"),
    /**
     * 低
     */
    LOW(Short.valueOf("1"),"低"),
    /**
     * 中
     */
    MEDIUM(Short.valueOf("2"),"中"),

    /**
     * 高
     */
    HIGH(Short.valueOf("3"),"高");


    private short value;
	
	private String description;

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private OnlineMessageType(short value, String description) {
		this.value = value;
		this.description = description;
	}

}
