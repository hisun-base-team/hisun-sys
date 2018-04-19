package com.hisun.saas.sys.taglib.util;
import com.hisun.util.StringUtils;

import java.util.ArrayList;

/**
 * 属性处理类。专门用于处理“name:value”格式的数据。
 * 此类与Attributes类全用，可以表达分级的格式数据“name:{name:value;name:{name:value;name:value}};name:value;name:{name:value}”。
 * 如果名称为NULL，将被自动转换为空字符串（""）。允许值为NULL及空字符。
 * @author 罗东森 刘福强 May 1, 2008
 */
public class Attribute{
	private String name=null;
	private Object value=null;
	//根据key-value对构造函数
	public Attribute(String name, Object value){
		if (name == null){
			name = "";
		}
		this.name = name;
		this.value = value;
	}
	public Attribute(){
	}
	/**
	 * 根据字符串构造一个属性对象
	 * @param attributeString 形如“name:value”的字符串
	 */
	public Attribute(String attributeString){
		if (StringUtils.isEmpty(attributeString ) == true){
			return;
		}
		ArrayList attrs = Attributes.splitAll(attributeString, ':');
		this.name = (String)attrs.get(0);
		if (this.name == null){
			this.name = "";
		}
		this.name = transSplitCodeReserve(this.name);
		if (attrs.size() <= 1){ // 没有内容部分。即没有“:”，则认为仅有名称，没有内容。
			this.value = null;
			return;
		}
		String o = (String)attrs.get(1);
		if (o == null){
			this.value = null;
			return;
		}
		// 判断可能存在的下级数据
		if (o.length() >= 2 && o.charAt(0) == '{' && o.charAt(o.length() -1) == '}'){
			this.value = new Attributes(o.substring(1, o.length() - 1));
		}else{
			this.value = transSplitCodeReserve(o);
		}
	}
	/**
	 * 将字符"!,:,;,{,}"替换成"!1,!2,!3,!4,!5"
	 * @param s 待替换的字符串
	 * @return  替换后的字符串
	 *
	 */
	public String transSplitCode(String s){
		if (s == null) {
			return s;
		}
		StringBuffer result = new StringBuffer();
		for(int i=0; i<s.length(); i++){
			char ch = s.charAt(i);
			switch(ch){
				case '!': result.append("!!"); break;
				case ':': result.append("!1"); break;
				case ';': result.append("!2"); break;
				case '{': result.append("!3"); break;
				case '}': result.append("!4"); break;
				default: result.append(ch);
			}
		}
		return result.toString();
	}
	/**
	 * 将字符"!1,!2,!3,!4,!5"替换成"!,:,;,{,}"
	 * @param s 待替换的字符串
	 * @return  替换后的字符串
	 */
	public String transSplitCodeReserve(String s){
		if (s == null) {
			return s;
		}
		StringBuffer result = new StringBuffer();
		for(int i=0; i<s.length(); i++){
			char ch = s.charAt(i);
			if (ch == '!'){
				i ++;
				if (i >= s.length()){
					result.append(ch);
					break;
				}else{
					ch = s.charAt(i);
					switch(ch){
						case '!': result.append("!"); break;
						case '1': result.append(":"); break;
						case '2': result.append(";"); break;
						case '3': result.append("{"); break;
						case '4': result.append("}"); break;
						default: result.append(ch);
					}
				}
			}else{
				result.append(ch);
			}
		}
		return result.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name == null){
			name = "";
		}
		this.name = transSplitCodeReserve(name); // 将其转换回本来字符串
	}
	public Object getValue() {
		if (this.value instanceof Attributes){ // 是下级属性集合对象
			return (Attributes)this.value;
		}else{ // 是字符串
			return (String)this.value; // 返回的字符串
		}
	}
	public String getStringValue() {
		if (this.value instanceof Attributes){ // 是下级属性集合对象
			return "";
		}else{ // 是字符串
			return (String)this.value; // 返回的字符串
		}
	}
	public void setValue(String value) {
		this.value = transSplitCodeReserve(value); // 将其转换回本来字符串
	}
	public void setValue(Attributes value) {
		this.value = value;
	}
	public String toString(){
		if (this.value instanceof Attributes){
			return transSplitCode(this.name) + ":" + ((Attributes)this.value).toStringForUp();
		}
		String value = (String)this.value;
		if (value == null){
			value = "";
		}
		return transSplitCode(this.name) + ":" + transSplitCode(value);
	}
	public Attribute clone(){
		Attribute attr = new Attribute();
		attr.setName(this.getName());
		Object o = this.getValue();
		if(o != null){
			if (o.getClass().getName().equalsIgnoreCase("java.lang.String")){
				attr.setValue((String)o);
			}else{ // 不是字符串，则一定是Attributes对象。
				attr.setValue(((Attributes)o).clone());
			}
		}
		return attr;
	}
}