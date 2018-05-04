/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.resource.controller;

import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.saas.sys.tenant.privilege.entity.TenantPrivilege;
import com.hisun.saas.sys.tenant.privilege.service.TenantPrivilegeService;
import com.hisun.saas.sys.tenant.privilege.vo.TenantPrivilegeVo;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.entity.TenantResourcePrivilege;
import com.hisun.saas.sys.tenant.resource.service.TenantResourcePrivilegeService;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import com.hisun.saas.sys.tenant.resource.vo.TenantResourceTreeNode;
import com.hisun.util.BeanMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author liuzj {279421824@qq.com}
*/
@Controller
@RequestMapping("/sys/tenant/resourcePrivilege")
public class TenantResourcePrivilegeController extends BaseController {

    @Resource
    private TenantPrivilegeService tenantPrivilegeService;
    @Resource
    TenantResourceService tenantResourceService;
    @Resource
    private TenantResourcePrivilegeService tenantResourcePrivilegeService;

    @RequestMapping("/list")
    public ModelAndView treeResources() {
        return  new ModelAndView("saas/sys/tenant/resourcePrivilege/listTreeResource");
    }

    @RequiresPermissions("sys-tenantResourcePrivilege:*")
    @RequestMapping("/ajax/privilegeList")
    public ModelAndView list(String resourceId,String resourceName) throws UnsupportedEncodingException {
        Map<String,Object> model = new HashMap<String,Object>();
        if(resourceId!=null && !resourceId.equals("1")) {
            TenantResource tenantResource = this.tenantResourceService.getByPK(resourceId);
            int privilegeSetting = tenantResource.getPrivilegeSetting();
            if(tenantResource.getPrivilegeSetting()==1) {
                CommonConditionQuery query = new CommonConditionQuery();
                CommonOrderBy orderBy = new CommonOrderBy();
                orderBy.add(CommonOrder.asc("sort"));
                List<TenantPrivilege> resultList = tenantPrivilegeService.list(query, orderBy);

                query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" tenantResource.id = :resourceId ", "resourceId", resourceId));
                List<TenantResourcePrivilege> tenantResourcePrivileges = this.tenantResourcePrivilegeService.list(query, null);

                List<TenantPrivilegeVo> vos = new ArrayList<TenantPrivilegeVo>();
                if (resultList != null) {
                    for (TenantPrivilege tenantPrivilege : resultList) {
                        TenantPrivilegeVo vo = new TenantPrivilegeVo();
                        BeanMapper.copy(tenantPrivilege, vo);
                        if (tenantResourcePrivileges != null) {
                            boo:
                            for (TenantResourcePrivilege tenantResourcePrivilege : tenantResourcePrivileges) {
                                if (tenantPrivilege.getId().equals(tenantResourcePrivilege.getTenantPrivilege().getId())) {
                                    vo.setIsChecked("true");
                                    break boo;
                                }
                            }
                        }
                        vos.add(vo);
                    }
                }
                model.put("vos", vos);
            }
            model.put("privilegeSetting",privilegeSetting);
        }else{
            model.put("privilegeSetting",0);
        }
        model.put("resourceId",resourceId);
        model.put("resourceName",resourceName);
        return new ModelAndView("saas/sys/tenant/resourcePrivilege/list",model);
    }

    @RequestMapping("/tree")
    public @ResponseBody Map<String, Object> tree(@RequestParam(value="status",required=false) Integer status,String dictionaryType)
            throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TenantResource> resources;
        List<TenantResource> privilegeSettingResources;

        try {
            CommonOrderBy orderBy = new CommonOrderBy();
            CommonConditionQuery query = new CommonConditionQuery();
            orderBy.add(CommonOrder.asc("sort"));
            if(status!=null){
                query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
            }
            resources = tenantResourceService.list(query, orderBy);

//            query = new CommonConditionQuery();
//            query.add(CommonRestrictions.and(" privilegeSetting = :privilegeSetting ", "privilegeSetting", Integer.valueOf(1)));
//            if(status!=null){
//                query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
//            }
//            privilegeSettingResources = tenantResourceService.list(query, orderBy);

            List<TenantResourceTreeNode> treeNodeVos = new ArrayList<TenantResourceTreeNode>();
            TenantResourceTreeNode treeNodeVo = new TenantResourceTreeNode();
            treeNodeVo.setId("1");
            treeNodeVo.setName("资源树");
            treeNodeVo.setOpen(true);
            treeNodeVo.setpId("-1");
            treeNodeVo.setPrivilegeSetting("0");
            treeNodeVos.add(treeNodeVo);
            for (TenantResource resource : resources) {
                    treeNodeVo = new TenantResourceTreeNode();
                    BeanMapper.copy(resource, treeNodeVo);
                    treeNodeVo.setName(resource.getResourceName());
                    treeNodeVo.setUrl(resource.getUrl());
                    treeNodeVo.setPrivilegeSetting(resource.getPrivilegeSetting().toString());
                    treeNodeVos.add(treeNodeVo);
            }

            map.put("success", true);
            map.put("data", treeNodeVos);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            logger.error(e);
        }
        return map;
    }

    @RequiresPermissions("sys-tenantResourcePrivilege:*")
    @RequestMapping("/ajax/save")
    public @ResponseBody Map<String,Object> save(String resourceId,String privilegeIds) throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        if(resourceId==null || resourceId.equals("")){
            returnMap.put("message","资源不能为空");
            returnMap.put("code",-1);
            return returnMap;
        }
        if(privilegeIds==null || privilegeIds.equals("")){
            returnMap.put("message","所选权限资源不能为空");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            String[] privilegeIdArr = privilegeIds.split(",");
            TenantResource tenantResource = this.tenantResourceService.getByPK(resourceId);
            TenantPrivilege tenantPrivilege = null;
            TenantResourcePrivilege tenantResourcePrivilege = null;
            List<TenantResourcePrivilege> tenantResourcePrivileges = tenantResource.getTenantResourcePrivileges();

            for(int i=0;i<privilegeIdArr.length;i++){
                tenantPrivilege = this.tenantPrivilegeService.getPK(privilegeIdArr[i]);
                boolean isAdd = true;
                boo:for(TenantResourcePrivilege tenantResourcePrivilegeTmp : tenantResourcePrivileges){
                    if(privilegeIdArr[i].equals(tenantResourcePrivilegeTmp.getTenantPrivilege().getId())){
                        isAdd = false;
                        break boo;
                    }
                }
                if(isAdd == true) {
                    tenantResourcePrivilege = new TenantResourcePrivilege();
                    tenantResourcePrivilege.setTenantPrivilege(tenantPrivilege);
                    tenantResourcePrivilege.setTenantResource(tenantResource);
                    this.tenantResourcePrivilegeService.save(tenantResourcePrivilege);
                }
            }
            //执行删除
            for(TenantResourcePrivilege tenantResourcePrivilegeTmp : tenantResourcePrivileges) {
                boolean isDelete = true;
                boo:for(int i=0;i<privilegeIdArr.length;i++){
                    if (privilegeIdArr[i].equals(tenantResourcePrivilegeTmp.getTenantPrivilege().getId())) {
                        isDelete = false;
                        break boo;
                    }
                }
                if(isDelete == true) {
                    this.tenantResourcePrivilegeService.deleteTenantResourcePrivilege(tenantResourcePrivilegeTmp.getId());
                }
            }
            returnMap.put("code",1);
        }catch (GenericException e){
            e.printStackTrace();
            returnMap.put("code",-1);
            returnMap.put("message", e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e,e);
            returnMap.put("code",-1);
            returnMap.put("message", "系统错误，请联系管理员");
        }

        return returnMap;
    }
}