package com.hisun.saas.sys.admin.dictionary.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryItemDao;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DictionaryItemServiceImpl extends BaseServiceImpl<DictionaryItem, String> implements DictionaryItemService {

    private DictionaryItemDao dictionaryItemDao;

    @Override
    @Resource
    public void setBaseDao(BaseDao<DictionaryItem, String> dictionaryItemDao) {
        this.baseDao = dictionaryItemDao;
        this.dictionaryItemDao = (DictionaryItemDao) dictionaryItemDao;
    }

    @Override
    public Integer getMaxSort(String typeId,String pId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = "select max(t.sort)+1 as sort from DictionaryItem t ";
        hql+=" where t.dictionaryType.id =:typeId ";
        map.put("typeId",typeId);
        if (pId != null && !pId.equals("")) {
            hql = hql + " and t.parentItem.id =:pId";
            map.put("pId", pId);
        } else {
            hql = hql + " and t.parentItem.id is null";
        }
        List<Map> maxSorts = this.dictionaryItemDao.list(hql, map);
        if (maxSorts.get(0).get("sort") == null) {
            return 1;
        } else {
            Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
            return maxSort;
        }
    }

    @Override
    public void saveDictionaryItem(DictionaryItem dictionaryItem) {
        String pId = "";
        if (dictionaryItem.getParentItem() != null) {
            pId = dictionaryItem.getParentItem().getId();
        }
        Integer oldSort = this.getMaxSort(dictionaryItem.getDictionaryType().getId(),pId);
        Integer newSort = dictionaryItem.getSort();
        int retval = newSort.compareTo(oldSort);
        if (retval > 0) {
            newSort = oldSort;
        }

        dictionaryItem.setSort(newSort);
        this.updateSort(dictionaryItem, oldSort);
        this.save(dictionaryItem);

    }

    private void updateSort(DictionaryItem dictionaryItem, Integer oldSort) {

        CommonConditionQuery query = new CommonConditionQuery();
        Integer newSort = dictionaryItem.getSort();
        String sql = "update DictionaryItem t set ";
        if (newSort > oldSort) {
            sql += "t.sort=t.sort-1";
        } else {
            sql += "t.sort=t.sort+1";
        }
        sql+=" where t.dictionaryType.id =:typeId ";
        if (newSort > oldSort) {
            sql += " and  t.sort<=" + newSort + " and t.sort >" + oldSort;
        } else {
            if (newSort == oldSort) {
                sql += " and  t.sort = -100";
            } else {
                sql += " and  t.sort<" + oldSort + " and t.sort>=" + newSort;
            }
        }
        query.add(CommonRestrictions.and("","typeId",dictionaryItem.getDictionaryType().getId()));
        this.dictionaryItemDao.executeBulk(sql, query);
        this.dictionaryItemDao.getSession().evict(dictionaryItem);

    }


    @Override
    public void updateDictionaryItem(DictionaryItem dictionaryItem, String oldPid, Integer oldSort) throws Exception {
        int newSort = dictionaryItem.getSort().intValue();
        String pId = "";
        if (dictionaryItem.getParentItem() != null) {
            pId = dictionaryItem.getParentItem().getId();
        }
        int maxSort = this.getMaxSort(dictionaryItem.getDictionaryType().getId(),pId);
        if (newSort > maxSort) {
            newSort = maxSort;
        }
        dictionaryItem.setSort(newSort);
        this.updateSort(dictionaryItem, oldSort);
        this.dictionaryItemDao.update(dictionaryItem);

    }


}
