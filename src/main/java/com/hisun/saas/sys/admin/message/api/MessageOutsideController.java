package com.hisun.saas.sys.admin.message.api;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.saas.sys.admin.message.service.OnlineMessageService;
import com.hisun.saas.sys.admin.message.vo.OnlineMessageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>Title: MessageOutsideController.java </p>
 * <p>Package com.hisun.cloud.sys.message.api </p>
 * <p>Description: 在线消息外部接口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年7月6日 下午2:32:33 
 * @version 
 */
@Controller
@RequestMapping("/api/message")
public class MessageOutsideController extends BaseController {

	@Resource
	private OnlineMessageService onlineMessageService;
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(OnlineMessageData onlineMessageData){
		Map<String, Object> map = Maps.newHashMap();
		if(onlineMessageData!=null){
			this.onlineMessageService.saveOnlineMessage(onlineMessageData);
			map.put("success", true);
		}else{
			map.put("success", false);
			map.put("message", "参数为空!");
		}
		return map;
	}
}
