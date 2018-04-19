package com.hisun.saas.sys.admin.log.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.AbstractRole;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.log.entity.Log;
import com.hisun.saas.sys.admin.log.service.LogService;
import com.hisun.saas.sys.admin.log.vo.LogVo;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

/**
 * <p>Title: LogController.java </p>
 * <p>Package com.hisun.saas.sys.controller </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月22日 下午2:15:01 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/log")
public class LogController extends BaseController {

	@Resource
	private LogService logService;
	
	@Resource
	private UserService userService;

	@RequiresPermissions("adminlog:security")
	@RequestMapping(value = "/security", method = RequestMethod.GET)
	public ModelAndView security(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
			List<AbstractRole> roles = currentUser.getRoles();
			List<String> roleCodes = Lists.newArrayList();
			for(AbstractRole role : roles){
				roleCodes.add(StringUtils.trim(role.getRoleCode()));
			}
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" userId=:userId ", "userId", currentUser.getUserid()));
			query.add(CommonRestrictions.and(" type=:type ", "type", Short.valueOf("4")));
			Long total = this.logService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createTime"));
			List<Log> queryList = this.logService.list(query, orderBy, pageNum, pageSize);
			List<LogVo> logvos = Lists.newArrayList();
			LogVo logvo;
			for(Log log : queryList){
				logvo = new LogVo();
				BeanUtils.copyProperties(log, logvo);
				User user = this.userService.getByPK(log.getUserId());
				logvo.setUserName(user.getRealname());
				logvos.add(logvo);
			}

			PagerVo<LogVo> pager = new PagerVo<LogVo>(logvos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
			
		}catch(Exception e){
			throw new GenericException(e);
		}
		
		return  new ModelAndView("saas/sys/admin/log/security",map);
	}

	@RequiresPermissions(value = {"adminlog:security","adminLog:securityAll"},logical = Logical.OR)
	@RequestMapping(value = "/security/all", method = RequestMethod.GET)
	public ModelAndView securityAll(HttpServletRequest request,
								 @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
			List<AbstractRole> roles = currentUser.getRoles();
			List<String> roleCodes = Lists.newArrayList();
			for(AbstractRole role : roles){
				roleCodes.add(StringUtils.trim(role.getRoleCode()));
			}
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" type=:type ", "type", Short.valueOf("4")));
			Long total = this.logService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createTime"));
			List<Log> queryList = this.logService.list(query, orderBy, pageNum, pageSize);
			List<LogVo> logvos = Lists.newArrayList();
			LogVo logvo;
			for(Log log : queryList){
				logvo = new LogVo();
				BeanUtils.copyProperties(log, logvo);
				User user = this.userService.getByPK(log.getUserId());
				if(user!=null){
					logvo.setUserName(user.getRealname());
				}
				logvos.add(logvo);
			}

			PagerVo<LogVo> pager = new PagerVo<LogVo>(logvos, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
			//}

		}catch(Exception e){
			throw new GenericException(e);
		}
		map.put("bool",true);
		return  new ModelAndView("saas/sys/admin/log/securityAll",map);
	}

	@RequiresPermissions(value = {"adminLog:*","log:list"},logical = Logical.OR)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			PagerVo<LogVo> pager = this.logService.selectLog(pageSize, pageNum, null);
			map.put("pager", pager);
		}catch(Exception e){
			throw new GenericException(e);
		}
		
		return  new ModelAndView("saas/sys/admin/log/list",map);
	}

	@RequiresPermissions("adminlog:security")
	@RequestMapping(value = "/security/search")
	public String searchLogList(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize,String start,String end) throws GenericException {
		try{
			PagerVo<LogVo> pager = this.logService.searchOwnSecurityLogList(pageSize, pageNum, start,end,null);
			request.setAttribute("pager", pager);
		}catch(Exception e){
			throw new GenericException(e);
		}
		return "saas/sys/admin/log/security";
	}

	@RequiresPermissions("adminlog:security")
	@RequestMapping(value = "/security/search/all")
	public String searchAllLogList(HttpServletRequest request,
								@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize,String start,String end,@RequestParam(value = "searchContent",required = false) String searchContent) throws GenericException {
		try{
			PagerVo<LogVo> pager = this.logService.searchAllSecurityLogList(pageSize, pageNum, start,end,searchContent);
			request.setAttribute("pager", pager);
			request.setAttribute("searchContent", searchContent);
		}catch(Exception e){
			throw new GenericException(e);
		}
		return "saas/sys/admin/log/securityAll";
	}


	@RequestMapping(value = "/search")
	public String searchLogList(HttpServletRequest request,
								@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize,String starttime,String endtime,String userName,String type) throws GenericException {
		try{
			PagerVo<LogVo> pager = this.logService.searchLogList(pageSize, pageNum, null, starttime,endtime,userName,type);
			request.setAttribute("pager", pager);
			request.setAttribute("userName", userName);
		}catch(Exception e){
			throw new GenericException(e);
		}
		return "saas/sys/admin/log/list";
	}
}
