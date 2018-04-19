package com.hisun.saas.sys.tenant.tenant.dao.impl;

import com.hisun.saas.sys.tenant.tenant.dao.TenantRegisterDao;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.base.dao.impl.BaseDaoImpl;
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

/**
 * <p>类名称:TenantRegisterDaoImpl</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/21上午9:44
 * @创建人联系方式:init@hn-hisun.com
 */
@Repository
public class TenantRegisterDaoImpl extends BaseDaoImpl<TenantRegister,String> implements TenantRegisterDao {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations}")
    private int hashIterations = 2;
    @Override
    public String save(TenantRegisterVo vo) {
        String sql = "insert into sys_tenant_register(id,tenant_name,email,username,realname,password,salt,tel,status,create_date) "
                + " SELECT ?,?,?,?,?,?,?,?,?,? from DUAL "
                + "WHERE NOT EXISTS(SELECT 1 FROM sys_tenant_user WHERE user_name = ? or email = ?)" +
                " and NOT EXISTS(SELECT 1 FROM sys_tenant_register WHERE username = ? or email = ?)";
        List<Object> paramList = new ArrayList<Object>();
        String uuid = UUID.randomUUID().toString().replace("-","");
        paramList.add(uuid);
        paramList.add(vo.getTenantName());
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
        paramList.add(salt);
        paramList.add(vo.getTel());
        paramList.add(TenantRegister.STATUS_NO);
        paramList.add(new Date());
        //where条件设值
        paramList.add(vo.getUsername());
        paramList.add(vo.getEmail());
        paramList.add(vo.getUsername());
        paramList.add(vo.getEmail());
        int result = executeNativeBulk(sql, paramList);
        return result==0?null:uuid;
    }

    @Override
    public int updateRegisterActivate(String id) {
        String sql = "update sys_tenant_register set status = ?, activateDate = ? where id = ? and status = ?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(TenantRegister.STATUS_YES);
        paramList.add(new Date());
        paramList.add(id);
        paramList.add(TenantRegister.STATUS_NO);
        int result = executeNativeBulk(sql, paramList);
        return result;
    }
}
