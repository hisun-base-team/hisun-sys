/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.taglib.selectTag;

import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;
/**
 * @author liuzj {279421824@qq.com}
 */

public final class SelectTag extends BodyTagSupport {
        //用于存储自定义属性 dictionaryType:bo1_type
    private String userParameter="";
    private String id;//显示层的id  (必填项)
    private String selectUrl;//调用下拉菜单的url
    private String width;//文本框的宽度
    private String height="33";//文本的高度
    private String radioOrCheckbox; //单选还是复选 radio单选 checkbox复选
    private String onchange;//改变事件
    private String textClass;//文本框样式
    private Style style = new Style();//内部使用的样式
    private String moreSelectSearch; // true为显示多选的search栏
    private String moreSelectAll; // true为显示多选的全选
    private String defaultvalues;//默认的值 要有多个必须和key的内容对应  用,分开
    private String defaultkeys;//默认的知道的相应key值 要有多个必须和值的内容对应  用,分开
    private String token;
    @Override
    public int doStartTag() throws JspTagException {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        try{
            //调用控件前先初始化要显示层内内容为空
            StringBuffer results = new StringBuffer("");

            try {
//			CurrentUserAccountInterface userinfo  = SystemHelper.getLogin().getLoginer((HttpServletRequest) pageContext.getRequest());
                String unitCode="";  //当前用户所在的单位
//			if(userinfo!= null){
//				unitCode = userinfo.getUnitCode();
//			}
                results.append(this.toSlelctHtml());
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

    public String toSlelctHtml(){
        StringBuffer noTreeresults = new StringBuffer("");
        if(radioOrCheckbox.equals("radio")){
            //单选的输出
            noTreeresults.append("<select type=\"text\" name=\""+id+"\" id=\""+id+"\" ");
            if(this.textClass!=null && !this.textClass.equals("")){
                noTreeresults.append(" class=\""+this.textClass+"\"");
            }
            if(this.textClass!=null && !this.textClass.equals("")){
                noTreeresults.append(" class=\""+this.textClass+"\"");
            }
            if(this.getStyle()!=null){
                if(width!=null && !width.equals("")){
                    try{
                        this.getStyle().addStyles("margin-bottom: 0px;width:"+width+";");
                    }catch(Exception e){
                    }
                }


                noTreeresults.append(" "+this.getStyle().toHtml());
            }else {

                if(width!=null && !width.equals("")){
                } else {
                    try {
                        this.getStyle().addStyles("margin-bottom: 0px;width:"+width+";");
                    } catch (Exception e) {
                    }
                }
            }
            noTreeresults.append(" >");
//            try {
//                SelectDataSource obj = ApplicationContextUtil.getBean(this.dataSource, AbstractSelectObject.class);
//                if(obj.getDataOptions()!=null && obj.getDataOptions().size()>0){
//                    List<SelectNode> selectNodes = obj.getDataOptions();
//                    for(SelectNode node : selectNodes){
//                        noTreeresults.append("<option value=\""+ StringUtils.trimNull2Empty(node.getOptionKey())+"\"");
//                        if(defaultkeys!=null && defaultkeys!="") {
//                            String[] defaultkey = defaultkeys.split(",");
//                            for(int i=0;i<defaultkey.length;i++) {
//                                if (defaultkey[i] != null && defaultkey[i].equals(node.getOptionKey())) {
//                                    noTreeresults.append("selected");
//                                }
//                            }
//                        }
//                        noTreeresults.append(">"+StringUtils.trimNull2Empty(node.getOptionValue())+"</option>");
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();;
//            }

            noTreeresults.append("</select>");
            noTreeresults.append("<input type=\"hidden\" id=\""+id+"_tagDefineAtt\" radioOrCheckbox=\""+radioOrCheckbox+"\" url=\""+selectUrl+"\" defaultkeys=\""+defaultkeys+"\" token=\""+token+"\" userParameter=\""+userParameter+"\">");
            noTreeresults.append("<script type=\"text/javascript\">");
            noTreeresults.append("selectLoadByTag(\""+selectUrl+"\",\""+id+"\",\""+token+"\");");
            noTreeresults.append("</script>");
        }else{
            //复选的输出
            noTreeresults.append("<div>");
            noTreeresults.append("<input type=\"hidden\" name='"+id+"' id='"+id+"' value=\"\"/>");
            noTreeresults.append("<select type=\"text\" name=\""+id+"_Select\" id=\""+id+"_Select\" inputId='"+id+"' multiple=\"multiple\" value=\"\"");
            if(this.textClass!=null && !this.textClass.equals("")){
                noTreeresults.append(" class=\""+this.textClass+"\"");
            }
            if(this.textClass!=null && !this.textClass.equals("")){
                noTreeresults.append(" class=\""+this.textClass+"\"");
            }
            if(this.getStyle()!=null){
                if(width!=null && !width.equals("")){
                    try{
                        this.getStyle().addStyles("margin-bottom: 0px;width:"+width+";");
                    }catch(Exception e){
                    }
                }


                noTreeresults.append(" "+this.getStyle().toHtml());
            }else {

                if(width!=null && !width.equals("")){
                } else {
                    try {
                        this.getStyle().addStyles("margin-bottom: 0px;width:"+width+";");
                    } catch (Exception e) {
                    }
                }
            }
            noTreeresults.append(" >");
//            try {
//                SelectDataSource obj = ApplicationContextUtil.getBean(this.dataSource, AbstractSelectObject.class);
//                if(obj.getDataOptions()!=null && obj.getDataOptions().size()>0){
//                    List<SelectNode> selectNodes = obj.getDataOptions();
//                    for(SelectNode node : selectNodes){
//                        noTreeresults.append("<option value=\""+StringUtils.trimNull2Empty(node.getOptionKey())+"\"");
//                        if(defaultkeys!=null&& defaultkeys!="") {
//                            String[] defaultkey = defaultkeys.split(",");
//                            for(int i=0;i<defaultkey.length;i++) {
//                                if (defaultkey[i] != null && defaultkey[i].equals(node.getOptionKey())) {
//                                    noTreeresults.append(" selected");
//                                }
//                            }
//                        }
//                        noTreeresults.append(">"+StringUtils.trimNull2Empty(node.getOptionValue())+"</option>");
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();;
//            }

            noTreeresults.append("</select>");
            noTreeresults.append("</div>");
            noTreeresults.append("<input type=\"hidden\" id=\""+id+"_Select_tagDefineAtt\" radioOrCheckbox=\""+radioOrCheckbox+"\" url=\""+selectUrl+"\" buttonHeight=\""+height+"\"" +
                    " token=\""+token+"\" userParameter=\""+userParameter+"\" defaultkeys=\""+defaultkeys+"\" moreSelectSearch=\""+moreSelectSearch+"\" moreSelectAll=\""+moreSelectAll+"\">");
            noTreeresults.append("<script type=\"text/javascript\">");
            noTreeresults.append("selectLoadByTag(\""+selectUrl+"\",\""+id+"_Select\",\""+token+"\");");
//            noTreeresults.append("$('#"+id+"_Select').multiselect({");
//            noTreeresults.append("columns:1,");
//            noTreeresults.append("placeholder: '请选择...',");
//            if(moreSelectSearch!=null && moreSelectSearch.equals("yes")) {
//                noTreeresults.append("search: true,");
//            }
//            noTreeresults.append("selectGroup: true,");
//            if(moreSelectAll!=null && moreSelectAll.equals("yes")) {
//                noTreeresults.append("selectAll: true");
//            }
//            noTreeresults.append("});");
            noTreeresults.append("</script>");
        }
        return noTreeresults.toString();
    }

    public String getUserParameter() {
        return userParameter;
    }

    public void setUserParameter(String userParameter) {
        this.userParameter = userParameter;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSelectUrl() {
        return selectUrl;
    }

    public void setSelectUrl(String selectUrl) {
        this.selectUrl = selectUrl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getTextClass() {
        return textClass;
    }

    public void setTextClass(String textClass) {
        this.textClass = textClass;
    }

    public String getMoreSelectSearch() {
        return moreSelectSearch;
    }

    public void setMoreSelectSearch(String moreSelectSearch) {
        this.moreSelectSearch = moreSelectSearch;
    }

    public String getMoreSelectAll() {
        return moreSelectAll;
    }

    public void setMoreSelectAll(String moreSelectAll) {
        this.moreSelectAll = moreSelectAll;
    }

    public String getDefaultvalues() {
        return defaultvalues;
    }

    public void setDefaultvalues(String defaultvalues) {
        this.defaultvalues = defaultvalues;
    }

    public String getDefaultkeys() {
        return defaultkeys;
    }

    public void setDefaultkeys(String defaultkeys) {
        this.defaultkeys = defaultkeys;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getRadioOrCheckbox() {
        return radioOrCheckbox;
    }

    public void setRadioOrCheckbox(String radioOrCheckbox) {
        this.radioOrCheckbox = radioOrCheckbox;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
