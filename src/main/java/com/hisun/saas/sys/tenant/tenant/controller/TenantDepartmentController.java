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
			TreeNode treeNode=null;
			for (TenantDepartment tenantDepartment : tenantDepartments) {
				treeNode = new TreeNode();
				treeNode.setId(tenantDepartment.getId());
				treeNode.setName(tenantDepartment.getName());
				if(tenantDepartment.getParent()!=null){
					treeNode.setpId(tenantDepartment.getParent().getId());
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
			if(StringUtils.isEmpty(currentNodeId)){//如果未选择左侧的单位,则默认列出所有部门
			}else{
				if(StringUtils.isEmpty(currentNodeParentId)){//如果选择最顶层节点,则列出当前单位下一级部门
					query.add(CommonRestrictions.and(" parent.id = :id ", "id",null));
				}else{
					query.add(CommonRestrictions.and(" parent.id = :id ", "id", currentNodeId));
				}
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
		String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
		String parentId = "";
		if(StringUtils.isNotBlank(currentNodeId) && StringUtils.isNotBlank(currentNodeParentId)){
			parentId = currentNodeId;
		}
		int sort = this.tenantDepartmentService.getMaxSort(parentId);
		TenantDepartmentVo vo = new TenantDepartmentVo();
		map.put("currentNodeId",currentNodeId);
		map.put("currentNodeName",currentNodeName);
		map.put("currentNodeParentId",currentNodeParentId);
		map.put("sort",sort);
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
			if(StringUtils.isNotBlank(parentId)){
				tenantDepartment.setParent(this.tenantDepartmentService.getByPK(parentId));
			}
			tenantDepartment.setTenant(userLoginDetails.getTenant());
			tenantDepartmentService.saveTenantDepartment(tenantDepartment);
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			map.put("success", false);
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
					map.put("msg", "该部门下还有子部门，不能删除!！");
				}else{
					this.tenantDepartmentService.deleteByPK(id);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);

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
		if(tenantDepartment.getParent()==null){
			//vo.setParentId(tenantDepartment.getTenant().getId());
			//vo.setParentName(tenantDepartment.getTenant().getName());
		}else {
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
			String oldPid = "";
			if(tenantDepartment.getParent()!=null){
				oldPid = tenantDepartment.getParent().getId();
			}
			BeanUtils.copyProperties(tenantDepartment, vo);
			tenantDepartment.setParent(this.tenantDepartmentService.getByPK(pId));
			tenantDepartment.setTenant(userLoginDetails.getTenant());
			this.tenantDepartmentService.updateTenantDepartment(tenantDepartment, oldPid, Integer.valueOf(0));
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			map.put("success", false);
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
				vo.setParentId(entity.getParent().getId());
				vo.setParentName(entity.getParent().getName());
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


	@RequiresPermissions("tenant:*")
	@RequestMapping("/max/sort")
	public @ResponseBody Map<String, Object> getMaxSort(@RequestParam(value="pId")String pId) {
		Map<String, Object> map = Maps.newHashMap();
		Integer maxSort = this.tenantDepartmentService.getMaxSort(pId);
		map.put("maxSort", maxSort);
		return map;
	}

	@RequiresPermissions("tenant:*")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getRoleSelection(
			@RequestParam(value = "id",required=false) String id){
		Map<String, Object> map = Maps.newConcurrentMap();
		//CommonConditionQuery query = new CommonConditionQuery();
		try {

			map.put("success", true);
			map.put("data", null);
		} catch (Exception e) {
			logger.error(e,e);
			map.put("success", false);
		}
		return map;
	}




}
