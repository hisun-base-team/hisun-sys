/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.log.aop;

import com.hisun.base.auth.Constants;
import com.hisun.saas.sys.admin.log.entity.Log;
import com.hisun.saas.sys.admin.log.service.LogService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.util.AddressUtil;
import com.hisun.util.WrapWebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

	private  static  final Logger logger = LoggerFactory.getLogger(LogAspect.class);  
	
	@Resource
	private LogService logService;
	@Resource
	private TenantLogService tenantlogService;

	@Value(value = "${communication.sms.on}")
	private boolean smsOn;
	@Value(value = "${sys.deploy.internet}")
	private boolean outernet;

	@Pointcut("execution(* com.hisun.saas..*.service..*Impl.save*(..)) || execution(* com.hisun.saas..*.service..*Impl.add*(..))")
	public void insertServiceCall() {
		//Nothing.
	}

	@Pointcut("execution(* com.hisun.saas..*.service..*Impl.update*(..)) || execution(* com.hisun.saas..*.service..*Impl.edit*(..))")
	public void updateServiceCall() {
		//Nothing.
	}

	@Pointcut("execution(* com.hisun.saas..*.service..*Impl.delete*(..)) || execution(* com.hisun.saas..*.service..*Impl.remove*(..))")
	public void deleteServiceCall() {
		//Nothing.
	}

	/**
	 * 管理员添加操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "insertServiceCall()", argNames = "rtv", returning = "rtv")
	public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
		saveLog(joinPoint,Short.valueOf("1"));
	}

	/**
	 * 管理员修改操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "updateServiceCall()", argNames = "rtv", returning = "rtv")
	public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv)
			throws Throwable {
		saveLog(joinPoint,Short.valueOf("2"));
	}

	/**
	 * 管理员删除影片操作(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "deleteServiceCall()", argNames = "rtv", returning = "rtv")
	public void deleteServiceCallCalls(JoinPoint joinPoint, Object rtv)
			throws Throwable {
		saveLog(joinPoint,Short.valueOf("3"));
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "insertServiceCall()", throwing = "e")
	public void doAfterThrowingInsertServiceCall(JoinPoint joinPoint, Throwable e) {
		saveLog(e);
	}
	
	@AfterThrowing(pointcut = "updateServiceCall()", throwing = "e")
	public void doAfterThrowingUpdateServiceCall(JoinPoint joinPoint, Throwable e) {
		saveLog(e);
	}
	
	@AfterThrowing(pointcut = "deleteServiceCall()", throwing = "e")
	public void doAfterThrowingDeleteServiceCall(JoinPoint joinPoint, Throwable e) {
		saveLog(e);
	}

	private void saveLog(Throwable e) {
		// 获取方法名
		//String methodName = joinPoint.getSignature().getName();
		
		String content = e.toString();
		String ip = "" ;
		
		ip = WrapWebUtils.getRemoteIp();
		if(StringUtils.isBlank(ip)){
			ip = "定时器自动触发无ip";
		}else{
			try {
				if(outernet) {
					ip = AddressUtil.getIpInformation(ip);
				}
			} catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
			}
		}
		 try {
			Log log = new Log();
			UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
			if(userLoginDetails==null){
				return;
			}

			log.setIp(ip);
			log.setContent(content);
			log.setCreateTime(new Date());
			log.setType(Short.valueOf("6"));
			 log.setUserId(userLoginDetails.getUser().getId());
			if(Constants.USER_TYPE_TENANT == userLoginDetails.getUserType()){
				TenantLog tenantLog = new TenantLog();
				BeanUtils.copyProperties(log, tenantLog);
				tenantLog.setTenant(new Tenant(userLoginDetails.getTenantId()));
				tenantlogService.log(tenantLog);
			}else{
				logService.log(log);
			}

		} catch (Exception e1) {
			logger.error(e1.getMessage(),e1);
		}
	}

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 */
	public String optionContent(Object[] args,String clazzName, String mName) throws Exception {

		if (args == null) {
			return null;
		}

		StringBuilder rs = new StringBuilder();
		rs.append(clazzName+"."+mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			if(info==null){
				continue;
			}
			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			//rs.append("[参数" + index + "，类型：" + className + "，值：");
			rs.append("[参数" + index + "，类型：" + className );
			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			rs.append("( "+info.toString() + ")");
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

	/**
	 *
	 * @param clz
	 * @return
     */
	public static boolean isWrapClassOrString(Class clz) {
		boolean bool;
		try {
			bool = StringUtils.equals("java.lang.String",clz.getName()) || ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	private void saveLog(JoinPoint joinPoint,short type) throws Exception {
		// 判断参数
		if (joinPoint.getArgs() == null) {// 没有参数
			return;
		}
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		
		String content = optionContent(joinPoint.getArgs(),joinPoint.getTarget().getClass().getName() ,methodName);
		
		String ip = "" ;
		
		ip = WrapWebUtils.getRemoteIp();
		if(StringUtils.isBlank(ip)){
			ip = "定时器自动触发无ip";
		}
		try {
			if(outernet) {
				ip = AddressUtil.getIpInformation(ip);
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(),e1);
		} finally{
			Log log = new Log();
			try{
				UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
				if(userLoginDetails!=null) {
					log.setIp(ip);
					log.setContent(content);
					log.setCreateTime(new Date());
					log.setType(type);
					log.setUserId(userLoginDetails.getUserid());
					if (Constants.USER_TYPE_TENANT == userLoginDetails.getUserType()) {
						TenantLog tenantLog = new TenantLog();
						BeanUtils.copyProperties(log, tenantLog);
						tenantLog.setTenant(new Tenant(userLoginDetails.getTenantId()));
						tenantlogService.log(tenantLog);
					} else {
						logService.log(log);
					}
					logService.log(log);
				}
			}catch(Exception e){
				//都是没有SESSION的报错，不输出日志
				logger.error(e.getMessage(),e);
			}
			
		}
	}
}
