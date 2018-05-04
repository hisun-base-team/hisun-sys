/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.tenant.controller;

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
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.privilege.PrivilegeRowExpress;
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.saas.sys.tenant.privilege.service.TenantPrivilegeService;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.resource.service.TenantResourcePrivilegeService;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2ResourcePrivilege;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourcePrivilegeService;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourceService;
import com.hisun.saas.sys.tenant.tenant.service.TenantDepartmentService;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.tenant.vo.Tenant2ResourcePrivilegeVo;
import com.hisun.saas.sys.tenant.tenant.vo.TenantVo;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.saas.sys.util.EntityWrapper;
import com.hisun.saas.sys.util.PinyinUtil;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.BeanMapper;
import com.hisun.util.StringUtils;
import com.hisun.util.ValidateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/sys/tenant/tenant")
public class TenantController extends BaseController {

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantUserService tenantUserService;

    @Resource
    private TenantPrivilegeService tenantPrivilegeService;
    @Resource
    TenantResourceService tenantResourceService;
    @Resource
    private TenantResourcePrivilegeService tenantResourcePrivilegeService;

    @Resource
    private Tenant2ResourceService tenant2ResourceService;

    @Resource
    private Tenant2ResourcePrivilegeService tenant2ResourcePrivilegeService;

    @Resource
    private TenantDepartmentService tenantDepartmentService;


    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/list")
    public ModelAndView list(String name,String start,String end,
                             @RequestParam(value="tombstone",defaultValue = "-1")int tombstone,
                             @RequestParam(value="pageNum",defaultValue = "1")int pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "15")int pageSize) throws GenericException {
        Map<String,Object> model = new HashMap<>();
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            if (StringUtils.isNotBlank(start)) {
                query.add(CommonRestrictions.and(" createDate >= :start ", "start", new DateTime(start).toDate()));
            }
            if (StringUtils.isNotBlank(end)) {
                query.add(CommonRestrictions.and(" createDate <= :end ", "end", new DateTime(end).plusDays(1).toDate()));
            }
            if (StringUtils.isNotBlank(name)) {
                query.add(CommonRestrictions.and(" name like :name ", "name", "%" + name + "%"));
            }
            if (tombstone != -1) {
                query.add(CommonRestrictions.and(" tombstone = :tombstone ", "tombstone", tombstone));
            }
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.desc("createDate"));
            Long total = this.tenantService.count(query);
            List<Tenant> tenants = this.tenantService.list(query, orderBy, pageNum, pageSize);
            List<TenantVo> vos = new ArrayList<>();
            TenantVo vo = null;
            for (Tenant entity : tenants) {
                vo = new TenantVo();
                BeanUtils.copyProperties(vo, entity);
                vo.setUsersCount(entity.getUsers().size());
                vos.add(vo);
            }
            PagerVo<TenantVo> pager =new PagerVo<TenantVo>(vos, total.intValue(), pageNum, pageSize);
            model.put("pager", pager);
            model.put("name", name);
            model.put("tombstone", tombstone);
        }catch (Exception e){
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("saas/sys/tenant/tenant/list",model);
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("saas/sys/tenant/tenant/add");
    }

    @RequiresLog(operateType = LogOperateType.SAVE,description = "新增租户:${vo.name}")
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/save")
    public @ResponseBody Map<String,Object> save(TenantVo vo) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> validMap = ValidateUtil.validAll(vo);
        if(validMap.size()>0){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            tenantService.save(vo);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            throw new GenericException(e.getMessage());
        }
        return returnMap;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        Map<String,Object> model = new HashMap<String,Object>();
        try {
            Tenant tenant = tenantService.getByPK(id);
            TenantVo tenantVo = new TenantVo();
            BeanUtils.copyProperties(tenantVo,tenant);
            model.put("vo", tenantVo);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return new ModelAndView("saas/sys/tenant/tenant/edit", model);
    }


    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/edit/own")
    public ModelAndView editOwn(){
        Map<String,Object> model = new HashMap<String,Object>();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            Tenant tenant = tenantService.getByPK(userLoginDetails.getTenantId());
            TenantVo tenantVo = new TenantVo();
            BeanUtils.copyProperties(tenantVo,tenant);
            model.put("vo", tenantVo);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return new ModelAndView("saas/sys/tenant/tenant/editOwn", model);
    }


    @RequiresLog(operateType = LogOperateType.UPDATE,description = "修改租户:${vo.name}")
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/update")
    public @ResponseBody Map<String,Object> update(TenantVo vo) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            Tenant entity = tenantService.getByPK(vo.getId());
            entity.setName(vo.getName());
            entity.setShortName(vo.getName());
            entity.setShortNamePy(vo.getShortNamePy());
            EntityWrapper.wrapperUpdateBaseProperties(entity,UserLoginDetailsUtil.getUserLoginDetails());
            tenantService.update(entity);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            throw new GenericException(e.getMessage());
        }
        return returnMap;
    }


    @RequiresLog(operateType = LogOperateType.UPDATE,description = "修改租户:${vo.name}")
    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/update/own")
    public @ResponseBody Map<String,Object> updateOwn(TenantVo vo) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            Tenant entity = tenantService.getByPK(vo.getId());
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            if(userLoginDetails.getTenantId().equals(entity.getId())) {
                entity.setName(vo.getName());
                entity.setShortName(vo.getName());
                entity.setShortNamePy(vo.getShortNamePy());
                EntityWrapper.wrapperUpdateBaseProperties(entity, userLoginDetails);
                tenantService.update(entity);
                returnMap.put("code", 1);
            }else{
                throw new GenericException("非本单位管理员操作!");
            }
        }catch (Exception e){
            logger.error(e,e);
            throw new GenericException(e.getMessage());
        }
        return returnMap;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/delete/{id}")
    public @ResponseBody Map<String,Object> delete(@PathVariable("id") String id) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            Tenant tenant = this.tenantService.getByPK(id);
            tenantService.updateToFreeze(tenant);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            throw new GenericException(e.getMessage());
        }

        return returnMap;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/activate/{id}")
    public @ResponseBody Map<String,Object> activate(@PathVariable("id") String id) throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            Tenant tenant = this.tenantService.getByPK(id);
            tenantService.updateToActivate(tenant);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }

        return returnMap;
    }


    @RequiresPermissions("sys-tenant:*")
    @RequestMapping(value = "/ajax/getTenantNameCode")
    public @ResponseBody Map<String, Object> getTenantNameCode(
            @RequestParam("name") String name,@RequestParam(value="shortName",required=false)String shortName) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            String pinYinHead = "";
            if(shortName.equals("") == false){
                pinYinHead = PinyinUtil.getAllFirstLetter(shortName);
            }else{
                pinYinHead =PinyinUtil.getAllFirstLetter(name);
            }
            map.put("pinYinHead", pinYinHead);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/privilegeManage/{tenantId}")
    public ModelAndView privilegeManage(@PathVariable(value="tenantId")String tenantId,String tenantName) {
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("tenantId",tenantId);
        model.put("tenantName",tenantName);
        return  new ModelAndView("saas/sys/tenant/tenant/privilegeManage/privilegeManage",model);
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/ajax/privilegeSet")
    public ModelAndView privilegeSet(String tenantId,String resourceId,String resourceName,String tenantName) throws GenericException {
        Map<String,Object> model = new HashMap<String,Object>();
        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("tenantPrivilege.sort"));
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
            List<TenantResourcePrivilege> tenantResourcePrivileges = this.tenantResourcePrivilegeService.list(query, orderBy);
            Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(tenantId, resourceId);
            List<Tenant2ResourcePrivilegeVo> vos = new ArrayList<>();
            if (tenantResourcePrivileges != null) {
                Tenant2ResourcePrivilegeVo vo = null;
                for (TenantResourcePrivilege tenantResourcePrivilege : tenantResourcePrivileges) {
                    vo = new Tenant2ResourcePrivilegeVo();
                    vo.setId(tenantResourcePrivilege.getId());
                    vo.setPrivilegeName(tenantResourcePrivilege.getTenantPrivilege().getName());
                    vo.setPrivilegeDescription(tenantResourcePrivilege.getTenantPrivilege().getDescription());
                    vo.setPrivilegeDisplayType(tenantResourcePrivilege.getTenantPrivilege().getDisplayType());
                    vo.setPrivilegeImpclass(tenantResourcePrivilege.getTenantPrivilege().getImpclass());
                    vo.setTenantPrivilegeId(tenantResourcePrivilege.getTenantPrivilege().getId());
                    vo.setSelectUrl(tenantResourcePrivilege.getTenantPrivilege().getSelectUrl());
                    if (tenant2Resource != null) {
                        //如果tenant2Resource!=null,试图去找该资源下配置的数据权限进行页面显示
                        if (tenant2Resource.getTenant2ResourcePrivileges() != null&& tenant2Resource.getTenant2ResourcePrivileges().size()>0) {
                            for(Tenant2ResourcePrivilege tenant2ResourcePrivilege : tenant2Resource.getTenant2ResourcePrivileges()){
                                if(tenant2ResourcePrivilege.getTenantPrivilege().getId().equals(tenantResourcePrivilege.getTenantPrivilege().getId())){
                                    vo.setSelectedNames(tenant2ResourcePrivilege.getSelectedNames());
                                    vo.setSelectedValues(tenant2ResourcePrivilege.getSelectedValues());
                                }
                            }

                        } else {
                            vo.setSelectedNames("");
                            vo.setSelectedValues("");
                        }
                    } else {
                        //如果tenant2Resource=null,则为新增
                        vo.setSelectedNames("");
                        vo.setSelectedValues("");
                    }
                    vos.add(vo);
                }
            }
            model.put("vos", vos);
            model.put("resourceId", resourceId);
            model.put("resourceName", resourceName);
            model.put("tenantId", tenantId);
            model.put("tenantName", tenantName);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("success", false);
            logger.error(e);
        }
        return new ModelAndView("saas/sys/tenant/tenant/privilegeManage/setDataPrivilege",model);
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping("/resource/tree")
    public @ResponseBody Map<String, Object> tree(@RequestParam(value="status",required=false) Integer status,String tenantId)
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
            resources = tenantResourceService.list(query, orderBy);
            List<TreeNode> treeNodeVos = new ArrayList<TreeNode>();
            TreeNode treeNodeVo = new TreeNode();
            treeNodeVo.setId("1");
            treeNodeVo.setName("资源树");
            treeNodeVo.setOpen(true);
            treeNodeVo.setpId("-1");
            treeNodeVos.add(treeNodeVo);

            query =  new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tenant.id = :tenantId ", "tenantId", tenantId));
            List<Tenant2Resource> tenant2Resources = this.tenant2ResourceService.list(query,null);

            for (TenantResource resource : resources) {
                treeNodeVo = new TreeNode();
                BeanMapper.copy(resource, treeNodeVo);
                treeNodeVo.setName(resource.getResourceName());
                treeNodeVo.setUrl(resource.getUrl());
                boolean isChecked = false;
                boo:for(Tenant2Resource tenant2Resource : tenant2Resources){
                    if(resource.getId().equals(tenant2Resource.getTenantResource().getId())){
                        isChecked = true;
                        break boo;
                    }
                }
                if(isChecked == true){
                    treeNodeVo.setChecked(true);
                    treeNodeVo.setOpen(true);
                }
                treeNodeVos.add(treeNodeVo);
            }
            map.put("success", true);
            map.put("data", treeNodeVos);
        } catch (Exception e) {
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequiresPermissions("sys-tenant:*")
    @RequestMapping(value="/save/tenant2Resource",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveTenant2Resource(String tenantId,String resourceIds) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            String[] resourceIdArr = resourceIds.split(",");
            for(String resourceId :resourceIdArr) {
                Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(tenantId, resourceId);
                if (tenant2Resource == null) {
                    tenant2Resource = new Tenant2Resource();
                    Tenant tenant = this.tenantService.getByPK(tenantId);
                    TenantResource tenantResource = this.tenantResourceService.getByPK(resourceId);
                    tenant2Resource.setTenant(tenant);
                    tenant2Resource.setTenantResource(tenantResource);
                    this.tenant2ResourceService.save(tenant2Resource);
                }
            }
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
    @RequestMapping(value="/delete/tenant2Resource",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> deleteTenant2Resource(String tenantId,String resourceIds) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            String[] resourceIdArr = resourceIds.split(",");
            for(String resourceId :resourceIdArr) {
                Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(tenantId, resourceId);
                if (tenant2Resource != null) {
                    this.tenant2ResourceService.deleteByPK(tenant2Resource.getId());
                }
            }
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
    @RequestMapping(value="/save/tenant2ResourcePrivilege",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveTenant2ResourcePrivilege(HttpServletRequest request) throws GenericException {
        String tenantId = StringUtils.trimNull2Empty(request.getParameter("tenantId"));
        String resourceId = StringUtils.trimNull2Empty(request.getParameter("resourceId"));

        Map<String,Object> returnMap = new HashMap<String,Object>();
        try{
            Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(tenantId,resourceId);
            List<Tenant2ResourcePrivilege> tenant2ResourcePrivileges = tenant2Resource.getTenant2ResourcePrivileges();

            tenant2Resource.getTenant2ResourcePrivileges();
            if(tenant2Resource==null) {
                returnMap.put("code",-1);
                returnMap.put("message", "该资源没有授权！");
            }else{
                CommonOrderBy orderBy = new CommonOrderBy();
                orderBy.add(CommonOrder.asc("tenantPrivilege.sort"));
                CommonConditionQuery query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
                List<TenantResourcePrivilege> tenantResourcePrivileges = this.tenantResourcePrivilegeService.list(query, orderBy);
                tenantResourcePrivileges.size();
                Tenant  tenant = this.tenantService.getByPK(tenantId);
                TenantResource tenantResource =this.tenantResourceService.getByPK(resourceId);

                if (tenantResourcePrivileges != null) {
                    for (TenantResourcePrivilege tenantResourcePrivilege : tenantResourcePrivileges) {
                        int displayType = tenantResourcePrivilege.getTenantPrivilege().getDisplayType();

                        String selectedValues = StringUtils.trimNull2Empty(request.getParameter(tenantResourcePrivilege.getTenantPrivilege().getId()));
                        String selectedNames = StringUtils.trimNull2Empty(request.getParameter((tenantResourcePrivilege.getTenantPrivilege().getId())+"_value"));
                        String sqlFilterExpress = "";
                        String hqlFilterExpres = "";

                        Tenant2ResourcePrivilege tenant2ResourcePrivilege = new Tenant2ResourcePrivilege();
                        boolean isAdd = true;//是否为添加
                        if(tenant2Resource.getTenant2ResourcePrivileges()!=null && tenant2Resource.getTenant2ResourcePrivileges().size()>0){
                            boo:for(Tenant2ResourcePrivilege tenant2ResourcePrivilegeTmp : tenant2Resource.getTenant2ResourcePrivileges()){
                                if(tenantResourcePrivilege.getTenantPrivilege().getId().equals(tenant2ResourcePrivilegeTmp.getTenantPrivilege().getId())){
                                    tenant2ResourcePrivilege = tenant2ResourcePrivilegeTmp;
                                    isAdd = false;
                                    break boo;
                                }
                            }
                        }
                        if(StringUtils.isNotEmpty(selectedValues)){
                            PrivilegeRowExpress privilegeRowExpressObj = ApplicationContextUtil.getBean(tenantResourcePrivilege.getTenantPrivilege().getImpclass());
                            if(privilegeRowExpressObj!=null){
                                sqlFilterExpress = privilegeRowExpressObj.getSqlFilterExpress(selectedValues);
                                hqlFilterExpres = privilegeRowExpressObj.getHqlFilterExpress(selectedValues);
                            }
                        }
                        tenant2ResourcePrivilege.setTenant(tenant);
                        tenant2ResourcePrivilege.setTenantPrivilege(tenantResourcePrivilege.getTenantPrivilege());
                        tenant2ResourcePrivilege.setTenant2Resource(tenant2Resource);
                        tenant2ResourcePrivilege.setSelectedNames(selectedNames);
                        tenant2ResourcePrivilege.setSelectedValues(selectedValues);
                        tenant2ResourcePrivilege.setHqlFilterExpress(hqlFilterExpres);
                        tenant2ResourcePrivilege.setSqlFilterExpress(sqlFilterExpress);
                        if(isAdd==true) {
                            tenant2ResourcePrivileges.add(tenant2ResourcePrivilege);
                        }
                    }
                }

                tenant2Resource.setTenant2ResourcePrivileges(tenant2ResourcePrivileges);
                tenant2Resource.setTenant(tenant);
                tenant2Resource.setTenantResource(tenantResource);
                this.tenant2ResourceService.update(tenant2Resource);
            }
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


    @RequiresPermissions("tenant:*")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<String, Object>();
        return new ModelAndView("saas/sys/tenant/tenant/index",map);
    }




    @RequiresPermissions("tenant:*")
    @RequestMapping("/tree")
    public @ResponseBody Map<String, Object> tree() throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            CommonConditionQuery query = new CommonConditionQuery();
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<TenantDepartment> tenantDepartments = tenantDepartmentService.list(query, orderBy);
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            TreeNode treeNode = new TreeNode();
            treeNode.setId(userLoginDetails.getTenant().getId());
            treeNode.setName(userLoginDetails.getTenant().getName());
            treeNode.setOpen(true);
            treeNodes.add(treeNode);
            TreeNode childTreeNode=null;
            for (TenantDepartment tenantDepartment : tenantDepartments) {
                childTreeNode = new TreeNode();
                childTreeNode.setId(tenantDepartment.getId());
                childTreeNode.setName(tenantDepartment.getName());
                if(tenantDepartment.getParent()==null){
                    childTreeNode.setpId(userLoginDetails.getTenant().getId());
                }else{
                    childTreeNode.setpId(tenantDepartment.getParent().getId());
                }
                treeNodes.add(childTreeNode);
            }
            map.put("success", true);
            map.put("data", treeNodes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e, e);
            map.put("success", false);
        }
        return map;
    }
}