package com.hisun.saas.sys.admin.dictionary.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.admin.dictionary.vo.DictionaryItemVo;
import com.hisun.saas.sys.admin.dictionary.vo.DictionaryTypeVo;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.util.BeanMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
 * <p>Title: DictionaryTypeController.java </p>
 * <p>Package com.hisun.saas.sys.dictionary.controller </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月7日 上午10:54:57 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/dictionary/item")
public class DictionaryItemController extends BaseController {

	@Resource
	private DictionaryItemService dictionaryItemService;
	
	@Resource
	private DictionaryTypeService dictionaryTypeService;
	
	@RequestMapping(value = "/list/{typeId}")
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize,@PathVariable("typeId") String typeId) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" dictionaryType.id=:id ", "id", typeId));
			Long total = this.dictionaryItemService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			List<DictionaryItem> queryList = this.dictionaryItemService.list(query, orderBy, pageNum, pageSize);
			//List<TenantVo> resultList = new ArrayList<TenantVo>();
			/*for (Tenant entity : queryList) {
				TenantVo vo = new TenantVo();
				BeanUtils.copyProperties(vo, entity);
				resultList.add(vo);
			}*/
			PagerVo<DictionaryItem> pager = new PagerVo<DictionaryItem>(queryList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
			map.put("typeId", typeId);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		
		return new ModelAndView("saas/sys/admin/dictionary/item/listItem",map);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(DictionaryItem dictionaryItem,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String newpId = request.getParameter("newpId");
		String type = request.getParameter("type");
		 
		try {
			if(StringUtils.isNotBlank(newpId)){
				dictionaryItem.setpId(newpId);
			}
			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(type);
			dictionaryItem.setDictionaryType(dictionaryType);
			dictionaryItem.setCreateUser(UserLoginDetailsUtil.getUserLoginDetails().getUserid());
			dictionaryItemService.saveDictionaryItem(dictionaryItem);
			map.put("success", true);
			map.put("data", dictionaryItem);
		} catch (Exception e) {
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
				query.add(CommonRestrictions.and(" pId = :pId ", "pId", id));
				Long total = dictionaryItemService.count(query);
				if(total>0){
					map.put("success", false);
					map.put("msg", "该树节点还有子节点，不能删除!");
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
	
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(DictionaryItem dictionaryItem,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		String newpId = request.getParameter("newpId");
		String type = request.getParameter("type");
		try {
			String oldPid = dictionaryItem.getpId();
			dictionaryItem.setpId(newpId);
			DictionaryType dictionaryType = this.dictionaryTypeService.getByPK(type);
			dictionaryItem.setDictionaryType(dictionaryType);
			this.dictionaryItemService.updateDictionaryItem(dictionaryItem, oldPid, Integer.valueOf(0));
			map.put("success", true);
		} catch (Exception e) {
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
				vo.setName(entity.getItem());
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
	
	@RequestMapping("/tree/{typeId}")
	public @ResponseBody Map<String, Object> tree(@PathVariable("typeId") String typeId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DictionaryItem> dictionaryItems;
		try {
			CommonOrderBy orderBy = new CommonOrderBy();
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" dictionaryType.id=:id ", "id", typeId));
			orderBy.add(CommonOrder.asc("sort"));
			dictionaryItems = dictionaryItemService.list(null, orderBy);
			List<DictionaryItemVo> dictionaryItemVos = new ArrayList<DictionaryItemVo>();
			DictionaryType dictionaryType = dictionaryTypeService.getByPK(typeId);
			
			DictionaryItemVo dictionaryItemVo;
			dictionaryItemVo = new DictionaryItemVo();
			dictionaryItemVo.setId("1");
			dictionaryItemVo.setName(dictionaryType.getName());
			dictionaryItemVo.setOpen(true);
			dictionaryItemVo.setpId(null);
			dictionaryItemVos.add(dictionaryItemVo);
			for (DictionaryItem dictionaryItem : dictionaryItems) {
				dictionaryItemVo = new DictionaryItemVo();
				BeanMapper.copy(dictionaryItem, dictionaryItemVo);
				dictionaryItemVo.setName(dictionaryItem.getItem());
				//dictionaryItemVo.setHref(dictionaryItem.getUrl());
				dictionaryItemVos.add(dictionaryItemVo);
			}
			map.put("success", true);
			map.put("data", dictionaryItemVos);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping("/ajax/list")
	public ModelAndView resources(HttpServletRequest req,String pId,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = (User) req.getAttribute(Constants.CURRENT_USER);
		try{
			//String userId = userLoginDetails.getUserid();
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			Long total = dictionaryItemService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.asc("sort"));
			List<DictionaryItem> resultList = dictionaryItemService.list(query, orderBy, pageNum, pageSize);
			PagerVo<DictionaryItem> pager = new PagerVo<DictionaryItem>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("/saas/sys/admin/dictionary/item/listDetails", map);
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
}
