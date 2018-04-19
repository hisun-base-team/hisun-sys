package com.hisun.saas.sys.admin.dictionary.service;

import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.base.service.BaseService;

/**
 * <p>Title: DictionaryItemService.java </p>
 * <p>Package com.hisun.cloud.sys.dictionary.service </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月7日 上午9:58:20 
 * @version 
 */
public interface DictionaryItemService extends BaseService<DictionaryItem, String> {

	public Integer getMaxSort(String pId);
	
	public void saveDictionaryItem(DictionaryItem dictionaryItem)throws Exception;
	
	public void updateSortAndQueryCode(DictionaryItem dictionaryItem,Integer oldSort)throws Exception;
	
	public void refreshQueryCodeForUp(String startCode, String endCode)throws Exception;
	
	public void refreshQueryCodeForDown(String startCode, String endCode) throws Exception;
	
	public void updateDictionaryItem(DictionaryItem dictionaryItem,String oldPid,Integer oldSort)throws Exception;
	
	public void refreshQueryCodeToTmp(String oldQueryCode)throws Exception;
	
	public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode)throws Exception;
}
