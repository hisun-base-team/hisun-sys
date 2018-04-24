/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.log;

import com.hisun.saas.sys.admin.log.entity.SysLog;
import com.hisun.saas.sys.admin.log.service.SysLogService;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.util.AddressUtil;
import com.hisun.util.WrapWebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private SysLogService logService;
    @Resource
    private TenantLogService tenantlogService;

    @Value(value = "${communication.sms.on}")
    private boolean smsOn;
    @Value(value = "${sys.deploy.internet}")
    private boolean outernet;


    @Pointcut("@annotation(com.hisun.saas.sys.log.RequiresLog)")
    public void requiresLogPointcut(){}

    @AfterReturning(value = "requiresLogPointcut()")
    public void requiresLogAfterReturningCalls(JoinPoint joinPoint)throws Throwable{
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            RequiresLog requiresLog = method.getAnnotation(RequiresLog.class);
            int type = requiresLog.operateType().getType();
            String decription = requiresLog.description();
            String parseDescription = parseDescription(decription,joinPoint,method);
            //可对描述进行扩展,以提高可读性
            saveLog(parseDescription,type,LogOperateStatus.NORMAL.getStatus());
        }

    }

    @AfterThrowing(pointcut = "requiresLogPointcut()", throwing = "e")
    public void requiresLogAfterThrowingCalls(JoinPoint joinPoint, Throwable e) {
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            RequiresLog requiresLog = method.getAnnotation(RequiresLog.class);
            int type = requiresLog.operateType().getType();
            String decription = requiresLog.description()+",错误:"+e.getMessage();
            saveLog(decription, type,LogOperateStatus.EXCEPTION.getStatus());
        }
    }

    private void saveLog(String description ,int type,int status){
        String ip = this.getIp();
        UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        try {
            if (userLoginDetails != null) {
                if (Constants.USER_TYPE_ADMIN == userLoginDetails.getUserType()) {
                    SysLog log = new SysLog();
                    log.setUserId(userLoginDetails.getUserid());
                    log.setUserName(userLoginDetails.getRealname());
                    log.setOperateTime(new Date());
                    log.setIp(ip);
                    log.setContent(description);
                    log.setType(type);
                    log.setStatus(status);
                    this.logService.save(log);
                } else {
                    TenantLog tenantLog = new TenantLog();
                    tenantLog.setUserId(userLoginDetails.getUserid());
                    tenantLog.setUserName(userLoginDetails.getRealname());
                    tenantLog.setOperateTime(new Date());
                    tenantLog.setIp(ip);
                    tenantLog.setContent(description);
                    tenantLog.setType(type);
                    tenantLog.setStatus(status);
                    this.tenantlogService.save(tenantLog);
                }
            } else {
                SysLog log = new SysLog();
                log.setUserId("");
                log.setUserName("后台程序");
                log.setOperateTime(new Date());
                log.setIp(ip);
                log.setContent(description);
                log.setType(type);
                log.setStatus(status);
                this.logService.save(log);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private String getIp(){
        String ip = WrapWebUtils.getRemoteIp();
        if(StringUtils.isEmpty(ip)){
            ip="localhost";
        }else{
            try {
                if (outernet) {
                    ip = AddressUtil.getIpInformation(ip);
                }
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return ip;
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

    private String  parseDescription(String src, JoinPoint joinPoint,Method method)throws Exception{
        String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        Matcher matcher = Pattern.compile(reg).matcher(src);
        List<String> descs = new ArrayList<String>();
        while (matcher.find()) {
            descs.add(matcher.group());
        }
        if(descs.size()>0){
            for(String desc : descs){
                String paramName = null;
                String fieldName = null;
                int dot = desc.indexOf(".");
                if(dot>-1){
                     paramName = desc.substring(0,dot);
                     fieldName = desc.substring(dot+1,desc.length());
                }else{
                     paramName = desc;
                }
                String value = this.getValue(paramName,fieldName,joinPoint,method);
                src = src.replace("${"+desc+"}",value);
            }
        }
        return src;
    }

    private String getValue(String param,String field,JoinPoint joinPoint,Method method)throws Exception{
        String value = "";
        int paramIndex = 0;
        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        int j = 0;
        for(String paramName : parameterNames){
            if(param.equals(paramName)){
                paramIndex = j;
            }
            j++;
        }
        Object[] args = joinPoint.getArgs();
        if(field!=null){
            Object obj = args[paramIndex];
            Method m = findGetMethod(obj.getClass(),field);
            if(m!=null){
                Object valueObj =  m.invoke(obj);
                value = com.hisun.util.StringUtils.trimNull2Empty(String.valueOf(valueObj==null?"":valueObj));
            }
        }else{
            value = com.hisun.util.StringUtils.trimNull2Empty(String.valueOf(args[paramIndex]==null?"":args[paramIndex]));
        }
        return value;
    }

    private  Method findGetMethod(Class clz,String name)throws Exception{
        Method method =null;
        try {
            method = clz.getMethod("get" + getMethodName(name));
        }catch (NoSuchMethodException e){

        }
        return  method;
    }

    private  String getMethodName(String fieldName) throws Exception{
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
