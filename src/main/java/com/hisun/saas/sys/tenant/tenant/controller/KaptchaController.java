package com.hisun.saas.sys.tenant.tenant.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hisun.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * <p>Title: KaptchaController.java </p>
 * <p>Package com.hisun.saas.sys.controller </p>
 * <p>Description: 验证码生成</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年3月31日 下午2:26:00 
 * @version 
 */

@Controller  
@RequestMapping("/kaptcha")  
public class KaptchaController  extends BaseController {

	@Resource
	private Producer captchaProducer;
	
	@RequestMapping("/image")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
        HttpSession session = request.getSession();  
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
        logger.info("******************验证码是: " + code + "******************");  
          
        response.setDateHeader("Expires", 0);  
          
        // Set standard HTTP/1.1 no-cache headers.  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
          
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
          
        // Set standard HTTP/1.0 no-cache header.  
        response.setHeader("Pragma", "no-cache");  
          
        // return a jpeg  
        response.setContentType("image/jpeg");  
          
        // create the text for the image  
        String capText = captchaProducer.createText();  
          
        // store the text in the session  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
          
        // create the image with the text  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();  
          
        // write the data out  
        ImageIO.write(bi, "jpg", out);  
        try {  
            out.flush();  
        } finally {  
            out.close();  
        }  
        return null;  
    }  
}
