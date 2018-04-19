package com.hisun.saas.sys.tenant.log.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.tenant.log.entity.TenantLog;
import com.hisun.saas.sys.tenant.log.service.TenantLogService;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.log.dao.TenantLogDao;
import com.hisun.saas.sys.tenant.log.vo.TenantLogVo;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>Title: LogServiceImpl.java </p>
 * <p>Package com.hisun.cloud.sys.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年5月21日 上午11:32:59 
 * @version 
 */
@Service
public class TenantLogServiceImpl extends BaseServiceImpl<TenantLog, String> implements TenantLogService {

	private TenantLogDao tenantLogDao;
	
	@Resource
	public void setBaseDao(BaseDao<TenantLog, String> tenantLogDao) {
		this.baseDao = tenantLogDao;
		this.tenantLogDao = (TenantLogDao) tenantLogDao;
	}

	@Override
	public void log(TenantLog log) {
		this.tenantLogDao.save(log);
	}

	@Override
	public PagerVo<TenantLogVo> selectLog(int pageSize, int pageNum, String property) {
		Session session = tenantLogDao.getSession();
		PagerVo<TenantLogVo> pager = null;
		Map<String,Object> map = Maps.newHashMap();
		String str = new String(" FROM SYS_USER u,SYS_LOG l WHERE l.user_id=u.id " +
				" AND l.type !='4' ORDER BY l.create_time DESC");
		String str1 = new String(" SELECT u.real_name,l.id,l.content,l.create_time,l.ip,l.type,l.user_id ");
		String str2 = new String(" SELECT COUNT(1) ");
		//map.put("property", property);
		pager = extracted(pageSize, pageNum, session, map, str, str1, str2);
		return pager;
	}

	@Override
	public PagerVo<TenantLogVo> selectSecurityLog(int pageSize, int pageNum,
			String property) {
		Session session = tenantLogDao.getSession();
		PagerVo<TenantLogVo> pager = null;
		Map<String,Object> map = Maps.newHashMap();

		String str = new String(" FROM SYS_USER u,SYS_LOG l WHERE l.user_id=u.id " +
				"  AND l.type ='4' ORDER BY l.create_time DESC");
		String str1 = new String(" SELECT u.real_name,l.id,l.content,l.create_time,l.ip,l.type,l.user_id ");
		String str2 = new String(" SELECT COUNT(1) ");
		//map.put("property", property);
		pager = extracted(pageSize, pageNum, session, map, str, str1, str2);
		return pager;
	}

	@Override
	public PagerVo<TenantLogVo> searchLogList(int pageSize, int pageNum,String property,
			String starttime, String endtime, String userName, short type) {
		Session session = tenantLogDao.getSession();
		PagerVo<TenantLogVo> pager = null;
		Map<String,Object> map = Maps.newHashMap();
		String str = new String(" FROM SYS_USER u,SYS_LOG l WHERE l.user_id=u.id " +
				" AND l.type =:type AND l.create_time BETWEEN :starttime AND :endtime AND u.real_name LIKE :userName ORDER BY l.create_time DESC");
		String str1 = new String(" SELECT u.real_name,l.id,l.content,l.create_time,l.ip,l.type,l.user_id ");
		String str2 = new String(" SELECT COUNT(1) ");
		map.put("property", property);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("type", type);
		map.put("userName", "%"+userName+"%");
		pager = extracted(pageSize, pageNum, session, map, str, str1, str2);
		return pager;
	}

