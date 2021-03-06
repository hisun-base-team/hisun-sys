package com.hisun.saas.sys.admin.user.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.LogOperateStatus;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.KaptchaUsernamePasswordToken;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.admin.log.entity.SysLog;
import com.hisun.saas.sys.admin.log.service.SysLogService;
import com.hisun.saas.sys.admin.message.entity.Notice;
import com.hisun.saas.sys.admin.message.service.NoticeService;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.util.AddressUtil;
import com.hisun.util.WrapWebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.owasp.csrfguard.CsrfGuard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminLoginController extends BaseController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private UserService userService;
	@Resource
	private SysLogService logService;
	@Resource
	private NoticeService noticeService;
	
	@Value("${elasticsearch.ip}")
	private String elasticsearchUrl;
	@Value("${elasticsearch.rest.port}")
	private String elasticsearchPort;
	@Value("${sys.admin.name}")
	private String sysAdminName;
	@Value("${sys.favicon}")
	private String favicon;
	@Value("${sys.login.logo}")
	private String sysLoginLogo;
	@Value("${sys.copyright}")
	private String sysCopyright;
	@Value(value= "${kaptcha.activated}")
	private boolean captchaActivated;
	@Value(value = "${communication.sms.on}")
	private boolean smsOn;
	@Value(value = "${sys.deploy.internet}")
	private boolean outernet;

	@RequestMapping(value = "/signin")
	public String signin(User loginUser, Model model, boolean remember, String code, HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		KaptchaUsernamePasswordToken token = new KaptchaUsernamePasswordToken(
				loginUser.getUsername(), loginUser.getPassword(),false,code,true);
		//if(remember){
			token.setRememberMe(false);
		//}
		try {
			subject.login(token);
			UserLoginDetails userLoginDetails = userService.findUserLoginDetails(loginUser.getUsername());
			User user = userService.getByPK(userLoginDetails.getUserid());
			subject.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
			String ip = this.getIp();
			SysLog log = new SysLog();
			log.setUserId(userLoginDetails.getUserid());
			log.setUserName(userLoginDetails.getRealname());
			log.setOperateTime(new Date());
			log.setIp(ip);
			log.setContent("");
			log.setType(LogOperateType.LOGIN.getType());
			log.setStatus(LogOperateStatus.NORMAL.getStatus());
			this.logService.save(log);
			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			csrfGuard.updateToken(WrapWebUtils.getSession());
			if(user.getAdmin()){
				return "redirect:/sys/tenant/tenant/list?OWASP_CSRFTOKEN="+ WrapWebUtils.getSession().getAttribute("OWASP_CSRFTOKEN");
			}else{
				return "redirect:/sys/admin/user/profile?OWASP_CSRFTOKEN="+ WrapWebUtils.getSession().getAttribute("OWASP_CSRFTOKEN");
			}
		} catch (AuthenticationException e) {
			token.clear();
			String content = "";
			if(e.getCause() instanceof GenericException){
				content="验证码错误!";
				model.addAttribute("error", "2");
			} else if(e instanceof ExcessiveAttemptsException){
				content="当天错误输入5次密码，该账号已被锁定!";
				model.addAttribute("error","3");
			}else if(e instanceof UnknownAccountException){
				content="不存在此账号!";
				model.addAttribute("error","4");
			}else if(e instanceof LockedAccountException){
				content="账号已冻结!";
				model.addAttribute("error","5");
			} else{
				content = e.getMessage();
				model.addAttribute("error", "1");
			}
			logger.error(content);
			String ip = this.getIp();
			SysLog log = new SysLog();
			log.setUserName(loginUser.getUsername());
			log.setOperateTime(new Date());
			log.setIp(ip);
			log.setContent(content);
			log.setType(LogOperateType.LOGIN.getType());
			log.setStatus(LogOperateStatus.EXCEPTION.getStatus());
			this.logService.save(log);
			return "redirect:/admin/login";
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


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest req, Model model) {
		UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		if(SecurityUtils.getSubject().isAuthenticated()&&userLoginDetails!=null){
			return new ModelAndView("redirect:/sys/admin/user/list");
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("sysAdminName", sysAdminName);
		map.put("favicon",favicon);
		map.put("sysLoginLogo", sysLoginLogo);
		map.put("captchaActivated", captchaActivated);
		map.put("smsOn", smsOn);
		map.put("sysCopyright",sysCopyright);
		return new ModelAndView("saas/sys/admin/login",map);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)  
    public String logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        Subject currentUser = SecurityUtils.getSubject();  
        currentUser.logout();
		String ip = this.getIp();
		SysLog log = new SysLog();
		log.setUserId(userLoginDetails.getUserid());
		log.setUserName(userLoginDetails.getRealname());
		log.setOperateTime(new Date());
		log.setIp(ip);
		log.setContent("");
		log.setType(LogOperateType.LOGOUT.getType());
		log.setStatus(LogOperateStatus.NORMAL.getStatus());
		this.logService.save(log);
		return "redirect:/admin/login";
    }
	
	@RequestMapping(value = "/adminDashboard")
	public ModelAndView adminDashboard() {
		return  new ModelAndView("saas/sys/adminDashboard");
	}
	
	@RequestMapping(value = "/forgot")
	public ModelAndView forgetPassword() {
		return  new ModelAndView("saas/sys/admin/user/forgot");
	}

	@RequiresPermissions("dashboard:*")
	@RequestMapping(value = "/dashboard")
	public ModelAndView dashboard() {
		ModelAndView modelAndView = null;
		UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		if(StringUtils.equals(Constants.ADMIN_USERNAME, userLoginDetails.getUsername())){
			modelAndView = new ModelAndView("redirect:/sys/admin/user/list");
		}else{
			Map<String, Object> map = Maps.newHashMap();
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" pushWay = :pushWay ", "pushWay", Short.valueOf("2")));
			query.add(CommonRestrictions.and(" status = :status ", "status", Boolean.TRUE));
			CommonOrderBy orderBy = new CommonOrderBy();
			List<Notice> resultList = noticeService.list(query, orderBy, 1, 20);
			map.put("noticeContents", resultList);
			modelAndView = new ModelAndView("saas/sys/admin/dashboard",map);
		}
		return modelAndView;
	}
	

}

