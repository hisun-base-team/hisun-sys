/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.privilege;

import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.util.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Component
@Aspect
public class PrivilegeInterceptor {

    @Pointcut("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions) && @annotation(com.hisun.saas.sys.privilege.RequiresPrivileges)")
    public void requiresPermissionsPointcut() {
    }

    @Before(value = "requiresPermissionsPointcut()")
    public void requiresPermissionsCalls(JoinPoint joinPoint) throws Throwable {
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            RequiresPermissions requiresPermissions = method.getAnnotation(org.apache.shiro.authz.annotation.RequiresPermissions.class);
            RequiresPrivileges requiresPrivileges = method.getAnnotation(com.hisun.saas.sys.privilege.RequiresPrivileges.class);
            ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            String[] permissions = requiresPermissions.value();//获取权限代码
            if(Arrays.asList(parameterNames).contains("privilegeDetails")){
                Logical logical =requiresPermissions.logical();
                String privilegeCode = requiresPrivileges.value();
                if(!StringUtils.isEmpty(privilegeCode)){
                    //多权限逻辑关系
                    PrivilegeDetails privilegeDetails = this.getPrivilegeDetails(joinPoint);
                    if(logical.equals(Logical.OR)){
                        privilegeDetails.setLogic(PrivilegeDetails.OR);
                    }else{
                        privilegeDetails.setLogic(PrivilegeDetails.AND);
                    }
                    //通过permisson,privilegeCode,当前用户获取数据权限
                    //当前只支持同一permission,同一privilegeCode仅能设置一条数据权限
                    for(String permission : permissions){
                        privilegeDetails.getPrivilegeDetails().add(getPrivilegeDetail(permission,privilegeCode));
                    }
                }else{
                    throw new Exception("PrivilegeCode is null.");
                }
            }else{
                throw new Exception("Parameter 'privilegeDetails' not found.");
            }
        }else{
            throw new Exception("Method not found.");
        }

    }

    private PrivilegeDetails getPrivilegeDetails(JoinPoint joinPoint){
        PrivilegeDetails privilegeDetails =null;
        Object[] args = joinPoint.getArgs();
        for(Object param : args){
            if(param instanceof PrivilegeDetails){
                privilegeDetails =(PrivilegeDetails)param;
            }
        }
        return privilegeDetails;
    }

    private PrivilegeDetail getPrivilegeDetail(String permissions,String privilegeCode){
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        PrivilegeDetail privilegeDetail = new PrivilegeDetail();
        privilegeDetail.setPrivilegeCode(userLoginDetails.getUserid());
        privilegeDetail.setHqlFilterExpress(" a01.extRYCJ in('01','02') ");
        return privilegeDetail;
    }


    private Method getMethod(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        try {
            method = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method;
    }

}
