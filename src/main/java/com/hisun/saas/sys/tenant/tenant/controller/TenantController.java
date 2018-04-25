package com.hisun.saas.sys.tenant.tenant.controller;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantVo;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.saas.sys.util.PinyinUtil;
import com.hisun.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *<p>类名称：OrganizationController</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月27日 上午9:47:09
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
@Controller
@RequestMapping("/sys/tenant/tenant")
public class TenantController extends BaseController {

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantUserService tenantUserService;

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/list")
    public ModelAndView list(String name,String start,String end,@RequestParam(value="tombstone",defaultValue = "-1")int tombstone
            ,@RequestParam(value="pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "15")int pageSize) throws UnsupportedEncodingException {
        Map<String,Object> model = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(name)){
            name = URLDecoder.decode(name,"UTF-8");
        }
        PagerVo<Tenant> pager = tenantService.listPager(name,tombstone,start,end,pageNum,pageSize);
        List<Integer> userCountList = new ArrayList<Integer>();
        for(Tenant tenant : pager.getDatas()){
            userCountList.add(tenantService.countUserByTenantId(tenant.getId()));
        }
        model.put("pager",pager);
        model.put("userCountList",userCountList);
        model.put("name",name);
        model.put("tombstone",tombstone);
        return new ModelAndView("saas/sys/tenant/tenant/list",model);
    }

//    @RequiresPermissions("tenant:tenantadd")
    @RequestMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("saas/sys/tenant/tenant/add");
    }

//    @RequiresPermissions("tenant:tenantadd")
    @RequestMapping("/save")
    public @ResponseBody Map<String,Object> save(TenantVo vo) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(vMap.size()>0){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            tenantService.add(vo);
            returnMap.put("code",1);
        }catch (GenericException e){
            returnMap.put("code",-1);
            returnMap.put("message", e.getMessage());
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
            returnMap.put("message", "系统错误，请联系管理员");
        }

        return returnMap;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        Map<String,Object> model = new HashMap<String,Object>();
        Tenant tenant = tenantService.getByPK(id);
        model.put("entity", tenant);
        return new ModelAndView("saas/sys/tenant/tenant/edit", model);
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/update")
    public @ResponseBody Map<String,Object> update(TenantVo vo) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(StringUtils.isBlank(vo.getName()) || vo.getName().length()<1 || vo.getName().length()>15){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        Tenant entity = tenantService.getByPK(vo.getId());
        if(entity==null){
            returnMap.put("message","数据不存在");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            tenantService.update(vo);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }
        return returnMap;
    }

    /**
     * 租户注销
     * @param id
     * @return
     */
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/delete/{id}")
    public @ResponseBody Map<String,Object> delete(@PathVariable("id") String id) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            tenantService.deleteById(id);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }

        return returnMap;
    }

    /**
     * 租户激活
     * @param id
     * @return
     */
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/activate/{id}")
    public @ResponseBody Map<String,Object> activate(@PathVariable("id") String id) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            tenantService.updateActivate(id);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }

        return returnMap;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/own/view")
    public ModelAndView ownView(){
        Map<String,Object> model = new HashMap<String,Object>();
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        Tenant tenant = tenantService.getByPK(details.getTenantId());
        int count = tenantUserService.countMember(details.getTenantId());
        model.put("entity",tenant);
        model.put("memberCount", count);
        return new ModelAndView("saas/sys/tenant/tenant/viewOwn",model);
    }

    /**
     * 打开维护本组织信息页面
     * @return
     */
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping(value="/own/update",method = RequestMethod.GET)
    public ModelAndView openOwnUpdate(){
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        Tenant tenant = tenantService.getByPK(details.getTenantId());
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("entity",tenant);
        return new ModelAndView("saas/sys/tenant/tenant/editOwn",model);
    }

    /**
     * 提交维护本组织信息
     * @return
     */
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping(value="/own/update",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> ownUpdate(TenantVo vo){
        tenantService.updateOwn(vo);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",1);
        return map;
    }

    /**
     * 平台管理员查看租户信息
     * @return
     */
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/sysadmin/view/{tenantId}")
    public ModelAndView platfAdminView(@PathVariable(value="tenantId")String tenantId){
        Map<String,Object> model = new HashMap<String,Object>();
        Tenant tenant = tenantService.getByPK(tenantId);
        int count = tenantUserService.countMember(tenantId);
        model.put("entity",tenant);
        model.put("memberCount", count);
        return new ModelAndView("saas/sys/tenant/tenant/viewPlatfAdmin",model);
    }

    @RequestMapping(value = "/ajax/getTenantNameCode")
    public @ResponseBody Map<String, Object> getTenantNameCode(
            @RequestParam("name") String name,@RequestParam(value="shortName",required=false)String shortName) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        String pinYinHead = "";
        if(shortName.equals("") == false){
            pinYinHead = PinyinUtil.getPinYinHeadChar(shortName);
        }else{
            pinYinHead = PinyinUtil.getPinYinHeadChar(name);
        }
        map.put("pinYinHead", pinYinHead);
        map.put("success", true);
        return map;
    }
}