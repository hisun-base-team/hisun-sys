package com.hisun.saas.sys.admin.dictionary.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: DictionaryTypeController.java </p>
 * <p>Package com.hisun.cloud.sys.dictionary.controller </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月7日 上午10:54:57 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/dictionary/type")
public class DictionaryTypeController extends BaseController {

	@Resource
	private DictionaryTypeService dictionaryTypeService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			Long total = this.dictionaryTypeService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			List<DictionaryType> queryList = this.dictionaryTypeService.list(query, orderBy, pageNum, pageSize);
			//List<TenantVo> resultList = new ArrayList<TenantVo>();
			/*for (Tenant entity : queryList) {
				TenantVo vo = new TenantVo();
				BeanUtils.copyProperties(vo, entity);
				resultList.add(vo);
			}*/
			PagerVo<DictionaryType> pager = new PagerVo<DictionaryType>(queryList, total.intValue(), pageNum, pageSize);
			request.setAttribute("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		
		return new ModelAndView("saas/sys/admin/dictionary/type/listType",map);
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("id") String id) throws GenericException {
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
	
	@RequestMapping(value = "/add")
	public ModelAndView add() {
		return new ModelAndView("saas/sys/admin/dictionary/type/addType");
	}
	
	@RequestMapping(value="/update/{id}",method = RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") String id)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		return new ModelAndView("saas/sys/admin/dictionary/type/updateType",map);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(DictionaryType dictionaryType){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryType.setCreateUser(UserLoginDetailsUtil.getUserLoginDetails().getUserid());
			dictionaryTypeService.save(dictionaryType);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}

		return map;
	}
	
	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.dictionaryTypeService.deleteByPK(id);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(DictionaryType dictionaryType)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.dictionaryTypeService.update(dictionaryType);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}
	
}
