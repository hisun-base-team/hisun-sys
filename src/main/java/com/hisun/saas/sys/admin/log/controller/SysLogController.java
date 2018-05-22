/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.log.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.log.entity.SysLog;
import com.hisun.saas.sys.admin.log.service.SysLogService;
import com.hisun.saas.sys.admin.log.vo.SysLogVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.util.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/sys/admin/log")
public class SysLogController extends BaseController {

	@Resource
	private SysLogService logService;
	
	@RequiresPermissions(value = {"adminlog-security:view"},logical = Logical.OR)
	@RequestMapping(value = "/security/list", method = RequestMethod.GET)
	public ModelAndView securityAll(HttpServletRequest request,
								 @RequestParam(value="pageNum",defaultValue="1")int pageNum,
								 @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			String searchContent = StringUtils.trimNull2Empty(request.getParameter("searchContent"));
			String start = StringUtils.trimNull2Empty(request.getParameter("start"));
			String end = StringUtils.trimNull2Empty(request.getParameter("end"));
			CommonConditionQuery query = new CommonConditionQuery();
			List types = Lists.newArrayList();
			types.add(LogOperateType.LOGIN.getType());
			types.add(LogOperateType.LOGOUT.getType());
			query.add(CommonRestrictions.and(" type in (:type)", "type",types));
			if(start.length()>0){
				query.add(CommonRestrictions.and(" operateTime >= :start", "start",new DateTime(start).toDate()));
			}
			if(end.length()>0){
				query.add(CommonRestrictions.and(" operateTime <= :end", "end",new DateTime(end).toDate()));
			}
			if(searchContent.length()>0){
				query.add(CommonRestrictions.and(" content like :searchContent ", "searchContent","%"+searchContent+"%"));
			}
			Long total = this.logService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("operateTime"));
			List<SysLog> queryList = this.logService.list(query, orderBy, pageNum, pageSize);
			List<SysLogVo> logvos = Lists.newArrayList();
			SysLogVo logvo;
			for(SysLog log : queryList){
				logvo = new SysLogVo();
				BeanUtils.copyProperties(log, logvo);
				logvos.add(logvo);
			}
			PagerVo<SysLogVo> pager = new PagerVo<SysLogVo>(logvos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			throw new GenericException(e);
		}
		map.put("bool",true);
		return  new ModelAndView("saas/sys/admin/log/securityList",map);
	}

	@RequiresPermissions(value = {"adminlog-operate:view"},logical = Logical.OR)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			String searchContent = StringUtils.trimNull2Empty(request.getParameter("searchContent"));
			String start = StringUtils.trimNull2Empty(request.getParameter("starttime"));
			String end = StringUtils.trimNull2Empty(request.getParameter("endtime"));
			String type = StringUtils.trimNull2Empty(request.getParameter("type"));
			String userName = StringUtils.trimNull2Empty(request.getParameter("userName"));
			CommonConditionQuery query = new CommonConditionQuery();
			List types = Lists.newArrayList();
			types.add(LogOperateType.LOGIN.getType());
			types.add(LogOperateType.LOGOUT.getType());
			query.add(CommonRestrictions.and(" type not in (:type)", "type",types));
			if(start.length()>0){
				query.add(CommonRestrictions.and(" operateTime >= :start", "start",new DateTime(start).toDate()));
			}
			if(end.length()>0){
				query.add(CommonRestrictions.and(" operateTime <= :end", "end",new DateTime(end).toDate()));
			}
			if(searchContent.length()>0){
				query.add(CommonRestrictions.and(" content like :searchContent ", "searchContent","%"+searchContent+"%"));
			}
			if(type.length()>0){
				query.add(CommonRestrictions.and(" type = :type ", "type",Integer.parseInt(type)));
			}
			if(userName.length()>0){
				query.add(CommonRestrictions.and(" userName like :userName ", "userName","%"+userName+"%"));
			}
			Long total = this.logService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("operateTime"));
			List<SysLog> queryList = this.logService.list(query, orderBy, pageNum, pageSize);
			List<SysLogVo> logvos = Lists.newArrayList();
			SysLogVo logvo;
			for(SysLog log : queryList){
				logvo = new SysLogVo();
				BeanUtils.copyProperties(log, logvo);
				logvos.add(logvo);
			}
			PagerVo<SysLogVo> pager = new PagerVo<SysLogVo>(logvos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			throw new GenericException(e);
		}
		
		return  new ModelAndView("saas/sys/admin/log/list",map);
	}




	@RequiresPermissions(value = {"adminLog-security-me:view"},logical = Logical.OR)
	@RequestMapping(value = "/me/security", method = RequestMethod.GET)
	public ModelAndView meSecurity(HttpServletRequest request,
									@RequestParam(value="pageNum",defaultValue="1")int pageNum,
									@RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
			String searchContent = StringUtils.trimNull2Empty(request.getParameter("searchContent"));
			String start = StringUtils.trimNull2Empty(request.getParameter("start"));
			String end = StringUtils.trimNull2Empty(request.getParameter("end"));
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" userId=:userId","userId",userLoginDetails.getUserid()));
			List types = Lists.newArrayList();
			types.add(LogOperateType.LOGIN.getType());
			types.add(LogOperateType.LOGOUT.getType());
			query.add(CommonRestrictions.and(" type in (:type)", "type",types));
			if(start.length()>0){
				query.add(CommonRestrictions.and(" operateTime >= :start", "start",new DateTime(start).toDate()));
			}
			if(end.length()>0){
				query.add(CommonRestrictions.and(" operateTime <= :end", "end",new DateTime(end).toDate()));
			}
			if(searchContent.length()>0){
				query.add(CommonRestrictions.and(" content like :searchContent ", "searchContent","%"+searchContent+"%"));
			}
			Long total = this.logService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("operateTime"));
			List<SysLog> queryList = this.logService.list(query, orderBy, pageNum, pageSize);
			List<SysLogVo> logvos = Lists.newArrayList();
			SysLogVo logvo;
			for(SysLog log : queryList){
				logvo = new SysLogVo();
				BeanUtils.copyProperties(log, logvo);
				logvos.add(logvo);
			}
			PagerVo<SysLogVo> pager = new PagerVo<SysLogVo>(logvos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			throw new GenericException(e);
		}
		map.put("bool",true);
		return  new ModelAndView("saas/sys/admin/log/meSecurityList",map);
	}



}
