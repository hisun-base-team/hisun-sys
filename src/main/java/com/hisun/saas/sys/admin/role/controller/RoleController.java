/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.role.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.saas.sys.admin.role.entity.RoleResource;
import com.hisun.saas.sys.admin.role.service.RoleService;
import com.hisun.saas.sys.admin.role.vo.RoleVo;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.role.vo.ResourceVo;
import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.saas.sys.admin.user.vo.UserVo;
import com.hisun.util.BeanMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys/admin/role")
public class RoleController extends BaseController {

	@javax.annotation.Resource
	private RoleService roleService;
	
	@javax.annotation.Resource
	private UserService userService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	@RequiresPermissions("admin-sys:role_add")
	@RequestMapping(value = "/add")
	public ModelAndView add(){
		return new ModelAndView("saas/sys/admin/role/addRole");
	}

	@RequiresPermissions("admin-sys:role_add")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(
			@ModelAttribute("roleVo") RoleVo roleVo,
			HttpServletResponse response)
			throws GenericException {
		
		String ret = "false";
		Role role = new Role();
		try {

			BeanUtils.copyProperties(role, roleVo);
			
			String id = role.getId();
			if (id == null || id.length() == 0) {
				this.roleService.save(role);
			} else {
				this.roleService.update(role);
			}
			PrintWriter pw = response.getWriter();
			ret = "true";
			pw.write(ret);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	@RequiresPermissions("admin-sys:role_update")
	@RequestMapping(value="/update/{roleId}",method = RequestMethod.GET)
	public ModelAndView update(@PathVariable("roleId") String roleId)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Role role = this.roleService.getByPK(roleId);
			RoleVo vo = new RoleVo();
			org.springframework.beans.BeanUtils.copyProperties(role, vo);
			map.put("vo", vo);
		}catch(Exception e){
			logger.error(e, e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/role/updateRole",map);
	}

	@RequiresPermissions("admin-sys:role_update")
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(
			@ModelAttribute("roleVo") RoleVo roleVo,
			@ModelAttribute("userLoginDetails") UserLoginDetails userLoginDetails)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(roleVo!=null && roleVo.getId()!=null
					&&roleVo.getId().equals("")==false){
				Role role = this.roleService.getByPK(roleVo.getId());
				role.setRoleName(roleVo.getRoleName());
				role.setRoleCode(roleVo.getRoleCode());
				role.setSort(roleVo.getSort());
				role.setDescription(roleVo.getDescription());
				this.roleService.update(role);
				map.put("success", true);
			}
		}catch(Exception e){
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");
		}
		return map;
	}

