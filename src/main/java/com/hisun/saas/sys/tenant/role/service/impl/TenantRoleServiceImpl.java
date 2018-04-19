package com.hisun.saas.sys.tenant.role.service.impl;

import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.vo.TreeNodeVo;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>类名称:TenantRoleServiceImpl</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/18下午2:15
 * @创建人联系方式:init@hn-hisun.com
 */
@Service
public class TenantRoleServiceImpl extends BaseServiceImpl<TenantRole,String> implements TenantRoleService {

    private TenantRoleDao tenantRoleDao;

    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantRole, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantRoleDao = (TenantRoleDao)baseDao;
    }

    @Override
    public TenantRole add(TenantRoleVo vo) {
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        TenantRole entity = new TenantRole();
        BeanUtils.copyProperties(vo, entity);
        entity.setId(null);
        setCreateUser(details, entity);
        tenantRoleDao.save(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        //删除角色资源中间表
        String sql = "delete from SYS_TENANT_ROLE_RESOURCE where tenant_role_id = ?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        tenantRoleDao.executeNativeBulk(sql, paramList);
        //删除租户角色中间表
        sql = "delete from SYS_TENANT_USER_ROLE where tenant_role_id = ?";
        tenantRoleDao.executeNativeBulk(sql, paramList);
        tenantRoleDao.deleteByPK(id);
    }

    @Override
    public TenantRole update(TenantRoleVo vo) {
        UserLoginDetails details = UserLoginDetailsUtil.getUserLoginDetails();
        TenantRole entity = tenantRoleDao.getByPK(vo.getId());
        BeanUtils.copyProperties(vo, entity);
        setUpdateUser(details, entity);
        tenantRoleDao.update(entity);
        return entity;
    }

    @Override
    public PagerVo<TenantRole> listPager(String name, int pageNum, int pageSize) {
        CommonConditionQuery query = new CommonConditionQuery();
        if(StringUtils.isNotBlank(name)){
            query.add(CommonRestrictions.and("roleName like :name","name", "%"+name+"%"));
        }
        query.add(CommonRestrictions.and("tombstone = :tombstone","tombstone", TombstoneEntity.TOMBSTONE_FALSE));
        CommonOrderBy orderBy = new CommonOrderBy();
        orderBy.add(CommonOrder.asc("sort"));
        List<TenantRole> list = tenantRoleDao.list(query, orderBy, pageNum, pageSize);
        Long total = tenantRoleDao.count(query);
        return new PagerVo<TenantRole>(list, total.intValue(), pageNum, pageSize);
    }

    @Override
    public List<TreeNodeVo> getRoleInstallResourceTree(String id) {
        /*select tmp.rrid existResourceId, res2.id id, res2.p_id pid,res2.resource_name name from (select rr.resource_id as existResourceId from sys_tenant_role role,
                sys_tenant_role_resource rr
                where role.id = '123'
                and role.id = rr.tenant_role_id) tmp right join
        sys_resource res2 on tmp.existResourceId = res2.id
        where res2.status = 0*/
        //上面注释是原始查询语句
        String sql = "select tmp.existResourceId existResourceId, res2.id id, res2.p_id pid,res2.resource_name name from "
            + "(select rr.resource_id as existResourceId from sys_tenant_role role, sys_tenant_role_resource rr where role.id = :roleId and role.id = rr.tenant_role_id) tmp "
            + " right join sys_resource res2 on tmp.existResourceId = res2.id where res2.type = 1 and res2.status = 0 order by res2.sort";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("roleId", id);
        List<Map> list = tenantRoleDao.countReturnMapBySql(sql, paramMap);
        List<TreeNodeVo> treeList = new ArrayList<TreeNodeVo>();
        TreeNodeVo root = new TreeNodeVo("1","资源树",null,true,false);
        treeList.add(root);
        TreeNodeVo treeNodeVo = null;
        Object object = null;
        for(Map rowMap : list){
            treeNodeVo = new TreeNodeVo();
            treeNodeVo.setId(rowMap.get("id").toString());
            treeNodeVo.setpId(rowMap.get("pid").toString());
            treeNodeVo.setName(rowMap.get("name").toString());
            treeNodeVo.setNocheck(false);
            object = rowMap.get("existResourceId");
            treeNodeVo.setChecked(object==null?false:true);
            treeList.add(treeNodeVo);
        }
        return treeList;
    }

    @Override
    public void saveResources(String roleId, String[] resourceIds) {
        String sql = "delete from sys_tenant_role_resource where tenant_role_id = ? ";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(roleId);
        tenantRoleDao.executeNativeBulk(sql, paramList);
        tenantRoleDao.batchSaveResource(roleId,resourceIds);

    }

    @Override
    public TenantRole getTenantRoleByCode(String code) {
        return tenantRoleDao.getByCode(code);
    }
}
