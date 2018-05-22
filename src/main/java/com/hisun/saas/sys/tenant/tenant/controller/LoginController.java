/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.controller;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.admin.message.service.NoticeService;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.KaptchaUsernamePasswordToken;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.LogOperateStatus;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.util.AddressUtil;
import com.hisun.util.WrapWebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("")
public class LoginController extends BaseController {
    @Resource
    private TenantUserService tenantUserService;
    @Resource
    private TenantLogService tenantLogService;
    @Resource
    private NoticeService noticeService;
    @Resource
    private TenantRegisterService tenantRegisterService;
    @Value("${elasticsearch.ip}")
    private String elasticsearchUrl;
    @Value("${elasticsearch.rest.port}")
    private String elasticsearchPort;
    @Value("${sys.name}")
    private String sysName;
    @Value("${sys.favicon}")
    private String favicon;
    @Value("${sys.login.logo}")
    private String sysLoginLogo;
    @Value("${sys.main.logo}")
    private String mainLogo;
    @Value("${sys.copyright}")
    private String sysCopyright;
    @Value(value= "${kaptcha.activated}")
    private boolean captchaActivated;
    @Value(value = "${communication.sms.on}")
    private boolean smsOn;
    @Value(value = "${sys.deploy.internet}")
    private boolean outernet;

    @RequestMapping(value = "/signin")
    public String signin(TenantUser loginUser, Model model, boolean remember, String kaptcha, HttpServletRequest req) {
        Subject currentUser = SecurityUtils.getSubject();
        KaptchaUsernamePasswordToken token = new KaptchaUsernamePasswordToken(loginUser.getUsername(), loginUser.getPassword(),false,kaptcha,false);
        token.setRememberMe(false);
        try {
            currentUser.login(token);
            UserLoginDetails userLoginDetails = tenantUserService.findUserLoginDetails(loginUser.getUsername());
            currentUser.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
            String ip = this.getIp();
            TenantLog log = new TenantLog();
            log.setUserId(userLoginDetails.getUserid());
            log.setUserName(userLoginDetails.getRealname());
            log.setTenant(userLoginDetails.getTenant());
            log.setOperateTime(new Date());
            log.setIp(ip);
            log.setContent("");
            log.setType(LogOperateType.LOGIN.getType());
            log.setStatus(LogOperateStatus.NORMAL.getStatus());
            this.tenantLogService.save(log);
            CsrfGuard csrfGuard = CsrfGuard.getInstance();
            csrfGuard.updateToken(WrapWebUtils.getSession());
            return "redirect:/zzb/dzda/dashboard?OWASP_CSRFTOKEN="+ WrapWebUtils.getSession().getAttribute("OWASP_CSRFTOKEN");
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
            TenantLog log = new TenantLog();
            log.setUserName(loginUser.getUsername());
            log.setOperateTime(new Date());
            log.setIp(ip);
            log.setContent(content);
            log.setType(LogOperateType.LOGIN.getType());
            log.setStatus(LogOperateStatus.EXCEPTION.getStatus());
            this.tenantLogService.save(log);
            return "redirect:/login";
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
    public ModelAndView login() {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        if(SecurityUtils.getSubject().isAuthenticated()&&userLoginDetails!=null){
            return new ModelAndView("redirect:/zzb/dzda/dashboard");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("sysName", sysName);
        map.put("favicon",favicon);
        map.put("sysLoginLogo", sysLoginLogo);
        map.put("captchaActivated", captchaActivated);
        map.put("smsOn", smsOn);
        map.put("sysCopyright",sysCopyright);
        return new ModelAndView("login",map);
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() throws IOException {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        String ip = this.getIp();
        TenantLog log = new TenantLog();
        log.setUserId(userLoginDetails.getUserid());
        log.setUserName(userLoginDetails.getRealname());
        log.setTenant(userLoginDetails.getTenant());
        log.setOperateTime(new Date());
        log.setIp(ip);
        log.setContent("");
        log.setType(LogOperateType.LOGOUT.getType());
        log.setStatus(LogOperateStatus.NORMAL.getStatus());
        this.tenantLogService.save(log);
        return "redirect:/login";
    }

    //@RequiresPermissions("tenant:dashboard")
    @RequestMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = null;
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("logo", mainLogo);
        modelAndView = new ModelAndView("saas/sys/tenant/dashboard", map);
        return modelAndView;
    }

    @RequestMapping(value = "/signup")
    public String signup(){
        return "redirect:/dashboard";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView openRegister(HttpServletRequest request){
        String path = request.getContextPath();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("path",path);
        return new ModelAndView("register",model);
    }
}