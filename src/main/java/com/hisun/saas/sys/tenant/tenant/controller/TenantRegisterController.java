package com.hisun.saas.sys.tenant.tenant.controller;

import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>类名称:TenantInviteController</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/21上午9:48
 * @创建人联系方式:init@hn-hisun.com
 */
@Controller
@RequestMapping(value = "/sys/tenant/register")
public class TenantRegisterController extends BaseController {

    @Resource
    private TenantRegisterService tenantRegisterService;

    @Value("${sys.domain}")
    private String domain;

    /**
     * 打开注册页面
     * @return
     */
    @RequestMapping(value="/new", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> registerSave(TenantRegisterVo vo){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",1);
        TenantRegister tenantRegister = new TenantRegister();
        BeanUtils.copyProperties(vo, tenantRegister);
        tenantRegister.setStatus(TenantRegister.STATUS_NO);
        tenantRegister.setCreateDate(new Date());

        try {
            String id = tenantRegisterService.saveRegister(vo);
            map.put("id",id);
        }catch(GenericException e){
            map.put("code",-1);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping(value="/suc/{id}")
    public ModelAndView registerSuc(@PathVariable(value="id")String id){
        Map<String,Object> map = new HashMap<String,Object>();
        TenantRegister entity = tenantRegisterService.getByPK(id);
        map.put("email",entity.getEmail());
        return new ModelAndView("registerSuccess",map);
    }

    @RequestMapping(value = "/activate/{id}",method = RequestMethod.GET)
    public ModelAndView inviteActivate(@PathVariable(value="id")String id){
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("success",true);
        if(StringUtils.isBlank(id)){
            model.put("success",false);
            model.put("message", "无效链接");
        }
        TenantRegister tenantRegister = tenantRegisterService.getByPK(id);
        if(tenantRegister == null){
            model.put("success",false);
            model.put("message", "无效链接");
        }
        if(TenantRegister.STATUS_YES == tenantRegister.getStatus()){
            model.put("success",false);
            model.put("message", "已经激活，请勿重新激活");
        }
        try{
            tenantRegisterService.saveActivate(id);
        }catch(GenericException e){
            model.put("success",false);
            model.put("message", e.getMessage());
        }

        return new ModelAndView("saas/sys/tenant/register/activateSuccess",model);
    }
}
