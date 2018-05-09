package com.hisun.saas.sys.tenant.role.controller;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.ErrorMsgShowException;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.privilege.PrivilegeRowExpress;
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.resource.service.TenantResourcePrivilegeService;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.role.entity.*;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourcePrivilegeService;
import com.hisun.saas.sys.tenant.role.service.TenantRoleResourceService;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Resource;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2ResourcePrivilege;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourcePrivilegeService;
import com.hisun.saas.sys.tenant.tenant.service.Tenant2ResourceService;
import com.hisun.saas.sys.tenant.tenant.vo.Tenant2ResourcePrivilegeVo;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.vo.TenantUserVo;
import com.hisun.saas.sys.tenant.vo.TreeNodeVo;
import com.hisun.saas.sys.util.EntityWrapper;
import com.hisun.util.ApplicationContextUtil;
import com.hisun.util.BeanMapper;
import com.hisun.util.ValidateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@Controller
@RequestMapping("/sys/tenant/role")
public class TenantRoleController extends BaseController {

    @javax.annotation.Resource
    private TenantRoleService tenantRoleService;

    @javax.annotation.Resource
    private ResourceService resourceService;

    @Resource
    TenantRoleResourcePrivilegeService tenantRoleResourcePrivilegeService;

    @Resource
    TenantResourceService tenantResourceService;

    @Resource
    private TenantResourcePrivilegeService tenantResourcePrivilegeService;

    @javax.annotation.Resource
    private TenantRoleResourceService tenantRoleResourceService;

    @Resource
    private Tenant2ResourceService tenant2ResourceService;

    @Resource
    private Tenant2ResourcePrivilegeService tenant2ResourcePrivilegeService;

