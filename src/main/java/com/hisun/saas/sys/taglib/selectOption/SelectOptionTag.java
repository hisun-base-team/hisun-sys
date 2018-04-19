/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.selectOption;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;


public final class SelectOptionTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * formName是取数据的form
	 * mainField 当需要从子表读取数据时则需要传入此参数 （用于多选 且需要取子表数据） 如果是单选或多选但不需要取子表数据 则不需要实现
	 * fieldCode 编码存储字段
	 * fieldText 文字存储字段
	 */
	private String formName;//form的名字
	private String mainField;//如果是是需要取子表的数据 需要传入子表的集合
	private String collectMeans;//用于收集子表数据的方法 （即字表的vo名字 必须在FORM里面实现收集子表数据的方法）还必须注意 在修改的时候子表的内容必须又二次开发者清空
	private String fieldCode = null; // 编码存储字段
	private String fieldText = null; // 文字存储字段


	private String fieldDisplay = null; // 对应上级表的显示内容（用于快速显示子表数据的组合内容）存储字段的值
	/**
	 * 字典类别，可以被dataSource类使用来读取需要的字典类别信息
	 */
	private String dictionaryType = null;

	private String id;//显示层的id  (必填项)

	private String dataSource;//实现SanTreeDataSourceInterface接口的类  (必填项)

	private String contextPath;//系统根目录路径  (必填项)

	private String checkboxDisplay;//是否显示复选框 yes显示 none隐藏

	private String radioOrCheckbox; //单选还是复选 radio单选 checkbox复选

	private String parentRadioEnable;//单选情况下父节点是否可选 true可选 false不可选

	private String parentCheckboxEnable;//复选情况下父节点是否可选 true可选 false不可选

	private String parentChildIsolate;// 设置为 true 选择父节点时子节点全部选中,取消时全部取消 子节点部分选中时，父节点选中变灰（灰勾） 设置为 false 父节点子和子节点可以分开选中，相互不影响


	private String userParameter;//现在用于多级树查询时的记录查询条件

	//private String is

	private String isshowtree;//是否显示多级树  yes为显示  no为显示一级的下拉选择项 （默认为只有一级的下拉  no）

	private String staticdata;//yes为动态查询树数据  no为静态查询树数据（只对多级树有效 默认为静态树查询 no）

	private String matchingsetup;//matchingsetup表示需要查询的状态 0表示不去匹配 1表示完全匹配  2表示模糊匹配  一共7个 分别是 节点名，key，助记码1,助记码2，全称,简称,全路径;

	private String selectvaluetype;//选择后显示的内容是全称还是显示内容 0为显示内容 1为全称 （默认为0）

	private String width;//下拉文本框的宽度 （默认为200）

	private String divmaxwidth;//有多级树的时候的出现滚动条的最大的宽度 （默认为300）

	private String divmaxheight;//层的最大的高度  （默认为300）

	private String defaultvalues;//默认的值 要有多个必须和key的内容对应  用,分开

	private String defaultkeys;//默认的知道的相应key值 要有多个必须和值的内容对应  用,分开

	private String isDisabled;//设置是否是能用  false为能用  true为不能用

	private String dictionaryEdit;//是否在下面显示字典维护的按钮  默认为false不显示  如果是ture则显示该按钮

	private String dictionaryEditFunc;//字典维护链接的方法

	private String buttonstyle;//下面按钮的样式导入

	private String buttoncss;//下面按钮的css导入

	private String isEnterQuery;//是否按回车查询 还是延迟查询 默认为false 延迟查询 为延迟加载时  按回车是失去焦点的事件  为true时按回车查询

	private String textFontColor;//文本框字体颜色

	private String textFontSize;//文本框字体大小

	private String fontColor;//字体颜色

	private String fontSize;//字体大小

	private String divBackgroundColor;//选择项层的背景颜色

	private String onmousemovecolor;//鼠标移到选项的值的颜色

	private String equalcolor;//查询完全命中的颜色

	private String myFunc;//用户最后需要调用的方法

	private String isAddNewData;//是否允许录入没完全匹配到的数据 true为可以录入 false为不能录入

	private Style style;//内部使用的样式 用于构造传入到GzzzbSelectOption

	private int childSize=0;//用于接收多选有需要读取子表的时候的子表记录条数  用于修改(内部使用)

	private String showWay;//定义显示层是显示在上面还是显示在下面 默认为bottom  往下显示   往上显示为top

	private String allownull;//暫時是依托GZZZB系统验证框架 验证是否允许为空 allownull="false" 为不允许为空  为true为允许为空

	private String des;//暫時是依托GZZZB系统验证框架 用于得到要提示的信息

	private String allowClear;//单选是否显示空格  用于清空  false为不需要显示空格 true为需要显示空格(仅单级单选)

	//	是否有动态加载数据源 默认为没有动态数据源 false/没动态加载数据源 true/有动态加载数据源（单级的才有效果 主要用于联动 需要动态实现数据源）刘燕的需求
	private String isDynamicLoadDataSource;

	private String chooseType;//用于提供是否需要父子影响  没灰勾的  0为不影响 1为影响 (最先为权限提供) 暂时只支持静态的

	private String useType;//用于标识是录入类型的还是查询类型的  add为录入类型的  query为查询类型的

	private String hidden;

	private String isSpecialWidthType;//是否特殊的宽度 如果为true表示必须传入的width参数必须是数字 生成的文本框宽度为传入宽度减去图片宽度 如果为false则文本框直接设置宽度为100%

	private String parentSelectedWithChild;//配置父节点复选框选中时，子节点复选框是否全部选中;值为true表示父节复选框选中时，子节点复选框全部选中;值为false表示父节点复选框选中，子节点保持原有状态不变;

	private String parentClearWithChild;//配置父节点复选框取消时，子节点复选框是否全部取消;值为true表示父节点复选框取消时，子节点复选框全部取消;值为false表示父节点复选框取消时，子节点复选框保持原有状态不变;

	private String lowerSelectedAllWithSuperior;//配置下级节点复选框全部选中时，父节点复选框是否选中;值为true表示下级节点复选框全部选中时，父节点复选框选中;值为false表示下级节点复选框全部选中时，父节点复选框保持原有状态不变;

	private String lowerClearWithSuperior;//配置下级节点复选框取消时，父节点复选框是否取消;值为true表示下级节点取消时，父节点复选框取消;值为false表示下级节点复选框取消时，父节点复选框保持原有状态不变;

	private String lowerClearAllWithSuperior;//配置下级节点复选框全部取消时，父节点复选框是否取消;值为true表示下级节点复选框全部取消时，父节点复选框取消;值为false表示下级节点复选框全部取消时，父节点复选框保持原有状态不变;

	//失去焦点事件
	private String onblur;//失去焦点事件
	//改变事件
	private String onchange;//改变事件
	//得到焦点事件
	private String onfocus;//得到焦点事件
	//单击事件
	private String onclick;//单击事件
	//按下键盘事件
	private String onkeydown;//按下键盘事件

	private String Onkeyup; //当用户释放键盘按键时触发
	//提示标题
	private String title;//提示标题

	private String tabindex;//设置或获取定义对象的 Tab 顺序的索引

	private String onmousemove;

	private String onmouseout;

	private String value;

	private String textClass;//文本框样式

	private String moreSelectSearch; // yes为显示多选的search栏

	private String moreSelectAll; // yes为显示多选的全选

	SelectOption select=null;
	public String getIsSpecialWidthType() {
		return isSpecialWidthType;
	}
	public void setIsSpecialWidthType(String isSpecialWidthType) {
		this.isSpecialWidthType = isSpecialWidthType;
	}
	//初始化数据
	public SelectOptionTag() {
		super();
		formName="";
		mainField="";
		collectMeans="";
		fieldCode="";
		fieldText="";
		id="select_id";
		dataSource="";
		contextPath="";
		checkboxDisplay="none";
		radioOrCheckbox="radio";//
		parentRadioEnable="true";
		parentCheckboxEnable="true";
		parentChildIsolate="false";
		userParameter="";
		isshowtree="no";
		staticdata="no";
		matchingsetup="2,1,0,0,2,0,0";
		selectvaluetype="0";
		width="200";
		divmaxwidth="300";//初始化最大宽度为300
		divmaxheight="300";//初始化最大高度为300
		defaultvalues="";
		defaultkeys="";
		isDisabled="false";
		dictionaryEdit="false";
		dictionaryEditFunc="";
		buttonstyle="";
		buttoncss="";
		isEnterQuery="false";
		textFontColor="";
		textFontSize="";
		fontColor="";
		fontSize="";
		divBackgroundColor="";
		onmousemovecolor="";
		equalcolor="";
		myFunc="";
		isAddNewData="false";
		showWay="bottom";
		allownull="true";
		des="";
		allowClear="true";
		isDynamicLoadDataSource="false";
		chooseType="0";
		useType="add";
		hidden = "false";
		isSpecialWidthType="false";

		parentSelectedWithChild = "true";
		parentClearWithChild = "true";
		lowerSelectedAllWithSuperior = "true";
		lowerClearWithSuperior = "false";
		lowerClearAllWithSuperior = "true";
		textClass = "span8 m-wrap";
//		moreSelectSearch = "no";
//		moreSelectAll = "no";
	}
	/**
	 * 初始化下拉的数据
	 *
	 */
	private void initSelectOption(){
		select.setButtoncss(buttoncss);
		select.setButtonstyle(buttonstyle);
		select.setCheckboxDisplay(checkboxDisplay);

		select.setContextPath("");
		select.setDataSource(this.getDataSource());
		select.setDictionaryEdit(dictionaryEdit);
		select.setDictionaryEditFunc(dictionaryEditFunc);
		select.setDictionaryType(dictionaryType);
		select.setDivBackgroundColor(divBackgroundColor);
		select.setDivmaxheight(divmaxheight);
		select.setDivmaxwidth(divmaxwidth);
		select.setEqualcolor(equalcolor);
		select.setFontColor(fontColor);
		select.setFontSize(fontSize);
		select.setId(id);
		select.setIsAddNewData(isAddNewData);
		select.setIsEnterQuery(isEnterQuery);
		select.setIsshowtree(isshowtree);
		select.setMatchingsetup(matchingsetup);
		select.setMyFunc(myFunc);
		if(this.getOnblur()!=null)
			select.setOnblur(this.getOnblur());
		if(this.getOnchange()!=null)
			select.setOnchange(this.getOnchange());
		if(this.getOnclick()!=null)
			select.setOnclick(this.getOnclick());
		if(this.getOnfocus()!=null)
			select.setOnfocus(this.getOnfocus());
		if(this.getOnkeydown()!=null)
			select.setOnkeydown(this.getOnkeydown());
		if(this.getOnkeyup()!=null)
			select.setOnkeyup(this.getOnkeyup());
		select.setOnMouseMoveColor(onmousemovecolor);
		select.setParentCheckboxEnable(parentCheckboxEnable);
		select.setParentChildIsolate(parentChildIsolate);
		select.setParentRadioEnable(parentRadioEnable);
		select.setRadioOrCheckbox(radioOrCheckbox);
		select.setSelectValueType(selectvaluetype);
		select.setStaticdata(staticdata);
		if(this.getTabindex()!=null)
			select.setTabindex(this.getTabindex());
		select.setTextFontColor(textFontColor);
		select.setTextFontSize(textFontSize);
		if(this.getTitle()!=null)
			select.setTitle(this.getTitle());
		select.setUserParameter(userParameter);
		select.setValue(value);
		select.setWidth(width);
		select.setShowWay(showWay);
		select.setAllownull(allownull);
		select.setDes(des);
		select.setAllowClear(allowClear);
		select.setIsDynamicLoadDataSource(isDynamicLoadDataSource);
		select.setChooseType(chooseType);
		select.setUseType(useType);
		select.setHidden(hidden);
		select.setOnmousemove(this.getOnmousemove());
		select.setOnmouseout(this.getOnmouseout());
		select.setIsSpecialWidthType(isSpecialWidthType);
		select.setParentSelectedWithChild(parentSelectedWithChild);
		select.setParentClearWithChild(parentClearWithChild);
		select.setLowerSelectedAllWithSuperior(lowerSelectedAllWithSuperior);
		select.setLowerClearWithSuperior(lowerClearWithSuperior);
		select.setLowerClearAllWithSuperior(lowerClearAllWithSuperior);
		select.setTextClass(textClass);
		select.setMoreSelectAll(moreSelectAll);
		select.setMoreSelectSearch(moreSelectSearch);
		//select.setStyle(style);
		getInitData();

	}

	/**
	 * 取得初始数据
	 *
	 */
	public void getInitData(){
		try {
			String keys = "";
			String values="";
//			if(formName!=null&&!formName.equals("")) {
//				if(mainField!=null&&!mainField.equals("")){//子表查询数据
//					Object value
//							= RequestUtils.lookup(pageContext,formName,mainField, null);
//					Collection list=(Collection)value;
//					if(list!=null){
//						Iterator it=list.iterator();
//						childSize=list.size();
//						while(it.hasNext()){
//							Object object=(Object)it.next();
//							Class beanClass = Class.forName(object.getClass().getName());
//							for(Method method : beanClass.getMethods()){
//								String text="";
//								String key="";
//								if(method.getName().equalsIgnoreCase("get" + fieldText) || method.getName().equalsIgnoreCase("is" + fieldText)){
//									text = (String)method.invoke(object, null);
//									//break;
//								}
//								if(method.getName().equalsIgnoreCase("get" + fieldCode) || method.getName().equalsIgnoreCase("is" + fieldCode)){
//									key = (String)method.invoke(object, null);
//									//break;
//								}
//								if(key!=null){
//									if(!key.equals("")){
//										if(keys.equals(""))
//											keys=key;
//										else
//											keys=keys+","+key;
//									}
//								}
//								if(text!=null){
//									if(!text.equals("")){
//										if(values.equals(""))
//											values=text;
//										else
//											values=values+","+text;
//									}
//								}
//							}
//						}
//					}
//				}else{//查询单表的记录
//					if(fieldCode!=null&&!fieldCode.equals("")){
//						Object keyobj
//								= RequestUtils.lookup(pageContext,formName,fieldCode, null);
//						if(keyobj!=null)
//							keys=keyobj.toString();
//					}
//					if(fieldText!=null&&!fieldText.equals("")){
//						Object valueobj
//								= RequestUtils.lookup(pageContext,formName,fieldText, null);
//						if(valueobj!=null)
//							values=valueobj.toString();
//					}
//				}
//			}
			if(values==null){
				values="";
			}
			if(keys==null){
				keys="";
			}
			if(!keys.equals("")){
				this.defaultkeys=keys;
				//this.defaultvalues=values;
			}
			if(!values.equals("")){
				//this.defaultkeys=keys;
				this.defaultvalues=values;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		try{
			//调用控件前先初始化要显示层内内容为空

			StringBuffer results = new StringBuffer("");
			select=new SelectOption();

			//由于不同的系统使用的contextPath不一样 需要修改contextPath为取得的contextPath
			this.contextPath = "";

			initSelectOption();


			try {
//			CurrentUserAccountInterface userinfo  = SystemHelper.getLogin().getLoginer((HttpServletRequest) pageContext.getRequest());
				String unitCode="";  //当前用户所在的单位
//			if(userinfo!= null){
//				unitCode = userinfo.getUnitCode();
//			}
				results.append(select.toOptionHtml(this.getContextPath(), this.id, this.defaultkeys,this.defaultvalues,
						this.isDisabled.equals("false")?true:false, true,"tag",formName,collectMeans,mainField,fieldCode,fieldText,childSize,unitCode));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.pageContext.getOut().print(results.toString());
		} catch (Exception e) {
			throw new JspTagException(e.getMessage());
		}
		return SKIP_BODY;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCheckboxDisplay() {
		return checkboxDisplay;
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
	public String getDivmaxheight() {
		return divmaxheight;
	}
	public void setDivmaxheight(String divmaxheight) {
		this.divmaxheight = divmaxheight;
	}
	public String getDivmaxwidth() {
		return divmaxwidth;
	}
	public void setDivmaxwidth(String divmaxwidth) {
		this.divmaxwidth = divmaxwidth;
	}
	public String getIsshowtree() {
		return isshowtree;
	}
	public void setIsshowtree(String isshowtree) {
		this.isshowtree = isshowtree;
	}
	public String getMatchingsetup() {
		return matchingsetup;
	}
	public void setMatchingsetup(String matchingsetup) {
		this.matchingsetup = matchingsetup;
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
	public String getSelectvaluetype() {
		return selectvaluetype;
	}
	public void setSelectvaluetype(String selectvaluetype) {
		this.selectvaluetype = selectvaluetype;
	}
	public String getStaticdata() {
		return staticdata;
	}
	public void setStaticdata(String staticdata) {
		this.staticdata = staticdata;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getUserParameter() {
		return userParameter;
	}
	public void setUserParameter(String userParameter) {
		this.userParameter = userParameter;
	}
	public String getDefaultkeys() {
		return defaultkeys;
	}
	public void setDefaultkeys(String defaultkeys) {
		this.defaultkeys = defaultkeys;
	}
	public String getDefaultvalues() {
		return defaultvalues;
	}
	public void setDefaultvalues(String defaultvalues) {
		this.defaultvalues = defaultvalues;
	}
	public String getButtoncss() {
		return buttoncss;
	}
	public void setButtoncss(String buttoncss) {
		this.buttoncss = buttoncss;
	}
	public String getButtonstyle() {
		return buttonstyle;
	}
	public void setButtonstyle(String buttonstyle) {
		this.buttonstyle = buttonstyle;
	}
	public String getDictionaryEdit() {
		return dictionaryEdit;
	}
	public void setDictionaryEdit(String dictionaryEdit) {
		this.dictionaryEdit = dictionaryEdit;
	}

	public String getDictionaryEditFunc() {
		return dictionaryEditFunc;
	}
	public void setDictionaryEditFunc(String dictionaryEditFunc) {
		this.dictionaryEditFunc = dictionaryEditFunc;
	}
	public String getIsDisabled() {
		return this.isDisabled;
	}
	public String getIsEnterQuery() {
		return isEnterQuery;
	}
	public void setIsEnterQuery(String isEnterQuery) {
		this.isEnterQuery = isEnterQuery;
	}
	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getDivBackgroundColor() {
		return divBackgroundColor;
	}
	public void setDivBackgroundColor(String divBackgroundColor) {
		this.divBackgroundColor = divBackgroundColor;
	}
	public String getTextFontColor() {
		return textFontColor;
	}
	public void setTextFontColor(String textFontColor) {
		this.textFontColor = textFontColor;
	}
	public String getTextFontSize() {
		return textFontSize;
	}
	public void setTextFontSize(String textFontSize) {
		this.textFontSize = textFontSize;
	}
	public String getEqualcolor() {
		return equalcolor;
	}
	public void setEqualcolor(String equalcolor) {
		this.equalcolor = equalcolor;
	}
	public String getOnmousemovecolor() {
		return onmousemovecolor;
	}
	public void setOnmousemovecolor(String onmousemovecolor) {
		this.onmousemovecolor = onmousemovecolor;
	}
	public String getMyFunc() {
		return myFunc;
	}
	public void setMyFunc(String myFunc) {
		this.myFunc = myFunc;
	}
	public String getIsAddNewData() {
		return isAddNewData;
	}
	public void setIsAddNewData(String isAddNewData) {
		this.isAddNewData = isAddNewData;
	}
	public String getDictionaryType() {
		return dictionaryType;
	}
	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getFieldDisplay() {
		return fieldDisplay;
	}
	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}
	public String getFieldText() {
		return fieldText;
	}
	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getMainField() {
		return mainField;
	}
	public void setMainField(String mainField) {
		this.mainField = mainField;
	}
	public String getCollectMeans() {
		return collectMeans;
	}
	public void setCollectMeans(String collectMeans) {
		this.collectMeans = collectMeans;
	}
	public String getShowWay() {
		return showWay;
	}
	public void setShowWay(String showWay) {
		this.showWay = showWay;
	}
	public String getAllownull() {
		return allownull;
	}
	public void setAllownull(String allownull) {
		this.allownull = allownull;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getAllowClear() {
		return allowClear;
	}
	public void setAllowClear(String allowClear) {
		this.allowClear = allowClear;
	}
	public String getIsDynamicLoadDataSource() {
		return isDynamicLoadDataSource;
	}
	public void setIsDynamicLoadDataSource(String isDynamicLoadDataSource) {
		this.isDynamicLoadDataSource = isDynamicLoadDataSource;
	}
	public String getChooseType() {
		return chooseType;
	}
	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseTypee(String useType) {
		this.useType = useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	/**
	 * @return the hidden
	 */
	public String getHidden() {
		return hidden;
	}
	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public String getLowerClearAllWithSuperior() {
		return lowerClearAllWithSuperior;
	}
	public void setLowerClearAllWithSuperior(String lowerClearAllWithSuperior) {
		this.lowerClearAllWithSuperior = lowerClearAllWithSuperior;
	}
	public String getLowerClearWithSuperior() {
		return lowerClearWithSuperior;
	}
	public void setLowerClearWithSuperior(String lowerClearWithSuperior) {
		this.lowerClearWithSuperior = lowerClearWithSuperior;
	}
	public String getLowerSelectedAllWithSuperior() {
		return lowerSelectedAllWithSuperior;
	}
	public void setLowerSelectedAllWithSuperior(String lowerSelectedAllWithSuperior) {
		this.lowerSelectedAllWithSuperior = lowerSelectedAllWithSuperior;
	}
	public String getParentClearWithChild() {
		return parentClearWithChild;
	}
	public void setParentClearWithChild(String parentClearWithChild) {
		this.parentClearWithChild = parentClearWithChild;
	}
	public String getParentSelectedWithChild() {
		return parentSelectedWithChild;
	}
	public void setParentSelectedWithChild(String parentSelectedWithChild) {
		this.parentSelectedWithChild = parentSelectedWithChild;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnkeyup() {
		return Onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		Onkeyup = onkeyup;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTextClass() {
		return textClass;
	}

	public void setTextClass(String textClass) {
		this.textClass = textClass;
	}

	public String getMoreSelectAll() {
		return moreSelectAll;
	}

	public void setMoreSelectAll(String moreSelectAll) {
		this.moreSelectAll = moreSelectAll;
	}

	public String getMoreSelectSearch() {
		return moreSelectSearch;
	}

	public void setMoreSelectSearch(String moreSelectSearch) {
		this.moreSelectSearch = moreSelectSearch;
	}
}