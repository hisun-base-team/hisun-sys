package com.hisun.saas.sys.admin.message.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.message.entity.Message;
import com.hisun.saas.sys.admin.message.entity.OnlineMessage;
import com.hisun.saas.sys.admin.message.service.MessageService;
import com.hisun.saas.sys.admin.message.service.OnlineMessageService;
import com.hisun.saas.sys.admin.message.vo.MessageVo;
import com.hisun.saas.sys.admin.message.vo.OnlineMessageVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: MessageController.java </p>
 * <p>Package com.hisun.cloud.sys.controller </p>
 * <p>Description: 在线消息</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月29日 下午7:46:57 
 * @version 
 */
@Controller
@RequestMapping("/sys/admin/message")
public class MessageController extends BaseController {
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private OnlineMessageService onlineMessageService;

	@RequestMapping("/user/conf")
	public @ResponseBody Map<String, Object> getConf(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		 Map<String, Object>  map = Maps.newHashMap();
		 UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
		 Message message = messageService.save(userLoginDetails.getUserid(), userLoginDetails.getTenantId());
		 MessageVo messageVo = new MessageVo();
		 BeanUtils.copyProperties(messageVo, message);
		 map.put("message", messageVo);
		 return map;
	}
	
	@RequestMapping("/user/conf/update")
	public @ResponseBody Map<String, Object> update(@RequestParam("messageId") String messageId,@RequestParam("name") String name,@RequestParam("status") boolean status,HttpServletRequest request){
		 Map<String, Object>  map = Maps.newHashMap();
		 Message message = this.messageService.getByPK(messageId);
		 switch (StringUtils.trim(name)) {
			case "noticeEmail":
				message.setNoticeEmail(status);
				break;
			case "noticeSmart":
				message.setNoticeSmart(status);		
				break;
			case "noticeExpress":
				message.setNoticeExpress(status);
				break;
			/*case "noticeDailyMail":
				message.setNoticeDailyMail(status);
				break;*/
			default:
				message.setNoticeSMS(status);
				break;
		}
		 this.messageService.update(message);
		 map.put("success", true);
		 return map;
	}
	
