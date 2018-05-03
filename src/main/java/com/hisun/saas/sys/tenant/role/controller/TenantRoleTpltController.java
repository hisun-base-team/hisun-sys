/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.role.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTplt;
import com.hisun.saas.sys.tenant.role.entity.TenantRoleTpltResource;
import com.hisun.saas.sys.tenant.role.service.TenantRoleTpltService;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleTpltVo;
import com.hisun.saas.sys.util.EntityWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("/sys/tenant/role/tplt")
public class TenantRoleTpltController extends BaseController {

	@Resource
	private TenantRoleTpltService tenantRoleTpltService;

	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();

		try{
			CommonConditionQuery query = new CommonConditionQuery();
			Long total = this.tenantRoleTpltService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("roleName"));
			List<TenantRoleTplt> queryList = this.tenantRoleTpltService.list(query, orderBy, pageNum, pageSize);
			List<TenantRoleTpltVo> vos = Lists.newArrayList();
			TenantRoleTpltVo  vo = null;
			for(TenantRoleTplt entity: queryList){
				vo = new TenantRoleTpltVo();
				BeanUtils.copyProperties(vo,entity);
				vos.add(vo);
			}
			PagerVo<TenantRoleTpltVo> pager = new PagerVo<TenantRoleTpltVo>(vos, total.intValue(), pageNum, pageSize);
			request.setAttribute("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/tenant/role/tplt/listRoleTplt",map);
	}

	@RequiresLog(operateType = LogOperateType.DELETE,description = "删除角色模板${id}")
	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				TenantRoleTplt entity = this.tenantRoleTpltService.getByPK(id);
				map.put("data", entity);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/add")
	public ModelAndView add() {
		return new ModelAndView("saas/sys/tenant/role/tplt/addRoleTplt");
	}

	@RequiresLog(operateType = LogOperateType.SAVE,description = "增加角色模板:${vo.name}")
	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(TenantRoleTpltVo vo)throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(this.tenantRoleTpltService.existRoleCode(vo.getRoleCodePrefix())==false){
				TenantRoleTplt entity = new TenantRoleTplt();
				BeanUtils.copyProperties(entity,vo);
				EntityWrapper.wrapperSaveBaseProperties(entity, UserLoginDetailsUtil.getUserLoginDetails());
				String resourceIds = vo.getResourceIds();
				String resourceNames = vo.getResourceNames();
				if(resourceIds!=null&& resourceIds.length()>0){
					String[] resourceIdArray = resourceIds.split(",");
					String[] resourceNameArray = resourceNames.split(",");
					int i =0;
					for(String id : resourceIdArray){
						if(id.equals("1")) continue;
						TenantRoleTpltResource tenantRoleTpltResource = new TenantRoleTpltResource();
						tenantRoleTpltResource.setResourceName(resourceNameArray[i]);
						tenantRoleTpltResource.setTenantResource(new TenantResource(id));
						entity.addTenantRoleTpltResource(tenantRoleTpltResource);
						i++;
					}
				}
				tenantRoleTpltService.save(entity);
				map.put("success", true);
			}else{
				logger.error("角色模板代码已存在!");
				throw new GenericException("角色模板代码已存在!");
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e.getMessage());
		}

		return map;
	}

	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/code/check")
	public @ResponseBody Map<String, Object> check(@RequestParam("code") String code,@RequestParam(value="id",required=false)String id) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		if(StringUtils.isNotBlank(id)){
			TenantRoleTplt entity = this.tenantRoleTpltService.getByPK(id);
			if(StringUtils.equalsIgnoreCase(entity.getRoleCodePrefix(), code)){
				map.put("success", true);
			}else{
				if(this.tenantRoleTpltService.existRoleCode(code)==true){
					map.put("success", false);
				}else{
					map.put("success", true);
				}
			}
		}else{
			if(this.tenantRoleTpltService.existRoleCode(code)==true){
				map.put("success", false);
			}else{
				map.put("success", true);
			}
		}
		return map;
	}

	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		TenantRoleTpltVo vo = new TenantRoleTpltVo();
		try {
			TenantRoleTplt entity = this.tenantRoleTpltService.getByPK(id);
			List<TenantRoleTpltResource> tenantRoleTpltResources = entity.getTenantRoleTpltResources();
			if(tenantRoleTpltResources!=null&&tenantRoleTpltResources.size()>0){
				StringBuffer resourceIds =  new StringBuffer();
				StringBuffer resourceNames = new StringBuffer();
				for(TenantRoleTpltResource tenantRoleTpltResource: tenantRoleTpltResources){
					resourceIds.append(",").append(tenantRoleTpltResource.getTenantResource().getId());
					resourceNames.append(",").append(tenantRoleTpltResource.getResourceName());
				}
				vo.setResourceIds(resourceIds.toString().substring(1,resourceIds.length()));
				vo.setResourceNames(resourceNames.toString().substring(1,resourceNames.length()));
			}
			BeanUtils.copyProperties(vo,entity);
		} catch (Exception e) {
			logger.error(e);

		}
		map.put("vo", vo);
		return new ModelAndView("saas/sys/tenant/role/tplt/updateRoleTplt",map);
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改角色模板:${vo.name}")
	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(TenantRoleTpltVo vo)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TenantRoleTplt tenantRoleTplt = this.tenantRoleTpltService.getByPK(vo.getId());
			BeanUtils.copyProperties(tenantRoleTplt,vo);

			List<TenantRoleTpltResource> tenantRoleTpltResources = new ArrayList<>();
			String resourceIds = vo.getResourceIds();
			String resourceNames = vo.getResourceNames();
			if(resourceIds!=null&& resourceIds.length()>0){
				String[] resourceIdArray = resourceIds.split(",");
				String[] resourceNameArray = resourceNames.split(",");
				int i =0;
				for(String id : resourceIdArray){
					if(id.equals("1")) {
						i++;
						continue;
					}
					TenantRoleTpltResource tenantRoleTpltResource = new TenantRoleTpltResource();
					tenantRoleTpltResource.setResourceName(resourceNameArray[i]);
					tenantRoleTpltResource.setTenantResource(new TenantResource(id));
					tenantRoleTpltResource.setTenantRoleTplt(tenantRoleTplt);
					tenantRoleTpltResources.add(tenantRoleTpltResource);
					i++;
				}
			}
			this.tenantRoleTpltService.update(tenantRoleTplt,tenantRoleTpltResources);

			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new GenericException(e.getMessage());
		}
		return map;
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改角色模板:${vo.name}")
	@RequiresPermissions("sys-tenantRoleTplt:*")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.tenantRoleTpltService.deleteByPK(id);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e.getMessage());
		}
		return map;
	}

}
