/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class Constants {
    public static final String CURRENT_USER = "user";
    public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";
    
    public static final String RESOURCE_QUERY_CODE = "queryCode";
	public static final String MENU_RESOURCE_ID = "menuResourceId";
    public static final String RESOURCE_ID = "id";
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PERMISSION = "all:admin";

	public static final Integer USER_TYPE_ADMIN = Integer.valueOf(0);//平台
	public static final Integer USER_TYPE_TENANT = Integer.valueOf(1);//租户

	public static final String HEAD_PHOTO_PATH = "/platform/user/photo/";
	public static final String SOP_TENANT_ID = "_SOP_TENANT_ID";
	public static final String SOP_TENANT_USER_ID = "_SOP_TENANT_USER_ID";

	public static final String NO_PASSWORD = "NO_PASSWORD";
	public static final String NO_CAPTCHA = "NO_CAPTCHA";


	public static final String PDF_TYPE = "PDF"; //导出格式为PDF

	public static final String WORD_TYPE = "WORD";//导出格式为WORD

	public static final String HTML_TYPE = "HTML";//导出格式为html

	public static final String EXCEL_TYPE = "EXCEL";//导出格式为EXCEL

}
