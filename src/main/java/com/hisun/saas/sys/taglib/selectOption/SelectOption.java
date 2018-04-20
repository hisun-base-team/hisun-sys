package com.hisun.saas.sys.taglib.selectOption;

import com.hisun.saas.sys.taglib.tree.AbstractTreeDataSource;
import com.hisun.saas.sys.taglib.selectOption.vo.SelectNode;
import com.hisun.saas.sys.taglib.tree.TreeDataSource;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.StringUtils;

import java.util.List;


/**
 * 此类主要用于生成下拉选择框及其中的数据。数据来源通常是字典或其他基础性数据（如机构、职务等），选择方式及数据存储通常有两种方式：
 * １、单选。单选时，通常存储于信息主表（如人员表）中，编码是必须存在的，以便用于恢复内容并可以自然保持与字典数据变化（显示内容变
 * 　　化，编码不同变化）的同步。
 * ２、多选。多选时，通常存储于信息主表（如人员表）的附属子表（如政治面貌）中。编码也是必须的。此种情况下，主表中也可能设置一个字
 * 　　段用于存放子表多条记录的组合显示内容（如政治面貌中的“如果是共产党员，则显示入党时间；如果是其他党派，则显示党派名称”）。
 * 　　此种情况下，此控件的绑定字段是查询定义对象（QUERYDEFINE）中相应子查询定义对象中的定义的字段。
 * 考虑到组织系统中大多数字典项在使用时，都允许用户自行输入字典项中没有的内容（从易用性出发），所以，除编码字段名，内容字段也是存
 * 在的。如此，显示内容时，直接显示内容字段的值即可（但仍要与实际值进行比对，如果不是字典内容，则应标识出来）。
 * @author liuzhijie
 * @TIME 2008-5-8
 * 使用说明：
 *      注意事项：如果要设置文本框的宽度 必须设置width属性  在style里面设置宽度无效
 *      如果有多级树时最好设置isEnterQuery为true 即按回车才开始查询
 */
public final class SelectOption{
	private static final long serialVersionUID = 1L;
	// 父类中已定义的属性包括：id,text,绑定字段（BindFields）,事件（Events）,RightControl,verify,Style。此类中不必再定义。

	private String dataSource;//实现SanTreeDataSourceInterface接口的类(必填项)
	/**
	 * 字典类别，可以被dataSource类使用来读取需要的字典类别信息
	 */
	private String contextPath;
	private String dictionaryType = null;
	private String checkboxDisplay;//是否显示复选框 yes显示 none隐藏
	private String radioOrCheckbox; //单选还是复选 radio单选 checkbox复选
	private String parentRadioEnable;//单选情况下父节点是否可选 true可选 false不可选
	private String parentCheckboxEnable;//复选情况下父节点是否可选 true可选 false不可选
	private String parentChildIsolate;// 设置为 true 选择父节点时子节点全部选中,取消时全部取消 子节点部分选中时，父节点选中变灰（灰勾） 设置为 false 父节点子和子节点可以分开选中，相互不影响
	private String userParameter;//现在用于多级树查询时的记录查询条件
	private String isshowtree;//是否显示多级树  yes为显示  no为显示一级的下拉选择项 （默认为只有一级的下拉  no）
	private String staticdata;//yes为动态查询树数据  no为静态查询树数据（只对多级树有效 默认为静态树查询 no）
	private String matchingsetup;//matchingsetup表示需要查询的状态 0表示不去匹配 1表示完全匹配  2表示模糊匹配  一共7个 分别是 节点名，key，助记码1,助记码2，全称,简称,全路径;
	private String selectValueType;//选择后显示的内容是全称还是显示内容 0为显示内容 1为全称 （默认为0）
	private String width;//下拉文本框的宽度 （默认为200）
	private String divmaxwidth;//有多级树的时候的出现滚动条的最大的宽度 （默认为300）
	private String divmaxheight;//层的最大的高度  （默认为300）
	private String defaultvalues;//默认的值 要有多个必须和key的内容对应  用,分开  如（11,）
	private String defaultkeys;//默认的知道的相应key值 要有多个必须和值的内容对应  用,分开  如（11,）
	//private String isDisabled;//设置是否是能用  false为能用  true为不能用
	private String dictionaryEdit;//是否在下面显示字典维护的按钮  默认为false不显示  如果是ture则显示该按钮
	private String dictionaryEditFunc;//字典维护链接的方法
	private String buttonstyle;//下面按钮的样式导入
	private String buttoncss;//下面按钮的css导入
	private String isEnterQuery;//是否按回车查询 还是延迟查询 默认为false 延迟查询 为延迟加载时  按回车是失去焦点的事件  为true时按回车查询
	private String textFontColor;//文本框字体颜色
	private String textFontSize;//文本框字体大小
	private String fontColor;//选择项层的字体颜色
	private String fontSize;//选择项层的字体大小
	private String divBackgroundColor;//选择项层的背景颜色
	private String onMouseMoveColor;//鼠标移到选项的值的颜色
	private String equalcolor;//查询完全命中的颜色
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

	private String myFunc = "";//用户最后需要调用的方法

	private boolean isAddNewData = true;//是否允许录入没完全匹配到的数据

	private String allownull;//暫時是依托GZZZB系统验证框架 验证是否允许为空 allownull="false" 为不允许为空  为true为允许为空(用于Tag部分)

	private String des;//暫時是依托GZZZB系统验证框架 用于得到要提示的信息(用于Tag部分)

	//选项显示方法 top为往上显示 bottom往下显示 默认为往下显示
	private String showWay;

	//是否有动态加载数据源 默认为没有动态数据源 false/没动态加载数据源 true/有动态加载数据源（单级的才有效果 主要用于联动 需要动态实现数据源）刘燕的需求
	private String isDynamicLoadDataSource;

	// 单选是否显示空格  用于清空  false为不需要显示空格 true为需要显示空格(仅单级单选)
	private String allowClear;

	// 专用于动态表单管理系统调用时使用。由于toOptionHtml函数参数过多，且被TAG程序和动态表单管理系统两个程序分别调用，
	// 不再增加其参数，而改用定义实例变量来解决。liufq 20080714
	private String templateCode = null;

	private String chooseType;//用于提供是否需要父子影响  没灰勾的  0为不影响 1为影响 (最先为权限提供) 暂时只支持静态的

	private String unitCode;

	private String useType;//用于标识是录入类型的还是查询类型的  add为录入类型的  query为查询类型的

	private String hidden;//用于标识该控件是否隐藏

	private String onmousemove;

	private String onmouseout;

	private String isSpecialWidthType;//是否特殊的宽度 如果为true表示必须传入的width参数必须是数字 生成的文本框宽度为传入宽度减去图片宽度 如果为false则文本框直接设置宽度为100%

	private String parentSelectedWithChild;//配置父节点复选框选中时，子节点复选框是否全部选中;值为true表示父节复选框选中时，子节点复选框全部选中;值为false表示父节点复选框选中，子节点保持原有状态不变;

	private String parentClearWithChild;//配置父节点复选框取消时，子节点复选框是否全部取消;值为true表示父节点复选框取消时，子节点复选框全部取消;值为false表示父节点复选框取消时，子节点复选框保持原有状态不变;

