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
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;
import com.hisun.saas.sys.tenant.tenant.service.TenantDepartmentService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantDepartmentVo;
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


@Controller
@RequestMapping("/sys/tenant/department")
public class TenantDepartmentController extends BaseController {

	@Resource
	private TenantDepartmentService tenantDepartmentService;


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
			treeNode.setName(userLoginDetails.getTenantName());
			treeNode.setId(userLoginDetails.getTenantId());
			treeNodes.add(treeNode);
			for (TenantDepartment tenantDepartment : tenantDepartments) {
				treeNode = new TreeNode();
				treeNode.setId(tenantDepartment.getId());
				treeNode.setName(tenantDepartment.getName());
				if(tenantDepartment.getParent()!=null){
					treeNode.setpId(tenantDepartment.getParent().getId());
				}else{
					treeNode.setpId(userLoginDetails.getTenantId());
				}
				treeNodes.add(treeNode);
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


	@RequiresPermissions("tenant:*")
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

			Long total = tenantDepartmentService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("sort"));
			List<TenantDepartment> entities = this.tenantDepartmentService.list(query, orderBy, pageNum, pageSize);
			List<TenantDepartmentVo> vos = new ArrayList<>();
			TenantDepartmentVo vo = null;
			if(entities!=null){
				for(TenantDepartment entity : entities){
					vo = new TenantDepartmentVo();
					BeanUtils.copyProperties(vo,entity);
					vos.add(vo);
				}
			}
			PagerVo<TenantDepartmentVo> pager = new PagerVo<TenantDepartmentVo>(vos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
			map.put("currentNodeId",currentNodeId);
			map.put("currentNodeName",currentNodeName);
			map.put("currentNodeParentId",currentNodeParentId);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/tenant/tenant/department/listDepartment", map);
	}


	@RequiresPermissions("tenant:*")
	@RequestMapping("/ajax/add")
	public ModelAndView add(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
		String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
		TenantDepartmentVo vo = new TenantDepartmentVo();
		String parentId = "";
		if(StringUtils.isNotBlank(currentNodeId) && StringUtils.isNotBlank(currentNodeParentId)){
			parentId = currentNodeId;
			TenantDepartment parentDepartment = this.tenantDepartmentService.getByPK(parentId);
			vo.setParentId(parentId);
			vo.setParentName(parentDepartment.getName());
		}else{
			vo.setParentId(userLoginDetails.getTenantId());
			vo.setParentName(userLoginDetails.getTenantName());
		}
		int sort = this.tenantDepartmentService.getMaxSort(parentId);

		vo.setSort(sort);
		map.put("currentNodeId",currentNodeId);
		map.put("currentNodeName",currentNodeName);
		map.put("currentNodeParentId",currentNodeParentId);
		map.put("vo",vo);
		return new ModelAndView("saas/sys/tenant/tenant/department/addDepartment",map);
	}

	@RequiresLog(operateType = LogOperateType.SAVE,description = "增加部门:${vo.name}")
	@RequiresPermissions("tenant:*")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(TenantDepartmentVo vo,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String parentId = vo.getParentId();
		try {
			UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
			TenantDepartment tenantDepartment = new TenantDepartment();
			BeanUtils.copyProperties(tenantDepartment, vo);
			if(StringUtils.isNotBlank(parentId)&& !parentId.equals(userLoginDetails.getTenantId())){
				tenantDepartment.setParent(this.tenantDepartmentService.getByPK(parentId));
			}
			tenantDepartment.setTenant(userLoginDetails.getTenant());
			EntityWrapper.wrapperSaveBaseProperties(tenantDepartment,userLoginDetails);
			tenantDepartmentService.saveTenantDepartment(tenantDepartment);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new GenericException(e);
		}

		return map;
	}

	@RequiresLog(operateType = LogOperateType.DELETE,description = "删除部门:${id}")
	@RequiresPermissions("tenant:*")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				CommonConditionQuery query = new CommonConditionQuery();
				query.add(CommonRestrictions.and(" parent.id = :pId ", "pId", id));
				Long total = tenantDepartmentService.count(query);
				if(total>0){
					map.put("success", false);
					map.put("msg", "该部门下还有子部门，不能删除!");
				}else{
					this.tenantDepartmentService.deleteByPK(id);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("tenant:*")
	@RequestMapping("/ajax/edit/{id}")
	public ModelAndView edit(@PathVariable("id") String id,HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		TenantDepartment tenantDepartment = tenantDepartmentService.getByPK(id);
		TenantDepartmentVo vo = new TenantDepartmentVo();
		String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
		org.springframework.beans.BeanUtils.copyProperties(tenantDepartment,vo);
		if(tenantDepartment.getParent()!=null){
			vo.setParentId(tenantDepartment.getParent().getId());
			vo.setParentName(tenantDepartment.getParent().getName());
		}
		model.put("currentNodeId",currentNodeId);
		model.put("currentNodeName",currentNodeName);
		model.put("currentNodeParentId",currentNodeParentId);
		model.put("vo", vo);
		return new ModelAndView("saas/sys/tenant/tenant/department/editDepartment", model);
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改部门:${vo.name}")
	@RequiresPermissions("tenant:*")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(TenantDepartmentVo vo,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String pId = vo.getParentId();
		try {

			UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
			TenantDepartment tenantDepartment = this.tenantDepartmentService.getByPK(vo.getId());
			if(StringUtils.trimNull2Empty(pId).equals(tenantDepartment.getId())){
				map.put("success", false);
				map.put("msg", "上级部门不能是自身!");
			}else{
				String oldPid = "";
				int oldSort = tenantDepartment.getSort();
				if(tenantDepartment.getParent()!=null){
					oldPid = tenantDepartment.getParent().getId();
				}
				BeanUtils.copyProperties(tenantDepartment, vo);
				if(StringUtils.isNotBlank(pId) && !pId.equals(userLoginDetails.getTenantId())){
					tenantDepartment.setParent(this.tenantDepartmentService.getByPK(pId));
				}
				tenantDepartment.setTenant(userLoginDetails.getTenant());
				EntityWrapper.wrapperUpdateBaseProperties(tenantDepartment,userLoginDetails);
				this.tenantDepartmentService.updateTenantDepartment(tenantDepartment, oldPid, oldSort);
				map.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new GenericException(e.getMessage());
		}
		return map;
	}

	@RequiresPermissions("tenant:*")
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				TenantDepartment entity = this.tenantDepartmentService.getByPK(id);
				TenantDepartmentVo vo = new TenantDepartmentVo();
				BeanUtils.copyProperties(vo, entity);
				if(entity.getParent()!=null){
					vo.setParentId(entity.getParent().getId());
					vo.setParentName(entity.getParent().getName());
				}
				map.put("data", vo);
				map.put("success", true);
			} else {
				map.put("success", false);
			}
		} catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}

}
