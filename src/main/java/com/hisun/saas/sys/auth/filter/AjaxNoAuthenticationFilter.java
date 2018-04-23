/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class AjaxNoAuthenticationFilter extends PathMatchingFilter {

    public static final String DEFAULT_LOGIN_URL = "/login.jsp";
    private String loginUrl = DEFAULT_LOGIN_URL;
    private String returnJson = null;

    protected PatternMatcher pathMatcher = new AntPathMatcher();
    public AjaxNoAuthenticationFilter(String returnJson){
        this.returnJson = returnJson;
    }

    public String getLoginUrl() {
        return loginUrl;
    }
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        if(pathsMatch(loginUrl,request)){
            return true;
        }
        if("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))){
            Subject currentUser = SecurityUtils.getSubject();
            if(currentUser != null && currentUser.isAuthenticated()){
                return true;
            }
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.getWriter().print(returnJson.trim());
            return false;
        }else{
            return true;
        }
    }
}