	/**
	 * 读取用户信息的方法
	 * @param RoleId
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value="/{RoleId}",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> get(@PathVariable("RoleId") String RoleId)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(RoleId!=null && RoleId.trim().equals("")==false){
				Role entity = this.roleService.getByPK(RoleId);
				RoleVo vo = new RoleVo();
				BeanUtils.copyProperties(vo, entity);
				map.put("data", vo);
				map.put("success", true);
			}else{
				throw new GenericException("错误：参数不正确。");
			}
		}catch(Exception e){
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("admin-sys:role_delete")
	@RequestMapping(value="/delete/{roleId}")
	public @ResponseBody Map<String,Object> delete(@PathVariable("roleId") String roleId)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(roleId!=null && roleId.trim().equals("")==false){
				this.roleService.deleteRoleUser(roleId);
				map.put("success", true);
			}else{
				throw new GenericException("错误：参数不正确。");
			}
		}catch(Exception e){
			throw new GenericException(e);
		}
		return map;
	}

	@RequestMapping(value="/authorization/{roleId}",method = RequestMethod.GET)
	public ModelAndView authorization(@PathVariable("roleId") String roleId)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Role role = this.roleService.getByPK(roleId);
			RoleVo vo = new RoleVo();
			org.springframework.beans.BeanUtils.copyProperties(role, vo);
			map.put("vo", vo);
		}catch(Exception e){
			logger.error(e, e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/role/authorization",map);
	}
	
	@RequiresPermissions("adminRole:*")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize,@RequestParam(value="searchContent",required=false)String searchContent) throws GenericException {
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" Role.roleCode!=:roleCode", "roleCode", "ROLE_ADMINISTRATOR"));
			if(StringUtils.isNotBlank(searchContent)){
				query.add(CommonRestrictions.and(" Role.roleName like :roleName",
						"roleName", "%"+searchContent+"%"));
			}
			Long total = this.roleService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("roleCode"));
			orderBy.add(CommonOrder.asc("sort"));
			List<Role> queryList = this.roleService.list(query, orderBy, pageNum, pageSize);
			List<RoleVo> resultList = new ArrayList<RoleVo>();
			for (Role role : queryList) {
				RoleVo vo = new RoleVo();
				BeanUtils.copyProperties(vo, role);
				resultList.add(vo);
			}
			PagerVo<RoleVo> pager = new PagerVo<RoleVo>(resultList, total.intValue(), pageNum, pageSize);
			
			request.setAttribute("pager", pager);
			request.setAttribute("searchContent", searchContent);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		
		return "saas/sys/admin/role/roleList";
	}
	
	/**
	 * 检查角色名
	 * 
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/checkRoleName", method = RequestMethod.POST)
	public void checkUserName(
			@RequestParam(value = "roleName") String roleName,
			HttpServletResponse response) throws GenericException {
		String ret = "false";
		//System.out.println(roleName);
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" Role.roleName = :roleName",
				"roleName", roleName));
		try {
			long count = this.roleService.count(query);
			if (count == 0) {
				ret = "true";
			}
			PrintWriter pw = response.getWriter();
			pw.write(ret);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}
	
	/**
	 * 读取角色信息
	 * 
	 * @param 
	 * @param 
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> load(
			@RequestParam(value = "id") String id,
			HttpServletResponse response) throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Role entity = this.roleService.getByPK(id);
			//Tenant tenant = entity.getTenant();
			RoleVo vo = new RoleVo();
			BeanUtils.copyProperties(vo, entity);
			map.put("vo", vo);
			//if(tenant!=null){
			//	map.put("orgId", tenant.getId());
			//}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}

	@RequestMapping(value = "/setAuth", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>  setAuth(
			@RequestParam("id") String id,
			@RequestParam("resIds") String resIds,
			HttpServletResponse response)
			throws GenericException {
		Map<String, Object> map =  Maps.newHashMap();
		
		try {
			String[] strs = StringUtils.splitByWholeSeparator(resIds, ";");
			this.roleService.saveRoleResource(id, Arrays.asList(strs));
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("admin-sys:role_member")
	@RequestMapping(value="/member/{roleId}",method = RequestMethod.GET)
	public ModelAndView member(@PathVariable("roleId") String roleId)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Role role = this.roleService.getByPK(roleId);
			RoleVo vo = new RoleVo();
			org.springframework.beans.BeanUtils.copyProperties(role, vo);
			List<Object> paramList = Lists.newArrayList();
			String hql = "select DISTINCT(user) from User as user where user.username !=?";
			paramList.add("admin");
			List<User> users = this.userService.list(hql, paramList, Integer.valueOf(1), Integer.MAX_VALUE);

			//已经属于该角色的用户
			paramList = Lists.newArrayList();
			hql = "select DISTINCT(user) from User as user "
					+ "join user.userRoles as userRole "
					+ "join userRole.role as role "
					+ " where role.id=?";
			paramList.add(roleId);
			List<User> users1 = this.userService.list(hql, paramList, Integer.valueOf(1), Integer.MAX_VALUE);

			List<String> userIds = Lists.newArrayList();
			List<UserVo> userVos = Lists.newArrayList();
			UserVo userVo ;
			for(User user : users1){
				userVo= new UserVo();
				org.springframework.beans.BeanUtils.copyProperties(user, userVo);
				userVos.add(userVo);
				userIds.add(user.getId());
			}

			List<UserVo> allUserVos = Lists.newArrayList();
			for(User user : users){
				userVo= new UserVo();
				org.springframework.beans.BeanUtils.copyProperties(user, userVo);
				if(!userIds.contains(user.getId())){
					allUserVos.add(userVo);
				}
			}


			map.put("vo", vo);
			map.put("allUserVos", allUserVos);
			map.put("userVos", userVos);
		}catch(Exception e){
			logger.error(e, e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/role/member",map);
	}

	@RequiresPermissions("admin-sys:role_member")
	@RequestMapping(value = "/set/member", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>  setMember(
			@RequestParam("id") String id,
			@RequestParam(value = "userIds[]",required = false) String[] userIds,
			HttpServletResponse response)
			throws GenericException {
		Map<String, Object> map =  Maps.newHashMap();
		
		try {
			this.roleService.saveRoleMember(id, userIds);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			throw new GenericException(e);
		}
		return map;
	}
	
	@RequestMapping(value = "/resource/{roleId}")
	public @ResponseBody Map<String, Object> getResources(
			@PathVariable("roleId") String roleId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (roleId != null && roleId.trim().equals("") == false) {
				List<RoleResource> roleResources = this.roleService.getByPK(roleId).getRoleResources();
				//List<Resource> resources = this.roleService.getByPK(roleId).getResources();
				List<String> list = Lists.newArrayList();
				for(RoleResource roleResource : roleResources){
					//if(resource.getResourceType()!=1){
						list.add(roleResource.getResource().getId());
					//}
				}
				map.put("data", list);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
	
	@RequestMapping(value = "/readonly/resource/{roleId}")
	public @ResponseBody Map<String, Object> readonlyResources(
			@PathVariable("roleId") String roleId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (roleId != null && roleId.trim().equals("") == false) {
//				List<Resource> resources = this.roleService.getByPK(roleId).getResources();
				List<RoleResource> roleResources = this.roleService.getByPK(roleId).getRoleResources();
				List<String> list = Lists.newArrayList();
				for(RoleResource resource : roleResources){
					list.add(resource.getResource().getId());
				}
				CommonOrderBy orderBy = new CommonOrderBy();
				CommonConditionQuery query = new CommonConditionQuery();
				query.add(CommonRestrictions.and(" resourceType != :resourceType ", "resourceType", Integer.valueOf(1)));
				query.add(CommonRestrictions.and(" type = :type ", "type", Integer.valueOf(0)));
				orderBy.add(CommonOrder.asc("sort"));
				List<Resource> resources = resourceService.list(query, orderBy);
				List<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
				ResourceVo resourceVo;
				resourceVo = new ResourceVo();
				resourceVo.setId("1");
				resourceVo.setName("资源树");
				resourceVo.setOpen(true);
				resourceVo.setpId("-1");
				resourceVos.add(resourceVo);
				resourceVo.setChkDisabled(true);
				resourceVo.setChecked(true);
				for (Resource resource : resources) {
					resourceVo = new ResourceVo();
					BeanMapper.copy(resource, resourceVo);
					resourceVo.setName(resource.getResourceName());
					resourceVo.setUrl("");
					resourceVos.add(resourceVo);
					resourceVo.setChkDisabled(true);
					if(list.contains(resource.getId())){
						resourceVo.setChecked(true);
					}
				}
				map.put("data", resourceVos);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
	
	@RequestMapping(value = "/user/{roleId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getUsers(
			@PathVariable("roleId") String roleId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (roleId != null && roleId.trim().equals("") == false) {
				List<UserRole> users = this.roleService.getByPK(roleId).getUserRoles();
				List<String> list = Lists.newArrayList();
				for(UserRole user : users){
					list.add(user.getUser().getId());
				}
				map.put("data", list);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
	
}
