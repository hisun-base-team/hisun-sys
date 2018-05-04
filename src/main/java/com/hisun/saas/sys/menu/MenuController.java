/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.menu;

import com.hisun.base.controller.BaseController;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.util.BeanMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys")
public class MenuController extends BaseController {


    @RequestMapping("/menu")
    public @ResponseBody Map<String, Object> menu() {
        Map<String, Object> map = new HashMap<>();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        List<Menu> menus = buildMenu(userLoginDetails.getResources());
        map.put("success", true);
        map.put("data", menus);
        return map;
    }



    public List<Menu> buildMenu(List<AbstractResource> resources) {
        List<Menu> items = new ArrayList<Menu>();
        for (AbstractResource resource : resources) {
            if (resource.getResourceType() == AbstractResource.APP) {//系统 顶层菜单
                Menu item = new Menu();
                BeanMapper.copy(resource, item);
                item.setResId(resource.getId());
                item.setTop(true);
                item.setPermission(resource.getPermission());
                buildChildMenu(item,resources);
                items.add(item);
            }
        }
        return items;
    }

    private void buildChildMenu(Menu menu,List<AbstractResource> resources) {
        List<Menu> children = new ArrayList<Menu>();
        for (AbstractResource resource : resources) {
            if (resource.getpId().equals(menu.getResId()) && resource.getResourceType() != 1) {
                Menu child = new Menu();
                BeanMapper.copy(resource, child);
                child.setResId(resource.getId());
                buildChildMenu(child,resources);
                children.add(child);
            }
        }
        menu.setChildren(children);
    }

}
