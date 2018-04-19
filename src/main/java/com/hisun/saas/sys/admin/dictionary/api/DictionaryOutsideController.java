package com.hisun.saas.sys.admin.dictionary.api;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: DictionaryOutsideController.java </p>
 * <p>Package com.hisun.saas.sys.dictionary.api </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月20日 下午4:46:06 
 * @version 
 */
@Controller
@RequestMapping("/api/dictionary")
public class DictionaryOutsideController extends BaseController {
	
	@Resource
	private DictionaryItemService dictionaryItemService;
	
	@RequestMapping("/{code}")
	public @ResponseBody Map<String, Object> dictionary(@PathVariable("code") String code){
		Map<String, Object> map = Maps.newHashMap();
		try {
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" dictionaryType.code=:code ", "code", code));
			CommonOrderBy orderBy = new CommonOrderBy();
			List<DictionaryItem> dictionaryItems = this.dictionaryItemService.list(query, orderBy);
			//DictionaryItemVo dictionaryItemVo;
			//List<DictionaryItemVo> dictionaryItemVos = Lists.newArrayList();
			
			/*for(DictionaryItem dictionaryItem : dictionaryItems){
				dictionaryItemVo = new DictionaryItemVo();
				BeanUtils.copyProperties(dictionaryItemVo, dictionaryItem);
				dictionaryItemVos.add(dictionaryItemVo);
			}*/
			map.put("success", true);
			//map.put("data", dictionaryItemVos);
			
			map.put("data", dictionaryItems);
		} catch (Exception e) {
			logger.error(e,e);
			map.put("success", false);
			map.put("message", "调用接口出错!");
		}
		return map;
	}
}
