package com.hisun.saas.sys.admin.user.controller;

import com.google.code.kaptcha.Constants;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.service.SMSService;
import com.hisun.saas.sys.admin.communication.vo.MessageResult;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.base.controller.BaseController;
import com.hisun.saas.sys.admin.user.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>Title: KaptchaController.java </p>
 * <p>Package com.hisun.cloud.sys.controller </p>
 * <p>Description: 验证码生成</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年3月31日 下午2:26:00 
 * @version 
 */

@Controller
@RequestMapping("/admin/sms")
public class AdminSMSCodeController extends BaseController {

    @Resource
    private UserService userService ;

    @Resource
    private SMSService smsService;

	@RequestMapping("/send/code")
    public @ResponseBody Map<String,Object> sendCode(HttpServletRequest request, HttpServletResponse response, String username) throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        HttpSession session = request.getSession();  
        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, RandomStringUtils.randomNumeric(4));
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        logger.info("******************短信验证码是: " + code + "******************");
        User user = this.userService.findByUsername(username);
        if(user !=null){
            MessageResult result = this.smsService.sendSms("您的验证码是"+code,user.getTel());
            if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
                map.put("success",false);
            }else{
                map.put("success",true);
            }
        }else{
            map.put("success",false);
        }
        return map;
    }

    @RequestMapping("/send/code/email")
    public @ResponseBody Map<String,Object> sendCodeToEmail(HttpServletRequest request, HttpServletResponse response, String email) throws Exception {
        Map<String,Object> map = Maps.newHashMap();
        HttpSession session = request.getSession();
        // store the text in the session
        session.setAttribute(com.hisun.saas.sys.admin.Constants.SMS_SESSION_KEY, RandomStringUtils.randomNumeric(4));
        String code = (String)session.getAttribute(com.hisun.saas.sys.admin.Constants.SMS_SESSION_KEY);
        logger.info("******************短信验证码是: " + code + "******************");
        User user = this.userService.findByEmail(email);
        if(user !=null){
            MessageResult result = this.smsService.sendSms("您的验证码是"+code,user.getTel());
            if(!StringUtils.equalsIgnoreCase("0", result.getCode())){
                map.put("code","-1");
            }else{
                map.put("code","0");
            }
        }else{
            map.put("code","1");
        }
        return map;
    }
}