	@RequestMapping("/messages")
	public ModelAndView getMessage(
			@RequestParam(value="messageType",required=false) String messageType, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = Maps.newHashMap();
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
		query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("0")));
		CommonOrderBy orderBy = new CommonOrderBy();
		orderBy.add(CommonOrder.desc("createDate"));
		List<OnlineMessage>  onlineMessages = this.onlineMessageService.list(query,orderBy);
		List<OnlineMessageVo> onlineMessageVos = Lists.newArrayList();
		OnlineMessageVo onlineMessageVo;
		for(OnlineMessage onlineMessage:onlineMessages){
			onlineMessageVo = new OnlineMessageVo();
			BeanUtils.copyProperties(onlineMessageVo, onlineMessage);
			onlineMessageVos.add(onlineMessageVo);
		}
		map.put("unread", onlineMessageVos);
		
		return new ModelAndView("saas/sys/admin/message/messages",map);
	}

	@RequestMapping("/pending")
	public @ResponseBody Map<String, Object> pending(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = Maps.newHashMap();
		if(!StringUtils.equals("admin", UserLoginDetailsUtil.getUserLoginDetails().getUsername())){
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("0")));
			query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createDate"));
			Long count = onlineMessageService.count(query);
			map.put("count", count);
			List<OnlineMessage> onlineMessages = this.onlineMessageService.list(query,orderBy);
			List<OnlineMessageVo> onlineMessageVos = Lists.newArrayList();
			OnlineMessageVo onlineMessageVo;
			for(OnlineMessage onlineMessage:onlineMessages){
				onlineMessageVo = new OnlineMessageVo();
				BeanUtils.copyProperties(onlineMessageVo, onlineMessage);
				onlineMessageVos.add(onlineMessageVo);
			}
			map.put("message", onlineMessageVos);
		}
		return map;
	}
	
	@RequestMapping("/ajax/pending")
	public @ResponseBody Map<String, Object> ajaxPending(HttpServletRequest request){
		Map<String, Object> map = Maps.newHashMap();
		if(!StringUtils.equals("admin", UserLoginDetailsUtil.getUserLoginDetails().getUsername())){
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("1")));
			query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
			Long count = onlineMessageService.count(query);
			map.put("count", count);
			query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("2")));
			query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
			Long read = onlineMessageService.count(query);
			map.put("read", read);
			map.put("success", true);
		}
		return map;
	}
	
	@RequestMapping(value = "/all/read",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> allRead(){
		Map<String, Object> map = Maps.newHashMap();
		try {
			StringBuilder sb = new StringBuilder("update SYS_ONLINE_MESSAGE set status=:status where createUserId=:id");
			Map<String, Object> columns = Maps.newHashMap();
			columns.put("status", Short.valueOf("2"));
			columns.put("id", UserLoginDetailsUtil.getUserLoginDetails().getUserid());
			this.onlineMessageService.update(sb.toString(), columns);
			map.put("success", true);
		} catch (NumberFormatException e) {
			map.put("success", false);
			logger.error(e,e);
		}
		return map;
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(@RequestParam(value="type") String type,@RequestParam(value="id") String id,@RequestParam(value="status") Short status,HttpServletRequest request) throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try {
			OnlineMessage onlineMessage = this.onlineMessageService.getByPK(id);
			onlineMessage.setStatus(status);
			this.onlineMessageService.update(onlineMessage);
			CommonConditionQuery query ;
			switch (StringUtils.trim(type)) {
				case "unread":
					query = new CommonConditionQuery();
					query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
					query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("0")));
					break;
				case "pending":
					query = new CommonConditionQuery();
					query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
					query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("1")));
					break;
				default:
					query = new CommonConditionQuery();
					query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
					query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("2")));
					break;
			}
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createDate"));
			List<OnlineMessage>  onlineMessages = this.onlineMessageService.list(query,orderBy);
			List<OnlineMessageVo> onlineMessageVos = Lists.newArrayList();
			OnlineMessageVo onlineMessageVo;
			for(OnlineMessage om:onlineMessages){
				onlineMessageVo = new OnlineMessageVo();
				BeanUtils.copyProperties(onlineMessageVo, om);
				onlineMessageVos.add(onlineMessageVo);
			}
			map.put("data", onlineMessageVos);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}
	
	@RequestMapping(value = "/list",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> list(@RequestParam(value="status") short status,HttpServletRequest request)  throws GenericException{
		Map<String, Object> map = Maps.newHashMap();
		try {
			CommonConditionQuery query ;
			query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
			query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", status));
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createDate"));
			List<OnlineMessage>  onlineMessages = this.onlineMessageService.list(query,orderBy);
			List<OnlineMessageVo> onlineMessageVos = Lists.newArrayList();
			OnlineMessageVo onlineMessageVo;
			for(OnlineMessage om:onlineMessages){
				onlineMessageVo = new OnlineMessageVo();
				BeanUtils.copyProperties(onlineMessageVo, om);
				onlineMessageVos.add(onlineMessageVo);
			}
			map.put("data", onlineMessageVos);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}
	
	
	@RequestMapping("/list/sitemesh/read")
	public ModelAndView read(HttpServletRequest req,@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="50") int pageSize)  throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = (User) req.getAttribute(Constants.CURRENT_USER);
		try{
			//String userId = userLoginDetails.getUserid();
			CommonConditionQuery query ;
			query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" OnlineMessage.createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
			query.add(CommonRestrictions.and(" OnlineMessage.status = :status", "status", Short.valueOf("2")));
			Long total = this.onlineMessageService.count(query);
			CommonOrderBy orderBy = new CommonOrderBy();
			orderBy.add(CommonOrder.desc("createDate"));
			List<OnlineMessage> resultList = this.onlineMessageService.list(query, orderBy, pageNum, pageSize);
			PagerVo<OnlineMessage> pager = new PagerVo<OnlineMessage>(resultList, total.intValue(), pageNum, pageSize);
			map.put("pager", pager);
		}catch(Exception e){
			logger.error(e);
			throw new GenericException(e);
		}
		return new ModelAndView("/saas/sys/admin/message/readMessage", map);
	}
}
