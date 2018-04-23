/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.resource.vo.TenantResourceVo;
import com.hisun.util.BeanMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys/tenant/resource")
public class TenantResourceController extends BaseController {

    @Resource
    TenantResourceService tenantResourceService;

    //@Resource
    //private SessionDAO sessionDAO;

    //@javax.annotation.Resource
    //private SessionHelper sessionHelper;

    @RequestMapping("/list/tree")
    public ModelAndView treeResources() {
        return  new ModelAndView("/saas/sys/tenant/resource/listTreeResource");
    }

    @RequestMapping(value = "/link", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> queryCodeLink(@RequestParam("menuCode") String menuCode){
        Map<String, Object> map = Maps.newHashMap();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        List<AbstractResource> resources = userLoginDetails.getResources();
        session.setAttribute(Constants.RESOURCE_QUERY_CODE, "");
        session.setAttribute(Constants.MENU_RESOURCE_ID, "");
        for(AbstractResource resource:resources){
            if(resource.getResourceType()!=1
                    && StringUtils.isNotBlank(menuCode)
                    &&StringUtils.equals(StringUtils.trim(menuCode), resource.getId())){
                StringBuilder sb = new StringBuilder();
                if(StringUtils.isNotBlank(resource.getQueryCode())){
                    for(int i =1 ;i<=resource.getQueryCode().length()/3; i++){
                        sb.append(resource.getQueryCode().substring(0,i*3)+",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                }
                session.setAttribute(Constants.RESOURCE_QUERY_CODE, sb.toString());
                session.setAttribute(Constants.MENU_RESOURCE_ID, resource.getId());
            }
        }
        map.put("success", true);
        return map;
    }

    @RequestMapping("/sitemesh/list")
    public ModelAndView resources(HttpServletRequest req, String pId,
                                  @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="20") int pageSize)
            throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();

        //User user = (User) req.getAttribute(Constants.CURRENT_USER);
        try{
            //String userId = userLoginDetails.getUserid();
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
            query.add(CommonRestrictions.and(" type = :type ","type" ,1));
            Long total = tenantResourceService.count(query);
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<TenantResource> resultList = tenantResourceService.list(query, orderBy, pageNum, pageSize);
            PagerVo<TenantResource> pager = new PagerVo<TenantResource>(resultList, total.intValue(), pageNum, pageSize);
            map.put("pager", pager);
        }catch(Exception e){
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("/saas/sys/tenant/resource/listResource", map);
    }

    @RequestMapping("/tree")
    public @ResponseBody Map<String, Object> tree(@RequestParam(value="status",required=false) Integer status)
            throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TenantResource> resources;
        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            CommonConditionQuery query = new CommonConditionQuery();
            orderBy.add(CommonOrder.asc("sort"));
            if(status!=null){
                query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
            }
            query.add(CommonRestrictions.and(" type = :type ","type" ,1));
            resources = tenantResourceService.list(query, orderBy);
            List<TenantResourceVo> resourceVos = new ArrayList<TenantResourceVo>();
            TenantResourceVo resourceVo = new TenantResourceVo();
            resourceVo.setId("1");
            resourceVo.setName("资源树");
            resourceVo.setOpen(true);
            resourceVo.setpId("-1");
            resourceVos.add(resourceVo);
            for (TenantResource resource : resources) {
                resourceVo = new TenantResourceVo();
                BeanMapper.copy(resource, resourceVo);
                resourceVo.setName(resource.getResourceName());
                resourceVo.setHref(resource.getUrl());
                resourceVos.add(resourceVo);
            }
            map.put("success", true);
            map.put("data", resourceVos);
        } catch (Exception e) {
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequestMapping("/select/tree")
    public @ResponseBody Map<String, Object> treeSelect(@RequestParam(value="status",required=false) Integer status)  throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TenantResource> resources;
        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            CommonConditionQuery query = new CommonConditionQuery();
            if(status!=null){
                query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
            }else{
                query.add(CommonRestrictions.and(" resourceType != :resourceType ", "resourceType", Integer.valueOf(1)));
            }
            orderBy.add(CommonOrder.asc("sort"));
            resources = tenantResourceService.list(query, orderBy);
            List<TenantResourceVo> resourceVos = new ArrayList<TenantResourceVo>();
            TenantResourceVo resourceVo;
            resourceVo = new TenantResourceVo();
            resourceVo.setId("1");
            resourceVo.setName("资源树");
            resourceVo.setOpen(true);
            resourceVo.setpId("-1");
            resourceVos.add(resourceVo);
            //resourceVo.setChkDisabled(true);
            for (TenantResource resource : resources) {
                resourceVo = new TenantResourceVo();
                BeanMapper.copy(resource, resourceVo);
                resourceVo.setName(resource.getResourceName());
                resourceVo.setUrl("");
                resourceVos.add(resourceVo);
                //resourceVo.setChkDisabled(true);
            }
            map.put("success", true);
            map.put("data", resourceVos);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> add(TenantResource resource, HttpServletRequest request)  throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        String newpId = request.getParameter("newpId");
        try {
            if(resource.getResourceType()!=1){//操作无需code
                if(StringUtils.isNotBlank(newpId)){
                    resource.setpId(newpId);
                }
            }
            tenantResourceService.saveResource(resource);
            map.put("success", true);
            map.put("data", resource);
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
        }

        return map;
    }

    @RequestMapping(value = "/delete/{id}")
    public @ResponseBody Map<String, Object> delete(
            @PathVariable("id") String id)  throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(id)) {
                CommonConditionQuery query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" pId = :pId ", "pId", id));
                query.add(CommonRestrictions.and(" type = :type ","type" ,1));
                Long total = tenantResourceService.count(query);
                if(total>0){
                    map.put("success", false);
                    map.put("message", "该树节点还有子节点，不能删除!");
                }else{
                    this.tenantResourceService.deleteByPK(id);
                    map.put("success", true);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员");
        }
        return map;
    }

    @RequestMapping(value = "/update")
    public @ResponseBody Map<String, Object> update(TenantResource resource,HttpServletRequest request)  throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        String newpId = request.getParameter("newpId");
        try {
            String oldPid = resource.getpId();
            resource.setpId(newpId);
            this.tenantResourceService.updateResources(resource, oldPid);
            map.put("success", true);
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
        }
        return map;
    }


    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> get(
            @PathVariable("id") String id) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(id)) {
                TenantResource entity = this.tenantResourceService.getByPK(id);
                TenantResourceVo vo = new TenantResourceVo();
                BeanUtils.copyProperties(vo, entity);
                map.put("data", vo);
                map.put("success", true);
            } else {
                throw new GenericException("错误：参数不正确。");
            }
        } catch (Exception e) {
            throw new GenericException(e);
        }
        return map;
    }

    @RequestMapping(value = "/permission/check")
    public @ResponseBody Map<String, Object> check(
            @RequestParam("permission") String permission,@RequestParam(value="id",required=false)String id) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" permission = :permission ", "permission", permission));
        if(StringUtils.isNotBlank(id)){
            TenantResource resource = tenantResourceService.getByPK(id);
            if(StringUtils.equalsIgnoreCase(resource.getPermission(), permission)){
                map.put("success", true);
            }else{
                Long total = tenantResourceService.count(query);
                if(total>=1){
                    map.put("success", false);
                    map.put("message", "权限字符串已存在！");
                }else{
                    map.put("success", true);
                }
            }
        }else{
            Long total = tenantResourceService.count(query);
            if(total>=1){
                map.put("success", false);
                map.put("message", "权限字符串已存在！");
            }else{
                map.put("success", true);
            }
        }
        return map;
    }

    @RequestMapping("/max/sort")
    public @ResponseBody Map<String, Object> getMaxSort(@RequestParam(value="pId")String pId) {
        Map<String, Object> map = Maps.newHashMap();
        Integer maxSort = this.tenantResourceService.getMaxSort(pId);
        map.put("maxSort", maxSort);
        return map;
    }
}
