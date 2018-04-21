/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.privilege;

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

    @Pointcut("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions)")
    public void requiresPermissionsPointcut() {
    }

    @Before(value = "requiresPermissionsPointcut()")
    public void requiresPermissionsCalls(JoinPoint joinPoint) throws Throwable {
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            //取得@RequiresPermissions
            RequiresPermissions requiresPermissions = method.getAnnotation(org.apache.shiro.authz.annotation.RequiresPermissions.class);
            String[] permissions = requiresPermissions.value();//获取配置的权限代码
            if(permissions!=null){
                for(String permission : permissions){

                }
                ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
                String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
                if(Arrays.asList(parameterNames).contains("requiresPrivileges")){
                    Object[] args = joinPoint.getArgs();
                    for(Object param : args){
                        if(param instanceof PrivilegeDetails){
                            PrivilegeDetail privilegeDetail = new PrivilegeDetail();
                            privilegeDetail.setPrivilegeCode("A");
                            privilegeDetail.setHqlFilterExpress(" a01.extRYCJ in('01','02') ");
                            ((PrivilegeDetails)param).getPrivilegeDetails().add(privilegeDetail);
                        }
                    }
                }
            }
        }

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
