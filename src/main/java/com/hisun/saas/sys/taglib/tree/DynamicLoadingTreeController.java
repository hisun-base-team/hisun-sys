/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.tree;
import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.GenericException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys/hisunTree")
public class DynamicLoadingTreeController extends BaseController{
	@RequestMapping(value="/ajax/loadTree")
	public @ResponseBody
	Map<String,Object> loadTree(HttpServletRequest request)throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String parentText=request.getParameter("parentText");
//			byte[] byt = parentText.getBytes("ISO-8859-1");
//			parentText = new String(byt,"GBK");
			String parentkey=request.getParameter("parentkey");
			String cengCount=request.getParameter("cengCount");
			String dataSource=request.getParameter("dataSource");
			String divId=request.getParameter("divId");
			String checkboxDisplay=request.getParameter("checkboxDisplay");
			String radioOrCheckBox=request.getParameter("radioOrCheckBox");
			String parentRadioEnable=request.getParameter("parentRadioEnable");
			String parentCheckboxEnable=request.getParameter("parentCheckboxEnable");
			String parentChildIsolate=request.getParameter("parentChildIsolate");
			String contextPath=request.getParameter("contextPath");
			String selectFunction=request.getParameter("selectFunction");
			String isNextNode=request.getParameter("isNextNode");
			String ondblclick=request.getParameter("ondblclick");
			String userParameter=request.getParameter("userParameter");
			String checkboxclickDefault=request.getParameter("checkboxclickDefault");
			String imgClick="";
			String aClick="";
			String checkBoxClick="";
			if(selectFunction!=null && !selectFunction.equals("")){
				String[] selectFunctionArr=selectFunction.split("#");
				if(selectFunctionArr.length>0 && selectFunctionArr.length==3){
					if(!selectFunctionArr[0].toString().equals("no")){
						imgClick=selectFunctionArr[0].toString();
					}

					if(!selectFunctionArr[1].toString().equals("no")){
						aClick=selectFunctionArr[1].toString();
					}

					if(!selectFunctionArr[2].toString().equals("no")){
						checkBoxClick=selectFunctionArr[2].toString();
					}
				}else{
					imgClick="no";
					aClick="no";
					checkBoxClick="no";
				}
			}

			/*
			Tree tree=new Tree(divId,dataSource,checkboxDisplay,
					radioOrCheckBox,parentRadioEnable,parentCheckboxEnable,
					parentChildIsolate,contextPath,parentText,parentkey,cengCount,this.getServletContext(),
					request,imgClick,checkBoxClick,aClick);
			StringBuffer nodesHtml=tree.getDynamicLoadChilds();
			*/

			Tree tree =new Tree();
			tree.setDivId(divId);//设置保存节点的div的id
			tree.setDataSource(dataSource);//设置树的数据源
			tree.setCheckboxDisplay(checkboxDisplay);//设置是否显示选择框对象
			tree.setRadioOrCheckbox(radioOrCheckBox);//设置是单选 还是 复选
			tree.setParentRadioEnable(parentRadioEnable);//单选情况下父节点是否可选 true可选 false不可选
			tree.setParentCheckboxEnable(parentCheckboxEnable);//复选情况下父节点是否可选 true可选 false不可选
			tree.setParentChildIsolate(parentChildIsolate);
			tree.setContextPath(contextPath);//设置系统路径
			tree.setParentText(parentText);//设置父节点标题
			tree.setParentkey(parentkey);//设置父节点key值
			tree.setCengCount(cengCount);//设置父节点层次值
			tree.setServletContext(request.getServletContext());//设置servlet上下文对象
			tree.setRequest(request);//设置HttpServletRequest对象
			tree.setImgClick(imgClick);//设置选择框控件,设置操作图片的函数
			tree.setCheckBoxClick(checkBoxClick);//设置选择框控件,设置选择框的函数
			tree.setAClick(aClick);//设置选择框控件,设置链接的函数
			tree.setIsNextNode(isNextNode);//用于动态载，加载节点的父节点是否有下一个同级节点
			tree.setDblonclick(ondblclick);
			tree.setUserParameter(userParameter);
			tree.setCheckBoxClick(checkboxclickDefault);

			StringBuffer nodesHtml= tree.getDynamicLoadChilds();
			if(nodesHtml!=null){
				map.put("nodesHtml",nodesHtml);
			}else{
				map.put("nodesHtml",nodesHtml);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			e.printStackTrace();
		}
		return map;
	}
}
