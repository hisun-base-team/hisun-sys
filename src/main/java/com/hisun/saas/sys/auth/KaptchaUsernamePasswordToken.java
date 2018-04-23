/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class KaptchaUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	// 验证码字符串
	private String captcha;

    // 判断前后台登录
    private boolean adminLogin;

	public KaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String captcha, boolean adminLogin) {
		super(username, password, rememberMe);
		this.captcha = captcha;
        this.adminLogin = adminLogin;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

    public boolean isAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(boolean adminLogin) {
        this.adminLogin = adminLogin;
    }
}
