/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.log.aop;

import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.admin.log.entity.SysLog;
import com.hisun.saas.sys.admin.log.service.SysLogService;
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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

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


    @Pointcut("@annotation(com.hisun.saas.sys.admin.log.aop.RequiresLog)")
    public void requiresLogPointcut(){}

    @AfterReturning(value = "requiresLogPointcut()")
    public void requiresLogAfterReturningCalls(JoinPoint joinPoint)throws Throwable{
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            RequiresLog requiresLog = method.getAnnotation(com.hisun.saas.sys.admin.log.aop.RequiresLog.class);
            int type = requiresLog.operateType().getType();
            String decription = requiresLog.operateType().getDescription()+requiresLog.description();

            saveLog(decription,type,LogOperateStatus.NORMAL.getStatus());
        }

    }
    @AfterThrowing(pointcut = "requiresLogPointcut()", throwing = "e")
    public void requiresLogAfterThrowingCalls(JoinPoint joinPoint, Throwable e) {
        Method method = this.getMethod(joinPoint);
        if(method!=null){
            RequiresLog requiresLog = method.getAnnotation(com.hisun.saas.sys.admin.log.aop.RequiresLog.class);
            int type = requiresLog.operateType().getType();
            saveLog(e.getMessage(), type,LogOperateStatus.EXCEPTION.getStatus());
        }
    }



    public String optionContent(Object[] args, String clazzName, String mName) throws Exception {

        if (args == null) {
            return null;
        }

        StringBuilder rs = new StringBuilder();
        rs.append(clazzName + "." + mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            if (info == null) {
                continue;
            }
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            //rs.append("[参数" + index + "，类型：" + className + "，值：");
            rs.append("[参数" + index + "，类型：" + className);
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            rs.append("( " + info.toString() + ")");
            /*if(isWrapClassOrString(info.getClass())){
				// 将值加入内容中
				rs.append("( "+info.toString() + ")");
			}else {

				// 遍历方法，判断get方法
				for (Method method : methods) {

					String methodName = method.getName();
					// 判断是不是get方法
					if (methodName.indexOf("get") == -1) {// 不是get方法
						continue;// 不处理
					}

					Object rsValue;
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);

					if (rsValue == null) {// 没有返回值
						continue;
					}

					// 将值加入内容中
					rs.append("(" + rsValue + ")");
				}
			}*/
            rs.append("]");

            index++;
        }

        return rs.toString();
    }

    private void saveLog(String description ,int type,int status){
        String ip = this.getIp();
        UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        try {
            if (userLoginDetails != null) {
                if (Constants.USER_TYPE_ADMIN == userLoginDetails.getUserType()) {
                    SysLog log = new SysLog();
                    log.setUserId(userLoginDetails.getUserid());
                    log.setUserName(userLoginDetails.getUsername());
                    log.setOperateTime(new Date());
                    log.setIp(ip);
                    log.setContent(description);
                    log.setType(type);
                    log.setStatus(status);
                    this.logService.save(log);
                } else {
                    TenantLog tenantLog = new TenantLog();
                    tenantLog.setUserId(userLoginDetails.getUserid());
                    tenantLog.setUserName(userLoginDetails.getUsername());
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
}
