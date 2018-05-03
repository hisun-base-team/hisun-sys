package com.hisun.saas.sys.tenant.role.dao.impl;

import com.hisun.saas.sys.tenant.base.dao.imp.TenantBaseDaoImpl;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.base.dao.impl.BaseDaoImpl;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


@Repository
public class TenantRoleDaoImpl extends TenantBaseDaoImpl<TenantRole,String> implements TenantRoleDao {

    @Override
    public void batchSaveResource(final String roleId,final String[] resourceIds) {
        Session session = getSession();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                String sql = "insert into sys_tenant_role_resource(id,tenant_role_id,resource_id) values(?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                for(int i=0;i<resourceIds.length;i++){
                    ps.setString(1, UUID.randomUUID().toString().replace("-",""));
                    ps.setString(2, roleId);
                    ps.setString(3, resourceIds[i]);
                    ps.addBatch();
                }
                ps.executeBatch();

                ps.close();
            }
        });
    }

    @Override
    public TenantRole getByCode(String code) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("roleCode = :roleCode","roleCode",code));
        query.add(CommonRestrictions.and("tombstone = :tombstone", "tombstone", TombstoneEntity.TOMBSTONE_FALSE));
        List<TenantRole> list = list(query, null ,1 ,1);
        return list.size()>0?list.get(0):null;
    }
}