    @RequiresPermissions("tenantRole:*")
    @RequestMapping("/list")
    public ModelAndView list(String searchName, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum
            , @RequestParam(value = "pageSize", defaultValue = "15") int pageSize) throws GenericException {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(searchName)) {
                searchName = URLDecoder.decode(searchName, "UTF-8");
            }
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tombstone =:tombstone", "tombstone", TombstoneEntity.TOMBSTONE_FALSE));
            if (StringUtils.isNotBlank(searchName)) {
                query.add(CommonRestrictions.and("(roleName like :name or roleCode like :name)", "name", "%" + searchName + "%"));
            }
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<TenantRole> entities = tenantRoleService.list(query, orderBy, pageNum, pageSize);
            Long total = tenantRoleService.count(query);
            List<TenantRoleVo> vos = new ArrayList<>();
            TenantRoleVo vo = null;
            if (entities != null) {
                for (TenantRole entity : entities) {
                    vo = new TenantRoleVo();
                    BeanUtils.copyProperties(vo, entity);
                    vos.add(vo);
                }
            }
            PagerVo<TenantRoleVo> pager = new PagerVo<TenantRoleVo>(vos, total.intValue(), pageNum, pageSize);
            model.put("pager", pager);
            model.put("name", searchName);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("saas/sys/tenant/role/list", model);
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        Map<String, Object> map = Maps.newHashMap();
        TenantRoleVo tenantRoleVo = new TenantRoleVo();
        int sort = this.tenantRoleService.getMaxSort();
        tenantRoleVo.setSort(sort);
        map.put("vo", tenantRoleVo);
        return new ModelAndView("saas/sys/tenant/role/add", map);
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/code/check")
    public
    @ResponseBody
    Map<String, Object> check(@RequestParam("code") String code, @RequestParam(value = "id", required = false) String id) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(id)) {
            TenantRole entity = this.tenantRoleService.getByPK(id);
            if (StringUtils.equalsIgnoreCase(entity.getRoleCode(), code)) {
                map.put("success", true);
            } else {
                if (this.tenantRoleService.existRoleCode(code) == true) {
                    map.put("success", false);
                    map.put("message", "角色代码已存在!");
                } else {
                    map.put("success", true);
                }
            }
        } else {
            if (this.tenantRoleService.existRoleCode(code) == true) {
                map.put("success", false);
                map.put("message", "角色代码已存在!");
            } else {
                map.put("success", true);
            }
        }
        return map;
    }


    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> save(TenantRoleVo vo) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, String> vMap = ValidateUtil.validAll(vo);
            if (vMap.size() > 0) {
                map.put("message", "数据验证不通过");
                map.put("code", -1);
                return map;
            }
            TenantRole role = new TenantRole();
            BeanUtils.copyProperties(role,vo);
            tenantRoleService.save(role);
            map.put("success", false);
            map.put("message", "保存成功!");
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e);
        }
        return map;
    }


    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/delete/{roleId}")
    public
    @ResponseBody
    Map<String, Object> delete(@PathVariable("roleId") String roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(roleId)) {
                this.tenantRoleService.deleteByPK(roleId);
                map.put("success", true);
                map.put("message", "删除成功!");
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage());
        }
        return map;
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/edit/{roleId}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("roleId") String roleId) {
        Map<String,Object> map = new HashMap<String,Object>();
        TenantRoleVo vo = new TenantRoleVo();
        try {
            TenantRole entity = this.tenantRoleService.getByPK(roleId);
            BeanUtils.copyProperties(vo,entity);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage());
        }
        map.put("vo", vo);
        return new ModelAndView("saas/sys/tenant/role/edit",map);
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> update(TenantRoleVo vo) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        try {
            Map<String, String> vMap = ValidateUtil.validAll(vo);
            if (vMap.size() > 0) {
                map.put("message", "数据验证不通过!");
                map.put("success", false);
                return map;
            }
            TenantRole tenantRole = this.tenantRoleService.getByPK(vo.getId());
            BeanUtils.copyProperties(vo,tenantRole);
            EntityWrapper.wrapperUpdateBaseProperties(tenantRole,userLoginDetails);
            tenantRoleService.update(tenantRole);
            map.put("success", true);
            map.put("message", "保存成功!");
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresPermissions("tenantRole:*")
    @RequestMapping("/privilegeManage/{roleId}")
    public ModelAndView privilegeManage(@PathVariable(value = "roleId") String roleId, String roleName) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("roleId", roleId);
        model.put("roleName", roleName);
        return new ModelAndView("/saas/sys/tenant/role/privilegeManage/privilegeManage", model);
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping("/ajax/privilegeSet")
    public ModelAndView privilegeSet(String roleId, String resourceId, String resourceName, String roleName) throws GenericException {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("tenantPrivilege.sort"));
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
            List<TenantResourcePrivilege> tenantResourcePrivileges = this.tenantResourcePrivilegeService.list(query, orderBy);
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
            Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(details.getTenantId(), resourceId);
            List<Tenant2ResourcePrivilegeVo> vos = new ArrayList<>();
            if (tenantResourcePrivileges != null && tenantResourcePrivileges.size() > 0) {
                TenantRoleResource tenantRoleResource = this.tenantRoleResourceService.findTenantRoleResourceByRoleAndReource(roleId, resourceId);
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
                    if (tenantRoleResource != null) {
                        //如果tenant2Resource!=null,试图去找该资源下配置的数据权限进行页面显示
                        if (tenantRoleResource.getTenantRoleResourcePrivileges() != null && tenantRoleResource.getTenantRoleResourcePrivileges().size() > 0) {
                            for (TenantRoleResourcePrivilege tenantRoleResourcePrivilege : tenantRoleResource.getTenantRoleResourcePrivileges()) {
                                if (tenantRoleResourcePrivilege.getTenantPrivilege().getId().equals(tenantResourcePrivilege.getTenantPrivilege().getId())) {
                                    vo.setSelectedNames(tenantRoleResourcePrivilege.getSelectedNames());
                                    vo.setSelectedValues(tenantRoleResourcePrivilege.getSelectedValues());
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
            model.put("tenant2ResourceId", tenant2Resource.getId());

            model.put("roleId", roleId);
            model.put("roleName", roleName);
        } catch (Exception e) {
            e.printStackTrace();
            model.put("success", false);
            logger.error(e);
        }
        return new ModelAndView("saas/sys/tenant/role/privilegeManage/setDataPrivilege", model);
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping("/tree")
    public
    @ResponseBody
    Map<String, Object> tree(@RequestParam(value = "status", required = false) Integer status, String roleId)
            throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TenantResource> resources;
        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            CommonConditionQuery query = new CommonConditionQuery();
            orderBy.add(CommonOrder.asc("sort"));
            if (status != null) {
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
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();

            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tenant.id = :tenantId ", "tenantId", details.getTenantId()));
            List<Tenant2Resource> tenant2Resources = this.tenant2ResourceService.list(query, null);

            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" role.id = :roleId ", "roleId", roleId));
            List<TenantRoleResource> tenantRoleResources = this.tenantRoleResourceService.list(query, null);

            for (TenantResource resource : resources) {
                for (Tenant2Resource tenant2Resource : tenant2Resources) {
                    if (resource.getId().equals(tenant2Resource.getTenantResource().getId())) {
                        treeNodeVo = new TreeNode();
                        BeanMapper.copy(resource, treeNodeVo);
                        treeNodeVo.setName(resource.getResourceName());
                        treeNodeVo.setUrl(resource.getUrl());
                        boolean isChecked = false;
                        boo:
                        for (TenantRoleResource tenantRoleResource : tenantRoleResources) {
                            if (tenantRoleResource.getTenantResource().getId().equals(tenant2Resource.getTenantResource().getId())) {
                                isChecked = true;
                                break boo;
                            }
                        }
                        if (isChecked == true) {
                            treeNodeVo.setChecked(true);
                            treeNodeVo.setOpen(true);
                        }
                        treeNodeVos.add(treeNodeVo);
                    }
                }

            }
            map.put("success", true);
            map.put("data", treeNodeVos);
        } catch (Exception e) {
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/save/tenantRoleResource", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> saveTenantRoleResource(String roleId, String resourceIds) throws GenericException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            String[] resourceIdArr = resourceIds.split(",");
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
            for (String resourceId : resourceIdArr) {
                TenantRoleResource tenantRoleResource = this.tenantRoleResourceService.findTenantRoleResourceByRoleAndReource(roleId, resourceId);
                if (tenantRoleResource == null) {
                    tenantRoleResource = new TenantRoleResource();
                    TenantRole role = this.tenantRoleService.getPK(roleId);

                    TenantResource tenantResource = this.tenantResourceService.getByPK(resourceId);
                    Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(details.getTenantId(), resourceId);
                    tenantRoleResource.setTenant2Resource(tenant2Resource);
                    tenantRoleResource.setRole(role);
                    tenantRoleResource.setTenantResource(tenantResource);
                    this.tenantRoleResourceService.save(tenantRoleResource);
                }
            }
            returnMap.put("code", 1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e, e);
            returnMap.put("code", -1);
            returnMap.put("message", "系统错误，请联系管理员");
        }
        return returnMap;
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/delete/tenantRoleResource", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteTenantRoleResource(String roleId, String resourceIds) throws GenericException {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            String[] resourceIdArr = resourceIds.split(",");
            for (String resourceId : resourceIdArr) {
                TenantRoleResource tenantRoleResource = this.tenantRoleResourceService.findTenantRoleResourceByRoleAndReource(roleId, resourceId);
                if (tenantRoleResource != null) {
                    this.tenantRoleResourceService.deleteByPK(tenantRoleResource.getId());
                }
            }
            returnMap.put("code", 1);
        } catch (GenericException e) {
            returnMap.put("code", -1);
            returnMap.put("message", e.getMessage());
        } catch (Exception e) {
            logger.error(e, e);
            returnMap.put("code", -1);
            returnMap.put("message", "系统错误，请联系管理员");
        }
        return returnMap;
    }

    @RequiresPermissions("tenantRole:*")
    @RequestMapping(value = "/save/tenantRoleResourcePrivilege", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> saveTenantRoleResourcePrivilege(HttpServletRequest request) throws GenericException {
        String roleId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("roleId"));
        String resourceId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("resourceId"));

        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            TenantRoleResource tenantRoleResource = this.tenantRoleResourceService.findTenantRoleResourceByRoleAndReource(roleId, resourceId);
            List<TenantRoleResourcePrivilege> tenantRoleResourcePrivileges = tenantRoleResource.getTenantRoleResourcePrivileges();
            TenantRole role = this.tenantRoleService.getPK(roleId);
            UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
            if (tenantRoleResource == null) {
                returnMap.put("code", -1);
                returnMap.put("message", "该资源没有授权！");
            } else {
                CommonOrderBy orderBy = new CommonOrderBy();
                orderBy.add(CommonOrder.asc("tenantPrivilege.sort"));
                CommonConditionQuery query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
                List<TenantResourcePrivilege> tenantResourcePrivileges = this.tenantResourcePrivilegeService.list(query, orderBy);
                tenantResourcePrivileges.size();
                TenantResource tenantResource = this.tenantResourceService.getByPK(resourceId);
                Tenant2Resource tenant2Resource = this.tenant2ResourceService.findTenant2ResourceByTenantAndReource(details.getTenantId(), resourceId);
                if (tenantResourcePrivileges != null) {
                    for (TenantResourcePrivilege tenantResourcePrivilege : tenantResourcePrivileges) {
                        int displayType = tenantResourcePrivilege.getTenantPrivilege().getDisplayType();

                        String selectedValues = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter(tenantResourcePrivilege.getTenantPrivilege().getId()));
                        String selectedNames = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter((tenantResourcePrivilege.getTenantPrivilege().getId()) + "_value"));
                        String sqlFilterExpress = "";
                        String hqlFilterExpres = "";

                        TenantRoleResourcePrivilege tenantRoleResourcePrivilege = new TenantRoleResourcePrivilege();
                        boolean isAdd = true;//是否为添加
                        if (tenantRoleResource.getTenantRoleResourcePrivileges() != null && tenantRoleResource.getTenantRoleResourcePrivileges().size() > 0) {
                            boo:
                            for (TenantRoleResourcePrivilege tenantRoleResourcePrivilegeTmp : tenantRoleResource.getTenantRoleResourcePrivileges()) {
                                if (tenantResourcePrivilege.getTenantPrivilege().getId().equals(tenantRoleResourcePrivilegeTmp.getTenantPrivilege().getId())) {
                                    tenantRoleResourcePrivilege = tenantRoleResourcePrivilegeTmp;
                                    isAdd = false;
                                    break boo;
                                }
                            }
                        }

                        Tenant2ResourcePrivilege tenant2ResourcePrivilege = this.tenant2ResourcePrivilegeService.findTenant2ResourcePrivilegeByResourceAndPrivilege(tenant2Resource.getId(), tenantResourcePrivilege.getTenantPrivilege().getId());
                        if (com.hisun.util.StringUtils.isNotEmpty(selectedValues)) {
                            PrivilegeRowExpress privilegeRowExpressObj = ApplicationContextUtil.getBean(tenantResourcePrivilege.getTenantPrivilege().getImpclass());
                            if (privilegeRowExpressObj != null) {
                                sqlFilterExpress = privilegeRowExpressObj.getSqlFilterExpressInRole(selectedValues, tenant2ResourcePrivilege.getId());
                                hqlFilterExpres = privilegeRowExpressObj.getHqlFilterExpressInRole(selectedValues, tenant2ResourcePrivilege.getId());
                            }
                        }
                        tenantRoleResourcePrivilege.setTenantResource(tenantResourcePrivilege.getTenantResource());
                        tenantRoleResourcePrivilege.setTenantPrivilege(tenantResourcePrivilege.getTenantPrivilege());
                        tenantRoleResourcePrivilege.setTenantRole(role);
                        tenantRoleResourcePrivilege.setTenantRoleResource(tenantRoleResource);
                        tenantRoleResourcePrivilege.setTenant2ResourcePrivilege(tenant2ResourcePrivilege);
                        tenantRoleResourcePrivilege.setTenantResourcePrivilege(tenantResourcePrivilege);
                        tenantRoleResourcePrivilege.setSelectedNames(selectedNames);
                        tenantRoleResourcePrivilege.setSelectedValues(selectedValues);
                        tenantRoleResourcePrivilege.setHqlFilterExpress(hqlFilterExpres);
                        tenantRoleResourcePrivilege.setSqlFilterExpress(sqlFilterExpress);
                        if (isAdd == true) {
                            tenantRoleResourcePrivileges.add(tenantRoleResourcePrivilege);
                        }
                    }
                }
                tenantRoleResource.setTenantRoleResourcePrivileges(tenantRoleResourcePrivileges);
                this.tenantRoleResourceService.update(tenantRoleResource);
            }
            returnMap.put("code", 1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e, e);
            returnMap.put("code", -1);
            returnMap.put("message", "系统错误，请联系管理员");
        }
        return returnMap;
    }
}