	private String lowerSelectedAllWithSuperior;//配置下级节点复选框全部选中时，父节点复选框是否选中;值为true表示下级节点复选框全部选中时，父节点复选框选中;值为false表示下级节点复选框全部选中时，父节点复选框保持原有状态不变;

	private String lowerClearWithSuperior;//配置下级节点复选框取消时，父节点复选框是否取消;值为true表示下级节点取消时，父节点复选框取消;值为false表示下级节点复选框取消时，父节点复选框保持原有状态不变;

	private String lowerClearAllWithSuperior;//配置下级节点复选框全部取消时，父节点复选框是否取消;值为true表示下级节点复选框全部取消时，父节点复选框取消;值为false表示下级节点复选框全部取消时，父节点复选框保持原有状态不变;

	private String id;

	private String value;

	private Style style = new Style();//内部使用的样式

	private String textClass;
	private String moreSelectSearch; // yes为显示多选的search栏

	private String moreSelectAll; // yes为显示多选的全选

	private String token;
	//初始化数据
	public SelectOption() {
		//super();
		dataSource="";
		checkboxDisplay="none";
		radioOrCheckbox="radio";//
		parentRadioEnable="true";
		parentCheckboxEnable="true";
		parentChildIsolate="false";
		userParameter="";
		isshowtree="no";
		staticdata="no";
		matchingsetup="2,1,0,0,2,0,0";
		selectValueType="0";
		width="200";
		divmaxwidth="300";//初始化最大宽度为300
		divmaxheight="300";//初始化最大高度为300
		defaultvalues="";
		defaultkeys="";
		//isDisabled="false";
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
		onMouseMoveColor="";
		equalcolor="";
		onblur="";
		onchange="";
		onfocus="";
		onclick="";
		onkeydown="";
		title="";
		tabindex="";
		Onkeyup="";
		myFunc="";
		isAddNewData=false;
		showWay="bottom";
		allownull="true";
		des="";
		isDynamicLoadDataSource="false";
		allowClear="true";
		unitCode="";
		chooseType="0";
		useType="add";
		hidden = "false";
		isSpecialWidthType = "false";

		parentSelectedWithChild = "true";
		parentClearWithChild = "true";
		lowerSelectedAllWithSuperior = "true";
		lowerClearWithSuperior = "false";
		lowerClearAllWithSuperior = "true";
		textClass = "span8 m-wrap";
		token = "";
//		moreSelectSearch = "no";
//		moreSelectAll = "no";
	}



