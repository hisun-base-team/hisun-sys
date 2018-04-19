package com.hisun.saas.sys.taglib.util;


import java.util.ArrayList;


public class AttributesSplit {
	/**
	 *
	 * @param str432
	 * @param splitChar
	 * @param startIndex
	 * @return
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
			}else if (bracketCount==0&&splitChar==ch){
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	 * 工具函数，按照正确的层次，取得字符分组
	 * @param str 待分割字符串
	 * @param splitChar 分隔符
	 * @return
	 */

	public static ArrayList split(String str, char splitChar){
		ArrayList<String> A = new ArrayList<String>();
		int beginIndex=0;//开始指针
		int endIndex = 0;//结束指针
		while(endIndex >= 0){
			//查询分隔符的位置，如不存在返回－1；
			endIndex = seekSplitIndex(str, splitChar, beginIndex);
			//如果找到分割符
			if (endIndex >= 0){
				if (endIndex > beginIndex){
					//将子串记录在ArrayList中
					A.add(str.substring(beginIndex, endIndex));
				}
				//移动开始指针
				beginIndex = endIndex + 1;
			}else{
				//如果未找到分隔符位置，返回从开始指针起，整个子串
				if (beginIndex < str.length()){
					A.add(str.substring(beginIndex));
				}
				break;
			}
		}
		return A;

	}

}

