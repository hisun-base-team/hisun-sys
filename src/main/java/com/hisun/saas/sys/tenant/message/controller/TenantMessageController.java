package com.hisun.saas.sys.tenant.message.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.message.service.TenantMessageService;
import com.hisun.saas.sys.tenant.message.service.TenantOnlineMessageService;
import com.hisun.saas.sys.tenant.message.vo.TenantOnlineMessageVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.tenant.message.entity.TenantMessage;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.message.entity.TenantOnlineMessage;
import com.hisun.saas.sys.tenant.message.vo.TenantMessageVo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * <p>类名称：TenantMessageController</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:39
 * @创建人联系方式：lihm_gz@30wish.net
 */
@Controller
@RequestMapping("/sys/tenant/message")
public class TenantMessageController extends BaseController {

    @Resource
    private TenantMessageService tenantMessageService;

    @Resource
    private TenantOnlineMessageService tenantOnlineMessageService;

    /**
     * 暂时屏蔽此功能，个人中心是否显示通知
     * @param request
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @RequestMapping("/user/conf")
    public @ResponseBody
    Map<String, Object> getConf(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> map = Maps.newHashMap();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        TenantMessage message = tenantMessageService.save(userLoginDetails.getUserid(), userLoginDetails.getTenantId());
        TenantMessageVo messageVo = new TenantMessageVo();
        BeanUtils.copyProperties(messageVo, message);
        map.put("message", messageVo);
        return map;
    }

    @RequestMapping("/user/conf/update")
    public @ResponseBody
    Map<String, Object> update(@RequestParam("messageId") String messageId, @RequestParam("name") String name, @RequestParam("status") boolean status, HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        TenantMessage message = this.tenantMessageService.getByPK(messageId);
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
        this.tenantMessageService.update(message);
        map.put("success", true);
        return map;
    }

    @RequestMapping("/messages")
    public ModelAndView getMessage(
            @RequestParam(value = "messageType", required = false) String messageType, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> map = Maps.newHashMap();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
        query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("0")));
        CommonOrderBy orderBy = new CommonOrderBy();
        orderBy.add(CommonOrder.desc("createDate"));
        List<TenantOnlineMessage> onlineMessages = this.tenantOnlineMessageService.list(query, orderBy);
        List<TenantOnlineMessageVo> onlineMessageVos = Lists.newArrayList();
        TenantOnlineMessageVo onlineMessageVo;
        for (TenantOnlineMessage onlineMessage : onlineMessages) {
            onlineMessageVo = new TenantOnlineMessageVo();
            BeanUtils.copyProperties(onlineMessage,onlineMessageVo);
            onlineMessageVos.add(onlineMessageVo);
        }
        map.put("unread", onlineMessageVos);

        return new ModelAndView("saas/sys/tenant/message/messages", map);
    }

    @RequestMapping("/pending")
    public @ResponseBody
    Map<String, Object> pending(HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> map = Maps.newHashMap();
        if (!StringUtils.equals("admin", UserLoginDetailsUtil.getUserLoginDetails().getUsername())) {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("0")));
            query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            Long count = tenantOnlineMessageService.count(query);
            map.put("count", count);

            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("0")));
            query.add(CommonRestrictions.and(" createUserId = :id GROUP BY type", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.desc("createDate"));
            List<TenantOnlineMessage> onlineMessages = this.tenantOnlineMessageService.list(query, orderBy);
            List<Map<String, Object>> onlineMessageVos = Lists.newArrayList();
            Map<String, Object> data = Maps.newHashMap();
            for (TenantOnlineMessage onlineMessage : onlineMessages) {
                //BeanUtils.copyProperties(onlineMessage,onlineMessageVo);
                data.put("type", onlineMessage.getType());
                data.put("title", onlineMessage.getTitle());
                onlineMessageVos.add(data);
            }
            map.put("message", onlineMessageVos);
        }
        return map;
    }

    @RequestMapping("/ajax/pending")
    public @ResponseBody Map<String, Object> ajaxPending(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        if (!StringUtils.equals("admin", UserLoginDetailsUtil.getUserLoginDetails().getUsername())) {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("1")));
            query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            Long count = tenantOnlineMessageService.count(query);
            map.put("count", count);
            query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("2")));
            query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            Long read = tenantOnlineMessageService.count(query);
            map.put("read", read);
            map.put("success", true);
        }
        return map;
    }

    @RequestMapping(value = "/all/read", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> allRead() {
        Map<String, Object> map = Maps.newHashMap();
        try {
            this.tenantOnlineMessageService.updateAllRead(UserLoginDetailsUtil.getUserLoginDetails().getUserid());
            map.put("success", true);
        } catch (NumberFormatException e) {
            map.put("success", false);
            logger.error(e, e);
        }
        return map;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> update(@RequestParam(value = "type") String type, @RequestParam(value = "id") String id, @RequestParam(value = "status") Short status, HttpServletRequest request) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            TenantOnlineMessage onlineMessage = this.tenantOnlineMessageService.getByPK(id);
            onlineMessage.setStatus(status);
            this.tenantOnlineMessageService.update(onlineMessage);
            CommonConditionQuery query;
            switch (StringUtils.trim(type)) {
                case "unread":
                    query = new CommonConditionQuery();
                    query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
                    query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("0")));
                    break;
                case "pending":
                    query = new CommonConditionQuery();
                    query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
                    query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("1")));
                    break;
                default:
                    query = new CommonConditionQuery();
                    query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
                    query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("2")));
                    break;
            }
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.desc("createDate"));
            List<TenantOnlineMessage> onlineMessages = this.tenantOnlineMessageService.list(query, orderBy);
            List<TenantOnlineMessageVo> onlineMessageVos = Lists.newArrayList();
            TenantOnlineMessageVo onlineMessageVo;
            for (TenantOnlineMessage om : onlineMessages) {
                onlineMessageVo = new TenantOnlineMessageVo();
                BeanUtils.copyProperties(om,onlineMessageVo);
                onlineMessageVo.setCreateDateFormat(new DateTime(om.getCreateDate()).toString("yyyy-MM-dd HH:mm:dd"));
                onlineMessageVos.add(onlineMessageVo);
            }
            map.put("data", onlineMessageVos);
            map.put("success", true);
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> list(@RequestParam(value = "status") short status, HttpServletRequest request) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            CommonConditionQuery query;
            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            query.add(CommonRestrictions.and(" status = :status", "status", status));
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.desc("createDate"));
            List<TenantOnlineMessage> onlineMessages = this.tenantOnlineMessageService.list(query, orderBy);
            List<TenantOnlineMessageVo> onlineMessageVos = Lists.newArrayList();
            TenantOnlineMessageVo onlineMessageVo;
            for (TenantOnlineMessage om : onlineMessages) {
                onlineMessageVo = new TenantOnlineMessageVo();
                BeanUtils.copyProperties(om,onlineMessageVo);
                onlineMessageVo.setCreateDateFormat(new DateTime(om.getCreateDate()).toString("yyyy-MM-dd HH:mm:dd"));
                onlineMessageVos.add(onlineMessageVo);
            }
            map.put("data", onlineMessageVos);
            map.put("success", true);
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
        }
        return map;
    }


    @RequestMapping("/list/sitemesh/read")
    public ModelAndView read(HttpServletRequest req, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "100") int pageSize) throws GenericException{
        Map<String, Object> map = Maps.newHashMap();

        try {
            CommonConditionQuery query;
            query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" createUserId = :id", "id", UserLoginDetailsUtil.getUserLoginDetails().getUserid()));
            query.add(CommonRestrictions.and(" status = :status", "status", Short.valueOf("2")));
            Long total = this.tenantOnlineMessageService.count(query);
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.desc("createDate"));
            List<TenantOnlineMessage> resultList = this.tenantOnlineMessageService.list(query, orderBy, pageNum, pageSize);
            PagerVo<TenantOnlineMessage> pager = new PagerVo<TenantOnlineMessage>(resultList, total.intValue(), pageNum, pageSize);
            map.put("pager", pager);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("saas/sys/tenant/message/readMessage", map);
    }
}