package com.hisun.saas.sys.taglib.hisunTree;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import com.hisun.saas.sys.taglib.tree.SanTreeDataSourceInterface;
import com.hisun.saas.sys.taglib.tree.impl.SanTreeNode;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.StringUtils;

/**
 * @author chensheng
 * @TIME 2008-3-30
 * 使用说明：
 */
public final class BuildTreeHtml{

	private String divId;

	private String dataSource;//实现SanTreeDataSourceInterface接口的类全路径

	private int treeId=0;

	private String contextPath;//系统路径

	private String checkboxDisplay;//是否显示选择框

	private String radioOrCheckbox;//单选或多择

	private String parentRadioEnable;//单选情况下,父节点选择框可选

	private String parentCheckboxEnable;//多选情况下,父节点选择框可选

	private String parentChildIsolate;//父子节点之间,是否可以相互影响

	//////////////////////////////
	private String parentText;//父节点标题

	private String parentkey;//父节点key值

	private String cengCount;//层次数

	private String userParameter;

	/////////////////////////////

	private PageContext pageContext;

	private ServletContext servletContext;

	private ServletRequest request;

	private String imgClick;//用于下拉选择框

	private String checkBoxClick;//用于下拉选择框

	private String aClick;//用于下拉选择框

	private String defaultOnclick;

	private String dblonclick;
	//单击标题时，复选框只选中一次
	private String firstSelectedByTitle="false";
	//单击复选框，标题得到焦点
	private String selectedTitleByCheckbox="false";
	private String imgPath="/images/tImg/";
	//用于动态载，加载节点的父节点是否有下一个同级节点
	private String isNextNode="";

	private String zzb_checkedTrueIcon="ct.png";//checkTrueImg.png
	private String zzb_checkedFalseIcon="cf.png";//checkFalseImg.png
	//用于点击打开子节点图片
	private String zzb_openIcon="Lp.png";//Lplus.png
	//第一层最顶节点图片，有子点状态下 没有展开的情况下
	private String zzb_topOpenIcon="Tps.png";//TopLplus.png
	//第一层最顶节点图片，有子点状态下 已展开的情况下
	private String zzb_topCloseIcon="Tlm.png";//TopLminus.png
	//用于点击收缩子节点图片
	private String zzb_closeIcon="Lm.png";//Lminus.png
	//没有子节点状态下图片
	private String zzb_noIcon="T.png";//T.png
	private String zzb_lastChildIcon="L.png";//L.png
	//节点之间线条缩进图片
	private String zzb_lineIcon="I.png";//I.png
	//缩进空图片
	private String zzb_nullIcon="b.png";//blank.png
	//第一层第一个节点，没有子节点状态下的图片
	private String zzb_topNoNodeIcon="Tp.gif";

	private String auto_dynamic_load="0";
	//当前节点导肮信息
	private String currentNodeNavigation="";
	//是否对树进行初始化
	private String isInit;
	//单击标题是否会影响复选框  true 为影响，false 为不影响
	private String seletedCheckboxByTitle;
	//选中上级节点时，下级节点是否要自动全部选中，是或否 1,0
	private String parentSelectedWithChild="1";
	//取消上级节点的选中状态时，下级节点是否要自动全部取消，是或否 1,0
	private String parentClearWithChild="1";
	//选中下级节点时，如果下级同级节点已全部都为选中状态，其上级是否自动选中，是或否 1,0
	private String lowerSelectedAllWithSuperior="1";
	//取消下级节点的选中状态时，其上级节点选中状态是否要自动取消，是或否 1,0
	private String lowerClearWithSuperior="1";
	//取消下级节点的选中状态时，其上级节点不自动取消的情况下，如果下级同级节点已全部为未选中状态，其上级是否自动取消，是或否 1,0
	private String lowerClearAllWithSuperior="1";
	//配置下级节点选中父节点是否。是或否 1,0
	private String lowerSelectedWithSuperior="false";

	private String openOnclick;

	private String type;

	private String expandByCheckbox="1";

	public String getExpandByCheckbox(){
		return expandByCheckbox;
	}

