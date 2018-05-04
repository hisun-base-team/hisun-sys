/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.service;

import com.hisun.saas.sys.admin.dictionary.entity.DictionaryItem;
import com.hisun.base.service.BaseService;


public interface DictionaryItemService extends BaseService<DictionaryItem, String> {

	public Integer getMaxSort(String pId);
	
	public void saveDictionaryItem(DictionaryItem dictionaryItem);
	
	public void updateSortAndQueryCode(DictionaryItem dictionaryItem,Integer oldSort);
	
	public void refreshQueryCodeForUp(String startCode, String endCode);
	
	public void refreshQueryCodeForDown(String startCode, String endCode) ;
	
	public void updateDictionaryItem(DictionaryItem dictionaryItem,String oldPid,Integer oldSort)throws Exception;
	
	public void refreshQueryCodeToTmp(String oldQueryCode);
	
	public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode);
}
