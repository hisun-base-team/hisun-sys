package com.hisun.saas.sys.tenant.tenant.service;/**
 * Created by liyikai on 15/11/21.
 */

import com.hisun.base.exception.GenericException;
import com.hisun.base.service.BaseService;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;

/**
 * <p>类名称:TenantRegisterService</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/21上午9:45
 * @创建人联系方式:init@hn-hisun.com
 */
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
