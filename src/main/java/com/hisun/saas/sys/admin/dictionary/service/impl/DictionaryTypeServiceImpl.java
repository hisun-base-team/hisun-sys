/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.dictionary.service.impl;

import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryItemDao;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryTypeDao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryType, String> implements DictionaryTypeService {

	private DictionaryTypeDao dictionaryTypeDao;
	

	@Override
	@Resource
	public void setBaseDao(BaseDao<DictionaryType, String> dictionaryTypeDao) {
		this.baseDao = dictionaryTypeDao;
		this.dictionaryTypeDao = (DictionaryTypeDao) dictionaryTypeDao;
	}


	public boolean existCode(String code){
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" code = :code", "code",code));
		Long total = this.dictionaryTypeDao.count(query);
		if(total>0){
			return true;
		}else{
			return false;
		}
	}
}
