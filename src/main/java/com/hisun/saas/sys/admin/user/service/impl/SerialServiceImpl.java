package com.hisun.saas.sys.admin.user.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.saas.sys.admin.communication.vo.MailSendVo;
import com.hisun.saas.sys.admin.user.dao.SerialDao;
import com.hisun.saas.sys.admin.user.entity.Serial;
import com.hisun.saas.sys.admin.user.service.SerialService;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: SerialServiceImpl.java </p>
 * <p>Package com.hisun.saas.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年4月28日 下午3:33:44 
 * @version 
 */
@Service
public class SerialServiceImpl extends BaseServiceImpl<Serial, String>
		implements SerialService {

	protected final Logger logger = Logger.getLogger(getClass());

	private SerialDao serialDao;

	@Resource
	private MailService mailService;

	@Resource
	public void setBaseDao(BaseDao<Serial, String> serialDao) {
		this.baseDao = serialDao;
        this.serialDao = (SerialDao) serialDao;		
	}

	@Override
	public Serial findByKey(String key) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" Serial.key = :key",
				"key", key));
		query.add(CommonRestrictions.and(" Serial.status = :status",
				"status", Boolean.FALSE));
		List<Serial> serialList = serialDao.list(query, null);
		if (serialList.size() == 0) {
			return null;
		}
		return serialList.get(0);
	}

	@Override
	public boolean save(Serial entity, String username,String email,String url) {

		this.save(entity);

		MailSendVo vo = new  MailSendVo();
		List<String> to = Lists.newArrayList();
		to.add(email);
		vo.setTo(to);
		Map<String,List<String>> map = Maps.newHashMap();
		map.put("%username%", ImmutableList.of(username));
		map.put("%url%", ImmutableList.of(url));
		map.put("%date%", ImmutableList.of(new DateTime().toString("yyyy-MM-dd HH:mm:dd")));

		vo.setSub(map);

		try {
			MailResult result = mailService.sendEmail("_new_forgot_pwd_platform","","",vo,false);
			if(StringUtils.equalsIgnoreCase(MailResult.SUCCESS,result.getMessage())) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return false;
	}


}
