/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.credentials;

import com.hisun.saas.sys.auth.KaptchaUsernamePasswordToken;
import com.hisun.saas.sys.auth.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        KaptchaUsernamePasswordToken usernamePasswordToken = (KaptchaUsernamePasswordToken) token;
        if (StringUtils.equalsIgnoreCase(Constants.NO_PASSWORD,
                String.valueOf(usernamePasswordToken.getPassword()))
                &&StringUtils.equalsIgnoreCase(Constants.NO_CAPTCHA,usernamePasswordToken.getCaptcha())) {
            return true;
        }else{
            String username = (String) token.getPrincipal();
            AtomicInteger retryCount = passwordRetryCache.get(username);
            if (retryCount == null) {
                retryCount = new AtomicInteger(0);
                passwordRetryCache.put(username, retryCount);
            }
            if (retryCount.incrementAndGet() > 5) {
                throw new ExcessiveAttemptsException();
            }
            boolean matches = super.doCredentialsMatch(token, info);
            if (matches) {
                passwordRetryCache.remove(username);
            }
            return matches;
        }
    }
}