	/**
	 *
	 * @param applicationName 当前应用名称。由SanDocumentManager类在新建此对象时设置此属性(动态表单)  TAG控件则是contextPath
	 * @param newid 主要用于动态表单处理ID 生成唯一id
	 * @param keys 修改时默认值的keys
	 * @param textvalues 修改时默认值的value
	 * @param enabled 是否可用
	 * @param canWrite 是否可写
	 * @param optionType 调用控件的类型  tag为控件调用  dt为动态表单中调用
	 * @param formName 为控件中传入的form的名字
	 * @param mainField 当需要从子表读取数据时则需要传入此参数 （用于多选 且需要取子表数据） 如果是单选或多选但不需要取子表数据 则不需要实现
	 * @param fieldCode 编码存储字段
	 * @param fieldText 文字存储字段
	 * @return  返回控件的HTML
	 * @throws Exception
	 */
	public String toOptionHtml(String applicationName,String newid,String keys, String textvalues, boolean enabled,boolean canWrite,
							   String optionType,String formName,String collectMeans,String mainField,String fieldCode,String fieldText,int childSize,String unitCode)  throws Exception{
		// 专用于TAG的使用。判断TAG传过来的值是否为空，如果为空，则使用缺省值
		String newdefaultkeys=keys;//接收tag传过来的keys
		String newdefaultvalues=textvalues;//接收tag传过来的values
		String defaultkeys = null, defaultvalues = null;
		if(newdefaultkeys.equals(""))
			defaultkeys = this.getDefaultkeys();
		else
			defaultkeys = newdefaultkeys;
		if(newdefaultvalues.equals(""))
			defaultvalues = this.getDefaultvalues();
		else
			defaultvalues = newdefaultvalues;
		keys = defaultkeys;
		textvalues = defaultvalues;
		return this.toOptionHtmlBase(applicationName, newid, keys, textvalues, enabled, canWrite, optionType, formName, collectMeans, mainField, fieldCode, fieldText, childSize, unitCode);
	}
	public String toOptionHtmlBase(String applicationName,String newid,String keys, String textvalues, boolean enabled,boolean canWrite,
								   String optionType,String formName,String collectMeans,String mainField,String fieldCode,String fieldText,int childSize,String unitCode)  throws Exception{
		//调用控件前先初始化要显示层内内容为空
		//String newid = doc.toDocumentId(super.getId());//保存转化后的id
		// 确定本次显示内容时的缺省值，如果未传入值，则使用配置时的缺省值；如果传入了有效值，则使用传入的值。
		//String defaultkeys = this.getDefaultkeys();
		//String defaultvalues = this.getDefaultvalues();

		String defaultkeys = keys, defaultvalues = textvalues;

		//=========暂时要求全部按回车查询  先在此设置强制按回车查询
		isEnterQuery="true";

		defaultkeys = StringUtils.trimNullCharacter2Empty(defaultkeys);
		defaultvalues = StringUtils.trimNullCharacter2Empty(defaultvalues);
		//触发根据code取得value的方法 当defaultvalues为空 defaultkeys不为空时调用
		if(isshowtree.equals("yes")) {
			if (!defaultkeys.equals("") && defaultvalues.equals("")) {
				defaultvalues = this.getValuesByKeys(defaultkeys, unitCode);
			}
		}
		//触发根据texts取得codes的方法 当defaultkeys为空 defaultvalues不为空时调用
		if(defaultvalues!=null && !defaultvalues.equals("") && defaultkeys.equals("")){
			defaultkeys = this.getCodesByText(defaultvalues,unitCode);
			//if(defaultkeys.equals("")){
			//	defaultkeys="";
			//	defaultvalues="";
			//}
		}


		if(defaultkeys==null||defaultkeys.equals("null")){
			defaultkeys="";
		}
		if(defaultvalues==null||defaultvalues.equals("null")){
			defaultvalues="";
		}

		StringBuffer results = new StringBuffer("");
		StringBuffer noTreeresults = new StringBuffer("");
		String hiddenStr = "true".equalsIgnoreCase(hidden) ? "display:none;" : "";
		//增加一个table
//		results.append("<table width=\""+width+"\" id=\"" + newid+ "_table\" border=0 cellSpacing=\"0\" style=\"padding-top:0px;background-color:"+divBackgroundColor+";" + hiddenStr + "\" cellPadding=\"0\"><tr height=\"21\"  style=\"border-bottom:0px;padding-top:0px;\">");
//		results.append("<td style=\"padding-left:0px;height:21px;padding-top:0px;\">");
//		results.append("<div  style=\"width: 100%\">");
		//=======不用弹出方式加载全部换为jquery   20180416 liuzj
//		if(isshowtree.equals("no")){
//			//判断是单选还是多选
//			if(radioOrCheckbox.equals("radio")){
//				//单选的输出
//				results.append("<input type=\"text\" name=\""+newid+"_text\" id=\""+newid+"_text\" ");
//
//				//加入用户的onclick事件
//				if(this.getOnclick()!=null){
//					if (canWrite == false){
//						results.append(" onclick=\""+this.getOnclick()+"\"");
//					}else{
//						results.append(" onclick=\"queryinfo_onclick('"+newid+"','1','1');"+this.getOnclick()+"\"");
//					}
//				}else{
//					if (canWrite != false)
//						results.append(" onclick=\"queryinfo_onclick('"+newid+"','1','1');\"");
//				}
//
//				//				加入用户的onkeydown事件
//				if(!this.getOnkeydown().equals("")){
//					if(isEnterQuery.equals("false"))
//						results.append(" onkeydown=\"queryinfo_last('"+newid+"','1','1');javascript:{if(event.keyCode==13){_gzzzb_selectOption_hidden_Select_enter('"+newid+"');}}"+this.getOnkeydown()+"\"");
//					else
//						results.append(" onkeydown=\"javascript:{if(event.keyCode==13){queryinfo_last('"+newid+"','1','1');}}"+this.getOnkeydown()+"\"");
//				}else{
//					if(isEnterQuery.equals("false"))
//						results.append(" onkeydown=\"queryinfo_last('"+newid+"','1','1');javascript:{if(event.keyCode==13){_gzzzb_selectOption_hidden_Select_enter('"+this.getId()+"');}}\"");
//					else
//						results.append(" onkeydown=\"javascript:{if(event.keyCode==13){queryinfo_last('"+newid+"','1','1');}}\"");
//				}
//				//加入用户的onblur事件
//				if(this.getOnblur()!=null){
//					results.append(" onblur=\"_gzzzb_selectOption_hidden_Select('"+newid+"');"+this.getOnblur()+"\"");
//				}else{
//					results.append(" onblur=\"_gzzzb_selectOption_hidden_Select('"+newid+"');\"");
//				}
//				if(this.getOnfocus()!=null){
//					results.append(" onfocus=\"_gzzzb_loadData_onfocus('"+newid+"');"+this.getOnfocus()+"\"");
//				}else{
//					results.append(" onfocus=\"_gzzzb_loadData_onfocus('"+newid+"');\"");
//				}
//				if(this.getOnmousemove()!=null){
//					results.append(" onmousemove=\""+this.getOnmousemove()+"\"");
//				}
//				if(this.getOnmouseout()!=null){
//					results.append(" onmouseout=\""+this.getOnmouseout()+"\"");
//				}
//			}else{
//				//复选的输出
//				results.append("<input type=\"text\" id=\""+newid+"_text\" name=\""+newid+"_text\"");
//
//				//			加入用户的onclick事件
//				if(!this.getOnclick().equals("")){
//					if (enabled == false){
//						results.append(" onclick=\""+this.getOnclick()+"\"");
//					}else{
//						results.append(" onclick=\"checkboxqueryinfo_onclick('"+newid+"','2','1');"+this.getOnclick()+"\"");
//					}
//				}else{
//					if (enabled != false)
//						results.append(" onclick=\"checkboxqueryinfo_onclick('"+newid+"','2','1');\"");
//				}
//
//				//				加入用户的onkeydown事件
//				if(!this.getOnkeydown().equals("")){
//					if(isEnterQuery.equals("false"))
//						results.append(" onkeydown=\"checkboxqueryinfo_last('"+newid+"','2','1');javascript:{if(event.keyCode==13){_gzzzb_selectOption_hidden_Select_checkbox_enter('"+newid+"');}}"+this.getOnkeydown()+"\"");
//					else
//						results.append(" onkeydown=\"javascript:{if(event.keyCode==13){checkboxqueryinfo_last('"+newid+"','2','1');}}"+this.getOnkeydown()+"\"");
//				}else{
//					if(isEnterQuery.equals("false"))
//						results.append(" onkeydown=\"checkboxqueryinfo_last('"+newid+"','2','1');javascript:{if(event.keyCode==13){_gzzzb_selectOption_hidden_Select_checkbox_enter('"+newid+"');}}\"");
//					else
//						results.append(" onkeydown=\"javascript:{if(event.keyCode==13){checkboxqueryinfo_last('"+newid+"','2','1');}}\"");
//				}
//				//加入用户的onblur事件
//				if(!this.getOnblur().equals("")){
//					results.append(" onblur=\"_gzzzb_selectOption_hidden_Select_checkbox('"+newid+"');"+this.getOnblur()+"\"");
//				}else{
//					results.append(" onblur=\"_gzzzb_selectOption_hidden_Select_checkbox('"+newid+"');\"");
//				}
//
//				if(this.getOnfocus()!=null){
//					results.append(" onfocus=\"_gzzzb_loadDataCheckbox_onfocus('"+newid+"');"+this.getOnfocus()+"\"");
//				}else{
//					results.append(" onfocus=\"_gzzzb_loadDataCheckbox_onfocus('"+newid+"');\"");
//				}
//				if(this.getOnmousemove()!=null){
//					results.append(" onmousemove=\""+this.getOnmousemove()+"\"");
//				}
//				if(this.getOnmouseout()!=null){
//					results.append(" onmouseout=\""+this.getOnmouseout()+"\"");
//				}
//			}
//		}else{
		if(isshowtree.equals("no")){
			//判断是单选还是多选
			if(radioOrCheckbox.equals("radio")){
				//单选的输出
				noTreeresults.append("<select type=\"text\" name=\""+newid+"\" id=\""+newid+"\" ");
				if(this.textClass!=null && !this.textClass.equals("")){
					noTreeresults.append(" class=\""+this.textClass+"\"");
				}
				if(this.textClass!=null && !this.textClass.equals("")){
					noTreeresults.append(" class=\""+this.textClass+"\"");
				}
				if(this.getStyle()!=null){
					if(width!=null && !width.equals("")){
						try{
							this.getStyle().addStyles("width:"+width+";");
						}catch(Exception e){
						}
					}

					if(!textFontColor.equals("")){
						this.getStyle().addStyles("color:"+textFontColor+"");
					}
					if(!textFontSize.equals("")){
						this.getStyle().addStyles("font-size:"+textFontSize+"");
					}
					noTreeresults.append(" "+this.getStyle().toHtml());
				}else {

					if(width!=null && !width.equals("")){
					} else {
						try {
							this.getStyle().addStyles("width:"+width+";");
						} catch (Exception e) {
						}
					}
				}
				noTreeresults.append(" >");
				try {
					SelectDataSource obj = ApplicationContextUtil.getBean(this.dataSource, AbstractSelectObject.class);
					if(obj.getDataOptions()!=null && obj.getDataOptions().size()>0){
						List<SelectNode> selectNodes = obj.getDataOptions();
						for(SelectNode node : selectNodes){
							noTreeresults.append("<option value=\""+StringUtils.trimNull2Empty(node.getOptionKey())+"\"");
							if(defaultkeys!="") {
								String[] defaultkey = defaultkeys.split(",");
								for(int i=0;i<defaultkey.length;i++) {
									if (defaultkey[i] != null && defaultkey[i].equals(node.getOptionKey())) {
										noTreeresults.append("selected");
									}
								}
							}
								noTreeresults.append(">"+StringUtils.trimNull2Empty(node.getOptionValue())+"</option>");
						}
					}
				}catch (Exception e){
					e.printStackTrace();;
				}
//				List<TreeNodeImpl> sanTreeNodes =  obj.getNodes();

				noTreeresults.append("</select");
//				<select class="m-wrap span4" required name="tblx" id="tblx" >
			}else{
				//复选的输出
				noTreeresults.append("<div>");
				noTreeresults.append("<input type=\"hidden\" name='"+newid+"' id='"+newid+"' value=\"\"/>");
				noTreeresults.append("<select type=\"text\" name=\""+newid+"_Select\" id=\""+newid+"_Select\" inputId='"+newid+"' multiple=\"multiple\" value=\"\"");
				if(this.textClass!=null && !this.textClass.equals("")){
					noTreeresults.append(" class=\""+this.textClass+"\"");
				}
				if(this.textClass!=null && !this.textClass.equals("")){
					noTreeresults.append(" class=\""+this.textClass+"\"");
				}
				if(this.getStyle()!=null){
					if(width!=null && !width.equals("")){
						try{
							this.getStyle().addStyles("width:"+width+";");
						}catch(Exception e){
						}
					}

					if(!textFontColor.equals("")){
						this.getStyle().addStyles("color:"+textFontColor+"");
					}
					if(!textFontSize.equals("")){
						this.getStyle().addStyles("font-size:"+textFontSize+"");
					}
					noTreeresults.append(" "+this.getStyle().toHtml());
				}else {

					if(width!=null && !width.equals("")){
					} else {
						try {
							this.getStyle().addStyles("width:"+width+";");
						} catch (Exception e) {
						}
					}
				}
				noTreeresults.append(" >");
				try {
					SelectDataSource obj = ApplicationContextUtil.getBean(this.dataSource, AbstractSelectObject.class);
					if(obj.getDataOptions()!=null && obj.getDataOptions().size()>0){
						List<SelectNode> selectNodes = obj.getDataOptions();
						for(SelectNode node : selectNodes){
							noTreeresults.append("<option value=\""+StringUtils.trimNull2Empty(node.getOptionKey())+"\"");
							if(defaultkeys!="") {
								String[] defaultkey = defaultkeys.split(",");
								for(int i=0;i<defaultkey.length;i++) {
									if (defaultkey[i] != null && defaultkey[i].equals(node.getOptionKey())) {
										noTreeresults.append(" selected");
									}
								}
							}
							noTreeresults.append(">"+StringUtils.trimNull2Empty(node.getOptionValue())+"</option>");
						}
					}
				}catch (Exception e){
					e.printStackTrace();;
				}
//				List<TreeNodeImpl> sanTreeNodes =  obj.getNodes();

				noTreeresults.append("</select");
				noTreeresults.append("</div>");
				noTreeresults.append("<script type=\"text/javascript\">");
				noTreeresults.append("$('#"+newid+"_Select').multiselect({");
				noTreeresults.append("columns:1,");
				noTreeresults.append("placeholder: '请选择...',");
				if(moreSelectSearch!=null && moreSelectSearch.equals("yes")) {
					noTreeresults.append("search: true,");
				}
				noTreeresults.append("selectGroup: true,");
				if(moreSelectAll!=null && moreSelectAll.equals("yes")) {
					noTreeresults.append("selectAll: true");
				}
				noTreeresults.append("});");
				noTreeresults.append("</script>");
			}
		}else{
			results.append("<div class=\"input-append date form_datetime\" style=\"width: 100%\"><input type=\"text\" name=\""+newid+"_text\" id=\""+newid+"_text\" ");
			if(defaultvalues!=null && !defaultvalues.equals("")){
				results.append("readonly");
			}
			//加入用户的onclick事件
			if(!this.getOnclick().equals("")){
				results.append(" onclick=\"_gzzzb_selectOption_openChoose_text('"+newid+"');"+this.getOnclick()+"\"");
			}else{
				results.append(" onclick=\"_gzzzb_selectOption_openChoose_text('"+newid+"');\"");
			}

//				加入用户的onkeydown事件
			if(!this.getOnkeydown().equals("")){
				results.append(" onkeydown=\"javascript:{if(event.keyCode==13){_gzzzb_selectOption_openChoose_onkeydown('"+newid+"');}}"+this.getOnkeydown()+"\"");
			}else{
				results.append(" onkeydown=\"javascript:{if(event.keyCode==13){_gzzzb_selectOption_openChoose_onkeydown('"+newid+"');}}\"");
			}
			//加入用户的onblur事件
			if(!this.getOnblur().equals("")){
				results.append(" onblur=\"_gzzzb_selectOption_openChoose_onBlur('"+newid+"');"+this.getOnblur()+"\"");
			}else{
				results.append(" onblur=\"_gzzzb_selectOption_openChoose_onBlur('"+newid+"');\"");
			}
			if(this.getOnmousemove()!=null){
				results.append(" onmousemove=\""+this.getOnmousemove()+"\"");
			}
			if(this.getOnmouseout()!=null){
				results.append(" onmouseout=\""+this.getOnmouseout()+"\"");
			}
		}
		//用于输出Tag端的是否验证非空的内容
		if(this.getAllownull().equals("false")){
			results.append(" required ");
		}

		//给输入框增加title
		if(!this.getTitle().equals("")){
			results.append(" title=\""+this.getTitle()+"\"");
		}

		if(defaultvalues!=null && !defaultvalues.equals("")){
			results.append(" value=\""+defaultvalues+"\"");
		}
		if(textClass!=null && !textClass.equals("")){
			results.append(" class=\""+textClass+"\"");
		}

		//给style增加样式  宽度不能在样式里面设置


		if(this.getStyle()!=null){
			if(isSpecialWidthType.equals("false")){
//				this.getStyle().addStyles("width:100%;height:21px;");
			}else{
				if(width.equals("")){
//					this.getStyle().addStyles("width:100%;height:21px;");
				}else{
					try{
						this.getStyle().addStyles("width:"+Integer.toString(Integer.parseInt(width)-20)+";");
					}catch(Exception e){
//						this.getStyle().addStyles("width:100%;height:21px;");
					}
				}
			}

			if(!textFontColor.equals("")){
				this.getStyle().addStyles("color:"+textFontColor+"");
			}
			if(!textFontSize.equals("")){
				this.getStyle().addStyles("font-size:"+textFontSize+"");
			}

			results.append(" "+this.getStyle().toHtml());
		}else{
			if(isSpecialWidthType.equals("false")){
//				this.style.addStyles("width:100%;height:21px;");
			}else{
				if(width.equals("")){
//					this.getStyle().addStyles("width:100%;height:21px;");
				}else{
					try{
						this.getStyle().addStyles("width:"+Integer.toString(Integer.parseInt(width)-20)+";");
					}catch(Exception e){
//						this.getStyle().addStyles("width:100%;height:21px;");
					}
				}
			}

			if(!textFontColor.equals("")){
				results.append("color:"+textFontColor+";");
			}
			if(!textFontSize.equals("")){
				results.append("font-size:"+textFontSize+";");
			}
			results.append(" \"");
		}
//		super.appendHtmlOfAttributes(results);
//		results.append(this.getVerify().toHtml());
		//设置是否可用
		if (enabled == false){
			results.append(" disabled");
		}
		if (canWrite == false){
			results.append(" readonly");
		}

//		super.appendHtmlOfAttributes(results);
//		results.append(this.getVerify().toHtml());

		//加入用户的onkeyup事件
		if(!this.getOnkeyup().equals("")){
			results.append(" onkeyup=\""+this.getOnkeyup()+"\"");
		}
		if(!this.getTabindex().equals("")){
			results.append(" tabIndex=\""+this.getTabindex()+"\"");
		}
		results.append("/>");
//		results.append("</td>");
//		results.append("<td style=\"padding-left:0px;padding-bottom:11px;\">");
		results.append("<span class=\"add-on\" id=\"").append(newid).append("_img\"");
//		results.append(" style=\"border:0;margin-top:0px;padding-top:0px;\"");
//		results.append(" src=\"").append(applicationName).append("/images/option/pick-button4.gif\"");
		//多级下拉和单级下拉的点击事件和失去焦点时间不同

		//=======不用弹出方式加载全部换为jquery   20180416 liuzj
//		if(isshowtree.equals("no")){
//			if(radioOrCheckbox.equals("radio")){
//				results.append(" onclick=\"_gzzzb_selectOption_hidden_Select_img('"+newid+"');\"");
//				results.append(" onblur=\"_gzzzb_selectOption_hidden_Select('"+newid+"');\"");
//			}else{
//				results.append(" onclick=\"_gzzzb_selectOption_hidden_Select_checkbox_img('"+newid+"');\"");
//				results.append(" onblur=\"_gzzzb_selectOption_hidden_Select_checkbox_enter('"+newid+"');\"");
//			}
//		}else{
			results.append(" onclick=\"_gzzzb_selectOption_openChoose('"+newid+"','');\"");
//		}
		////////=======

		//如果输入框不能用 则图片也不能用
//		if (enabled == false){
//			results.append(" disabled");
//		}
//		if (canWrite == false){
//			results.append(" disabled");
//		}
		results.append("style=\"height:24px\" ><i class=\"icon-search\"></i></span>");

				results.append("<script type=\"text/javascript\">$(\"#"+newid+"_text\").css(\"width\", window.document.getElementById(\""+newid+"_text\").offsetWidth-28);</script>");
//				results.append("</td></tr>");
		// 用于多选的时候判断是否有空值的key 	value不为空的情况  如果有则需要生成和value个数相同的key的已‘，’分开的空格 如传入的value为 部长，处长  key为空 则需把key转为 ， ，
		if(defaultvalues!=null && !defaultvalues.equals("")&&radioOrCheckbox.equals("checkbox")){
			if(defaultkeys.equals("")&&!defaultvalues.equals("")){
				String[] defaultvalue=defaultvalues.split(",");
				for(int i=0;i<defaultvalue.length;i++){
					defaultkeys=defaultkeys+" ,";
				}
			}else if(!defaultkeys.equals("")&&!defaultvalues.equals("")){
				String[] defaultkey=defaultkeys.split(",");
				String[] defaultvalue=defaultvalues.split(",");
				if(defaultkey.length!=defaultvalue.length){
					if(defaultvalue.length>defaultkey.length){
						for(int i=0;i<defaultvalue.length-defaultkey.length;i++){
							if(defaultkeys.equals(""))
								defaultkeys=defaultkeys+" ,";
							else{
								if(defaultkeys.lastIndexOf(",")==defaultkeys.length()-1){
									defaultkeys=defaultkeys+" ,";
								}else
									defaultkeys=defaultkeys+" , ,";
							}
						}
					}
				}
			}

			if(!defaultkeys.equals("")&&radioOrCheckbox.equals("checkbox"))
				if(defaultkeys.lastIndexOf(",")!=defaultkeys.length()-1)
					defaultkeys=defaultkeys+",";
			if(!defaultvalues.equals("")&&radioOrCheckbox.equals("checkbox"))
				if(defaultvalues.lastIndexOf(",")!=defaultvalues.length()-1)
					defaultvalues=defaultvalues+",";
		}

		//=======不用弹出方式加载全部换为jquery   20180416 liuzj
//		if(isshowtree.equals("no")){
//			//用于记录以前全部选中的记录内容的key  只能增加保存新内容 不能减少已保存的记录内容
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_checked_allkey\" name=\"gzzzb_"+newid+"_checked_allkey\" value=\""+defaultkeys+"\"/>\n");
//			// 用于记录以前全部选中的记录内容  只能增加保存新内容 不能减少已保存的记录内容
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_checked_allvalue\" name=\"gzzzb_"+newid+"_checked_allvalue\" value=\""+defaultvalues+"\"/>\n");
//
//			// 用于记录上次选择的内容
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_oldqueryvalue\" name=\"gzzzb_"+newid+"_oldqueryvalue\" value=\""+defaultvalues+"\"/>\n");
//			//用于记录上次选择的内容的key
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_oldquerykey\" name=\"gzzzb_"+newid+"_oldquerykey\" value=\""+defaultkeys+"\"/>\n");
//			//用于记录以前全部选中的记录的全路径  只能增加保存新内容 不能减少已保存的记录内容
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_checked_fullurl\" name=\"gzzzb_"+newid+"_checked_fullurl\" value=\""+defaultvalues+"\"/>\n");
//
//			//用于记录在需要按键查询的时候比较是否有改变文本框的值
//			results.append("<input type='hidden' id=\"gzzzb_"+newid+"_queryValue\" name=\"gzzzb_"+newid+"_queryValue\" value=\""+defaultvalues+"\"/>\n");
//		}
//		 用于记录现在选择的内容的key
		results.append("<input type='hidden' id=\""+newid+"\" name=\""+newid+"\" value=\""+defaultkeys+"\"/>\n");
