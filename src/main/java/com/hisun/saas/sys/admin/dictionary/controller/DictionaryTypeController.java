/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.admin.dictionary.vo.DictionaryTypeVo;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.util.EntityWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/sys/admin/dictionary")
public class DictionaryTypeController extends BaseController {

	@Resource
	private DictionaryTypeService dictionaryTypeService;

	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			Long total = this.dictionaryTypeService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("code"));
			List<DictionaryType> queryList = this.dictionaryTypeService.list(query, orderBy, pageNum, pageSize);
			List<DictionaryTypeVo> vos = Lists.newArrayList();
			DictionaryTypeVo  vo = null;
			for(DictionaryType entity: queryList){
				vo = new DictionaryTypeVo();
				BeanUtils.copyProperties(vo,entity);
				vos.add(vo);
			}
			PagerVo<DictionaryTypeVo> pager = new PagerVo<DictionaryTypeVo>(vos, total.intValue(), pageNum, pageSize);
			request.setAttribute("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/dictionary/type/listType",map);
	}

	@RequiresLog(operateType = LogOperateType.DELETE,description = "删除字典${id}")
	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				DictionaryType entity = this.dictionaryTypeService.getByPK(id);
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

	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/add")
	public ModelAndView add() {
		return new ModelAndView("saas/sys/admin/dictionary/type/addType");
	}

	@RequiresLog(operateType = LogOperateType.SAVE,description = "增加字典:${vo.name}")
	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(DictionaryTypeVo vo)throws GenericException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(this.dictionaryTypeService.existCode(vo.getCode())==false){
				DictionaryType entity = new DictionaryType();
				BeanUtils.copyProperties(entity,vo);
				EntityWrapper.wrapperSaveBaseProperties(entity, UserLoginDetailsUtil.getUserLoginDetails());
				dictionaryTypeService.save(entity);
				map.put("success", true);
			}else{
				logger.error("字典代码已存在!");
				throw new GenericException("字典代码已存在!");
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e.getMessage());
		}

		return map;
	}

	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/code/check")
	public @ResponseBody Map<String, Object> check(@RequestParam("code") String code,@RequestParam(value="id",required=false)String id) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" code = :code ", "code", code));
		if(org.apache.commons.lang3.StringUtils.isNotBlank(id)){
			DictionaryType entity = this.dictionaryTypeService.getByPK(id);
			if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(entity.getCode(), code)){
				map.put("success", true);
			}else{
				if(this.dictionaryTypeService.existCode(code)==true){
					map.put("success", false);
				}else{
					map.put("success", true);
				}
			}
		}else{
			if(this.dictionaryTypeService.existCode(code)==true){
				map.put("success", false);
			}else{
				map.put("success", true);
			}
		}
		return map;
	}

	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		DictionaryTypeVo vo = new DictionaryTypeVo();
		try {
			DictionaryType entity = this.dictionaryTypeService.getByPK(id);
			BeanUtils.copyProperties(vo,entity);
		} catch (Exception e) {
			logger.error(e);

		}
		map.put("vo", vo);
		return new ModelAndView("saas/sys/admin/dictionary/type/updateType",map);
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改字典:${vo.name}")
	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(DictionaryTypeVo vo)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(vo.getId());
			BeanUtils.copyProperties(dictionaryType,vo);
			this.dictionaryTypeService.update(dictionaryType);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e.getMessage());
		}
		return map;
	}

	@RequiresLog(operateType = LogOperateType.UPDATE,description = "修改字典:${vo.name}")
	@RequiresPermissions("adminDictionary:*")
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.dictionaryTypeService.deleteByPK(id);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e.getMessage());
		}
		return map;
	}

}
