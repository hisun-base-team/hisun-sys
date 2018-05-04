/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.resource.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.resource.vo.ResourceMenuItem;
import com.hisun.saas.sys.admin.role.vo.ResourceVo;
import com.hisun.util.BeanMapper;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys/admin/resource")
public class AdminResourceController extends BaseController {

	@javax.annotation.Resource
	ResourceService resourceService;
	
	@RequiresPermissions("adminResource:tree")
	@RequestMapping("/list/tree")
	public ModelAndView treeResources() {
		return  new ModelAndView("saas/sys/admin/resource/listTreeResource");
	}
	
	@RequestMapping(value = "/link", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryCodeLink(@RequestParam("menuCode") String menuCode){
		Map<String, Object> map = Maps.newHashMap();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
    	List<AbstractResource> resources = userLoginDetails.getResources();
    	session.setAttribute(Constants.RESOURCE_QUERY_CODE, "");
		session.setAttribute(Constants.MENU_RESOURCE_ID, "");
    	for(AbstractResource resource:resources){
    		if(resource.getResourceType()!=1
					&&StringUtils.isNotBlank(menuCode)
					&&StringUtils.equals(StringUtils.trim(menuCode), resource.getId())){
    			StringBuilder sb = new StringBuilder();
    			if(StringUtils.isNotBlank(resource.getQueryCode())){
    				for(int i =1 ;i<=resource.getQueryCode().length()/3; i++){
    					sb.append(resource.getQueryCode().substring(0,i*3)+",");
    				}
    				sb.deleteCharAt(sb.length()-1);
    			}
				session.setAttribute(Constants.MENU_RESOURCE_ID, resource.getId());
    		}
    	}
		map.put("success", true);
		
		return map;
	}
	
	@RequestMapping("/sitemesh/list")
	public ModelAndView resources(HttpServletRequest req,String pId,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = (User) req.getAttribute(Constants.CURRENT_USER);
		try{
			//String userId = userLoginDetails.getUserid();
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			query.add(CommonRestrictions.and(" type = :type ","type" ,0));
			Long total = resourceService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("sort"));
			List<Resource> resultList = resourceService.list(query, orderBy, pageNum, pageSize);
			PagerVo<Resource> pager = new PagerVo<Resource>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/resource/listResource", map);
	}
	
	@RequestMapping("/tree")
	public @ResponseBody Map<String, Object> tree(@RequestParam(value="status",required=false) Integer status)  throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Resource> resources;
		try {
			CommonOrderBy orderBy = new CommonOrderBy();
			CommonConditionQuery query = new CommonConditionQuery();
			orderBy.add(CommonOrder.asc("sort"));
			if(status!=null){
				query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
			}
			query.add(CommonRestrictions.and(" type = :type ", "type", Integer.valueOf(0)));

			resources = resourceService.list(query, orderBy);
			List<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
			ResourceVo resourceVo;
			resourceVo = new ResourceVo();
			resourceVo.setId("1");
			resourceVo.setName("资源树");
			resourceVo.setOpen(true);
			resourceVo.setpId("-1");
			resourceVos.add(resourceVo);
			for (Resource resource : resources) {
				resourceVo = new ResourceVo();
				BeanMapper.copy(resource, resourceVo);
				resourceVo.setName(resource.getResourceName());
				resourceVo.setHref(resource.getUrl());
				resourceVos.add(resourceVo);
			}
			map.put("success", true);
			map.put("data", resourceVos);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/select/tree")
	public @ResponseBody Map<String, Object> treeSelect(@RequestParam(value="status",required=false) Integer status)  throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Resource> resources;
		try {
			CommonOrderBy orderBy = new CommonOrderBy();
			CommonConditionQuery query = new CommonConditionQuery();
			if(status!=null){
				query.add(CommonRestrictions.and(" status = :status ", "status", Integer.valueOf(0)));
			}else{
				query.add(CommonRestrictions.and(" resourceType != :resourceType ", "resourceType", Integer.valueOf(1)));
			}
			query.add(CommonRestrictions.and(" type = :type ", "type", Integer.valueOf(0)));
			orderBy.add(CommonOrder.asc("sort"));
			resources = resourceService.list(query, orderBy);
			List<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
			ResourceVo resourceVo;
			resourceVo = new ResourceVo();
			resourceVo.setId("1");
			resourceVo.setName("资源树");
			resourceVo.setOpen(true);
			resourceVo.setpId("-1");
			resourceVos.add(resourceVo);
			//resourceVo.setChkDisabled(true);
			for (Resource resource : resources) {
				resourceVo = new ResourceVo();
				BeanMapper.copy(resource, resourceVo);
				resourceVo.setName(resource.getResourceName());
				resourceVo.setUrl("");
				resourceVos.add(resourceVo);
				//resourceVo.setChkDisabled(true);
			}
			map.put("success", true);
			map.put("data", resourceVos);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}

	@RequiresLog(operateType = LogOperateType.SAVE,description = "新增资源:${resource.resourceName}")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(Resource resource,HttpServletRequest request)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String newpId = request.getParameter("newpId");
		try {
			if(resource.getResourceType()!=1){//操作无需code
				if(StringUtils.isNotBlank(newpId)){
					resource.setpId(newpId);
				}
			}
			resource.setType(0);
			resourceService.saveResource(resource,0);
			map.put("success", true);
			map.put("data", resource);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}

		return map;
	}

	@RequiresLog(operateType = LogOperateType.DELETE,description = "删除资源:${id}")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				CommonConditionQuery query = new CommonConditionQuery();
				query.add(CommonRestrictions.and(" pId = :pId ", "pId", id));
				query.add(CommonRestrictions.and(" type = :type ", "type", Integer.valueOf(0)));
				Long total = resourceService.count(query);
				if(total>0){
					map.put("success", false);
					map.put("message", "该树节点还有子节点，不能删除!");
				}else{
					this.resourceService.deleteByPK(id);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");

		}
		return map;
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改资源:${resource.resourceName}")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(Resource resource,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String newpId = request.getParameter("newpId");
		try {
			String oldPid = resource.getpId();
			resource.setpId(newpId);
			this.resourceService.updateResources(resource, oldPid, Integer.valueOf(0),0);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e,e);
			map.put("success", false);
		}
		return map;
	}

	
	/**
	 * 读取用户信息的方法
	 * 
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				Resource entity = this.resourceService.getByPK(id);
				ResourceVo vo = new ResourceVo();
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
			Resource resource = resourceService.getByPK(id);
			if(StringUtils.equalsIgnoreCase(resource.getPermission(), permission)){
				map.put("success", true);
			}else{
				Long total = resourceService.count(query);
				if(total>=1){
					map.put("success", false);
				}else{
					map.put("success", true);
				}
			}
		}else{
			Long total = resourceService.count(query);
			if(total>=1){
				map.put("success", false);
			}else{
				map.put("success", true);
			}
		}
		return map;
	}
	@RequestMapping("/getMenu")
	public @ResponseBody Map<String, Object> getMenu() {
		Map<String, Object> map = new HashMap<String, Object>();
		UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		List<AbstractResource> resources = userLoginDetails.getResources();
		
		List<ResourceMenuItem> items = new ArrayList<ResourceMenuItem>();
		for (AbstractResource resource : resources) {
			if (resource.getResourceType()==2) {//系统 顶层菜单
				ResourceMenuItem item = new ResourceMenuItem();
				BeanMapper.copy(resource, item);
				item.setResId(resource.getId());
				item.setTop(true);
				setAllChildren(item, resources);
				items.add(item);
			}
		}
		
		map.put("success", true);
		map.put("data", items);
		return map;
	}
	
	@RequestMapping("/max/sort")
	public @ResponseBody Map<String, Object> getMaxSort(@RequestParam(value="pId")String pId) {
		Map<String, Object> map = Maps.newHashMap();
		Integer maxSort = this.resourceService.getMaxSort(pId,0);
		map.put("maxSort", maxSort);
		return map;
	}
	private void setAllChildren(ResourceMenuItem node, List<AbstractResource> list) {
		List<ResourceMenuItem> children = new ArrayList<ResourceMenuItem>();
		for (AbstractResource resource : list) {
			if (resource.getpId().equals(node.getResId())&&resource.getResourceType()!=1) {
				ResourceMenuItem child = new ResourceMenuItem();
				BeanMapper.copy(resource, child);
				child.setResId(resource.getId());
				setAllChildren(child, list);
				children.add(child);
			}
		}
		node.setChildren(children);
	}
	
}
