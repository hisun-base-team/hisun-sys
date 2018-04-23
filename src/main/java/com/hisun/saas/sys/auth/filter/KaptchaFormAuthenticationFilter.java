/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.filter;

import com.hisun.saas.sys.auth.KaptchaUsernamePasswordToken;
import com.hisun.saas.sys.auth.http.ClientSavedRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class KaptchaFormAuthenticationFilter extends FormAuthenticationFilter {

    public KaptchaFormAuthenticationFilter() {
        super();
    }

    public static final String DEFAULT_CAPTCHA_PARAM = "kaptcha";
    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected KaptchaUsernamePasswordToken createToken(ServletRequest request,
                                                       ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        boolean adminLogin = false;
        if (new AntPathMatcher().match(StringUtils.trim("/admin/login"), WebUtils.toHttp(request).getServletPath())) {
            adminLogin = true;
        }
        return new KaptchaUsernamePasswordToken(username,
                password, rememberMe, captcha, adminLogin);
    }

    @Override
    protected void saveRequest(ServletRequest request) {
        super.saveRequest(request);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (new AntPathMatcher().match(StringUtils.trim("/admin/login"), WebUtils.toHttp(request).getServletPath())) {
            ClientSavedRequest savedRequest = new ClientSavedRequest(WebUtils.toHttp(request), "/admin/login");
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
            return true;
        } else {
            return super.onAccessDenied(request, response);
        }
    }
}
