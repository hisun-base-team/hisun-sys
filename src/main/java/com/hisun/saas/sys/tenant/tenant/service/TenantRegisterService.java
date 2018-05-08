package com.hisun.saas.sys.tenant.tenant.service;/**
 * Created by liyikai on 15/11/21.
 */

import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;

public interface TenantRegisterService extends BaseService<TenantRegister,String> {

    /**
     * 激活
     * @param id
     */
    public void saveActivate(String id)  throws GenericException;

    /**
     * 注册
     * @param vo
     * @return
     */
    public String saveRegister(TenantRegisterVo vo)  throws GenericException;

    public TenantRegister getByUsername(String username);
}
