package com.hisun.saas.sys.taglib.util;
import java.util.ArrayList;

import com.hisun.util.StringUtils;
/**
 * 属性对表达类。
 * 与Attribute类连用，可以表达分级的格式数据“name:value;name:{name:value;name:value};name:value;name:{name:value}”。
 * @author 罗东森 刘福强 May 1, 2008
 */
public class Attributes{
	/**
	 * attributes  记录属性字符串在内存中的映象
	 */
	private ArrayList<Attribute> attributes = new ArrayList<Attribute>();

	public Attributes(){}
	/**
	 * 根据字符串初始化一个Attributes对象
	 * @param attributeString 属性字符串
	 */
	public Attributes(String attributeString){
		this.addAttributes(attributeString);
	}
	/**
	 * 返回类似“html:{body:{input:{type:text;value:1};input:{type:text;value:3};tt:{value:3}}”的字符串
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<attributes.size(); i++){
			if (i > 0){
				sb.append(";");
			}
			sb.append(attributes.get(i).toString());
		}
		return sb.toString();
	}
	/**
	 * 被上级属性调用，返回本级属性集合。会在属性外面用大括号封装起来。
	 * @return
	 */
	protected String toStringForUp(){
		return "{" + this.toString() + "}";
	}
	/**
	 * 形成值的字符串，以逗号分隔，形如：value1,value2,value3
	 * @return
	 */
	public String toValueString(){
		return this.toStringOfValue();
	}
	/**
	 * 形成值的字符串，以逗号分隔，形如：value1,value2,value3
	 * @return
	 */
	public String toStringOfValue(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<this.attributes.size(); i++){
			if (i > 0){
				sb.append(",");
			}
			sb.append(this.attributes.get(i).getStringValue());
		}
		return sb.toString();
	}
	/**
	 * 形成名称的字符串，以逗号分隔，形如：name1,name2,name3
	 * @return
	 */
	public String toStringOfName(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<this.attributes.size(); i++){
			if (i > 0){
				sb.append(",");
			}
			sb.append(this.attributes.get(i).getName());
		}
		return sb.toString();
	}
	/**
	 * 返回Attributes 集合个数
	 * @return
	 */
	public int size(){
		return this.attributes.size();
	}
	/**
	 * 根据索引得到属性
	 * @param index
	 * @return
	 */
	public Attribute get(int index){
		return this.attributes.get(index);
	}
	/**
	 * 追加属性
	 * @param
	 * @return
	 */
	public Attribute add(Attribute attribute){
		this.attributes.add(attribute);
		return attribute;
	}
	/**
	 * 追加属性，判断重复，如果重复，则使用新值
	 * @param
	 * @return 放入属性集合中的属性对象
	 */
	public Attribute addNoRepeat(Attribute attribute){
		if (attribute == null){
			return null;
		}
		Attribute attr = this.get(attribute.getName());
		if (attr == null){ // 未找到，则新增之
			this.attributes.add(attribute);
			attr = attribute;
		}else{ // 找到，则更新其值
			Object o = attribute.getValue();
			if (o instanceof Attributes){ // 是属性集，则继续判断之。
				// 判断当前已有的属性对象的值是否也是属性集，如果是，则需要递归
				Object oldValue = attr.getValue();
				if (oldValue instanceof Attributes){ // 是属性集
					((Attributes)oldValue).addAttributesNoRepeat((Attributes)o);
				}else{ // 不是属性集，是字符串，则直接使用新的属性集作为其值
					attr.setValue((Attributes)o);
				}
			}else{ // 是字符串，则更新之
				attr.setValue((String)o);
			}
		}
		return attr;
	}
	/**
	 * 通过字符串（形如“name:value”的字符串）追加属性
	 * @param attribute 属性字符串 形如“name:value”的字符串
	 * @return
	 */
	public Attribute add(String attribute){
		Attribute attr = new Attribute(attribute);
		this.attributes.add(attr);
		return attr;
	}
	/**
	 * 通过字符串（形如“name:value”的字符串）追加属性，判断重复，如果重复，则使用新值
	 * @param attribute 属性字符串 形如“name:value”的字符串
	 * @return
	 */
	public Attribute addNoRepeat(String attribute){
		Attribute attr = new Attribute(attribute);
		this.addNoRepeat(attr);
		return attr;
	}
	/**
	 * 通过属性集合对象追加属性
	 * @param
	 * @return
	 */
	public void addAttributes(Attributes attributes){
		for(int i=0; i<attributes.size(); i++){
			this.attributes.add(attributes.get(i));
		}
	}
	/**
	 * 通过属性集合对象追加属性，判断重复，如果重复，则使用新值
	 * @param
	 * @return
	 */
	public void addAttributesNoRepeat(Attributes attributes){
		for(int i=0; i<attributes.size(); i++){
			this.addNoRepeat(attributes.get(i));
		}
	}
	/**
	 * 通过字符串追加属性
	 * @param
	 * @return
	 */
	public void addAttributes(String attributeString){
		//在同一层次用";"划分
		ArrayList temp = Attributes.split(attributeString, ';');
		for(int i=0; i<temp.size(); i++){
			Attribute a = new Attribute((String)temp.get(i));
			this.attributes.add(a);
		}
	}
	/**
	 * 通过字符串追加属性，判断重复，如果重复，则使用新值
	 * @param
	 * @return
	 */
	public void addAttributesNoRepeat(String attributeString){
		//在同一层次用";"划分
		ArrayList temp = Attributes.split(attributeString, ';');
		for(int i=0; i<temp.size(); i++){
			this.addNoRepeat((String)temp.get(i));
		}
	}
	/**
	 * 根据属性名称获得属性对象
	 * @param name
	 * @return
	 */
	public Attribute get(String name){
		if (name == null){
			name = "";
		}
		for(int i=0; i<this.attributes.size(); i++){
			Attribute attribute=(Attribute)this.attributes.get(i);
			if(attribute.getName().equalsIgnoreCase(name)){
				return attribute;
			}
		}
		return null;
	}
	/**
	 * 根据路径返回属性对象
	 * @param path
	 * @return
	 */
	public  Attribute getAttributeByPath(String path){
		if (path == null || path.length() == 0){
			return null;
		}
		String[] names = path.split("/", -1); // 拆分当前字符串，空值也算一个
		return this.getAttributeByPath(names, 0);
	}
	private  Attribute getAttributeByPath(String[] path, int index){
		String name = path[index];
		for(int i=0; i<this.size(); i++){
			Attribute attribute = this.get(i);
			if (StringUtils.isEmpty(name) && StringUtils.isEmpty(attribute.getName())
					|| StringUtils.equals(name, attribute.getName())){ // 名称相同
				if(path.length -1 == index){ //如果已经到达最后一级，且找到此属性，返回属性对象
					return attribute;
				}else{ //如果路径未到达最后一级，继续递归查找
					Object o = attribute.getValue();
					if (o instanceof Attributes){ //如果o是一个Attributes对象
						Attribute attr =  ((Attributes)o).getAttributeByPath(path, index + 1);
						if (attr != null){
							return attr;
						}
					}else{ //如果o不是一个Attributes对象，直接返回
						return null;
					}
				}
			}
		}
		//未查询到属性对象，直接返回null
		return null;
	}
	public Attributes clone(){
		Attributes attrs = new Attributes();
		for(int i=0; i<this.size(); i++){
			attrs.add(this.get(i).clone());
		}
		return attrs;
	}
	/**
	 * 按指定分隔符分解指定字符串，从指定位置开始。认为字符串是分级的，分级分隔符为“{}”。
	 * @param str 被分解字符串
	 * @param splitChar 分隔符，字符型
	 * @param startIndex 开始位置
	 * @return 找到的位置
	 */
	public static int seekSplitIndex(String str, char splitChar, int startIndex){
		int index = -1;
		//括号记数器
		int bracketCount = 0;
		//循环字符串
		for(int i=startIndex; i<str.length(); i++){
			char ch = str.charAt(i);
			//如果字符为'{',记数器加1
			if (ch=='{'){
				bracketCount ++ ;
				//如果字符为'}',记数器减1
			}else if (ch=='}'){
				bracketCount --;
				//如果括号正确匹配，且指向字符等于分割符，返回分割符位置
			}else if (bracketCount == 0 && splitChar == ch){
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	 * 根据指定的分隔符，按照正确的层次，取得字符分组。认为字符串是分级的，分级分隔符为“{}”。
	 * @param str 待分割字符串
	 * @param splitChar 分隔符
	 * @return 动态数组。不含无内容的段。
	 */
	public static ArrayList split(String str, char splitChar){
		ArrayList<String> al = new ArrayList<String>();
		int beginIndex=0;//开始指针
		int endIndex = 0;//结束指针
		if (StringUtils.isEmpty(str) == false){ // 仅处理非空串
			int len = str.length();
			while(beginIndex < len){
				//查询分隔符的位置，如不存在返回－1；
				endIndex = seekSplitIndex(str, splitChar, beginIndex);
				//如果找到分割符
				if (endIndex >= 0){
					if (endIndex > beginIndex){//仅处理有内容的段，无内容的段被忽略。
						//将子串记录在ArrayList中
						al.add(str.substring(beginIndex, endIndex));
					}
					//移动开始指针
					beginIndex = endIndex + 1;
				}else{
					//如果未找到分隔符位置，返回从开始指针起，整个子串
					al.add(str.substring(beginIndex));
					break;
				}
			}
		}
		return al;
	}
	/**
	 * 根据指定的分隔符，按照正确的层次，取得字符分组。认为字符串是分级的，分级分隔符为“{}”。
	 * @param str 待分割字符串
	 * @param splitChar 分隔符
	 * @return 动态数组。含无内容的段，其值为空字符串（""）。
	 */
	public static ArrayList splitAll(String str, char splitChar){
		ArrayList<String> al = new ArrayList<String>();
		int beginIndex=0;//开始指针
		int endIndex = 0;//结束指针
		if (StringUtils.isEmpty(str) == false){ // 仅处理非空串
			int len = str.length();
			while(beginIndex <= len){
				//查询分隔符的位置，如不存在返回－1；
				endIndex = seekSplitIndex(str, splitChar, beginIndex);
				//如果找到分割符
				if (endIndex >= 0){
					//将子串记录在ArrayList中，可能为空字符串。
					al.add(str.substring(beginIndex, endIndex));
					//移动开始指针
					beginIndex = endIndex + 1;
				}else{
					//如果未找到分隔符位置，返回从开始指针起，整个子串
					al.add(str.substring(beginIndex));
					break;
				}
			}
		}
		return al;
	}
	/**
	 * 通过项目的值获取项目。认为各项的值都是字符串。返回第一个匹配的项。
	 * @param value
	 * @return
	 */
	public Attribute getByValue(String value){
		if (value != null){
			for(int i=0; i<this.size(); i++){
				Attribute attr = this.get(i);
				if (StringUtils.equals((String)attr.getValue(), value) == true){
					return attr;
				}
			}
		}
		return null;
	}
	/**
	 * 通过项目的值获取项目。认为各项的值都是字符串。返回第一个匹配的项。
	 * @param value
	 * @return
	 */
	public String getNameByValue(String value){
		Attribute attr = this.getByValue(value);
		if (attr != null){
			return attr.getName();
		}else{
			return null;
		}
	}
	public void remove(Attribute attribute){
		this.attributes.remove(attribute); // 删除对象。其内容采用比较地址方式。如此，即使有重复数据，也不会误删除。
	}
	public void remove(String name){
		Attribute attr = this.get(name); // 取命中的第一个
		if (attr != null){
			this.attributes.remove(attr); // 删除对象。其内容采用比较地址方式。如此，即使有重复数据，也不会误删除。
		}
	}
	public void remove(int index){
		this.attributes.remove(index);
	}
	public static void main(String[] args) throws Exception{
		Attributes as = new Attributes("html:{body:{input:{type:text;value:1};input:{type:text;value:3};input:{type:text;value:4};submit:{type:text;value:2}};tt:{value:3}}");
	}


}