//		输出JS需要的参数
		results.append("<hidden id='gzzzb_"+newid+"_hiddenvalue' name='gzzzb_"+newid+"_hiddenvalue'");
		results.append(" isshowtree='").append(isshowtree).append("'");
		results.append(" staticdata='").append(staticdata).append("'");
		results.append(" matchingsetup=\"").append(matchingsetup).append("\"");
		results.append(" selectvaluetype=\"").append(selectValueType).append("\"");
		results.append(" divmaxwidth=\"").append(divmaxwidth).append("\"");
		results.append(" divmaxheight=\"").append(divmaxheight).append("\"");
		results.append(" divmaxheight=\"").append(divmaxheight).append("\"");
		results.append(" width=\"").append(width).append("\"");
		results.append(" dataSource=\"").append(dataSource).append("\"");
		results.append(" contextPath=\"").append(applicationName).append("\"");
		results.append(" checkboxDisplay=\"").append(checkboxDisplay).append("\"");
		results.append(" radioOrCheckbox=\"").append(radioOrCheckbox).append("\"");
		results.append(" parentRadioEnable=\"").append(parentRadioEnable).append("\"");
		results.append(" parentCheckboxEnable=\"").append(parentCheckboxEnable).append("\"");

		results.append(" parentSelectedWithChild=\"").append(parentSelectedWithChild).append("\"");
		results.append(" parentClearWithChild=\"").append(parentClearWithChild).append("\"");
		results.append(" lowerSelectedAllWithSuperior=\"").append(lowerSelectedAllWithSuperior).append("\"");
		results.append(" lowerClearWithSuperior=\"").append(lowerClearWithSuperior).append("\"");
		results.append(" lowerClearAllWithSuperior=\"").append(lowerClearAllWithSuperior).append("\"");

		results.append(" parentChildIsolate=\"").append(parentChildIsolate).append("\"");
		results.append(" userParameter=\"").append(userParameter).append("\"");
		results.append(" dictionaryType=\"").append(dictionaryType).append("\"");
		results.append(" token=\"").append(token).append("\"");
		//如果设置了字体颜色 则把字体颜色增加进去
		if(fontColor!=null){
			results.append(" fontColor=\"").append(fontColor).append("\"");
		}else{
			results.append(" fontColor=\"\"");
		}
		if(fontSize!=null){
			results.append(" fontSize=\"").append(fontSize).append("\"");
		}else{
			results.append(" fontSize=\"\"");
		}
		if (StringUtils.isEmpty(this.onchange) == false){ // 如果有事件代码，处理其最后的“()”
			if (this.onchange.endsWith(";") == true){
				this.onchange = this.onchange.substring(0, this.onchange.length() -1);
			}
			if (this.onchange.endsWith(")") == false){
				this.onchange += "()";
			}
		}
		// 如果当前是动态表单系统在调用，则需要在onchange事件中加入动态表单的总控件程序需要的事件代码。
