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
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.admin.dictionary.vo.DictionaryItemVo;
import com.hisun.saas.sys.admin.dictionary.vo.DictionaryTypeVo;
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import com.hisun.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
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
@RequestMapping("/sys/admin/dictionary/item")
public class DictionaryItemController extends BaseController {

	@Resource
	private DictionaryItemService dictionaryItemService;
	@Resource
	private DictionaryTypeService dictionaryTypeService;


	@RequestMapping("/index/{typeId}")
	public ModelAndView index(@PathVariable("typeId") String typeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId",typeId);
		return new ModelAndView("saas/sys/admin/dictionary/item/index");
	}

	@RequestMapping("/tree/{typeId}")
	public @ResponseBody Map<String, Object> tree(@PathVariable("typeId") String typeId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictionaryItem> dictionaryItems;
		try {
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" dictionaryType.id=:typeId ", "typeId", typeId));
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("queryCode"));
			dictionaryItems = dictionaryItemService.list(query, orderBy);
			DictionaryType dictionaryType = dictionaryTypeService.getByPK(typeId);
			List<TreeNode> treeNodes = new ArrayList<TreeNode>();
			//--------字典类型节点
			TreeNode treeNode = new TreeNode();
			treeNode.setId(dictionaryType.getId());
			treeNode.setName(dictionaryType.getName());
			treeNode.setOpen(true);
			treeNodes.add(treeNode);
			//-------字典项节点
			TreeNode childTreeNode=null;
			for (DictionaryItem dictionaryItem : dictionaryItems) {
				childTreeNode = new TreeNode();
				childTreeNode.setId(dictionaryItem.getId());
				childTreeNode.setName(dictionaryItem.getName());
				if(dictionaryItem.getParentItem()==null){
					childTreeNode.setpId(dictionaryType.getId());
				}else{
					childTreeNode.setpId(dictionaryItem.getParentItem().getId());
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

	@RequestMapping("/ajax/list")
	public ModelAndView listItem(HttpServletRequest request,
								  @RequestParam(value="pageNum",defaultValue="1")int pageNum,
								  @RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		String currentNodeId = StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName = StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId =StringUtils.trimNull2Empty( request.getParameter("currentNodeParentId"));//取得当前树节点的父ID属性
		String typeId = StringUtils.trimNull2Empty(request.getParameter("typeId"));
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			if(StringUtils.equals(typeId,currentNodeId)){//如果为最高层节点,则为字典
				query.add(CommonRestrictions.and(" dictionaryType.id=:typeId ", "typeId", typeId));
			}else{//其他为字典项
				query.add(CommonRestrictions.and(" parentItem.id = :id ", "id", currentNodeId));
			}

			Long total = dictionaryItemService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("queryCode"));
			List<DictionaryItem> entities = this.dictionaryItemService.list(query, orderBy, pageNum, pageSize);
			List<DictionaryItemVo> vos = new ArrayList<>();
			DictionaryItemVo vo = null;
			if(entities!=null){
				for(DictionaryItem entity : entities){
					vo = new DictionaryItemVo();
					BeanUtils.copyProperties(vo,entity);
					vos.add(vo);
				}
			}
			PagerVo<DictionaryItemVo> pager = new PagerVo<DictionaryItemVo>(vos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
			map.put("currentNodeId",currentNodeId);
			map.put("currentNodeName",currentNodeName);
			map.put("currentNodeParentId",currentNodeParentId);
			map.put("typeId",typeId);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("/saas/sys/admin/dictionary/item/listItem", map);
	}
	



	@RequestMapping("/ajax/add")
	public ModelAndView add(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
		String typeId= StringUtils.trimNull2Empty(request.getParameter("typeId"));
		String pId = currentNodeId;
		if(StringUtils.equals(pId,typeId)){
			pId ="";
		}
		int sort = this.dictionaryItemService.getMaxSort(pId);
		DictionaryItemVo vo = new DictionaryItemVo();
		map.put("currentNodeId",currentNodeId);
		map.put("currentNodeName",currentNodeName);
		map.put("currentNodeParentId",currentNodeParentId);
		map.put("typeId",typeId);
		map.put("sort",sort);
		return new ModelAndView("saas/sys/admin/dictionary/item/addItem",map);
	}

	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(DictionaryItemVo vo,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String pId = vo.getpId();
		String typeId = vo.getTypeId();
		try {
			DictionaryItem dictionaryItem = new DictionaryItem();
			BeanUtils.copyProperties(dictionaryItem, vo);
			if(StringUtils.isNotBlank(pId) && !StringUtils.equals(pId,typeId)){
				dictionaryItem.setParentItem(this.dictionaryItemService.getByPK(pId));
			}
			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(typeId);
			dictionaryItem.setDictionaryType(dictionaryType);
			dictionaryItemService.saveDictionaryItem(dictionaryItem);
			map.put("success", true);
//			map.put("data", dictionaryItem);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			map.put("success", false);
		}

		return map;
	}
	
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				CommonConditionQuery query = new CommonConditionQuery();
				query.add(CommonRestrictions.and(" parentItem.id = :pId ", "pId", id));
				Long total = dictionaryItemService.count(query);
				if(total>0){
					map.put("success", false);
					map.put("msg", "该字典项下还有子项，不能删除!！");
				}else{
					this.dictionaryItemService.deleteByPK(id);
					map.put("success", true);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);

		}
		return map;
	}

	@RequestMapping("/ajax/edit/{id}")
	public ModelAndView edit(@PathVariable("id") String id,HttpServletRequest request){
		Map<String,Object> model = new HashMap<String,Object>();
		DictionaryItem dictionaryItem = dictionaryItemService.getByPK(id);
		DictionaryItemVo vo = new DictionaryItemVo();
		String currentNodeId =StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
		String currentNodeName =StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
		String currentNodeParentId = StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
		String typeId= StringUtils.trimNull2Empty(request.getParameter("typeId"));
		org.springframework.beans.BeanUtils.copyProperties(dictionaryItem,vo);
		if(dictionaryItem.getParentItem()==null){
			vo.setpId(dictionaryItem.getDictionaryType().getId());
			vo.setpId_value(dictionaryItem.getDictionaryType().getName());
		}else {
			vo.setpId(dictionaryItem.getParentItem().getId());
			vo.setpId_value(dictionaryItem.getParentItem().getName());
		}
		model.put("currentNodeId",currentNodeId);
		model.put("currentNodeName",currentNodeName);
		model.put("currentNodeParentId",currentNodeParentId);
		model.put("typeId",typeId);
		model.put("vo", vo);
		return new ModelAndView("saas/sys/admin/dictionary/item/editItem", model);
	}
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(DictionaryItemVo vo,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String pId = vo.getpId();
		String typeId = vo.getTypeId();
		try {
			DictionaryItem dictionaryItem = this.dictionaryItemService.getByPK(vo.getId());
			String oldPid = "";
			if(dictionaryItem.getParentItem()!=null){
				oldPid = dictionaryItem.getParentItem().getId();
			}
			BeanUtils.copyProperties(dictionaryItem, vo);
			if(StringUtils.isNotBlank(pId) && !StringUtils.equals(pId,typeId)){
				dictionaryItem.setParentItem(this.dictionaryItemService.getByPK(pId));
			}
			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(typeId);
			dictionaryItem.setDictionaryType(dictionaryType);
			this.dictionaryItemService.updateDictionaryItem(dictionaryItem, oldPid, Integer.valueOf(0));
//		String newpId = request.getParameter("newpId");
//		String type = request.getParameter("type");
//		try {
//			String oldPid = dictionaryItem.getParentItem().getId();
//			dictionaryItem.setParentItem(this.dictionaryItemService.getByPK(newpId));
//			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(type);
//			dictionaryItem.setDictionaryType(dictionaryType);
//			this.dictionaryItemService.updateDictionaryItem(dictionaryItem, oldPid, Integer.valueOf(0));
			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
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
				DictionaryItem entity = this.dictionaryItemService.getByPK(id);
				DictionaryItemVo vo = new DictionaryItemVo();
				BeanUtils.copyProperties(vo, entity);
				vo.setTypeName(entity.getDictionaryType().getName());
				vo.setTypeId(entity.getDictionaryType().getId());
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
	

	

	
	@RequestMapping("/max/sort")
	public @ResponseBody Map<String, Object> getMaxSort(@RequestParam(value="pId")String pId) {
		Map<String, Object> map = Maps.newHashMap();
		Integer maxSort = this.dictionaryItemService.getMaxSort(pId);
		map.put("maxSort", maxSort);
		return map;
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getRoleSelection(
			@RequestParam(value = "id",required=false) String id){
		Map<String, Object> map = Maps.newConcurrentMap();
		//CommonConditionQuery query = new CommonConditionQuery();
		try {
			List<DictionaryType> list = this.dictionaryTypeService.list();
			List<DictionaryTypeVo> dictionaryTypeVos = Lists.newArrayList();
			if(StringUtils.isNotBlank(id)){
				DictionaryItem dictionaryItem = this.dictionaryItemService.getByPK(id);
				DictionaryTypeVo dictionaryTypeVo;
				for(DictionaryType dictionaryType : list){
					dictionaryTypeVo = new DictionaryTypeVo();
					BeanUtils.copyProperties(dictionaryTypeVo, dictionaryType);
					if(StringUtils.equals(dictionaryItem.getDictionaryType().getId(), dictionaryType.getId())){
						dictionaryTypeVo.setSelected("selected");
					}
					dictionaryTypeVos.add(dictionaryTypeVo);
				}
			}else{
				DictionaryTypeVo dictionaryTypeVo;
				for(DictionaryType dictionaryType : list){
					dictionaryTypeVo = new DictionaryTypeVo();
					BeanUtils.copyProperties(dictionaryTypeVo, dictionaryType);
					dictionaryTypeVos.add(dictionaryTypeVo);
				}
			}
			map.put("success", true);
			map.put("data", dictionaryTypeVos);
		} catch (Exception e) {
			logger.error(e,e);
			map.put("success", false);
		}
		return map;
	}
	@RequestMapping(value = "/code/check")
	public @ResponseBody Map<String, Object> check(
			@RequestParam("typeId") String typeId,@RequestParam("code") String code,@RequestParam(value="id",required=false)String id) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" dictionaryType.id=:typeId ", "typeId", typeId));
		query.add(CommonRestrictions.and(" code = :code ", "code", code));
		if(org.apache.commons.lang3.StringUtils.isNotBlank(id)){
			DictionaryItem entity = this.dictionaryItemService.getByPK(id);
			if(org.apache.commons.lang3.StringUtils.equalsIgnoreCase(entity.getCode(), code)){
				map.put("success", true);
			}else{
				Long total = dictionaryItemService.count(query);
				if(total>=1){
					map.put("success", false);
				}else{
					map.put("success", true);
				}
			}
		}else{
			Long total = dictionaryItemService.count(query);
			if(total>=1){
				map.put("success", false);
			}else{
				map.put("success", true);
			}
		}
		return map;
	}
}
