package com.hisun.saas.sys.tenant.log.controller;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.saas.sys.tenant.log.vo.TenantLogVo;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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
@RequestMapping("/sys/tenant/log")
public class TenantLogController extends BaseController {

	@Resource
	private TenantLogService tenantLogService;
	
	@Resource
	private UserService userService;

	/**
	 * 查看自己登录日志
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws GenericException
	 */
	@RequiresPermissions("tenant:logown")
	@RequestMapping(value = "/login/own/list")
	public ModelAndView loginown(String start,String end,
								 @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" userId=:userId ", "userId", currentUser.getUserid()));
			if(StringUtils.isNotBlank(start)){
				query.add(CommonRestrictions.and(" createTime >= :start","start", new DateTime(start).toDate()));
			}
			if(StringUtils.isNotBlank(end)){
				query.add(CommonRestrictions.and(" createTime <= :end ", "end", new DateTime(end).plusDays(1).toDate()));
			}
			query.add(CommonRestrictions.and(" type=:type ", "type", Short.valueOf("4")));
			Long total = this.tenantLogService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createTime"));
			List<TenantLog> queryList = this.tenantLogService.list(query, orderBy, pageNum, pageSize);
			PagerVo<TenantLog> pager = new PagerVo<TenantLog>(queryList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);

		}catch(Exception e){
			throw new GenericException(e);
		}
		return  new ModelAndView("saas/sys/tenant/log/loginown",map);
	}


	/**
	 * 查看自己登录日志
	 * @param request
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws GenericException
	 */
	@RequiresPermissions("tenant:logadminlist")
	@RequestMapping(value = "/login/admin/list")
	public ModelAndView loginAdmin(String start,String end,String searchContent,String email,String ip,
								 @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize) throws GenericException,UnsupportedEncodingException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			PagerVo<Map> pager = tenantLogService.adminSearchLoginLog(start, end, searchContent, email,ip, pageNum, pageSize);
			map.put("pager", pager);
			map.put("searchContent", searchContent);
			map.put("ip",ip);
			map.put("start",start);
			map.put("end",end);
		}catch(Exception e){
			throw new GenericException(e);
		}
		return  new ModelAndView("saas/sys/tenant/log/loginAdmin",map);
	}

    /**
     * 操作日志
     * @param start
     * @param end
     * @param searchContent
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     * @throws GenericException
     */
	@RequiresPermissions("tenantLog:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(String start,String end,String searchContent,String type,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="20") int pageSize) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try{
			UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();

            PagerVo<TenantLogVo> pager = tenantLogService.searchOperationLog(pageSize, pageNum, start, end, searchContent, currentUser.getTenantId(), type);
            map.put("pager", pager);
            map.put("start", start);
            map.put("end", end);
            map.put("searchContent", searchContent);
            map.put("type", type);
        }catch(Exception e){
			throw new GenericException(e);
		}
		
		return  new ModelAndView("saas/sys/tenant/log/list",map);
	}
}
