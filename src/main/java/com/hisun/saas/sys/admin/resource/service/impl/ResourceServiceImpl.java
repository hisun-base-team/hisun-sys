package com.hisun.saas.sys.admin.resource.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.resource.dao.ResourceDao;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service("resourceService")
public class ResourceServiceImpl extends
		BaseServiceImpl<Resource, String> implements ResourceService {

	private Map<String, Object> map = Collections.emptyMap();
	
	@Override
	public void deleteByPK(final String id) {
		final StringBuilder sb = new StringBuilder(" DELETE FROM SYS_ROLE_RESOURCE WHERE resource_id=?");
		final StringBuilder sb1 = new StringBuilder(" DELETE FROM SYS_TENANT_ROLE_RESOURCE WHERE resource_id=?");
		this.resourceDao.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt = null;
				try {
					stmt = connection.prepareStatement(sb.toString());
					stmt.setString(1, id);
					stmt.executeUpdate();
					stmt1 = connection.prepareStatement(sb1.toString());
					stmt1.setString(1, id);
					stmt1.executeUpdate();
					connection.commit();
				}catch (SQLException e){
					throw e;
				}finally {
					if (stmt!=null){
						stmt.close();
					}
					if (stmt1!=null){
						stmt1.close();
					}
				}
			}
		});
		super.deleteByPK(id);
	}

	private ResourceDao resourceDao;
	
	@javax.annotation.Resource
	@Override
	public void setBaseDao(BaseDao<Resource, String> resourceDao) {
		this.baseDao = resourceDao;
        this.resourceDao = (ResourceDao) resourceDao;
	}

	private String extracted(Resource resource, Resource resource2,int type)
			throws IllegalAccessException, InvocationTargetException {
		StringBuilder sb = new StringBuilder();
		String pId = resource2.getpId();
		if((resource.getResourceType()!=1&&resource.getResourceType()!=resource2.getResourceType())||(resource.getResourceType()!=1&&StringUtils.isNotBlank(resource2.getQueryCode()))){//操作无需code
			String queryCode = "";
			DecimalFormat decimalFormat = new DecimalFormat("000"); 
			if(!StringUtils.equals("1", pId)){
				Resource parentResource = this.resourceDao.getByPK(pId);
				queryCode = parentResource.getQueryCode()+decimalFormat.format(resource.getSort());
			}else{
				queryCode = decimalFormat.format(resource.getSort());
			}
			resource.setQueryCode(queryCode);
			sb.append(queryCode);
		}else {
			resource.setQueryCode(resource2.getQueryCode());
			sb.append(resource2.getQueryCode());
		}
		BeanUtils.copyProperties(resource2, resource);
		resource2.setpId(pId);
		this.resourceDao.update(resource2);
		return sb.toString();
	}

	@Override
	public void saveResource(Resource resource,int type) throws Exception {
		
		Integer oldSort = this.resourceDao.getMaxSort(resource.getpId(),type);
		Integer newSort = resource.getSort();
		int retval = 0;
		if(oldSort!=null){
			retval = newSort.compareTo(oldSort);
		}
		
		if(retval>0){
			newSort = oldSort;
		}
		
		String queryCode = "";
		DecimalFormat decimalFormat = new DecimalFormat("000"); 
		if(!StringUtils.equals("1", resource.getpId())){
			Resource parentResource = this.resourceDao.getByPK(resource.getpId());
			if(parentResource!=null){
				queryCode = parentResource.getQueryCode()+decimalFormat.format(newSort);
			}else{
				queryCode = decimalFormat.format(newSort);
			}
		}else{
			queryCode = decimalFormat.format(newSort);
		}
		resource.setQueryCode(queryCode);
		resource.setSort(newSort);
		
		this.updateSortAndQueryCode(resource, oldSort,type);
		this.resourceDao.save(resource);
	}

	@Override
	public void updateSortAndQueryCode(Resource resource, Integer oldSort,int type) throws Exception {
		if(resource!=null){
			Integer newSort = resource.getSort();
			String newQueryCode = resource.getQueryCode();
			
			DecimalFormat decimalFormat = new DecimalFormat("000"); 
			Resource parentResource = this.resourceDao.getByPK(resource.getpId());
			String oldQueryCode;
			if(parentResource==null){
				oldQueryCode = decimalFormat.format(oldSort);
			}else{
				oldQueryCode = parentResource.getQueryCode()+decimalFormat.format(oldSort);
			}
			
			//String oldQueryCode = this.generateQueryCode(currentOrg.getParent().getQueryCode(), oldOrder, "0000");
			
			String sql="UPDATE SYS_RESOURCE t SET ";
			if(newSort>oldSort){
				sql+="t.SORT=t.SORT-1";
			}else{
				sql+="t.SORT=t.SORT+1";
			}
			sql+=" where t.p_id='"+resource.getpId()+"' and t.type= " + type;
			if(newSort>oldSort){
				sql+=" and t.SORT<="+newSort+" and t.SORT >"+oldSort;
			}else{
				if(newSort==oldSort){
					sql+=" and t.SORT = -100";
				}else{
					sql+=" and t.SORT<"+oldSort+" and t.SORT>="+newSort;
				}
			}
			
			this.resourceDao.update(sql,map);
			
			if(newSort>oldSort){
				this.refreshQueryCodeForUp(oldQueryCode, newQueryCode,type);
			}else{
				if(newSort==oldSort){
				}else{
					this.refreshQueryCodeForDown(newQueryCode, oldQueryCode,type);
				}
			}
		}else{
			throw new Exception("参数为空!");
		}
	}

	private void refreshQueryCodeForUp(String startCode, String endCode, int type)
			throws Exception {
		int i = startCode.length();
		String updateSql = "UPDATE SYS_RESOURCE r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)-1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
		"WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"' and r.type=" + type;
		this.resourceDao.update(updateSql,map);
	}

	private void refreshQueryCodeForDown(String startCode, String endCode, int type)
			throws Exception {
		int i = startCode.length();
		String updateSql = "UPDATE SYS_RESOURCE r SET r.QUERY_CODE = concat(substring(r.query_code, 1, "+i+"-3),right(concat('000',substring(r.query_code,"+i+"-3+1,3)+1),3),substring(r.QUERY_CODE,"+i+"+1,length(r.QUERY_CODE)))"+
		"WHERE substr(r.QUERY_CODE,1,"+i+") >= '"+startCode+"' and substr(r.QUERY_CODE,1,"+i+") <='"+endCode+"' and r.type=" + type;
		this.resourceDao.update(updateSql,map);
	}

	@Override
	public void updateResources(Resource resource, String oldPid,
			Integer oldSort,int type) throws Exception {
		Resource resource2 = this.resourceDao.getByPK(resource.getId());
		Integer oldSatus = resource2.getStatus();
		Integer newStatus = resource.getStatus();
		if(resource.getResourceType()!=1){
			
			int newSort = resource.getSort().intValue();
			int maxSort = this.resourceDao.getMaxSort(resource.getpId(),type);
			if(newSort>maxSort){
				newSort = maxSort;
			}
			
			String queryCode = "";
			DecimalFormat decimalFormat = new DecimalFormat("000"); 
			if(!StringUtils.equals("1", resource.getpId())){
				Resource parentResource = this.resourceDao.getByPK(resource.getpId());
				queryCode = parentResource.getQueryCode()+decimalFormat.format(newSort);
			}else{
				queryCode = decimalFormat.format(newSort);
			}
			resource.setQueryCode(queryCode);
			resource.setSort(newSort);
			if(StringUtils.isNotBlank(resource2.getQueryCode())){
				this.refreshQueryCodeToTmp(resource2.getQueryCode(),type);
			}
			if(StringUtils.equals(resource.getpId(), oldPid)){
				this.updateSortAndQueryCode(resource, resource2.getSort(),type);
			}else{
				this.updateSortAndQueryCode(resource, maxSort,type);
			}
			if(StringUtils.isNotBlank(resource2.getQueryCode())){
				this.refreshQueryCodeToFormal(resource2.getQueryCode(), queryCode,type);
			}
			
		}
		BeanUtils.copyProperties(resource2, resource);
		this.resourceDao.update(resource2);
		if(oldSatus!=newStatus){
			extracted(resource, newStatus,type);
		}
	}


	private void extracted(Resource resource, Integer newStatus,int type) {
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" pId = :pId ", "pId", resource.getId()));
		query.add(CommonRestrictions.and(" type = :type ", "type", type));
		CommonOrderBy orderBy = new CommonOrderBy();
		orderBy.add(CommonOrder.asc("sort"));
		List<Resource> resources = resourceDao.list(query, orderBy);
		for(Resource resource3 : resources){
			resource3.setStatus(newStatus);
			this.resourceDao.update(resource3);
			extracted(resource3, newStatus,type);
		}
	}

	@Override
	public void refreshQueryCodeToTmp(String oldQueryCode,int type) throws Exception {
		int i = oldQueryCode.length();
		String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
		String updateSql = "UPDATE SYS_RESOURCE b set b.QUERY_CODE = concat('"+tmpCode+"',substr(b.QUERY_CODE,"+i+"+1,length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like '"+oldQueryCode+"%' and b.type=" + type;
		this.resourceDao.update(updateSql, map);
	}
	
	public void refreshQueryCodeToFormal(String oldQueryCode,String newQueryCode ,int type)  {
		int i = oldQueryCode.length();
		String tmpCode = oldQueryCode.substring(0,oldQueryCode.length()-3)+"tmp";
	    String updateSql="update SYS_RESOURCE b set b.QUERY_CODE = concat('"+newQueryCode+"',substr(b.QUERY_CODE,"+(i+1)+",length(b.QUERY_CODE))) where substr(b.QUERY_CODE,1,"+i+") like'"+tmpCode+"%' and b.type=" + type;
	    this.resourceDao.update(updateSql, map);
	}

	@Override
	public Integer getMaxSort(String pId ,int type) {
		return this.resourceDao.getMaxSort(pId,type);
	}
}
