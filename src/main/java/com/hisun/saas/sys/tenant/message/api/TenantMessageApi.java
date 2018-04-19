package com.hisun.saas.sys.tenant.message.api;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.tenant.message.vo.TenantOnlineMessageData;
import com.hisun.saas.sys.tenant.message.service.TenantOnlineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>类名称：TenantMessageApi</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午6:40
 * @创建人联系方式：lihm_gz@30wish.net
 */
@RestController
@RequestMapping("/api/tenant/message")
public class TenantMessageApi {

    @Resource
    private TenantOnlineMessageService tenantOnlineMessageService;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> save(TenantOnlineMessageData tenantOnlineMessageData){
        Map<String, Object> map = Maps.newHashMap();
        if(tenantOnlineMessageData!=null){
            this.tenantOnlineMessageService.saveOnlineMessage(tenantOnlineMessageData);
            map.put("success", true);
        }else{
            map.put("success", false);
            map.put("message", "参数为空!");
        }
        return map;
    }
}
