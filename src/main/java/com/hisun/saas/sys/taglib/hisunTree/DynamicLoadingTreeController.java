package com.hisun.saas.sys.taglib.hisunTree;
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
			BuildTreeHtml buildTreeHtml=new BuildTreeHtml(divId,dataSource,checkboxDisplay,
					radioOrCheckBox,parentRadioEnable,parentCheckboxEnable,
					parentChildIsolate,contextPath,parentText,parentkey,cengCount,this.getServletContext(),
					request,imgClick,checkBoxClick,aClick);
			StringBuffer nodesHtml=buildTreeHtml.getDynamicLoadChilds();
			*/

			BuildTreeHtml buildTreeHtml=new BuildTreeHtml();
			buildTreeHtml.setDivId(divId);//设置保存节点的div的id
			buildTreeHtml.setDataSource(dataSource);//设置树的数据源
			buildTreeHtml.setCheckboxDisplay(checkboxDisplay);//设置是否显示选择框对象
			buildTreeHtml.setRadioOrCheckbox(radioOrCheckBox);//设置是单选 还是 复选
			buildTreeHtml.setParentRadioEnable(parentRadioEnable);//单选情况下父节点是否可选 true可选 false不可选
			buildTreeHtml.setParentCheckboxEnable(parentCheckboxEnable);//复选情况下父节点是否可选 true可选 false不可选
			buildTreeHtml.setParentChildIsolate(parentChildIsolate);
			buildTreeHtml.setContextPath(contextPath);//设置系统路径
			buildTreeHtml.setParentText(parentText);//设置父节点标题
			buildTreeHtml.setParentkey(parentkey);//设置父节点key值
			buildTreeHtml.setCengCount(cengCount);//设置父节点层次值
			buildTreeHtml.setServletContext(request.getServletContext());//设置servlet上下文对象
			buildTreeHtml.setRequest(request);//设置HttpServletRequest对象
			buildTreeHtml.setImgClick(imgClick);//设置选择框控件,设置操作图片的函数
			buildTreeHtml.setCheckBoxClick(checkBoxClick);//设置选择框控件,设置选择框的函数
			buildTreeHtml.setAClick(aClick);//设置选择框控件,设置链接的函数
			buildTreeHtml.setIsNextNode(isNextNode);//用于动态载，加载节点的父节点是否有下一个同级节点
			buildTreeHtml.setDblonclick(ondblclick);
			buildTreeHtml.setUserParameter(userParameter);
			buildTreeHtml.setCheckBoxClick(checkboxclickDefault);

			StringBuffer nodesHtml=buildTreeHtml.getDynamicLoadChilds();
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