	private PagerVo<TenantLogVo> extracted(int pageSize, int pageNum,
			Session session, Map<String, Object> map, String str, String str1,
			String str2) {
		PagerVo<TenantLogVo> pager;
		int count;
		Query query = session.createSQLQuery(str2+str);
		query.setProperties(map);
		count = Integer.parseInt(query.uniqueResult().toString());
		
		query = session.createSQLQuery(str1+str);
		query.setProperties(map);
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNum-1)*pageSize);
		List<Object[]> objects=query.list();
		
		List<TenantLogVo> list = new ArrayList<TenantLogVo>();
		TenantLogVo logVo = null;
		if(objects!=null&&objects.size()>0){
			for(Object[] obs:objects){
				int index=0;
				logVo = new TenantLogVo();
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
		pager = new PagerVo<TenantLogVo>(list,count,pageNum,pageSize);
		return pager;
	}

	@Override
	public PagerVo<Map> adminSearchLoginLog(String start, String end, String searchContent, String email, String ip, int pageNum, int pageSize) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		StringBuilder sql = new StringBuilder(" from sys_tenant_log log , sys_tenant_user user where 1=1 ");
		if(StringUtils.isNotBlank(start)){
			sql.append(" and create_time >= :start");
			paramMap.put("start", new DateTime(start).toDate());
		}
		if(StringUtils.isNotBlank(end)){
			sql.append(" and create_time <= :end");
			paramMap.put("end", new DateTime(end).plusDays(1).toDate());
		}
		sql.append(" and log.tenant_id = :tenantId  and type = 4 and log.user_id = user.id ");
		paramMap.put("tenantId", UserLoginDetailsUtil.getUserLoginDetails().getTenantId());
		if(StringUtils.isNotBlank(searchContent)){
			sql.append(" and (real_name like :searchContent or user_name like :searchContent or email like :searchContent)");
			paramMap.put("searchContent", "%"+ searchContent + "%");
		}
		if(StringUtils.isNotBlank(email)){
			sql.append(" and email like :email");
			paramMap.put("email", "%"+ email + "%");
		}

		if(StringUtils.isNotBlank(ip)){
			sql.append(" and ip like :ip");
			paramMap.put("ip", "%"+ ip + "%");
		}

		int count = tenantLogDao.countBySql("select count(1) " + sql.toString(),paramMap);
		paramMap.put("startNum", (pageNum-1)*pageSize);
		paramMap.put("pageSize",pageSize);
		sql.append(" order by create_time desc limit :startNum,:pageSize");
		List<Map> list = tenantLogDao.countReturnMapBySql("select log.ip ip,log.create_time createTime, user.real_name realname " + sql.toString(),paramMap);

		return new PagerVo<Map>(list, count , pageNum, pageSize);
	}

    @Override
    public PagerVo<TenantLogVo> searchOperationLog(int pageSize, int pageNum, String start, String end, String searchContent, String tenantId, String type) {

        Map<String,Object> paramMap = Maps.newHashMap();
        StringBuilder sql = new StringBuilder(" from sys_tenant_log log , sys_tenant_user user where 1=1 ");
        if(StringUtils.isNotBlank(start)){
            sql.append(" and log.create_time >= :start");
            paramMap.put("start", new DateTime(start).toDate());
        }
        if(StringUtils.isNotBlank(end)){
            sql.append(" and log.create_time <= :end");
            paramMap.put("end", new DateTime(end).plusDays(1).toDate());
        }
        sql.append(" and log.tenant_id = :tenantId ");
        paramMap.put("tenantId", tenantId);
        if(StringUtils.isNotBlank(searchContent)){
            sql.append(" and (user.user_name like :searchContent");
            sql.append(" or user.real_name like :searchContent");
            sql.append(" or user.email like :searchContent )");
            paramMap.put("searchContent", "%"+ searchContent + "%");
        }
        if (StringUtils.isNotBlank(type)) {
            sql.append(" and log.type = :type");
            paramMap.put("type", type);
        } else {
            sql.append(" and log.type in (1,2,3)");
        }

        sql.append(" and log.user_id = user.id ");

		int count = tenantLogDao.countBySql("select count(1) " + sql.toString(),paramMap);
		sql.append(" order by create_time desc limit :startNum,:pageSize");
		paramMap.put("startNum", (pageNum-1)*pageSize);
		paramMap.put("pageSize",pageSize);
		List<Map> list = tenantLogDao.countReturnMapBySql("select log.ip ip,log.create_time createTime, log.content content, log.type type, user.real_name realname, user.user_name userName " + sql.toString(),paramMap);
        List<TenantLogVo> logVoList = Lists.newArrayList();
        TenantLogVo logVo;
        for (Map map : list) {
            logVo = new TenantLogVo();
            logVo.setIp(map.get("ip").toString());
            logVo.setContent(map.get("content").toString());
            logVo.setCreateTime((Date)map.get("createTime"));
            logVo.setUserName(map.get("realname") == null ? map.get("userName").toString():map.get("realname").toString());
            logVo.setType(Short.valueOf(map.get("type").toString()));
            logVoList.add(logVo);
        }

        return new PagerVo<>(logVoList, count, pageNum, pageSize);
    }
}
