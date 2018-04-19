/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.realm;

import com.google.common.collect.Sets;
import com.hisun.base.auth.Constants;
import com.hisun.base.auth.KaptchaUsernamePasswordToken;
import com.hisun.base.auth.realm.BaseRealm;
import com.hisun.base.entity.AbstractResource;
import com.hisun.base.entity.AbstractRole;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class UserRealm extends BaseRealm {

    @javax.annotation.Resource
    private UserService userService;

    @Value(value = "${kaptcha.activated}")
    private boolean captchaActivated;

    @javax.annotation.Resource
    private TenantUserService tenantUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserLoginDetails user = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Set<String> roleCodes = Sets.newHashSet();
        Set<String> permissions = Sets.newHashSet();
        List<AbstractRole> roles = user.getRoles();
        for (AbstractRole role : roles) {
            roleCodes.add(role.getRoleCode());
        }
        List<AbstractResource> resources = user.getResources();
        if (resources != null) {
            for (AbstractResource resource : resources) {
                String permission = resource.getPermission();
                permissions.add(permission);
            }
        }
        authorizationInfo.setRoles(roleCodes);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        KaptchaUsernamePasswordToken token = (KaptchaUsernamePasswordToken) authcToken;
//        if(StringUtils.equalsIgnoreCase(Constants.NO_CAPTCHA,token.getCaptcha())
//                &&StringUtils.equalsIgnoreCase(Constants.NO_PASSWORD,String.valueOf(token.getPassword()))&&!token.isAdminLogin()) {
//            String username = (String) token.getPrincipal();
//
//            SimpleAuthenticationInfo authenticationInfo;
//            if(!StringUtils.equalsIgnoreCase("sysadmin",username)) {
//
//                TenantUser user = tenantUserService.findByUsername(username);
//
//                authenticationInfo = new SimpleAuthenticationInfo(
//                        user.getUsername(), //用户名
//                        Constants.NO_PASSWORD, //密码
//                        ByteSource.Util.bytes(Constants.NO_PASSWORD),//salt=username+salt
//                        getName()  //realm name
//                );
//            }else{
//                User user = userService.findByUsername(username);
//
//                authenticationInfo = new SimpleAuthenticationInfo(
//                        user.getUsername(), //用户名
//                        Constants.NO_PASSWORD, //密码
//                        ByteSource.Util.bytes(Constants.NO_PASSWORD),//salt=username+salt
//                        getName()  //realm name
//                );
//            }
//            return authenticationInfo;
//        }else{
        //是否开启验证码
        if (captchaActivated) {
            String captcha = token.getCaptcha();
            String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (!StringUtils.equalsIgnoreCase("selenium-test", captcha)) {
                if (StringUtils.isBlank(captcha) || !StringUtils.equalsIgnoreCase(exitCode, captcha)) {
                    throw new GenericException("验证码错误");
                }
            }
        }
        String username = (String) token.getPrincipal();
        if (token.isAdminLogin()) {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UnknownAccountException();//没找到帐号
            }
            /*if(Boolean.TRUE.equals(user.isLocked())) {
                throw new LockedAccountException(); //帐号锁定
            }*/
            //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.getUsername(), //用户名
                    user.getPassword(), //密码
                    ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                    getName()  //realm name
            );
            return authenticationInfo;
        } else {
            TenantUser user = tenantUserService.findByUsername(username);
            if (user == null) {
                throw new UnknownAccountException();//没找到帐号
            }
            /*if(Boolean.TRUE.equals(user.isLocked())) {
                throw new LockedAccountException(); //帐号锁定
            }*/
            //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.getUsername(), //用户名
                    user.getPassword(), //密码
                    ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        // }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        return super.isPermitted(principals, permission);
    }


}