//		if (StringUtils.equalsIgnoreCase(optionType, "dt")){
//			String dtOnChange = "_sanDoc_Documents.getDocumentByTemplateCode('" + this.templateCode
//					+ "').raiseEvent('" + this.getId().toLowerCase() + "_onchange', this);";
//			if (StringUtils.isEmpty(this.onchange)){
//				results.append(" onchange=\"" ).append(dtOnChange).append( "\"");
//			}else{
//				results.append(" onchange=\"" ).append(this.onchange).append(";").append(dtOnChange).append( "\"");
//			}
//		}else{ // 不是动态表单管理系统的调用，则直接输出定义的事件即可。
//			results.append(" onchange=\"" ).append(onchange).append( "\"");
//		}
		results.append(" myFunc=\"").append(myFunc).append("\"");
		results.append(" onMouseMoveColor=\"").append(onMouseMoveColor).append("\"");
		results.append(" equalcolor=\"").append(equalcolor).append("\"");
		results.append(" isAddNewData=\"").append(isAddNewData).append("\"");
		results.append(" showWay=\"").append(showWay).append("\"");
		results.append(" dynamicAttri=\"\"");//输出一个空的属性 用于保存页面取到的数据源自定义的数据
		results.append(" allowClear=\"").append(allowClear).append("\"");//单选是否显示空格  用于清空  false为不需要显示空格 true为需要显示空格(仅单级单选)
		results.append(" chooseType=\"").append(chooseType).append("\"");
		results.append(" useType=\"").append(this.useType).append("\"");

		results.append(" optionType=\"").append(optionType).append("\"");
		if(optionType.equals("tag")){
			results.append(" collectMeans=\"").append(collectMeans).append("\"");
			results.append(" formName=\"").append(formName).append("\"");
			results.append(" mainField=\"").append(mainField).append("\"");
			results.append(" fieldCode=\"").append(fieldCode).append("\"");
			results.append(" fieldText=\"").append(fieldText).append("\"");
			results.append(" childSize=\"").append(childSize).append("\"");

		}
		results.append(" isDynamicLoadDataSource=\"").append(this.isDynamicLoadDataSource).append("\"");

		results.append("/>\n");
		if(isshowtree.equals("no")){
//			results.append("<tr valign=\"middle\" style=\"background-color: "+divBackgroundColor+"\">\n<td colspan=2 width=\"100%\" style=\"padding-left:0px;height:0px;background-color:"+divBackgroundColor+"\">");
//			if(radioOrCheckbox.equals("radio")){//onmousemove=\"_gzzzb_selectOption_hidden_Select_DIV_Input_Onblur(this);\" onblur=\"_gzzzb_selectOption_hidden_Select('"+newid+"');\"
//				results.append("<div id=\"gzzzb_"+newid+"_main\" style=\"width:100%;z-index:99;position:absolute;background-color: "+divBackgroundColor+"; border:1px;border-style:solid;border-color:Gray;  filter:progid:DXImageTransform.Microsoft.Shadow(color='dimgray', Direction=135, Strength=2);display:none;\" onmousemove=\"_gzzzb_selectOption_hidden_Select_DIV_Input_Onblur('"+newid+"');\" onmouseout=\"_gzzzb_selectOption_hidden_Select_MainDiv('"+newid+"');\">");
//				//用来把生成模拟的保存数据的标签
//				results.append("<B style=\"line-height:0px;visibility:hidden;\">");
//				if(optionType.equals("tag")){
//					if(!this.getId().equals(fieldCode))
//						results.append("<input type=\"hidden\" name=\""+fieldCode+"\" value=\""+defaultkeys+"\" />");
//					if(!defaultvalues.equals(""))//如果是修改 则生成一个模拟的保存内容的hidden
//						results.append("<input type=\"hidden\" name=\""+fieldText+"\" value=\""+defaultvalues+"\" />");
//				}
//				results.append("</B>");
//				results.append("<div id=\""+newid+"_option\" style=\"white-space:nowrap;background-color:"+divBackgroundColor+";\" onmousemove=\"_gzzzb_selectOption_hidden_Select_DIV('"+newid+"','2');\"><p align=\"left\" style=\"color:#E1D355;font-size:14px;\"><img src=\""+applicationName+"/images/option/loadingImg.gif\" style=\"width:24px;height:24px;\"><b>正在加载数据 请稍候....</b></p></div>");
//				results.append("<div id=\""+newid+"_hidden_option_all\" style=\"overflow-y:hidden;white-space:nowrap;background-color: "+divBackgroundColor+";display:none;\"></div>\n");
//			}else{// onmouseover=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','2');\" onblur=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','1');\"
//				results.append("<div id=\"gzzzb_"+newid+"_main\" style=\"width:100%;z-index:99;position:absolute;background-color: "+divBackgroundColor+"; border:1px;border-style:solid;border-color:Gray;  filter:progid:DXImageTransform.Microsoft.Shadow(color='dimgray', Direction=135, Strength=2);display:none\"  onmousemove=\"_gzzzb_selectOption_hidden_Select_DIV_Input_Onblur('"+newid+"');\" onmouseout=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox_mainDIv('"+newid+"','1');\" >");
//				//			用来把生成模拟的保存数据的标签
//				results.append("<B style=\"line-height:0px;visibility:hidden;\">");
//				if(optionType.equals("tag")){
//					if(defaultvalues!=""){
//						String[] defaultkey=defaultkeys.split(",");
//						String[] defaultvalue=defaultvalues.split(",");
//						if(mainField!=null&&!mainField.equals("")){
//							for(int i=0;i<defaultvalue.length;i++){
//								if(defaultvalue[i]!=""){
//									results.append("<input type=\"hidden\" name=\""+collectMeans+"["+i+"]."+fieldCode+"\" value=\""+defaultkey[i]+"\"/><input type=\"hidden\" name=\""+collectMeans+"["+i+"]."+fieldText+"\" value=\""+defaultvalue[i]+"\"/>");
//								}
//							}
//						}else{
//							if(fieldText!=null&&fieldText!=""){
//								results.append("<input type=\"hidden\" name=\""+fieldText+"\" value=\""+defaultvalues+"\"/>");
//							}
//						}
//					}
//				}
//				results.append("</B>");
//				results.append("<div id=\""+newid+"_option\"  style=\"swhite-space:nowrap;background-color: "+divBackgroundColor+";\" onscroll=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','2');\" onclick=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','2');\" onblur=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','1');\"><p align=\"left\" style=\"color:#E1D355;font-size:14px;\"><img src=\""+applicationName+"/images/option/loadingImg.gif\" style=\"width:24px;height:24px;\"><b>正在加载数据 请稍候....</b></p></div>\n");
//				results.append("<div id=\""+newid+"_hidden_option_all\" style=\"overflow-y:hidden;white-space:nowrap;background-color: "+divBackgroundColor+";display:none;\" onscroll=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','2');\" onclick=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','2');\" onblur=\"_gzzzb_selectOption_hidden_Select_DIV_checkbox('"+newid+"','1');\"></div>\n");
//			}
//			results.append("<div id=\""+newid+"_option_old\" style=\"overflow-y:hidden;white-space:nowrap;background-color:"+divBackgroundColor+";display:none;\"></div>\n");
//
//			////输出下面的按钮  单选只输出一个取消按钮    如果dictionaryEdit为true则显示一个字典维护的按钮
//			if(radioOrCheckbox.equals("radio")){
//				/*results.append("<table width=\"100%\" bgColor=\""+divBackgroundColor+"\" cellSpacing=0 cellPadding=0><tr><td align=center><input type=\"button\" value=\"取消\" onclick=\"show_parentvalue('"+newid+"','1');\"");
//				if(buttonstyle!=null&&!buttonstyle.equals("")){
//					results.append(" style=\""+buttonstyle+"\"");
//				}
//				if(buttoncss!=null&&!buttoncss.equals("")){
//					results.append(" css=\""+buttoncss+"\"");
//				}
//				results.append(">");
//				if(dictionaryEdit.equals("true")){
//					results.append("<input type=\"button\" value=\" 字典维护 \" onclick=\""+dictionaryEditFunc+"\"");
//					if(buttonstyle!=null&&!buttonstyle.equals("")){
//						results.append(" style=\""+buttonstyle+"\"");
//					}
//					if(buttoncss!=null&&!buttoncss.equals("")){
//						results.append(" css=\""+buttoncss+"\"");
//					}
//					results.append(">");
//				}
//				results.append("</td>\n</tr>\n</table>\n");
//				*/
//				results.append("</div>\n");
//			}else{//复选输出确定按钮和复选按钮   如果dictionaryEdit为true则显示一个字典维护的按钮
//				results.append(" &nbsp;&nbsp;<input type=\"button\" value=\"确定\" onclick=\"checkbox_submit('"+newid+"');\"");
//				if(buttonstyle!=null&&!buttonstyle.equals("")){
//					results.append(" style=\""+buttonstyle+"\"");
//				}
//				if(buttoncss!=null&&!buttoncss.equals("")){
//					results.append(" css=\""+buttoncss+"\"");
//				}
//				results.append(">&nbsp;&nbsp;<input type=\"button\" value=\"取消\" onclick=\"show_parentvalue('"+newid+"','1');\"");
//				if(buttonstyle!=null&&!buttonstyle.equals("")){
//					results.append(" style=\""+buttonstyle+"\"");
//				}
//				if(buttoncss!=null&&!buttoncss.equals("")){
//					results.append(" css=\""+buttoncss+"\"");
//				}
//				results.append(">");
//				if(dictionaryEdit.equals("true")){
//					results.append("<input type=\"button\" value=\"字典维护\" onclick=\""+dictionaryEditFunc+"\"");
//					if(buttonstyle!=null&&!buttonstyle.equals("")){
//						results.append(" style=\""+buttonstyle+"\"");
//					}
//					if(buttoncss!=null&&!buttoncss.equals("")){
//						results.append(" css=\""+buttoncss+"\"");
//					}
//					results.append(">");
//				}
//				results.append("</div>");
//			}
//
////			results.append("</td></tr>\n");
		}else{
			results.append("<div id=\"gzzzb_"+newid+"_main\" style=\"z-index:99;position:absolute;background-color: "+divBackgroundColor+"; border:1px;border-style:solid;border-color:Gray;  filter:progid:DXImageTransform.Microsoft.Shadow(color='dimgray', Direction=135, Strength=2);display:none;\" >\n");
			//用来把生成模拟的保存数据的标签
			results.append("<EM style=\"visibility:hidden\">\n");
			if(optionType.equals("tag")){
				if(radioOrCheckbox.equals("radio")){
					if(!this.getId().equals(fieldCode))
						results.append("<input type=\"hidden\" name=\""+fieldCode+"\" value=\""+defaultkeys+"\" />");
					if(!defaultvalues.equals(""))//如果是修改 则生成一个模拟的保存内容的hidden
						results.append("<input type=\"hidden\" name=\""+fieldText+"\" value=\""+defaultvalues+"\" />");
				}else{
					if(!defaultvalues.equals("")){
						String[] defaultkey=defaultkeys.split(",");
						String[] defaultvalue=defaultvalues.split(",");
						if(mainField!=null&&!mainField.equals("")){
							for(int i=0;i<defaultvalue.length;i++){
								if(defaultvalue[i]!=""){
									results.append("<input type=\"hidden\" name=\""+collectMeans+"["+i+"]."+fieldCode+"\" value=\""+defaultkey[i]+"\"/><input type=\"hidden\" name=\""+collectMeans+"["+i+"]."+fieldText+"\" value=\""+defaultvalue[i]+"\"/>");
								}
							}
						}else{
							if(fieldText!=null&&fieldText!=""){
								results.append("<input type=\"hidden\" name=\""+fieldText+"\" value=\""+defaultvalues+"\"/>");
							}
						}
					}
				}
			}
			results.append("</EM></div>\n");
		}
