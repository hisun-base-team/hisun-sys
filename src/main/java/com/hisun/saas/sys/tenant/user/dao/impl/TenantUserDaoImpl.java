package com.hisun.saas.sys.tenant.user.dao.impl;

import com.hisun.saas.sys.tenant.base.dao.imp.TenantBaseDaoImpl;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository("tenantUserDao")
public class TenantUserDaoImpl extends TenantBaseDaoImpl<TenantUser,String> implements TenantUserDao {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;

    @Override
    public TenantUser saveRegister(TenantRegisterVo vo, Tenant tenant) {
        String sql = "insert into sys_tenant_user(id,email,user_name,real_name,password,tel,salt,locked,tenant_id,create_date,tombstone,status) "
                + " SELECT ?,?,?,?,?,?,?,false,?,?,0,0 from DUAL "
                + "WHERE NOT EXISTS(SELECT 1 FROM sys_tenant_user WHERE user_name = ? or email = ?)";
        List<Object> paramList = new ArrayList<Object>();
        String uuid = UUID.randomUUID().toString().replace("-","");
        paramList.add(uuid);
        paramList.add(vo.getEmail());
        paramList.add(vo.getUsername());
        paramList.add(vo.getRealname());
        String salt = randomNumberGenerator.nextBytes().toHex();
        String pwd = new SimpleHash(
                algorithmName,
                vo.getPassword(),
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
        paramList.add(pwd);
        paramList.add(vo.getTel());
        paramList.add(salt);
        paramList.add(tenant.getId());
        paramList.add(new Date());
        //where条件设值
        paramList.add(vo.getUsername());
        paramList.add(vo.getEmail());
        int result = executeNativeBulk(sql, paramList);
        TenantUser entity = null;
        if(result == 1){
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and("username = :username","username",vo.getUsername()));
            query.add(CommonRestrictions.and("email = :email","email",vo.getEmail()));
            List<TenantUser> list = list(query,null,1,1);
            entity = list.size()>0?list.get(0):null;
        }
        return entity;
    }

    @Override
    public void updateToFreezeByTenant(String tenantId) {
        String hql = " update TenantUser  tenantUser set tenantUser.tombstone = :tombstone where tenantUser.tenant.id = :tenantId";
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("","tombstone",TombstoneEntity.TOMBSTONE_TRUE));
        query.add(CommonRestrictions.and("","tenantId",tenantId));
        this.executeBulk(hql,query);
    }

    @Override
    public void updateToActivateByTenant(String tenantId) {
        String hql = " update TenantUser  tenantUser set tenantUser.tombstone = :tombstone where tenantUser.tenant.id = :tenantId";
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("","tombstone",TombstoneEntity.TOMBSTONE_FALSE));
        query.add(CommonRestrictions.and("","tenantId",tenantId));
        this.executeBulk(hql,query);
    }
}
