package com.hisun.saas.sys.warn;


/**
 * 
 *<p>类名称：</p>
 *<p>类描述: 告警级别枚举类</p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：lyk
 *@创建时间：2015年5月7日
 *@创建人联系方式：
 *@version
 */
public enum WarnLevelEnum {
	/**
	 * 提示
	 */
	INFORMATIONAL(0,"提示"),
	/**
	 * 低
	 */
	LOW(1,"低"),
	/**
	 * 中
	 */
	MEDIUM(2,"中"),

    /**
     * 高
     */
    HIGH(3,"高");



	
	private int level;
	
	private String description;
	
	private WarnLevelEnum(int level,String description){
		this.level = level;
		this.description = description;
	}
	
	public int getValue(){
		return level;
	}
	
	public String getDescription(){
		return description;
	}		
	
	public static WarnLevelEnum getEnumBykey(int level){
		for(WarnLevelEnum w:WarnLevelEnum.values()){
			if(w.level==level){
				return w;
			}
		}
		return null;
	}
}