//		results.append("</table>\n");
		results.append("</div>\n");
		//输出JS需要的对象
		results.append("<script>\n");

		/**
		 * 此对象主要用于二次开发者客服端取得数据
		 */

//		if(isshowtree.equals("no")){
//			results.append("var "+newid+"_img_obj=document.all(\""+newid+"_img\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"_img\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_img_obj;\n\t");
//
//			results.append("var gzzzb_"+newid+"_checked_allkey_obj=document.all(\"gzzzb_"+newid+"_checked_allkey\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_checked_allkey\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_checked_allkey_obj;\n\t");
//
//			results.append("var gzzzb_"+newid+"_checked_allvalue_obj=document.all(\"gzzzb_"+newid+"_checked_allvalue\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_checked_allvalue\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_checked_allvalue_obj;\n\t");
//			/**
//			 * 此对象主要用于二次开发者客服端取得数据
//			 */
//			results.append("var gzzzb_"+newid+"_oldqueryvalue_obj=document.all(\"gzzzb_"+newid+"_oldqueryvalue\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_oldqueryvalue\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_oldqueryvalue_obj;\n\t");
//
//			results.append("var gzzzb_"+newid+"_oldquerykey_obj=document.all(\"gzzzb_"+newid+"_oldquerykey\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_oldquerykey\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_oldquerykey_obj;\n\t");
//
//			results.append("var gzzzb_"+newid+"_checked_fullurl_obj=document.all(\"gzzzb_"+newid+"_checked_fullurl\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_checked_fullurl\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_checked_fullurl_obj;\n\t");
//
//			results.append("var "+newid+"_option_obj=document.all(\""+newid+"_option\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"_option\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_option_obj;\n\t");
//
//			results.append("var "+newid+"_option_old_obj=document.all(\""+newid+"_option_old\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"_option_old\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_option_old_obj;\n\t");
//
//			results.append("var "+newid+"_hidden_option_all_obj=document.all(\""+newid+"_hidden_option_all\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"_hidden_option_all\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_hidden_option_all_obj;\n\t");
//
//			results.append("var gzzzb_"+newid+"_query_obj=document.all(\"gzzzb_"+newid+"_queryValue\");\n\t");
//			results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_queryValue\";\n\t");
//			results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_query_obj;\n\t");
//		}
		results.append("var gzzzb_"+newid+"_main_obj=document.all(\"gzzzb_"+newid+"_main\");\n\t");
		results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_main\";\n\t");
		results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_main_obj;\n\t");

		results.append("var "+newid+"_text_obj=document.all(\""+newid+"_text\");\n\t");
		results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"_text\";\n\t");
		results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_text_obj;\n\t");

		results.append("var "+newid+"_obj=document.all(\""+newid+"\");\n\t");
		results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\""+newid+"\";\n\t");
		results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]="+newid+"_obj;\n\t");

		results.append("var gzzzb_"+newid+"_hiddenvalue_obj=document.all(\"gzzzb_"+newid+"_hiddenvalue\");\n\t");
		results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_hiddenvalue\";\n\t");
		results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_hiddenvalue_obj;\n\t");

		results.append("var gzzzb_"+newid+"_isOpenState=0;\n\t");//特殊标记 如果为0则表示未打开 为1则标示已打开
		results.append("Gzzzb_SelectOption_ids[Gzzzb_SelectOption_ids.length]=\"gzzzb_"+newid+"_isOpenState\";\n\t");
		results.append("Gzzzb_SelectOption_objs[Gzzzb_SelectOption_objs.length]=gzzzb_"+newid+"_isOpenState;\n\t");


		results.append("</script>");

		//this.defaultkeys=""; // 此句将导致配置文件中指定的缺省值丢失，不能有。liufq 20080715
		//this.defaultvalues="";

		//ResponseUtils.write(pageContext, results.toString());
		//text = null;
		if(isshowtree.equals("no")){
			return noTreeresults.toString();
		}else {
			return results.toString();
		}
	}

	/**
	 *
	 * @codes code传入的code
	 * @return 返回根据
	 */
	private String getValuesByKeys(String codes,String unitCode){
		String values="";
		Class beanClass;
		try {
//			beanClass = Class.forName(dataSource);
			TreeDataSource obj = ApplicationContextUtil.getBean(this.getDataSource(), AbstractTreeDataSource.class);
//			TreeDataSource obj = (AbstractTreeDataSource)beanClass.newInstance();
			values=obj.getValueByKeys(codes,userParameter,radioOrCheckbox,unitCode);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	/**
	 *
	 *  texts传入的texts
	 * @return 更具传入的texts得到codes
	 */
	private String getCodesByText(String texts,String unitCode){
		String values="";
		Class beanClass;
		try {
//			beanClass = Class.forName(dataSource);
//
//			TreeDataSource obj = (AbstractTreeDataSource)beanClass.newInstance();
			TreeDataSource obj = ApplicationContextUtil.getBean(this.getDataSource(), AbstractTreeDataSource.class);
			values=obj.checkTextEqual(texts,userParameter,radioOrCheckbox,unitCode);
		}
		catch (Exception e) {
			//"数据源中getValueByKeys()方法为定义或方法异常!";
			e.printStackTrace();
		}
		return values;
	}
	public String getCheckboxDisplay() {
		return checkboxDisplay;
	}
	public void setCheckboxDisplay(String checkboxDisplay) {
		this.checkboxDisplay = checkboxDisplay;
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
	public String getSelectValueType() {
		return selectValueType;
	}
	public void setSelectValueType(String selectValueType) {
		this.selectValueType = selectValueType;
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

	public String getIsEnterQuery() {
		return isEnterQuery;
	}
	public void setIsEnterQuery(String isEnterQuery) {
		this.isEnterQuery = isEnterQuery;
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
	public String getOnMouseMoveColor() {
		return onMouseMoveColor;
	}
	public void setOnMouseMoveColor(String onMouseMoveColor) {
		this.onMouseMoveColor = onMouseMoveColor;
	}

	public boolean isChanged() {
		return false;
	}
	public void setStatus(int newStatus) {
	}
	public String getTagName() {
		return "GzzzbSelectOption";
	}
	public String collectBindFieldIds() {
		return null;
	}
	public String collectEventListeners() {
		return null;
	}
	public String collectJavaScriptFunction() {
		return null;
	}
	public String collectJavaScriptImportFilename() {
		return "/js/select_option.js,/js/zzbxtree.js,/js/gzzzbtext.js";
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
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	public String getOnkeydown() {
		return onkeydown;
	}
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}
	public String getTabindex() {
		return tabindex;
	}
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOnkeyup() {
		return Onkeyup;
	}
	public void setOnkeyup(String onkeyup) {
		Onkeyup = onkeyup;
	}
	public String getMyFunc() {
		return myFunc;
	}
	public void setMyFunc(String myFunc) {
		this.myFunc = myFunc;
	}
	public boolean isAddNewData(){
		return this.isAddNewData;
	}
	public String getIsAddNewData() {
		return String.valueOf(isAddNewData);
	}
	public void setIsAddNewData(String isAddNewData) {
		this.isAddNewData = new Boolean(isAddNewData);
	}
	/**
	 * 获取字典类别，可以被dataSource类使用来读取需要的字典信息
	 * @return
	 */
	public String getDictionaryType() {
		return dictionaryType;
	}
	/**
	 * 设置字典类别，可以被dataSource类使用来读取需要的字典信息
	 * @param dictionaryType
	 */
	public void setDictionaryType(String dictionaryType) {
		this.dictionaryType = dictionaryType;
	}

	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
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
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUseType() {
		return useType;
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
	public String getIsSpecialWidthType() {
		return isSpecialWidthType;
	}
	public void setIsSpecialWidthType(String isSpecialWidthType) {
		this.isSpecialWidthType = isSpecialWidthType;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}