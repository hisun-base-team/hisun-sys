package com.hisun.saas.sys.tenant.tenant.dao;/**
 * Created by liyikai on 15/11/21.
 */

import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.base.dao.BaseDao;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;

/**
 * <p>类名称:TenantRegisterDao</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/21上午9:42
 * @创建人联系方式:init@hn-hisun.com
 */
public interface TenantRegisterDao extends BaseDao<TenantRegister,String> {

    public String save(TenantRegisterVo vo);

    public int updateRegisterActivate(String id);
}
