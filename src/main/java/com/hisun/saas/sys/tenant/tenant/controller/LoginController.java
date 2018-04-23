/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.KaptchaUsernamePasswordToken;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.entity.AbstractRole;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.admin.message.service.NoticeService;
import com.hisun.saas.sys.admin.resource.vo.ResourceMenuItem;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.util.AddressUtil;
import com.hisun.util.WrapWebUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.owasp.csrfguard.CsrfGuard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Value("${sys.name}")
    private String loginTitle;

    @Value("${sys.login.logo}")
    private String loginLogo;

    @Value("${sys.main.logo}")
    private String logo;

    @Value(value= "${kaptcha.activated}")
    private boolean captchaActivated;

    @Value(value= "${sys.domain}")
    public String domain;

    @Value(value = "${communication.sms.on}")
    private boolean smsOn;

    @RequestMapping(value = "/signin")
    public String signin(TenantUser loginUser, Model model, boolean remember, String kaptcha, HttpServletRequest req) {
        Subject currentUser = SecurityUtils.getSubject();
        KaptchaUsernamePasswordToken token = new KaptchaUsernamePasswordToken(
                loginUser.getUsername(), loginUser.getPassword(),false,kaptcha,false);
        token.setRememberMe(false);
        try {
            currentUser.login(token);
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" username = :username","username", loginUser.getUsername()));
            List<TenantUser> userList = tenantUserService.list(query, null,1,1);
            TenantUser user = userList.size()==0?null:userList.get(0);
            if(user.getTenant() == null){
                model.addAttribute("error","4");
                model.addAttribute("username", loginUser.getUsername());
                return "redirect:/login";
            }
            if(user.getTombstone() == TombstoneEntity.TOMBSTONE_TRUE){
                model.addAttribute("error","5");
                model.addAttribute("username", loginUser.getUsername());
                return "redirect:/login";
            }
            UserLoginDetails userLoginDetails = tenantUserService.findUserLoginDetails(loginUser.getUsername());

            if(user!=null && user.getLocked()){
                model.addAttribute("error","3");
                model.addAttribute("username", loginUser.getUsername());
                return "redirect:/login";
            }else{
                userLoginDetails.setUserType(Constants.USER_TYPE_TENANT);
                currentUser.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);

                TenantLog log = new TenantLog();
                String ip = WrapWebUtils.getRemoteIp();
                try {
                    if(smsOn) {
                        ip = AddressUtil.getIpInformation(ip);
                    }
                } catch (Exception e1) {
                    logger.error(e1.getMessage());
                } finally{
                    log.setIp(ip);
                    log.setUserId(userLoginDetails.getUserid());
                    log.setOperateTime(new Date());
                    log.setType(Short.valueOf("4"));
                    log.setTenant(new Tenant(userLoginDetails.getTenantId()));
                    tenantLogService.log(log);
                }
                return "redirect:/dashboard";
            }
        } catch (AuthenticationException e) {
            token.clear();
            if(e.getCause() instanceof GenericException){
                logger.error("验证码错误!");
                model.addAttribute("error", "2");
            } else if(e instanceof ExcessiveAttemptsException){
                logger.error("当天错误输入5次密码，该账号已被锁定!");
                model.addAttribute("error","3");
            } else{
                logger.error(e,e);
                //用户不存在有两种情况，1用户打错用户名或密码，2是未激活，用户表没有.
                TenantRegister register = tenantRegisterService.getByUsername(loginUser.getUsername());
                if(register==null){
                    model.addAttribute("error", "1");
                }else{
                    model.addAttribute("error", "6");
                }

            }
            model.addAttribute("username", loginUser.getUsername());

            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest req, Model model) {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        if(SecurityUtils.getSubject().isAuthenticated()&&userLoginDetails!=null){
            return new ModelAndView("redirect:/dashboard");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("captchaActivated", captchaActivated);
        map.put("loginLogo", loginLogo);
        map.put("loginTitle", loginTitle);
        return new ModelAndView("login",map);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        UserLoginDetails userLoginDetails = (UserLoginDetails) currentUser.getSession().getAttribute(Constants.CURRENT_USER);
        User user = new User();
        user.setId(userLoginDetails.getUserid());
        currentUser.logout();
        return "redirect:/login";
    }

    //@RequiresPermissions("tenant:dashboard")
    @RequestMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = null;
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        List<AbstractRole> roles = userLoginDetails.getRoles();
        boolean isAdmin = false;
        for (AbstractRole role : roles) {
            if (role.getRoleCode().equals("ROLE_TENANTADMIN")) {
                isAdmin = true;
                break;
            }
        }
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("sys.main.logo", logo);
        CsrfGuard csrfGuard = CsrfGuard.getInstance();
        csrfGuard.updateToken(WrapWebUtils.getSession());
        if(isAdmin){
            modelAndView = new ModelAndView("/saas/sys/tenant/dashboard", map);
        }else{
            modelAndView = new ModelAndView("/saas/sys/tenant/dashboard", map);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/signup")
    public String signup(){
        return "redirect:/dashboard";
    }

    @RequestMapping("/sys/resource/getMenu")
    public @ResponseBody
    Map<String, Object> getMenu() {
        Map<String, Object> map = new HashMap<String, Object>();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        List<ResourceMenuItem> items = userLoginDetails.getResourceMenuItems();
        map.put("success", true);
        map.put("data", items);
        return map;
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView openRegister(HttpServletRequest request){
        String path = request.getContextPath();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("path",path);
        return new ModelAndView("register",model);
    }
}