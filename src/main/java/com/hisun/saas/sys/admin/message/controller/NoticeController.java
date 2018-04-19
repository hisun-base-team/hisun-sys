package com.hisun.saas.sys.admin.message.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.message.entity.Notice;
import com.hisun.saas.sys.admin.message.service.NoticeService;
import com.hisun.saas.sys.admin.message.vo.NoticeVo;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: NoticeController.java </p>
 * <p>Package com.hisun.saas.sys.controller </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年6月18日 上午11:25:35 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/notice")
public class NoticeController extends BaseController{

	@Resource
	private NoticeService noticeService;

	@RequiresPermissions("adminNotice:list")
	@RequestMapping(value="/list")
	public ModelAndView listNotice(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			Long total = noticeService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<Notice> resultList = noticeService.list(query, orderBy, pageNum, pageSize);
			PagerVo<Notice> pager = new PagerVo<Notice>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/notice/listNotice",map);
	}
	
	@RequestMapping(value="/all/list")
	public ModelAndView allListNotice(HttpServletRequest req,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException{
		
		Map<String, Object> map = Maps.newHashMap();
		
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" pId = :pId ", "pId", pId));
			query.add(CommonRestrictions.and(" pushWay = :pushWay ", "pushWay", Short.valueOf("2")));
			query.add(CommonRestrictions.and(" status = :status ", "status", Boolean.TRUE));
			Long total = noticeService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<Notice> resultList = noticeService.list(query, orderBy, pageNum, pageSize);
			PagerVo<Notice> pager = new PagerVo<Notice>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("saas/sys/admin/notice/allListNotice",map);
	}
	
	@RequestMapping(value = "get/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("id") String id) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				Notice entity = this.noticeService.getByPK(id);
				NoticeVo vo = new NoticeVo();
				BeanUtils.copyProperties(entity, vo);
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
	@RequestMapping(value = "/add")
	public ModelAndView add() {
		return new ModelAndView("saas/sys/admin/notice/addNotice");
	}
	
	@RequestMapping(value="/update/{id}",method = RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") String id)throws GenericException{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		return new ModelAndView("saas/sys/admin/notice/updateNotice",map);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(Notice notice){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			noticeService.save(notice);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}

		return map;
	}

	@RequestMapping(value = "/delete/{id}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("id") String id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.noticeService.deleteByPK(id);
				map.put("success", true);
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);

		}
		return map;
	}

	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> update(Notice notice){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Notice entity = this.noticeService.getByPK(notice.getId());
			entity.setNoticeContent(notice.getNoticeContent());
			entity.setNoticeLevel(notice.getNoticeLevel());
			entity.setNoticeTitle(notice.getNoticeTitle());
			entity.setPushWay(notice.getPushWay());
			
			this.noticeService.update(entity);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping(value = "/get/online")
	public @ResponseBody Map<String, Object> onlineNotice() {
		Map<String, Object> map = Maps.newHashMap();
		try {
			/*this.noticeService.update(notice);*/
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" pushWay = :pushWay ", "pushWay", Short.valueOf("2")));
			query.add(CommonRestrictions.and(" status = :status ", "status", Boolean.TRUE));
			//Long total = noticeService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			//orderBy.add(CommonOrder.asc("sort"));
			List<Notice> resultList = noticeService.list(query, orderBy, 1, 20);
			
			map.put("success", true);
			if(resultList.size()>0){
				map.put("noticeContent", resultList.get(0).getNoticeContent());
			}
		} catch (Exception e) {
			logger.error(e);
			map.put("success", false);
		}
		return map;
	}
}
