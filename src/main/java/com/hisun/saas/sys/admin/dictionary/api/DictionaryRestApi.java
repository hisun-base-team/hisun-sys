/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.api;

import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.taglib.selectTag.SelectNode;
import com.hisun.saas.sys.taglib.treeTag.TreeNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@RestController
@RequestMapping(value="/api/dictionary")
public class DictionaryRestApi {

    @Resource
    private DictionaryTypeService dictionaryTypeService;
    @Resource
    private DictionaryItemService dictionaryItemService;


    @RequestMapping(value = "/tree",method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getTreeNodes(String typeCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DictionaryItem> dictionaryItems;
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" dictionaryType.code=:typeCode ", "typeCode", typeCode));
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("code"));
            dictionaryItems = dictionaryItemService.list(query, orderBy);
            List<TreeNode> nodes = new ArrayList<>();
            TreeNode node=null;
            for (DictionaryItem dictionaryItem : dictionaryItems) {
                node = new TreeNode();
                node.setId(dictionaryItem.getId());
                node.setName(dictionaryItem.getName());
                if(dictionaryItem.getParentItem()==null){
                    node.setpId("");
                }else{
                    node.setpId(dictionaryItem.getParentItem().getId());
                }
                nodes.add(node);
            }
            map.put("success", true);
            map.put("data", nodes);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }


    @RequestMapping(value = "/select",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getSelectNodes(String typeCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DictionaryItem> dictionaryItems;
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" dictionaryType.code=:typeCode ", "typeCode", typeCode));
            CommonOrderBy orderBy = new CommonOrderBy();
            orderBy.add(CommonOrder.asc("code"));
            dictionaryItems = dictionaryItemService.list(query, orderBy);
            List<SelectNode> nodes = new ArrayList<>();
            SelectNode node=null;
            for (DictionaryItem dictionaryItem : dictionaryItems) {
                node = new SelectNode();
                node.setOptionKey(dictionaryItem.getCode());
                node.setOptionValue(dictionaryItem.getName());
                nodes.add(node);
            }
            map.put("success", true);
            map.put("data", nodes);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }
}
