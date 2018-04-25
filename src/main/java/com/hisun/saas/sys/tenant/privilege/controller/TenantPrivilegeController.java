/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.privilege.controller;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.privilege.service.TenantPrivilegeService;
import com.hisun.saas.sys.tenant.privilege.vo.TenantPrivilegeVo;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.saas.sys.util.EntityWrapper;
import com.hisun.util.ValidateUtil;
import netscape.security.Privilege;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* @author liuzj {279421824@qq.com}
*/
@Controller
@RequestMapping("/sys/tenant/privilege")
public class TenantPrivilegeController extends BaseController {

    @Resource
    private TenantPrivilegeService tenantPrivilegeService;
    @Resource
    private TenantService tenantService;

    @Resource
    private TenantUserService tenantUserService;

    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value="pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "15")int pageSize) throws UnsupportedEncodingException {
        Map<String,Object> model = new HashMap<String,Object>();
        CommonConditionQuery query = new CommonConditionQuery();
//        query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
        Long total = tenantPrivilegeService.count(query);
        CommonOrderBy orderBy = new CommonOrderBy();
        orderBy.add(CommonOrder.asc("sort"));
        List<TenantPrivilege> resultList = tenantPrivilegeService.list(query,orderBy,pageNum,pageSize);
        List<Integer> userCountList = new ArrayList<Integer>();
        PagerVo<TenantPrivilege> pager = new PagerVo<TenantPrivilege>(resultList, total.intValue(), pageNum, pageSize);
        model.put("pager",pager);
        return new ModelAndView("saas/sys/tenant/privilege/list",model);
    }

    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/add")
    public ModelAndView add(){
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Integer maxSort = this.tenantPrivilegeService.getMaxSort()+1;
        returnMap.put("maxSort", maxSort);
        return new ModelAndView("saas/sys/tenant/privilege/add",returnMap);
    }
    @RequestMapping(value = "/code/check")
    public @ResponseBody Map<String, Object> codeCheck(
            @RequestParam("code") String code,@RequestParam(value="id",required=false)String id) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" code = :code ", "code", code));
        if(StringUtils.isNotBlank(id)){
            TenantPrivilege privilege = tenantPrivilegeService.getByPK(id);
            if(StringUtils.equalsIgnoreCase(privilege.getCode(), code)){
                map.put("success", true);
            }else{
                Long total = tenantPrivilegeService.count(query);
                if(total>=1){
                    map.put("success", false);
                }else{
                    map.put("success", true);
                }
            }
        }else{
            Long total = tenantPrivilegeService.count(query);
            if(total>=1){
                map.put("success", false);
            }else{
                map.put("success", true);
            }
        }
        return map;
    }
    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/save")
    public @ResponseBody Map<String,Object> save(@ModelAttribute TenantPrivilegeVo vo) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(vMap.size()>0){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
            TenantPrivilege entity = new TenantPrivilege();
            BeanUtils.copyProperties(vo, entity);
            entity.setId(null);
            EntityWrapper.wrapperSaveBaseProperties(entity,details);
            tenantPrivilegeService.save(entity);
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

    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        Map<String,Object> model = new HashMap<String,Object>();
        TenantPrivilege tenant = tenantPrivilegeService.getByPK(id);
        TenantPrivilegeVo vo = new TenantPrivilegeVo();
        BeanUtils.copyProperties(tenant,vo);
        model.put("entity", vo);
        return new ModelAndView("saas/sys/tenant/privilege/edit", model);
    }

    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/update")
    public @ResponseBody Map<String,Object> update(@ModelAttribute TenantPrivilegeVo vo, HttpServletRequest request) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(StringUtils.isBlank(vo.getName()) || vo.getName().length()<1 || vo.getName().length()>15){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        TenantPrivilege entity = tenantPrivilegeService.getByPK(vo.getId());
        if(entity==null){
            returnMap.put("message","数据不存在");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
            BeanUtils.copyProperties(vo, entity);
            EntityWrapper.wrapperUpdateBaseProperties(entity,details);
            tenantPrivilegeService.update(entity);
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
    @RequiresPermissions("sys-tenantPrivilege:*")
    @RequestMapping("/delete/{id}")
    public @ResponseBody Map<String,Object> delete(@PathVariable("id") String id) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            tenantPrivilegeService.deleteByPK(id);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }

        return returnMap;
    }
}