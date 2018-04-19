/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.selectOption;


import com.hisun.saas.sys.taglib.util.Attributes;
import com.hisun.saas.sys.taglib.util.Attribute;
import com.hisun.util.StringUtils;

public class Style{
	private Attributes style =new Attributes();
	public Style(){}
	public Style(String styles){
		if (StringUtils.isEmpty(styles) == false){
			this.style.addAttributes(styles);
		}
	}
	public  String getTagName(){
		return "style";
	}
	/**
	 * 设置样式。使用新样式取代原来的样式。原来的所有样式将全部删除。
	 * @param text String 新样式，如：“width:100;height:200”
	 */
	public void setText(String text){
		this.setStyle(text);
	}
	/**
	 * 获取样式的字符串表达，如：“width:100;height:200”
	 */
	public String getText(){
		if (this.style != null){
			return this.style.toString();
		}else{
			return "";
		}
	}
	/**
	 * 设置样式。使用新样式取代原来的样式。原来的所有样式将全部删除。
	 * @param styles String 新样式，如：“width:100;height:200”
	 */
	public void setStyle(String styles){
		if (StringUtils.isEmpty(styles) == false){
			this.style = new Attributes(styles);
		}
	}
	public Attributes getAttributes(){
		return this.style;
	}
	public Attribute getStyle(String styleName){
		return this.style.get(styleName);
	}
	public void addStyles(Style style){
		// 增加一个样式对象中定义的样式，相同时，使用后者
		this.style.addAttributesNoRepeat(style.getAttributes());
	}
	/**
	 * 获取样式的字符串表达，如：“width:100;height:200”
	 * @return
	 */
	public String getStyleString(){
		return this.style.toString();
	}
	public String toString(){
		return this.style.toString();
	}
	/**
	 * 增加样式，判断重复，如果重复，则使用新值。
	 * @param styles String 新样式，如：“width:100;height:200”
	 */
	public void addStyles(String styles){
		this.style.addAttributesNoRepeat(styles);
	}
	/**
	 * 增加样式，判断重复，如果重复，则使用新值。
	 * @param style Attribute 新样式，属性对象
	 */
	public void addStyles(Attribute style){
		this.style.addNoRepeat(style);
	}
	/**
	 * 获取在HTML代码中的样式字符串，如：“ style="width:100;height:200"”
	 * @return HTML代码，前面有一空格。
	 */
	public String toHtml(){
		StringBuffer sb=new StringBuffer();
		sb.append(" style=\"").append(this.style.toString()).append("\"");
		return sb.toString();
	}

	/**
	 * 克隆一份自身
	 * @param template: SanTemplate 
	 * @throws Exception
	 * @author <a href="mailto:yihy@30san.com">alwayslater</a>
	 */
//	public Style clone(SanTemplate template) throws Exception {
//		Style newStyle = new Style();
//		
//		super.clone(template, newStyle);
//		
//		newStyle.setStyle(this.getStyleString());
//		newStyle.setText(this.getText());
//		return newStyle;
//	}
}
