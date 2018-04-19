package com.hisun.saas.sys.tenant.role.service;

import com.hisun.base.service.BaseService;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import com.hisun.saas.sys.tenant.vo.TreeNodeVo;

import java.util.List;

/**
 * Created by liyikai on 15/11/18.
 */
public interface TenantRoleService extends BaseService<TenantRole,String> {

    public TenantRole add(TenantRoleVo vo);

    public void deleteById(String id);

    public TenantRole update(TenantRoleVo vo);

    public PagerVo<TenantRole> listPager(String name, int pageNum, int pageSize);

    /**
     * 获取角色的设置资源的树
     * @param id
     * @return
     */
    public List<TreeNodeVo> getRoleInstallResourceTree(String id);

    public void saveResources(String roleId, String[] resourceIds);

    /**
     * 根据code查询角色
     * @param code
     * @return
     */
    TenantRole getTenantRoleByCode(String code);
}