	public void setExpandByCheckbox(String expandByCheckbox) {
		this.expandByCheckbox = expandByCheckbox;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeletedCheckboxByTitle() {
		return seletedCheckboxByTitle;
	}

	public void setSeletedCheckboxByTitle(String seletedCheckboxByTitle) {
		this.seletedCheckboxByTitle = seletedCheckboxByTitle;
	}

	public String getIsInit() {
		return isInit;
	}

	public void setIsInit(String isInit) {
		this.isInit = isInit;
	}

	public String getDefaultOnclick() {
		if(this.defaultOnclick==null || this.defaultOnclick.equals("")){
			return "javascript:{}";
		}else{
			return defaultOnclick;
		}
	}

	public String getDblonclick() {
		return dblonclick;
	}

	public void setDblonclick(String dblonclick) {
		this.dblonclick = dblonclick;
	}

	public void setDefaultOnclick(String defaultOnclick) {
		this.defaultOnclick = defaultOnclick;
	}

	public String getAClick(){
		if(this.aClick==null || this.aClick.equals("")){
			return "";
		}else{
			return aClick;
		}
	}

	public void setAClick(String aClick) {
		if(aClick==null || aClick.equals("no")){
			this.aClick="";
		}else{
			this.aClick=aClick;
		}
	}

	public String getCheckBoxClick() {
		if(this.checkBoxClick==null || this.checkBoxClick.equals("")){
			return "";
		}else{
			return checkBoxClick;
		}
	}

	public void setCheckBoxClick(String checkBoxClick) {
		if(checkBoxClick==null || checkBoxClick.equals("no")){
			this.checkBoxClick="";
		}else{
			this.checkBoxClick=checkBoxClick;
		}
	}

	public String getImgClick() {
		if(this.imgClick==null || this.imgClick.equals("")){
			return "";
		}else{
			return this.imgClick;
		}
	}

	public void setImgClick(String imgClick) {
		if(imgClick==null || imgClick.equals("no")){
			this.imgClick="";
		}else{
			this.imgClick=imgClick;
		}
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public String getCheckboxDisplay() {
		if(this.checkboxDisplay==null || this.checkboxDisplay.equals("") || this.checkboxDisplay.equals("yes") || this.checkboxDisplay.equals("block")){
			return "";
		}else{
			return "display:none";
		}

	}

	public void setCheckboxDisplay(String checkboxDisplay) {
		this.checkboxDisplay = checkboxDisplay;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getParentCheckboxEnable() {
		return parentCheckboxEnable;
	}

	public void setParentCheckboxEnable(String parentCheckboxEnable) {
		this.parentCheckboxEnable = parentCheckboxEnable;
	}

	public String getParentChildIsolate() {
		return parentChildIsolate;
	}

	public void setParentChildIsolate(String parentChildIsolate) {
		this.parentChildIsolate = parentChildIsolate;
	}

	public String getParentRadioEnable() {
		return parentRadioEnable;
	}

	public void setParentRadioEnable(String parentRadioEnable) {
		this.parentRadioEnable = parentRadioEnable;
	}

	public String getRadioOrCheckbox() {
		return radioOrCheckbox;
	}

	public void setRadioOrCheckbox(String radioOrCheckbox) {
		this.radioOrCheckbox = radioOrCheckbox;
	}

	public int getTreeId() {
		return treeId;
	}

	public void setTreeId(int treeId) {
		this.treeId = treeId;
	}

	public String getCengCount() {
		return cengCount;
	}

	public void setCengCount(String cengCount) {
		this.cengCount = cengCount;
	}

	public String getParentkey() {
		return parentkey;
	}

	public void setParentkey(String parentkey) {
		this.parentkey = parentkey;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public String getUserParameter() {
		return userParameter;
	}

	public void setUserParameter(String userParameter) {
		this.userParameter = userParameter;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public BuildTreeHtml(){

	}

	/*
     * 用于标签中
     * version 1.0
     */
	public BuildTreeHtml(String divId,String dataSource,String checkboxDisplay,
						 String radioOrCheckbox,String parentRadioEnable,String parentCheckboxEnable,
						 String parentChildIsolate,PageContext pageContext,String userParameter){
		this.divId=divId;
		this.dataSource=dataSource;
		this.checkboxDisplay=checkboxDisplay;
		this.radioOrCheckbox=radioOrCheckbox;
		this.parentRadioEnable=parentRadioEnable;
		this.parentCheckboxEnable=parentCheckboxEnable;
		this.parentChildIsolate=parentChildIsolate;
		this.pageContext=pageContext;
		this.servletContext=this.pageContext.getServletContext();
		this.contextPath=this.pageContext.getAttribute("contextPath").toString();
		this.request=this.pageContext.getRequest();
		this.userParameter=userParameter;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 用于servlet中动态加载整颗树
	 */
	public BuildTreeHtml(String divId,String dataSource,String checkboxDisplay,
						 String radioOrCheckbox,String parentRadioEnable,String parentCheckboxEnable,
						 String parentChildIsolate,String contextPath,ServletContext servletContext,ServletRequest request,String userParameter,
						 String imgClick,String checkBoxClick,String aClick){
		this.divId=divId;
		this.dataSource=dataSource;
		this.checkboxDisplay=checkboxDisplay;
		this.radioOrCheckbox=radioOrCheckbox;
		this.parentRadioEnable=parentRadioEnable;
		this.parentCheckboxEnable=parentCheckboxEnable;
		this.parentChildIsolate=parentChildIsolate;
		this.contextPath=contextPath;
		this.parentText="0";
		this.parentkey="0";
		this.cengCount="1";
		this.servletContext=servletContext;
		this.request=request;
		this.setUserParameter(userParameter);
		this.setImgClick(imgClick);
		this.setCheckBoxClick(checkBoxClick);
		this.setAClick(aClick);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////
/*
 * 用于servlet中动态加载子节点
 */
	public BuildTreeHtml(String divId,String dataSource,String checkboxDisplay,
						 String radioOrCheckbox,String parentRadioEnable,String parentCheckboxEnable,
						 String parentChildIsolate,String contextPath,String parentText,String parentkey,
						 String cengCount,ServletContext servletContext,ServletRequest request,
						 String imgClick,String checkBoxClick,String aClick){
		this.divId=divId;
		this.dataSource=dataSource;
		this.checkboxDisplay=checkboxDisplay;
		this.radioOrCheckbox=radioOrCheckbox;
		this.parentRadioEnable=parentRadioEnable;
		this.parentCheckboxEnable=parentCheckboxEnable;
		this.parentChildIsolate=parentChildIsolate;

		this.contextPath=contextPath;
		this.parentText=parentText;
		this.parentkey=parentkey;
		this.cengCount=cengCount;
		this.servletContext=servletContext;
		this.request=request;

		this.setImgClick(imgClick);
		this.setCheckBoxClick(checkBoxClick);
		this.setAClick(aClick);


	}
	///////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 标签调用来加载,树节点
	*/
	public StringBuffer getTreeHtml(){
		StringBuffer nodesHtml=new StringBuffer();
		try{
			//Class beanClass = Class.forName(this.getDataSource());
			SanTreeDataSourceInterface obj = ApplicationContextUtil.getBean(this.getDataSource(), SanTreeDataSourceInterface.class);
//			SanTreeDataSourceInterface obj = (TreeObject)beanClass.newInstance();
			List<SanTreeNode> list=null;
			obj.setServletContext(this.servletContext);
			obj.setUserParameter(this.userParameter);
			obj.setRequest(this.request);
			list = (List<SanTreeNode>)obj.getNodes();
			this.treeId=0;

			if(list!=null && list.size()>0){
				nodesHtml = addNodesNew(list,"0",1,getDivId());

				nodesHtml.append("<script>var TempZZBTreeItem = new ZZBTreeItem(document.getElementById('")
						.append(getDivId()).append("'),'").append(this.contextPath).append("');")
						.append("TempZZBTreeItem.setOnclicks('").append(this.getDefaultOnclick()).append("');")
						.append("TempZZBTreeItem.setRadioOrCheckbox('").append(this.radioOrCheckbox).append("');")
						.append("TempZZBTreeItem.setRadioSelectParent(").append(this.parentRadioEnable).append(");")
						.append("TempZZBTreeItem.setCheckSelectParent(").append(this.parentCheckboxEnable).append(");")
						.append("TempZZBTreeItem.setCheckBoxSelectWay(").append(this.parentChildIsolate).append(");")
						.append("TempZZBTreeItem.setIsInit(\""+this.getIsInit()+"\");")

						.append("TempZZBTreeItem.setDataSource(\""+this.dataSource+"\");")
						.append("TempZZBTreeItem.setcheckboxDisplay(\""+this.getCheckboxDisplay()+"\");")
						.append("TempZZBTreeItem.setzzb_auto_dynamic_load(\""+this.getAuto_dynamic_load()+"\");")
						.append("TempZZBTreeItem.setCurrentNodeNavigation(\""+this.currentNodeNavigation+"\");")
						.append("TempZZBTreeItem.setOndblclick(\""+this.getDblonclick()+"\");")
						.append("TempZZBTreeItem.setUserParameter(\""+this.getUserParameter()+"\");")
						.append("TempZZBTreeItem.setSeletedCheckboxByTitle(\""+this.getSeletedCheckboxByTitle()+"\");")
						.append("TempZZBTreeItem.setAclickDefault(\""+this.getAClick()+"\");")
						.append("TempZZBTreeItem.setCheckboxclickDefault(\""+this.getCheckBoxClick()+"\");")
						.append("TempZZBTreeItem.setFirstSelectedByTitle(\""+this.getFirstSelectedByTitle()+"\");")
						.append("TempZZBTreeItem.setSelectedTitleByCheckbox(\""+this.getSelectedTitleByCheckbox()+"\");")
						.append("TempZZBTreeItem.setType(\""+this.getType()+"\");")
						.append("TempZZBTreeItem.setOpenOnclick(\""+this.getOpenOnclick()+"\");")
						.append("TempZZBTreeItem.setExpandByCheckbox(\""+this.getExpandByCheckbox()+"\");")

						.append("TempZZBTreeItem.setParentSelectedWithChild(\""+this.getParentSelectedWithChild()+"\");")
						.append("TempZZBTreeItem.setParentClearWithChild(\""+this.getParentClearWithChild()+"\");")
						.append("TempZZBTreeItem.setLowerSelectedAllWithSuperior(\""+this.getLowerSelectedAllWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerClearWithSuperior(\""+this.getLowerClearWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerClearAllWithSuperior(\""+this.getLowerClearAllWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerSelectedWithSuperior(\""+this.getLowerSelectedWithSuperior()+"\");")

						.append("TempZZBTreeItem.setIsInit(\""+isInit+"\");")//
						.append("TempZZBTreeItem.init();")
						.append("zzbTreeHandler.idCounter=").append(this.getTreeId()).append(";")
						.append("</script>");
				//
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" getTreeHtml() 生成HTML错误 ! (" + e.getMessage() + ")");
		}
		return nodesHtml;
	}


	/*
	 * servlet调用来加载,树节点
	 */
	public StringBuffer getTreeHtmlByServlet(){
		StringBuffer nodesHtml=new StringBuffer();
		try{
			SanTreeDataSourceInterface obj = ApplicationContextUtil.getBean(this.getDataSource(), SanTreeDataSourceInterface.class);
			List list=null;
			obj.setServletContext(this.servletContext);
			obj.setRequest(this.request);
			obj.setUserParameter(this.userParameter);
			list = obj.getNodes();
			this.treeId=0;
			String isInit="true";
			if(this.getIsInit()!=null && !this.getIsInit().equals("") && !this.getIsInit().equals("null")){
				isInit=this.getIsInit();
			}
			if(list!=null && list.size()>0){
				nodesHtml = addNodesNew(list,"0",1,getDivId());
				//nodesHtml = addNodes(list,"0",1,getDivId());
				nodesHtml.append("##### var TempZZBTreeItem = getObjByMainDivId('").append(getDivId()).append("');")
						.append("TempZZBTreeItem.setOnclicks('").append(this.getDefaultOnclick()).append("');")
						.append("TempZZBTreeItem.setContextPath('").append(this.contextPath).append("');")
						.append("TempZZBTreeItem.setRadioOrCheckbox('").append(this.radioOrCheckbox).append("');")
						.append("TempZZBTreeItem.setRadioSelectParent(").append(this.parentRadioEnable).append(");")
						.append("TempZZBTreeItem.setCheckSelectParent(").append(this.parentCheckboxEnable).append(");")
						.append("TempZZBTreeItem.setCheckBoxSelectWay(").append(this.parentChildIsolate).append(");")

						.append("TempZZBTreeItem.setDataSource(\""+this.dataSource+"\");")
						.append("TempZZBTreeItem.setcheckboxDisplay(\""+this.getCheckboxDisplay()+"\");")
						.append("TempZZBTreeItem.setzzb_auto_dynamic_load(\""+this.getAuto_dynamic_load()+"\");")
						.append("TempZZBTreeItem.setCurrentNodeNavigation(\""+this.currentNodeNavigation+"\");")
						.append("TempZZBTreeItem.setOndblclick(\""+this.getDblonclick()+"\");")
						.append("TempZZBTreeItem.setUserParameter(\""+this.getUserParameter()+"\");")
						.append("TempZZBTreeItem.setSeletedCheckboxByTitle(\""+this.getSeletedCheckboxByTitle()+"\");")
						.append("TempZZBTreeItem.setAclickDefault(\""+this.getAClick()+"\");")
						.append("TempZZBTreeItem.setCheckboxclickDefault(\""+this.getCheckBoxClick()+"\");")
						.append("TempZZBTreeItem.setFirstSelectedByTitle(\""+this.getFirstSelectedByTitle()+"\");")
						.append("TempZZBTreeItem.setSelectedTitleByCheckbox(\""+this.getSelectedTitleByCheckbox()+"\");")
						.append("TempZZBTreeItem.setType(\""+this.getType()+"\");")
						.append("TempZZBTreeItem.setOpenOnclick(\""+this.getOpenOnclick()+"\");")
						.append("TempZZBTreeItem.setExpandByCheckbox(\""+this.getExpandByCheckbox()+"\");")

						.append("TempZZBTreeItem.setParentSelectedWithChild(\""+this.getParentSelectedWithChild()+"\");")
						.append("TempZZBTreeItem.setParentClearWithChild(\""+this.getParentClearWithChild()+"\");")
						.append("TempZZBTreeItem.setLowerSelectedAllWithSuperior(\""+this.getLowerSelectedAllWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerClearWithSuperior(\""+this.getLowerClearWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerClearAllWithSuperior(\""+this.getLowerClearAllWithSuperior()+"\");")
						.append("TempZZBTreeItem.setLowerSelectedWithSuperior(\""+this.getLowerSelectedWithSuperior()+"\");")

						.append("TempZZBTreeItem.setIsInit(\""+isInit+"\");")
						.append("TempZZBTreeItem.init();")

						.append("zzbTreeHandler.idCounter=").append(this.getTreeId());
				//
			}

		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" getTreeHtml() 生成HTML错误 ! (" + e.getMessage() + ")");
		}

		return nodesHtml;
	}
	private StringBuffer addNodes(List list,String parentKey,int cengCount,String divId) {
		return addNodes(list, parentKey, cengCount, divId, 0);
	}
	private StringBuffer addNodes(List list,String parentKey,int cengCount,String divId, int index) {
		StringBuffer NodesString = null;
		for(int i=index; i<list.size(); i++){
			SanTreeNode t1=(SanTreeNode)list.get(i);
			if(StringUtils.equals(t1.getParentKey(), parentKey)){
				if (NodesString == null){
					NodesString = new StringBuffer();
				}
				if(t1.getDynamicLoading()==1){
					NodesString.append(getDynamicLoadingDivHtml(cengCount, divId, t1));
				}else{
					StringBuffer sb1 = addNodes(list, t1.getKey(), cengCount+1, divId, i + 1);
					if (sb1 == null){
						NodesString.append(getDivHtml(cengCount,divId,t1,"no"));
					}else{
						NodesString.append(getDivHtml(cengCount,divId,t1,"yes"));
						NodesString.append(sb1);
					}
					NodesString.append("</div>");
				}
			}
		}
		return NodesString;
	}


	/**
	 * 设置节点函数
	 */
	private void setFunction(SanTreeNode t){
		String checkboxJs=t.getCheckboxJs();
		String jsFunction=t.getJsFunction();
		if(checkboxJs==null || checkboxJs.equals("") || checkboxJs.equals("javascript:{}")){
			t.setCheckboxJs("defaultOnclick(this)");
		}
		if(jsFunction==null || jsFunction.equals("") || jsFunction.equals("javascript:{}")){
			t.setJsFunction("defaultOnclick(null)");
		}

	}

	/**静态树
	 * 通过节点信息,创建节点html代码
	 * @param cengCount
	 * @param divId
	 * @param t
	 * @param childCount
	 * @return
	 */
	private String getDivHtml(int cengCount,String divId,SanTreeNode t,String childCount){
		StringBuffer results=new StringBuffer("");
		int id=this.getTreeId();
		String objId="";//divId+"-zzb-tree-object-"+id;
		StringBuffer cengImg=new StringBuffer("");
		cengImg.append(t.getCengImg());

		String enable="";
		if(t.getEnable()!=null && t.getEnable().equals("false")){
			enable="disabled";
		}

		//设置节点函数,相关函数属性为空的情况下,赋于默认函数
		this.setFunction(t);
		//设置选择框的html代码 在没有设置为灰勾的情况下
		StringBuffer radioOrCheckBoxHtml=new StringBuffer("");
		//设置选择框的html代码 在设置为灰勾的情况下
		if(t.getGrayImg()!=null && t.getGrayImg().equals("1")){
			radioOrCheckBoxHtml=new StringBuffer("");
			radioOrCheckBoxHtml.append("<img onclick=\"gic(this);").append(t.getCheckboxJs()).append(";").append(this.getCheckBoxClick()).append(";\" ")
					.append("style=\"display:black;cursor:hand;border-color:red;\" ")
					.append("src=\"")
//					.append(this.contextPath)
					.append(this.imgPath).append(this.zzb_checkedTrueIcon).append("\">");
		}

		if(this.getCheckboxDisplay().equals("")){//设置显示复选框的情况下
			if(t.getCheckboxDisplay().equals("")){
				String dis="";
				if(t.getGrayImg().equals("1")){
					dis=";display:none";
				}
				String sel="0";
				if(t.getChecked().equals("checked")){
					sel="1";
				}
				radioOrCheckBoxHtml.append("<input onpropertychange=\"cch(this)\" type=\"checkbox\" ").append(enable)
						.append(" style=\"height:13px").append(dis).append("\" tag=\"INPUT\" ")
						.append("name=\"").append(t.getCheckboxName())
						.append("\" sel=\""+sel+"\" ").append(t.getChecked()).append(" onclick=\"javascript:{cs(this,'true');")
						.append(t.getCheckboxJs()).append(";")
						.append(this.getCheckBoxClick()).append("}\" ")
						.append(objId).append(">");
			}
		}
		//动态加载方法设定
		String dynamicFunction="";
		if(t.getDynamicLoading()==1){//当节点设置为动态加载的情况进行下面操作 1为动态加载,0为非动态加载
			dynamicFunction="Ldn(this);";
		}

		//设置操作图片的html代码
		StringBuffer openCloseImg=new StringBuffer();
		openCloseImg.append("<img open=\""+t.getOpen()+"\"  ").append("onclick=\"orc(this);").append(dynamicFunction).append(this.getImgClick()).append("\" ")
				.append("style=\"cursor:hand;\" id=\"").append(objId).append("plus\" ")
				.append("src=\"")
//				.append(this.contextPath)
				.append(this.imgPath).append(t.getNodeOperateImg()).append("\"/>");

		StringBuffer selectFunction=new StringBuffer("");
		selectFunction.append(this.getImgClick().equals("")?"no":this.getImgClick()).append("#")
				.append(this.getAClick().equals("")?"no":this.getAClick()).append("#")
				.append(this.getCheckBoxClick().equals("")?"no":this.getCheckBoxClick());

		//设置标题提示
		String title="";
		if(t.getTitleTip()!=null && !t.getTitleTip().equals("")){
			title=" title=\""+t.getTitleTip()+"\" ";
		}else{
			title=" title=\""+t.getText()+"\" ";
		}
		//节点链接对象的html代码
		StringBuffer hrefHtml=new StringBuffer("");


		hrefHtml.append("<a href=\"").append(t.getHref()).append("\" ")
				.append(" onfocus=\"nf(this);\" ")
				.append(" ondblclick=\"javascript:{dea(this);").append(this.getDblonclick()).append("}\" ")
				.append(" jsf=\"").append(t.getJsFunction()).append("\" ")
				.append(" onClick=\"javascript:{").append("return ea(this);}\" ")
				.append(title)
				.append(" >").append(t.getText()).append("</a>");
		//999999 静	style=\"position:relative;overflow-y:auto;overflow-x:auto;\"
		results.append("<DIV class=t1  noWrap ")
				.append("rn=\"").append(t.getRadioEnable()).append("\" ")//单选的情况下,是否可以单选
				.append("cc=\"").append(t.getCustomContent()).append("\" ") //自定义标题
				.append("fl=\"").append(t.getFullUrl()).append("\" ")//全路径
				.append("ft=\"").append(t.getForShort()).append("\" ")//简称
				.append("hc2=\"").append(t.getHelpCode2()).append("\" ")//助记码2
				.append("sf=\"").append(selectFunction).append("\"  ")//下拉控件使用函数
				.append("fn=\"").append(t.getFullName()).append("\" ")//全称
				.append("hy1=\"\" ")//历史记录1 用于记录选择框的历史状态
				.append("css=\""+t.getChildSelectedStatus()+"\" ")
				.append("is=\"\" ")//初始状态
				.append("gc=\"0\" ")//选择框单击次数
				.append("tc=\"0\" ")//单击标题次数
				.append("gi=\"").append(t.getGrayImg()).append("\" ")//选择框是否可以出现灰勾状态
				.append("da=\"").append(t.getDynamicAttri()).append("\" ")//动态属性
				.append("dd=\"").append(t.getDynamicLoading()).append("\" ")//是否为动态加载 子节点
				.append("ds=\"").append(t.getDefaultSelected()).append("\" ")//选择框是否默认选中
				.append("iv=\"").append(t.geInputValue()).append("\"  ")//选择框的值
				.append("cs=\"").append(cengCount).append("\" ")//节点层次数
				.append("dc=\"0\" ")//0表示未加载过子节点 同一个节点只能加载一次
				.append("md=\"").append(divId).append("\" ")//树控件的容器id
				.append("key=\"").append(t.getKey()).append("\" ")//key值
				.append("nt=\"").append(t.getText()).append("\" ")//节点标题
				.append("igi=\""+t.getIsolateGrayImg()+"\" ")//受子节点影响的灰勾
				.append("icd=\"").append(t.getCheckboxDisplay().equals("")?"1":"0").append("\" ")//是否显示这个节点的复选框
				.append(t.getCustomAttribute())
				.append(" selAll=\""+t.getChildSelAll()+"\" ")//标识子节点是否全部选中
				.append("hc=\"").append(t.getHelpCode()).append("\">")//助记码
				.append(cengImg).append(openCloseImg.toString()).append(radioOrCheckBoxHtml.toString()).append(hrefHtml.toString()).append("</div>")
				.append("<div class=t2 noWrap style=\""+t.getNodeOpen()+"\" ")
				.append(">");
		return results.toString();
	}

	/////////////////////////////////////////////////////////////////////////////
	/*
	 * 动态加载子节点
	 *
	 */
	public StringBuffer getDynamicLoadChilds(){
		StringBuffer nodesHtml=null;
		try{
			SanTreeDataSourceInterface obj = ApplicationContextUtil.getBean(this.getDataSource(), TreeObject.class);
			List list=null;

			obj.setServletContext(servletContext);
			obj.setRequest(this.request);
			obj.setUserParameter(this.getUserParameter());
			list = obj.getChildrenNodes(this.parentkey, this.parentText);
			this.treeId=0;
			if(list!=null && list.size()>0){
				//nodesHtml = dynamicChildNodes(list,this.parentkey,Integer.parseInt(this.cengCount)+1,this.divId);
				if(this.isNextNode!=null && this.isNextNode.length()>0){
					this.isNextNode=this.isNextNode.substring(0,this.isNextNode.length()-1);
				}
				nodesHtml = addDynamicNodesNew(list,this.parentkey,Integer.parseInt(this.cengCount)+1,getDivId());
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(" getTreeHtmlByServlet() 生成HTML错误 ! (" + e.getMessage() + ")");
		}

		return nodesHtml;
	}

	private StringBuffer dynamicChildNodes(List list,String parentKey,int cengCount,String divId) {
		StringBuffer NodesString=new StringBuffer();
		//int cengLast=0;
		for(int i=0;i<list.size();i++){

			SanTreeNode t1=(SanTreeNode)list.get(i);

			if(t1.getParentKey().equals(parentKey)){
				//if(t1.getDynamicLoading()==1){
				NodesString.append(getDynamicLoadingDivHtml(cengCount,divId,t1)+"</div>");
				//}else{
				//	NodesString.append(getDivHtml(cengCount,divId,t1,"no"));
				//}
			}
		}

		return NodesString;
	}


	///////动态树
	private String getDynamicLoadingDivHtml(int cengCount,String divId,SanTreeNode t){
		int id=this.getTreeId();
		String objId="";//divId+"-zzb-tree-object-"+id;
		StringBuffer cengImg=new StringBuffer("");
		cengImg.append(t.getCengImg());
		String enable="";
		if(t.getEnable()!=null && t.getEnable().equals("false")){
			enable="disabled";
		}
		//如果有设置动态加载参数,设置函数,在点击操作图片的时候执行动态加载
		String dynamicFunction="";
		if(t.getDynamicLoading()==1){
			dynamicFunction="Ldn(this);";
		}

		//设置节点函数,相关函数属性为空的情况下,赋于默认函数
		this.setFunction(t);
		StringBuffer radioOrCheckBoxHtml=new StringBuffer("");
		//设置选择框的html代码
		radioOrCheckBoxHtml.append("<img ")
				.append("onclick=\"javascript:{gic(this);").append(t.getCheckboxJs()).append(";").append(this.getCheckBoxClick()).append(";}\" ")
				.append("style=\"display:none;cursor:hand\" ")
				.append("id=\"inputImg\" ")
				.append("src=\"")
//				.append(this.contextPath)
				.append(this.imgPath).append(this.zzb_checkedTrueIcon).append("\">");
		//
		if(t.getGrayImg()!=null && t.getGrayImg().equals("1")){
			radioOrCheckBoxHtml=new StringBuffer("");
			radioOrCheckBoxHtml.append("<img ")
					.append("onclick=\"javascript:{gic(this);").append(t.getCheckboxJs()).append(";").append(this.getCheckBoxClick()).append(";}\" ")
					.append("style=\"display:black;cursor:hand\" ")
					.append("id=\"inputImg\" src=\"")
//					.append(this.contextPath)
					.append(this.imgPath).append(this.zzb_checkedTrueIcon).append("\">");
		}
		if(this.getCheckboxDisplay().equals("")){
			if(t.getCheckboxDisplay().equals("")){
				String dis="";
				if(t.getGrayImg().equals("1")){
					dis=";display:none";
				}
				String sel="0";
				if(t.getChecked().equals("checked")){
					sel="1";
				}
				radioOrCheckBoxHtml.append("<input type=\"checkbox\" onpropertychange=\"cch(this)\" ").append(enable).append(" ")
						.append("style=\"height:13px").append(dis).append("\" ")
						.append("tag=\"INPUT\" ")
						.append("name=\"").append(t.getCheckboxName()).append("\" ")
						.append(t.getChecked())
						.append(" sel=\""+sel+"\" ")
						.append(" onclick=\"cs(this,'true');").append(t.getCheckboxJs()).append(";").append(this.getCheckBoxClick()).append(";\" ")
						.append(">");
			}
		}
		StringBuffer openCloseImg=new StringBuffer("");
		openCloseImg.append("<img open=\"no\" ")
				.append("onclick=\"orc(this);").append(dynamicFunction).append(this.getImgClick()).append(";\" ")
				.append(" style=\"cursor:hand;display:\" id=\"").append(objId).append("plus\" ")
				.append("src=\"")
//				.append(this.contextPath)
				.append(this.imgPath).append(t.getNodeOperateImg()).append("\"/>");
		StringBuffer hrefHtml=new StringBuffer();
		//设置标题提示
		String title="";
		if(t.getTitleTip()!=null && !t.getTitleTip().equals("")){
			title=" title=\""+t.getTitleTip()+"\" ";
		}else{
			title=" title=\""+t.getText()+"\" ";
		}
		hrefHtml.append("<a href=\"").append(t.getHref()).append("\" ")//title=\""+t.getText()+"\" ")
				.append(" onfocus=\"nf(this);\" ondblclick=\"ej(this);")
				.append(this.getDblonclick()).append("\" jsf=\"")
				.append(t.getJsFunction()).append("\" ")
				.append(title)
				.append(" onClick=\"")
				.append(";return ea(this);\">")
				.append(t.getText())
				.append("</a>");
		//设置下拉控件需要的函数
		StringBuffer selectFunction=new StringBuffer("");
		selectFunction.append(this.getImgClick().equals("")?"no":this.getImgClick()).append("#")
				.append(this.getAClick().equals("")?"no":this.getAClick()).append("#")
				.append(this.getCheckBoxClick().equals("")?"no":this.getCheckBoxClick());
		StringBuffer childHtml=new StringBuffer("");
		if(t.getDynamicLoading()==1){//如果是动态加载
			childHtml.append(getDynamicLoadingChildHtml(cengCount));
		}
		//999999 动
		StringBuffer results=new StringBuffer();
		results.append("<DIV class=t1 noWrap ")
				.append("rn=\"").append(t.getRadioEnable()).append("\" ")//单选的情况下,是否可以单选
				.append("cc=\"").append(t.getCustomContent()).append("\" ")//自定义标题
				.append("fl=\"").append(t.getFullUrl()).append("\" ")//全路径
				.append("ft=\"").append(t.getForShort()).append("\" ")//简称
				.append("hc2=\"").append(t.getHelpCode2()).append("\" ")//助记码2
				.append("sf=\"").append(selectFunction).append("\"  ")//下拉控件使用函数
				.append("fn=\"").append(t.getFullName()).append("\" ")//全称
				.append("hy1=\"\" ")//历史记录1 用于记录选择框的历史状态
				.append("is=\"\" ")
				.append(" css=\""+t.getChildSelectedStatus()+"\" ")
				//.append("contextPath=\"").append(this.contextPath).append("\" ")//系统路径
				//.append("radioOrCheckbox=\"").append(this.radioOrCheckbox).append("\" ")//是单选还是复选
				//.append("parentChildIsolate=\"").append(this.parentChildIsolate).append("\" ")//在单击选择框的时候 是否可以父子之间相互影响
				//.append("parentCheckboxEnable=\"").append(this.parentCheckboxEnable).append("\" ")//在多选的情况下,父节点是否可选
				//.append("parentRadioEnable=\"").append(this.parentRadioEnable).append("\" ")//在单选的情况,父节点的选择框是否可选
				//.append("checkboxDisplay=\"").append(this.getCheckboxDisplay()).append("\" ")//是否显示选择框
				//.append("dataSource=\"").append(this.dataSource).append("\" ")//数据源全路径
				.append("gc=\"0\" ")//用于灰勾的情况下 选择框单击次数
				.append("gi=\"").append(t.getGrayImg()).append("\" ")//选择框是否可以出现灰勾状态
				.append("da=\"").append(t.getDynamicAttri()).append("\"  ")//动态属性
				.append("tc=\"0\" ")//单击标题次数
				.append("ds=\"").append(t.getDefaultSelected()).append("\" ")//选择框是否默认选中
				.append("iv=\""+t.geInputValue()).append("\"   ")//选择框的值
				.append("cs=\"").append(cengCount).append("\" ")//节点层次数
				.append("dc=\"0\" ")//动态加载次数
				.append("dd=\"").append(t.getDynamicLoading()).append("\" ")//是否为动态加载节点 1是 0否
				.append("md=\"").append(divId).append("\" ")//树控件的容器id
				.append("key=\"").append(t.getKey()).append("\" ")//key值
				.append("nt=\"").append(t.getText()).append("\" ")//节点标题
				.append("igi=\"").append(t.getIsolateGrayImg()).append("\" ")////受子节点影响的灰勾
				.append("icd=\"").append(t.getCheckboxDisplay().equals("")?"1":"0").append("\" ")//是否显示这个节点的复选框
				.append(" selAll=\""+t.getChildSelAll()+"\" ")//标识子节点是否全部选中
				.append("").append(t.getCustomAttribute()).append(" ")
				.append("hc=\"").append(t.getHelpCode()).append("\">\n")//助记码
				.append(cengImg.toString()).append(openCloseImg.toString()).append(radioOrCheckBoxHtml.toString()).append(hrefHtml).append("</div>")
				.append("<div id=\"").append(objId).append("cont\" class=t2 ")
				.append("style=\""+t.getNodeOpen()+"\"")

				.append(" cct=\"1\">")//标识子节点存放区是否有值 1 有 0没有

				.append(childHtml.toString()).append("");


		return results.toString();
	}

	private String getDynamicLoadingChildHtml(int cengCount){
		int id=this.getTreeId();
		String objId=divId+"-zzb-tree-object-"+id;
		StringBuffer cengImg=new StringBuffer("");
		int i=cengCount+1;

		while(i>1){
			cengImg.append("<img id=\"").append(objId).append("-indent-").append(cengCount).append("\" ")
					.append("src=\"")
//					.append(this.contextPath)
					.append(this.imgPath).append(this.zzb_nullIcon).append("\">");
			i--;
		}

		String hrefHtml="<a style=\"cursor:hand;\">Loading.....</a>";
		StringBuffer openCloseImg=new StringBuffer();
		openCloseImg.append("<img open=\"no\"  onclick=\"orc(this)\" ")
				.append("  style=\"cursor:hand;\" id=\"").append(objId).append("plus\" ")
				.append("src=\"")
//				.append(this.contextPath)
				.append(this.imgPath).append(this.zzb_openIcon).append("\"/>");
		StringBuffer results=new StringBuffer("");
		results.append("<DIV noWrap id=\"").append(objId).append("\"")
//				"defaultSelected=\"\"  class=\"t1\" ")
//				.append("inputValue=\"\" ")
//				.append("nodeStatus=\"0\" ")
//				.append("customContent=\"\" ")
//				.append("fullUrl=\"\" ")
//				.append("forShort=\"\" ")
//				.append("helpCode2=\"\" ")
//				.append("selectFunction=\"\"  ")
//				.append("fullName=\"\" ")
//				.append("history1=\"\" checkboxDefault=\"\" ")
//				.append("contextPath=\"\" ")
//				.append("parentChildIsolate=\"\" ")
//				.append("parentCheckboxEnable=\"\" ")
//				.append("parentRadioEnable=\"\" ")
//				.append("checkboxDisplay=\"\" ")
//				.append("dataSource=\"\" ")
//				.append("checkboxClick=\"0\" grayImg=\"\" ")
//				.append("dynamicAttri=\"\"  ")
//				.append("clickCount=\"0\" ")
//				.append("defaultSelected=\"\" ")
//				.append("inputValue=\"\"   ")
//				.append("childCont=\"yes\" ")
//				.append("cengCount=\"").append(i).append("\" ")
//				.append("dynamicLoadCount=\"0\" ")
//				.append("DynamicLoad=\"\" ")
//				.append("MainDivId=\""+divId+"\" ")
//				.append("key=\"\" ")
//				.append("nodeText=\"\" ")
//				.append("helpCode=\"\" ")
				.append(">\n")
				.append(cengImg).append(openCloseImg).append(hrefHtml).append("</div>")
				.append("<div class=t2 style=\"display:none\" ")
				.append("cct=\"\" ></div>");
		return results.toString();
	}

	private StringBuffer addDynamicNodesNew(List<SanTreeNode> list,String parentKey,int cengCount,String divId) {
		Node root = new Node(null,null);
		//root.parentNode=new Node(null,null);
		// find root 查找根节点
		for(int i=list.size() -1; i>=0; i--){
			SanTreeNode stn = list.get(i);
			if (StringUtils.equals(stn.getParentKey(), parentKey)){
				Node node=new Node(stn,root);
				node.cengCount=cengCount;
				root.children.add(0,node);
				list.remove(i);
			}
		}

		this.initNode(root,cengCount);

		StringBuffer sb = new StringBuffer();
		for(int i=0; i<root.children.size(); i++){
			this.toHtml(root.children.get(i), cengCount, divId, sb);
		}
		return sb;
	}

	//
	private StringBuffer addNodesNew(List<SanTreeNode> list,String parentKey,int cengCount,String divId) {
		Node root = new Node(null,new Node(null,null));
		// find root 查找根节点
		for(int i=list.size() -1; i>=0; i--){
			SanTreeNode stn = list.get(i);
			if (StringUtils.equals(stn.getParentKey(), "")){
				root.children.add(0, new Node(stn,null));
				list.remove(i);
			}
		}
		if(root.children.size()<=0){
			for(int i=list.size() -1; i>=0; i--){
				SanTreeNode stn = list.get(i);
				if (StringUtils.equals(stn.getParentKey(), "0")){
					root.children.add(0, new Node(stn,null));
					list.remove(i);
				}
			}
		}
		// find other nodes
		//查找根节点下的所有子节点，包括子节点的子节点
		this.findChildrenNode(root, list);
		this.initNode(root,1);
		//this.initLine(root);
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<root.children.size(); i++){
			this.toHtml(root.children.get(i), cengCount, divId, sb);
		}
		//System.out.println(System.currentTimeMillis());
		return sb;
	}
	private void toHtml(Node node, int cengCount,String divId, StringBuffer sb) {
		String hasChildNode = "0";
		if (node.children.size() > 0){
			hasChildNode = "1";
		}

		if(node.content.getDynamicLoading()==1){
			sb.append(getDynamicLoadingDivHtml(cengCount, divId, node.content));
		}else{
			sb.append(getDivHtml(cengCount, divId, node.content, hasChildNode));
		}

		for(int i=0; i<node.children.size(); i++){
			this.toHtml(node.children.get(i), cengCount + 1, divId, sb);
		}

		sb.append("</div>");
	}
	private void findChildrenNode(Node root, List<SanTreeNode> list){
		if (list.size() > 0){
			for(int i=0; i<root.children.size(); i++){
				Node node = root.children.get(i);
				for(int j=list.size() -1; j>=0; j--){
					SanTreeNode stn = list.get(j);
					if (StringUtils.equals(stn.getParentKey(), node.content.getKey())){
						node.children.add(0, new Node(stn,node));
						list.remove(j);
					}
				}
				//find other node.
				findChildrenNode(node, list);
			}
		}
	}
	class Node{
		public SanTreeNode content = null;
		public Node parentNode=null;
		public Node upNode=null;
		public Node NextNode=null;
		public int cengCount=0;
		public String position="";//在同级中所属的位置
		public int index=0;//在同级中所属的索引,第一个从1开始
		public ArrayList<Node> children = new ArrayList<Node>();
		public Node(SanTreeNode node,Node parentNode){
			this.content = node;
			this.parentNode = parentNode;
		}
	}

	private int isSetCurrent=0;//标识是否已经计算当前节点
	private void getCurrentNodeNavigation(Node node){
		String nodeNavigation="";
		nodeNavigation=node.index+"";
		Node parentNode=node.parentNode;
		while(parentNode!=null){
			nodeNavigation=parentNode.index+","+nodeNavigation;
			parentNode=parentNode.parentNode;
		}
		this.currentNodeNavigation=nodeNavigation;
		//System.out.println("当前节点是::::::" + node.content.getText() + "  nodeNavigation:" + nodeNavigation);
	}
	/*
	 * 初始化树节点
	 */
	private void initNode(Node root,int cengCout){
		//System.out.println("< ................................................................................................... >");
		for(int i=0;i<root.children.size();i++){
			Node node=(Node)root.children.get(i);
			node.index=i+1;
			//设置层次数
			node.cengCount=cengCout;
			///////设置节点在同级中的位置//////////////
			String position="";
			if(i==root.children.size()-1){
				node.position="last";
			}else if(i==0){
				node.position="first";
			}
			//同级节点中不是第一个节点或者不是最后一个节点时
			if(i!=0){
				node.upNode=root.children.get(i-1);
			}
			if(i!=root.children.size()-1){
				node.NextNode=root.children.get(i+1);
			}
			//////////////////////////////////////
			//设置默认展开的节点
			if(node.content.getDefaultSelected().equals("1")){
				if(node.content.getDynamicLoading()!=1){//如果不是动态载的情况下，才可以展开
					if(!this.getExpandByCheckbox().equals("0")){
						node.content.setNodeOpen("display:");
						node.content.setOpen("yes");
						OpenParentNode(node,position);
					}

				}
				node.content.setChecked("checked");
			}
			//设置当前节点
			if(isSetCurrent==0 && node.content.getIsCurrentNode().equals("true")){
				///System.out.println("设置当前节点...............................");
				this.getCurrentNodeNavigation(node);
				isSetCurrent=1;
			}

			//设置节点操作图片
			setOperateImg(node);
			//设置层次线
			if(this.isNextNode!=null && this.isNextNode.length()>0){//动态加载的节点
				this.setDynamicLineImg(node);
			}else{
				this.setLineImg(node);
			}
			if(node.children.size()>0){
				initNode(node,cengCout+1);
			}
		}
	}

	private void OpenParentNode(Node node,String position){
		Node parentNode=node.parentNode;
		if(parentNode!=null && parentNode.content!=null){
			parentNode.content.setNodeOpen("display:");
			parentNode.content.setOpen("yes");
			OpenParentNode(parentNode,position);
			setOperateImg(parentNode);
		}
	}
	//设置操作图片
	private void setOperateImg(Node node){
		if(node.content.getDynamicLoading()==1){//是动态节点的时候
			if(node.parentNode==null){
				//没有子节点
				//zzb_topOpenIcon
				if(node.position.equals("first")){
					node.content.setNodeOperateImg(this.zzb_topOpenIcon);
				}else if(node.position.equals("last")){
					if(node.upNode==null){
						node.content.setNodeOperateImg(this.zzb_topOpenIcon);
					}else{
						node.content.setNodeOperateImg(this.zzb_openIcon);
					}
				}else{
					node.content.setNodeOperateImg(this.zzb_openIcon);
				}
			}else{
				node.content.setNodeOperateImg(this.zzb_openIcon);
			}

		}else{//是静态节点的情况下
			if(node.children.size()==0 && node.parentNode!=null){//没有子节点，又不是顶层节点
				if(node.content.getDynamicLoading()==1){
					node.content.setNodeOperateImg(this.zzb_openIcon);
				}else{
					if(node.position.equals("last")){
						node.content.setNodeOperateImg(this.zzb_lastChildIcon);
					}else{
						node.content.setNodeOperateImg(this.zzb_noIcon);
					}
				}
			}else if(node.children.size()>0 && node.parentNode!=null){//有子节点，又不是顶层节点
				if(node.content.getOpen().equals("yes")){
					node.content.setNodeOperateImg(this.zzb_closeIcon);
				}else{
					node.content.setNodeOperateImg(this.zzb_openIcon);
				}
			}
			/////////处理第一级根节点
			if(node.parentNode==null){//顶层节点
				//没有子节点
				if(node.children.size()==0){
					if(node.position.equals("first")){
						node.content.setNodeOperateImg(this.zzb_topNoNodeIcon);
					}else if(node.position.equals("last")){
						node.content.setNodeOperateImg(this.zzb_lastChildIcon);
					}else{
						node.content.setNodeOperateImg(this.zzb_noIcon);
					}
				}else{//有子节点的情况
					if(node.position.equals("first")){
						if(node.content.getOpen().equals("yes")){
							node.content.setNodeOperateImg(this.zzb_topCloseIcon);
						}else{
							node.content.setNodeOperateImg(this.zzb_topOpenIcon);
						}
					}else{
						//如果是第最后一个节点，并且同级只有这个节点时
						if(node.position.equals("last") && node.upNode==null){
							if(node.content.getOpen().equals("yes")){
								node.content.setNodeOperateImg(this.zzb_topCloseIcon);
							}else{
								node.content.setNodeOperateImg(this.zzb_topOpenIcon);
							}
						}else{
							if(node.content.getOpen().equals("yes")){
								node.content.setNodeOperateImg(this.zzb_closeIcon);
							}else{
								node.content.setNodeOperateImg(this.zzb_openIcon);
							}
						}
					}
				}
			}
		}
	}
	//设置线条
	private void setLineImg(Node node){
		int cengCount=node.cengCount;
		String cengImg=new String();
		Node parentNode=node.parentNode;
		while(parentNode!=null){
			//如果父点有兄弟节点 加线图片 没有加空图片
			if(parentNode.NextNode==null){
				cengImg="<img id=\"indent\" src=\""+this.imgPath+this.zzb_nullIcon+" \" />"+cengImg;
			}else{
				cengImg="<img id=\"indent\" src=\""+this.imgPath+this.zzb_lineIcon+" \" />"+cengImg;
			}
			parentNode=parentNode.parentNode;

		}
		node.content.setCengImg(cengImg.toString());
	}

	//动态加载设置线条
	private void setDynamicLineImg(Node node){
		//System.out.println("设置层次图片.........");
		String[] isNextNodesArr=this.isNextNode.split(":");
		int cengCount=node.cengCount;
		StringBuffer cengImg=new StringBuffer("");
		Node parentNode=node.parentNode;
		for(int i=(isNextNodesArr.length-1);i>=0;i--){
			if(isNextNodesArr[i].equals("yes")){
				cengImg.append("<img id=\"indent\" src=\""+this.imgPath+this.zzb_lineIcon+" \" />");
			}else{
				cengImg.append("<img id=\"indent\" src=\""+this.imgPath+this.zzb_nullIcon+" \" />");
			}
		}
		node.content.setCengImg(cengImg.toString());
	}
	private void initLine(Node root){
		for(int i=0;i<root.children.size();i++){
			Node node=(Node)root.children.get(i);

			//设置层次线
			setLineImg(node);
			if(node.children.size()>0){
				initLine(node);
			}
		}
	}

	public String getIsNextNode() {
		return isNextNode;
	}

	public void setIsNextNode(String isNextNode) {
		this.isNextNode = isNextNode;
	}

	public String getAuto_dynamic_load() {
		return auto_dynamic_load;
	}

	public void setAuto_dynamic_load(String auto_dynamic_load) {
		this.auto_dynamic_load = auto_dynamic_load;
	}

	public String getFirstSelectedByTitle() {
		return firstSelectedByTitle;
	}

	public void setFirstSelectedByTitle(String firstSelectedByTitle) {
		this.firstSelectedByTitle = firstSelectedByTitle;
	}

	public String getSelectedTitleByCheckbox() {
		return selectedTitleByCheckbox;
	}

	public void setSelectedTitleByCheckbox(String selectedTitleByCheckbox) {
		this.selectedTitleByCheckbox = selectedTitleByCheckbox;
	}

	public String getOpenOnclick() {
		return openOnclick;
	}

	public void setOpenOnclick(String openOnclick) {
		this.openOnclick = openOnclick;
	}

	public String getParentSelectedWithChild() {
		return parentSelectedWithChild;
	}

	public void setParentSelectedWithChild(String parentSelectedWithChild) {
		this.parentSelectedWithChild = parentSelectedWithChild;
	}

	public String getParentClearWithChild() {
		return parentClearWithChild;
	}

	public void setParentClearWithChild(String parentClearWithChild) {
		this.parentClearWithChild = parentClearWithChild;
	}

	public String getLowerSelectedAllWithSuperior() {
		return lowerSelectedAllWithSuperior;
	}

	public void setLowerSelectedAllWithSuperior(String lowerSelectedAllWithSuperior) {
		this.lowerSelectedAllWithSuperior = lowerSelectedAllWithSuperior;
	}

	public String getLowerClearWithSuperior() {
		return lowerClearWithSuperior;
	}

	public void setLowerClearWithSuperior(String lowerClearWithSuperior) {
		this.lowerClearWithSuperior = lowerClearWithSuperior;
	}

	public String getLowerClearAllWithSuperior() {
		return lowerClearAllWithSuperior;
	}

	public void setLowerClearAllWithSuperior(String lowerClearAllWithSuperior) {
		this.lowerClearAllWithSuperior = lowerClearAllWithSuperior;
	}

	public String getLowerSelectedWithSuperior() {
		return lowerSelectedWithSuperior;
	}

	public void setLowerSelectedWithSuperior(String lowerSelectedWithSuperior) {
		this.lowerSelectedWithSuperior = lowerSelectedWithSuperior;
	}

}

