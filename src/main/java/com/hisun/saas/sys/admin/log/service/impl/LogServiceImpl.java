/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.log.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.admin.log.dao.LogDao;
import com.hisun.saas.sys.admin.log.entity.Log;
import com.hisun.saas.sys.admin.log.service.LogService;
import com.hisun.saas.sys.admin.log.vo.LogVo;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	private LogDao logDao;
	
	@Resource
	public void setBaseDao(BaseDao<Log, String> logDao) {
		this.baseDao = logDao;
		this.logDao = (LogDao) logDao;
	}

	@Override
	public void log(Log log) {
		this.logDao.save(log);
	}

	@Override
	public PagerVo<LogVo> selectLog(int pageSize, int pageNum, String property) {
		Session session = logDao.getSession();
		PagerVo<LogVo> pager = null;
		Map<String,Object> map = Maps.newHashMap();
		String str = new String(" FROM SYS_USER u,SYS_LOG l WHERE l.user_id=u.id " +
				" AND l.type <> '4' ORDER BY l.create_time DESC");
		String str1 = new String(" SELECT u.real_name,l.id,l.content,l.create_time,l.ip,l.type,l.user_id ");
		String str2 = new String(" SELECT COUNT(1) ");
		//map.put("property", property);
		pager = extracted(pageSize, pageNum, session, map, str, str1, str2);
		return pager;
	}

	@Override
	public PagerVo<LogVo> searchAllSecurityLogList(int pageSize, int pageNum, String start, String end, String searchContent) {
		Map<String,Object> paramMap = Maps.newHashMap();
		StringBuilder sql = new StringBuilder(" from sys_log log , sys_user user where 1=1 ");
		if(StringUtils.isNotBlank(start)){
			sql.append(" and create_time >= :start");
			paramMap.put("start", new DateTime(start).toDate());
		}
		if(StringUtils.isNotBlank(end)){
			sql.append(" and create_time <= :end");
			paramMap.put("end", new DateTime(end).toDate());
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (real_name like :searchContent or user_name like :searchContent or email like :searchContent)");
			paramMap.put("searchContent", "%"+ searchContent + "%");
		}
		sql.append(" and type = 4 and log.user_id = user.id order by create_time desc");

		List<Map> list = logDao.countReturnMapBySql("select log.ip ip,log.create_time createTime, user.real_name realname " + sql.toString(),paramMap);
		int count = logDao.countBySql("select count(1) " + sql.toString(),paramMap);

		List<LogVo> logVoList = Lists.newArrayList();
		LogVo logVo;
		for (Map map : list) {
			logVo = new LogVo();
			logVo.setIp(map.get("ip").toString());
			//logVo.setContent(map.get("content").toString());
			logVo.setCreateTime((Date)map.get("createTime"));
			logVo.setUserName(map.get("realname").toString());
			//logVo.setType(Short.valueOf(map.get("type").toString()));
			logVoList.add(logVo);
		}
		return new PagerVo<>(logVoList, count, pageNum, pageSize);
	}

	@Override
	public PagerVo<LogVo> searchOwnSecurityLogList(int pageSize, int pageNum, String start, String end, String searchContent) {
		Map<String,Object> paramMap = Maps.newHashMap();
		StringBuilder sql = new StringBuilder(" from sys_log log , sys_user user where user.id = :userId ");
		paramMap.put("userId", UserLoginDetailsUtil.getUserLoginDetails().getUserid());
		if(StringUtils.isNotBlank(start)){
			sql.append(" and create_time >= :start");
			paramMap.put("start", new DateTime(start).toDate());
		}
		if(StringUtils.isNotBlank(end)){
			sql.append(" and create_time <= :end");
			paramMap.put("end", new DateTime(end).toDate());
		}
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (real_name like :searchContent or user_name like :searchContent or email like :searchContent)");
			paramMap.put("searchContent", "%"+ searchContent + "%");
		}
		sql.append(" and type = 4 and log.user_id = user.id order by create_time desc");

		List<Map> list = logDao.countReturnMapBySql("select log.ip ip,log.create_time createTime, user.real_name realname " + sql.toString(),paramMap);
		int count = logDao.countBySql("select count(1) " + sql.toString(),paramMap);

		List<LogVo> logVoList = Lists.newArrayList();
		LogVo logVo;
		for (Map map : list) {
			logVo = new LogVo();
			logVo.setIp(map.get("ip").toString());
			//logVo.setContent(map.get("content").toString());
			logVo.setCreateTime((Date)map.get("createTime"));
			logVo.setUserName(map.get("realname").toString());
			//logVo.setType(Short.valueOf(map.get("type").toString()));
			logVoList.add(logVo);
		}
		return new PagerVo<>(logVoList, count, pageNum, pageSize);
	}

	@Override
	public PagerVo<LogVo> searchLogList(int pageSize, int pageNum, String property, String start, String end, String userName, String type) {
		Map<String,Object> paramMap = Maps.newHashMap();
		StringBuilder sql = new StringBuilder(" from sys_log log , sys_user user where 1=1 ");
		if(StringUtils.isNotBlank(start)){
			sql.append(" and log.create_time >= :start");
			paramMap.put("start", new DateTime(start).toDate());
		}
		if(StringUtils.isNotBlank(end)){
			sql.append(" and log.create_time <= :end");
			paramMap.put("end", new DateTime(end).plusDays(1).toDate());
		}
		if(StringUtils.isNotBlank(userName)){
			sql.append(" and (real_name like :userName or user_name like :userName )");
			paramMap.put("userName", "%"+ userName + "%");
		}
		if(StringUtils.isBlank(type)){
			sql.append(" and type != 4 and log.user_id = user.id ");
		}else{
			sql.append(" and type =:type and log.user_id = user.id ");
			paramMap.put("type",type);
		}

		List<Map> list = logDao.countReturnMapBySql("select log.ip ip,log.create_time createTime, log.content content, log.type type, user.real_name realname, user.user_name userName  " + sql.toString(),paramMap);
		int count = logDao.countBySql("select count(1) " + sql.toString(),paramMap);

		List<LogVo> logVoList = Lists.newArrayList();
		LogVo logVo;
		for (Map map : list) {
			logVo = new LogVo();
			logVo.setIp(map.get("ip").toString());
			logVo.setContent(map.get("content").toString());
			logVo.setCreateTime((Date)map.get("createTime"));
			logVo.setUserName(map.get("realname") == null ? map.get("userName").toString():map.get("realname").toString());
			logVo.setType(Short.valueOf(map.get("type").toString()));
			logVoList.add(logVo);
		}
		return new PagerVo<>(logVoList, count, pageNum, pageSize);
	}

	private PagerVo<LogVo> extracted(int pageSize, int pageNum,
			Session session, Map<String, Object> map, String str, String str1,
			String str2) {
		PagerVo<LogVo> pager;
		int count;
		Query query = session.createSQLQuery(str2+str);
		query.setProperties(map);
		count = Integer.parseInt(query.uniqueResult().toString());
		
		query = session.createSQLQuery(str1+str);
		query.setProperties(map);
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNum-1)*pageSize);
		List<Object[]> objects=query.list();
		
		List<LogVo> list = new ArrayList<LogVo>();
		LogVo logVo = null;
		if(objects!=null&&objects.size()>0){
			for(Object[] obs:objects){
				int index=0;
				logVo = new LogVo();
				logVo.setUserName((String)obs[index++]);
				logVo.setId((String)obs[index++]);
				logVo.setContent((String)obs[index++]);
				logVo.setCreateTime((Date)obs[index++]);
				logVo.setIp((String)obs[index++]);
				logVo.setType((short)obs[index++]);
				logVo.setUserId((String)obs[index++]);
				list.add(logVo);
			}
		}
		pager = new PagerVo<LogVo>(list,count,pageNum,pageSize);
		return pager;
	}

}
