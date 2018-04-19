/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.privilege.entity;

import com.hisun.base.entity.AbstractPrivilege;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant2Privilege;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Entity
@Table(name = "sys_tenant_privilege")
public class TenantPrivilege extends AbstractPrivilege implements Serializable {

    private static final long serialVersionUID = 2768991738347437057L;

    private List<Tenant2Privilege> tenant2Privileges;

    @OneToMany(mappedBy = "tenantPrivilege")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Tenant2Privilege> getTenant2Privileges() {
        return tenant2Privileges;
    }

    public void setTenant2Privileges(List<Tenant2Privilege> tenant2Privileges) {
        this.tenant2Privileges = tenant2Privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TenantPrivilege that = (TenantPrivilege) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(code, that.code)
                .append(description, that.description)
                .append(impclass, that.impclass)
                .append(type, that.type)
                .append(displayType, that.displayType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(code)
                .append(description)
                .append(impclass)
                .append(type)
                .append(displayType)
                .toHashCode();
    }


}
