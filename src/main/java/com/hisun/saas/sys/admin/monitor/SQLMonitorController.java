package com.hisun.saas.sys.admin.monitor;

import com.hisun.base.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Title: SQLMonitor.java</p>
 * <p>Description: Druid sql监控控制器</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @version v0.1
 * @email jason4j@qq.com
 * @date 2015-12-08 16:14
 */
@Controller
@RequestMapping("/sys/admin/sql")
public class SQLMonitorController extends BaseController{

    @RequiresPermissions("sql:druid")
    @RequestMapping("/monitor")
    public ModelAndView sqlMonitor(){
        return new ModelAndView("saas/sys/admin/sql/monitor");
    }
}
