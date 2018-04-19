package com.hisun.saas.sys.admin.dictionary.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryItemDao;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.saas.sys.admin.dictionary.service.DictionaryItemService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: DictionaryItemServiceImpl.java </p>
 * <p>Package com.hisun.cloud.sys.dictionary.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月7日 上午9:59:37 
 * @version 
 */
@Service
public class DictionaryItemServiceImpl extends BaseServiceImpl<DictionaryItem, String> implements DictionaryItemService {

	private DictionaryItemDao dictionaryItemDao;
	
	private Map<String, Object> map = Collections.emptyMap();
	
	@Override
	@Resource
	public void setBaseDao(BaseDao<DictionaryItem, String> dictionaryItemDao) {
		this.baseDao = dictionaryItemDao;
		this.dictionaryItemDao = (DictionaryItemDao) dictionaryItemDao;
	}

	@Override
	public Integer getMaxSort(String pId) {
		Map<String, Object> map=new HashMap<String, Object>();
		String hql = "select max(t.sort)+1 as sort from SYS_DICT_ITEM t where t.p_id=:pId";
		map.put("pId", pId);
		List<Map> maxSorts = this.dictionaryItemDao.countReturnMapBySql(hql, map);
		if(maxSorts.get(0).get("sort")==null){
			return 1;
		}else{
			Integer maxSort = ((Number) maxSorts.get(0).get("sort")).intValue();
			return maxSort;
		}
	}

	@Override
	public void saveDictionaryItem(DictionaryItem dictionaryItem)
			throws Exception {
		Integer oldSort = this.getMaxSort(dictionaryItem.getpId());
		Integer newSort = dictionaryItem.getSort();
		
		int retval = newSort.compareTo(oldSort);
		
		if(retval>0){
			newSort = oldSort;
		}
		
		String queryCode = "";
		DecimalFormat decimalFormat = new DecimalFormat("000"); 
		if(!StringUtils.equals("1", dictionaryItem.getpId())){
			DictionaryItem parentdictionaryItem = this.dictionaryItemDao.getByPK(dictionaryItem.getpId());
			queryCode = parentdictionaryItem.getQueryCode()+decimalFormat.format(newSort);
		}else{
			queryCode = decimalFormat.format(newSort);
		}
		dictionaryItem.setQueryCode(queryCode);
		dictionaryItem.setSort(newSort);
		
		this.updateSortAndQueryCode(dictionaryItem, oldSort);
		this.save(dictionaryItem);
		
	}

	@Override
	public void updateSortAndQueryCode(DictionaryItem dictionaryItem,
			Integer oldSort) throws Exception {
		Integer newSort = dictionaryItem.getSort();
		String newQueryCode = dictionaryItem.getQueryCode();
		
		DecimalFormat decimalFormat = new DecimalFormat("000"); 
		DictionaryItem parentDictionaryItem = this.dictionaryItemDao.getByPK(dictionaryItem.getpId());
		String oldQueryCode;
		if(parentDictionaryItem==null){
			oldQueryCode = decimalFormat.format(oldSort);
		}else{
			oldQueryCode = parentDictionaryItem.getQueryCode()+decimalFormat.format(oldSort);
		}
		
		//String oldQueryCode = this.generateQueryCode(currentOrg.getParent().getQueryCode(), oldOrder, "0000");
		
		String sql="UPDATE SYS_DICT_ITEM t SET ";
		if(newSort>oldSort){
			sql+="t.SORT=t.SORT-1";
		}else{
			sql+="t.SORT=t.SORT+1";
		}
		sql+=" where t.p_id='"+dictionaryItem.getpId()+"'";
		if(newSort>oldSort){
			sql+=" and t.SORT<="+newSort+" and t.SORT >"+oldSort;
		}else{
			if(newSort==oldSort){
				sql+=" and t.SORT = -100";
			}else{
				sql+=" and t.SORT<"+oldSort+" and t.SORT>="+newSort;
			}
		}
		
		this.dictionaryItemDao.update(sql,map);
		
		if(newSort>oldSort){
			this.refreshQueryCodeForUp(oldQueryCode, newQueryCode);
		}else{
			if(newSort==oldSort){
			}else{
				this.refreshQueryCodeForDown(newQueryCode, oldQueryCode);
			}
		}
	}

	@Override
	public void refreshQueryCodeForUp(String startCode, String endCode)
			throws Exception {
		int i = startCode.length();
		String updateSql = "UPDATE SYS_DICT_ITEM r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)-1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
		"WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"'";
		this.dictionaryItemDao.update(updateSql,map);
	}

	@Override
	public void refreshQueryCodeForDown(String startCode, String endCode)
			throws Exception {
		int i = startCode.length();
		String updateSql = "UPDATE SYS_DICT_ITEM r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)+1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
		"WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"'";
		this.dictionaryItemDao.update(updateSql,map);
		
	}

	@Override
	public void updateDictionaryItem(DictionaryItem dictionaryItem,
			String oldPid, Integer oldSort) throws Exception {
		DictionaryItem dictionaryItem2 = this.dictionaryItemDao.getByPK(dictionaryItem.getId());
			
		int newSort = dictionaryItem.getSort().intValue();
		int maxSort = this.getMaxSort(dictionaryItem.getpId());
		if(newSort>maxSort){
			newSort = maxSort;
		}
		
		String queryCode = "";
		DecimalFormat decimalFormat = new DecimalFormat("000"); 
		if(!StringUtils.equals("1", dictionaryItem.getpId())){
			DictionaryItem parentDictionaryItem = this.dictionaryItemDao.getByPK(dictionaryItem.getpId());
			queryCode = parentDictionaryItem.getQueryCode()+decimalFormat.format(newSort);
		}else{
			queryCode = decimalFormat.format(newSort);
		}
		dictionaryItem.setQueryCode(queryCode);
		dictionaryItem.setSort(newSort);
		
		this.refreshQueryCodeToTmp(dictionaryItem2.getQueryCode());
		if(StringUtils.equals(dictionaryItem.getpId(), oldPid)){
			this.updateSortAndQueryCode(dictionaryItem, dictionaryItem2.getSort());
		}else{
			this.updateSortAndQueryCode(dictionaryItem, maxSort);
		}
		
		this.refreshQueryCodeToFormal(dictionaryItem2.getQueryCode(), queryCode);
			
		BeanUtils.copyProperties(dictionaryItem2, dictionaryItem);
		this.dictionaryItemDao.update(dictionaryItem2);
		
	}

	@Override
	public void refreshQueryCodeToTmp(String oldQueryCode) throws Exception {
		int i = oldQueryCode.length();
		String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
		String updateSql = "UPDATE SYS_DICT_ITEM b set b.QUERY_CODE = concat('"+tmpCode+"',substr(b.QUERY_CODE,"+i+"+1,length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like '"+oldQueryCode+"%'";
		this.dictionaryItemDao.update(updateSql, map);
	}

	@Override
	public void refreshQueryCodeToFormal(String oldQueryCode,
			String newQueryCode) throws Exception {
		int i = oldQueryCode.length();
		String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
	    String updateSql="update SYS_DICT_ITEM b set b.QUERY_CODE = concat('"+newQueryCode+"',substr(b.QUERY_CODE,"+(i+1)+",length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like'"+tmpCode+"%'";
	    this.dictionaryItemDao.update(updateSql, map);
	}

}
