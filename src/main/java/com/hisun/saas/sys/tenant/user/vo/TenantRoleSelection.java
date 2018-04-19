package com.hisun.saas.sys.tenant.user.vo;

import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;

/**
 * <p>类名称：TenantRoleSelection</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/19 下午7:08
 * @创建人联系方式：lihm_gz@30wish.net
 */
public class TenantRoleSelection extends TenantRoleVo {

    private String selected = "";

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
