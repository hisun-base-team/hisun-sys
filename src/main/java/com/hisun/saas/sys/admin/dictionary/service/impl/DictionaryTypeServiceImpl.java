package com.hisun.saas.sys.admin.dictionary.service.impl;

import com.hisun.saas.sys.admin.dictionary.service.DictionaryTypeService;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryItemDao;
import com.hisun.saas.sys.admin.dictionary.entity.DictionaryType;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.dictionary.dao.DictionaryTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>Title: DictionaryTypeServiceImpl.java </p>
 * <p>Package com.hisun.saas.sys.dictionary.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月7日 上午10:03:39 
 * @version 
 */
@Service
public class DictionaryTypeServiceImpl extends BaseServiceImpl<DictionaryType, String> implements DictionaryTypeService {

	private DictionaryTypeDao dictionaryTypeDao;
	
	@Resource
	private DictionaryItemDao dictionaryItemDao;
	@Override
	@Resource
	public void setBaseDao(BaseDao<DictionaryType, String> dictionaryTypeDao) {
		this.baseDao = dictionaryTypeDao;
		this.dictionaryTypeDao = (DictionaryTypeDao) dictionaryTypeDao;
	}

	@Override
	public void deleteByPK(String pk) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" dictionaryType.id=:id ", "id", pk));
		dictionaryItemDao.deleteBatch(query);
		super.deleteByPK(pk);
	}

}
