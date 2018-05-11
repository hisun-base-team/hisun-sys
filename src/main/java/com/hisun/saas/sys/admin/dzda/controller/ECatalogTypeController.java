/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dzda.controller;

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
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.saas.sys.admin.dzda.entity.ECatalogTypeInfo;
import com.hisun.saas.sys.admin.dzda.service.ECatalogTypeService;
import com.hisun.saas.sys.admin.dzda.vo.ECatalogTypeInfoVo;
import com.hisun.saas.sys.util.EntityWrapper;
import com.hisun.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
* @author liuzj {279421824@qq.com}
*/
@Controller
@RequestMapping("/sys/admin/dzda/catalogType")
public class ECatalogTypeController extends BaseController {

    @Resource
    private ECatalogTypeService eCatalogTypeService;

    @RequestMapping(value = "/list")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<String, Object>();
        return new ModelAndView("saas/sys/admin/dzda/catalogType/list",map);
    }

    @RequestMapping("/ajax/add")
    public ModelAndView add(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
        String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
        ECatalogTypeInfoVo vo = new ECatalogTypeInfoVo();
        String parentId = "";
        int parentSort = 0;
        if(StringUtils.isNotBlank(currentNodeId) && StringUtils.isNotBlank(currentNodeParentId)){
            parentId = currentNodeId;
            ECatalogTypeInfo parentECatalogTypeInfo = this.eCatalogTypeService.getByPK(parentId);
            vo.setParentId(parentId);
            vo.setParentName(parentECatalogTypeInfo.getCatalogValue());
            parentSort=parentECatalogTypeInfo.getSort();
        }else{
            vo.setParentId(currentNodeId);
            vo.setParentName(currentNodeName);
        }
        int sort = this.eCatalogTypeService.getMaxSort(parentId);

        vo.setSort(sort);

        Integer catalogCode = sort * 10;
        if(catalogCode<100&&StringUtils.isEmpty(parentId)){
            vo.setCatalogCode("0"+catalogCode.toString());
        }else if(StringUtils.isNotEmpty(parentId)){
            catalogCode = parentSort * 10 + sort;
            if(catalogCode<100){
                vo.setCatalogCode("0"+catalogCode.toString());
            }else {
                vo.setCatalogCode(catalogCode.toString());
            }
        }else {
            vo.setCatalogCode(catalogCode.toString());
        }



        map.put("currentNodeId",currentNodeId);
        map.put("currentNodeName",currentNodeName);
        map.put("currentNodeParentId",currentNodeParentId);
        map.put("parentSort",parentSort);
        map.put("vo",vo);
        return new ModelAndView("saas/sys/admin/dzda/catalogType/addCatalogType",map);
    }
    @RequestMapping("/ajax/changeAddEdit")
    public @ResponseBody Map<String, Object> changeAddEdit(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
        String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
        ECatalogTypeInfoVo vo = new ECatalogTypeInfoVo();
        String parentId = "";
        int parentSort = 0;
        if(StringUtils.isNotBlank(currentNodeId) && StringUtils.isNotBlank(currentNodeParentId)){
            parentId = currentNodeId;
            ECatalogTypeInfo parentECatalogTypeInfo = this.eCatalogTypeService.getByPK(parentId);
            vo.setParentId(parentId);
            vo.setParentName(parentECatalogTypeInfo.getCatalogValue());
            parentSort=parentECatalogTypeInfo.getSort();
        }else{
            vo.setParentId(currentNodeId);
            vo.setParentName(currentNodeName);
        }
        int sort = this.eCatalogTypeService.getMaxSort(parentId);

        vo.setSort(sort);

        Integer catalogCode = sort * 10;
        if(catalogCode<100&&StringUtils.isEmpty(parentId)){
            vo.setCatalogCode("0"+catalogCode.toString());
        }else if(StringUtils.isNotEmpty(parentId)){
            catalogCode = parentSort * 10 + sort;
            if(catalogCode<100){
                vo.setCatalogCode("0"+catalogCode.toString());
            }else {
                vo.setCatalogCode(catalogCode.toString());
            }
        }else {
            vo.setCatalogCode(catalogCode.toString());
        }

        map.put("sort",vo.getSort());
        map.put("catalogCode",vo.getCatalogCode());
        return map;
    }

    @RequiresPermissions("catalogType:*")
    @RequestMapping("/ajax/list")
    public ModelAndView list(HttpServletRequest request,
                             @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                             @RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        String currentNodeId = StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        String currentNodeName = StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
        String currentNodeParentId =StringUtils.trimNull2Empty( request.getParameter("currentNodeParentId"));//取得当前树节点的父ID属性
        try{
            CommonConditionQuery query = new CommonConditionQuery();
            if(StringUtils.isEmpty(currentNodeParentId)){//如果选择最顶层节点,则列出当前单位下一级部门
                query.add(CommonRestrictions.and(" parent.id is null ",null,null));
            }else{
                query.add(CommonRestrictions.and(" parent.id = :id ", "id", currentNodeId));
            }

            Long total = this.eCatalogTypeService.count(query);
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<ECatalogTypeInfo> entities = this.eCatalogTypeService.list(query, orderBy, pageNum, pageSize);
            List<ECatalogTypeInfoVo> vos = new ArrayList<>();
            ECatalogTypeInfoVo vo = null;
            if(entities!=null){
                for(ECatalogTypeInfo entity : entities){
                    vo = new ECatalogTypeInfoVo();
                    BeanUtils.copyProperties(vo,entity);
                    vo.setName(entity.getCatalogValue());
                    vos.add(vo);
                }
            }
            PagerVo<ECatalogTypeInfoVo> pager = new PagerVo<ECatalogTypeInfoVo>(vos, total.intValue(), pageNum, pageSize);
            map.put("pager", pager);
            map.put("currentNodeId",currentNodeId);
            map.put("currentNodeName",currentNodeName);
            map.put("currentNodeParentId",currentNodeParentId);
        }catch(Exception e){
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("saas/sys/admin/dzda/catalogType/listCatalogType", map);
    }

    @RequestMapping("/tree")
    public @ResponseBody
    Map<String, Object> tree() throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<ECatalogTypeInfo> eCatalogTypeInfos = eCatalogTypeService.list(query, orderBy);
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            TreeNode treeNode = new TreeNode();
            treeNode.setId("0");
            treeNode.setName("目录类型");
            treeNode.setpId("0");
            treeNode.setOpen(true);
            treeNodes.add(treeNode);
            TreeNode childTreeNode=null;
            for (ECatalogTypeInfo eCatalogTypeInfo : eCatalogTypeInfos) {
                childTreeNode = new TreeNode();
                childTreeNode.setId(eCatalogTypeInfo.getId());
                childTreeNode.setName(eCatalogTypeInfo.getSort()+"."+eCatalogTypeInfo.getCatalogValue());
                if(eCatalogTypeInfo.getParent()==null){
                    childTreeNode.setpId(treeNode.getId());
                }else{
                    childTreeNode.setpId(eCatalogTypeInfo.getParent().getId());
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

    @RequiresLog(operateType = LogOperateType.SAVE,description = "增加材料目录:${vo.name}")
    @RequiresPermissions("catalogType:*")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> save(ECatalogTypeInfoVo vo,HttpServletRequest request) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        String parentId = vo.getParentId();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            ECatalogTypeInfo eCatalogTypeInfo = new ECatalogTypeInfo();
            BeanUtils.copyProperties(eCatalogTypeInfo, vo);
            if(StringUtils.isNotBlank(parentId)){
                eCatalogTypeInfo.setParent(this.eCatalogTypeService.getByPK(parentId));
            }
            eCatalogTypeInfo.setCatalogCode(vo.getCatalogCode());
            eCatalogTypeInfo.setCatalogValue(vo.getName());
            EntityWrapper.wrapperSaveBaseProperties(eCatalogTypeInfo,userLoginDetails);
            eCatalogTypeService.save(eCatalogTypeInfo);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new GenericException(e);
        }

        return map;
    }

    @RequestMapping("add/edit/tree")
    public @ResponseBody
    Map<String, Object> addEditTree() throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("sort"));
            List<ECatalogTypeInfo> eCatalogTypeInfos = eCatalogTypeService.list(query, orderBy);
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            TreeNode treeNode = new TreeNode();
            treeNode.setId("0");
            treeNode.setName("目录类型");
            treeNode.setpId("0");
            treeNode.setOpen(true);
            treeNodes.add(treeNode);
            TreeNode childTreeNode=null;
            for (ECatalogTypeInfo eCatalogTypeInfo : eCatalogTypeInfos) {
                childTreeNode = new TreeNode();
                childTreeNode.setId(eCatalogTypeInfo.getId());
                childTreeNode.setName(eCatalogTypeInfo.getCatalogValue());
                if(eCatalogTypeInfo.getParent()==null){
                    childTreeNode.setpId(treeNode.getId());
                    treeNodes.add(childTreeNode);
                }
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

    @RequestMapping("/ajax/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, HttpServletRequest request){
        Map<String,Object> model = new HashMap<String,Object>();
        int parentSort = 0;
        ECatalogTypeInfo eCatalogTypeInfo = eCatalogTypeService.getByPK(id);
        ECatalogTypeInfoVo vo = new ECatalogTypeInfoVo();
        String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
        String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
        org.springframework.beans.BeanUtils.copyProperties(eCatalogTypeInfo,vo);
        vo.setName(eCatalogTypeInfo.getCatalogValue());
        if(eCatalogTypeInfo.getParent()!=null){
            vo.setParentId(eCatalogTypeInfo.getParent().getId());
            vo.setParentName(eCatalogTypeInfo.getParent().getCatalogValue());
            parentSort= eCatalogTypeInfo.getParent().getSort();
        }else {
            vo.setParentName("目录类型");
        }

        model.put("parentSort",parentSort);
        model.put("currentNodeId",currentNodeId);
        model.put("currentNodeName",currentNodeName);
        model.put("currentNodeParentId",currentNodeParentId);
        model.put("vo", vo);
        return new ModelAndView("saas/sys/admin/dzda/catalogType/editCatalogType", model);
    }

    @RequiresLog(operateType = LogOperateType.UPDATE,description = "修改材料目录:${vo.name}")
    @RequiresPermissions("catalogType:*")
    @RequestMapping(value = "/update")
    public @ResponseBody Map<String, Object> update(ECatalogTypeInfoVo vo,HttpServletRequest request) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        String pId = vo.getParentId();
        try {

            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            ECatalogTypeInfo eCatalogTypeInfo = this.eCatalogTypeService.getByPK(vo.getId());
            if(StringUtils.trimNull2Empty(pId).equals(eCatalogTypeInfo.getId())){
                map.put("success", false);
                map.put("msg", "上级目录不能是自身!");
            }else{
                String oldPid = "";
                int oldSort = eCatalogTypeInfo.getSort();
                if(eCatalogTypeInfo.getParent()!=null){
                    oldPid = eCatalogTypeInfo.getParent().getId();
                }
                BeanUtils.copyProperties(eCatalogTypeInfo, vo);
                eCatalogTypeInfo.setCatalogValue(vo.getName());
                if(StringUtils.isNotBlank(pId) && !pId.equals(userLoginDetails.getTenantId())){
                    eCatalogTypeInfo.setParent(this.eCatalogTypeService.getByPK(pId));
                }
                EntityWrapper.wrapperUpdateBaseProperties(eCatalogTypeInfo,userLoginDetails);
                this.eCatalogTypeService.updateCatalogType(eCatalogTypeInfo, oldPid, oldSort);
                map.put("success", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new GenericException(e.getMessage());
        }
        return map;
    }

    @RequiresLog(operateType = LogOperateType.DELETE,description = "删除材料:${id}")
    @RequiresPermissions("catalogType:*")
    @RequestMapping(value = "/delete/{id}")
    public @ResponseBody Map<String, Object> delete(
            @PathVariable("id") String id) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(id)) {
                CommonConditionQuery query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" parent.id = :pId ", "pId", id));
                Long total = eCatalogTypeService.count(query);
                if(total>0){
                    map.put("success", false);
                    map.put("msg", "该目录下还有子目录，不能删除!");
                }else{
                    this.eCatalogTypeService.deleteByPK(id);
                    map.put("success", true);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e);
        }
        return map;
    }
}