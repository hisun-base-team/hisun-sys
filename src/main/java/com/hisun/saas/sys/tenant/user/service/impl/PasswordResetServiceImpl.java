package com.hisun.saas.sys.tenant.user.service.impl;

import com.hisun.saas.sys.auth.service.PasswordHelper;
import com.hisun.saas.sys.auth.vo.PasswordSecurity;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.saas.sys.admin.communication.vo.MailSendSingleVo;
import com.hisun.saas.sys.tenant.user.dao.PasswordResetDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.entity.PasswordReset;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.service.PasswordResetService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>类名称:PasswordResetServiceImpl</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/22下午4:31
 * @创建人联系方式:init@hn-hisun.com
 */
@Service
public class PasswordResetServiceImpl extends BaseServiceImpl<PasswordReset,String> implements PasswordResetService {

    private Logger logger = Logger.getLogger(PasswordResetServiceImpl.class);

    @Resource
    private PasswordHelper passwordHelper;

    private PasswordResetDao passwordResetDao;

    @Resource
    private TenantUserDao tenantUserDao;

    @Resource
    private MailService mailService;

    @Value("${sys.domain}")
    private String domain;

    @Resource
    @Override
    public void setBaseDao(BaseDao<PasswordReset, String> baseDao) {
        this.baseDao = baseDao;
        this.passwordResetDao = (PasswordResetDao)baseDao;
    }

    @Override
    public void addForgot(String email)  throws GenericException {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("email = :email","email",email));
        List<TenantUser> list = tenantUserDao.list(query,null,1,1);
        if(list.size() == 0){
            throw new GenericException("该用户邮箱不存在");
        }
        TenantUser tenantUser = list.get(0);
        //每次申请重置，覆盖之前的申请重置
        String sql = "delete from sys_tenant_password_reset where tenant_user_id = ?";
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(tenantUser.getId());
        passwordResetDao.executeNativeBulk(sql, paramList);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setTenantUser(tenantUser);
        passwordResetDao.save(passwordReset);
        StringBuilder url = new StringBuilder();
        url.append(domain + "/sys/tenant/user/pwdreset/"+ passwordReset.getId());
        String content = "您好，请点击如下地址进行密码重置。\n" + url;
        MailResult mailResult = null;
        try {
            //TODO 邮件模板
            //this.mailService.sendEmail("【三零优异服务网】密码重置", content, email);
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("username", StringUtils.isNotBlank(tenantUser.getRealname())?tenantUser.getRealname():tenantUser.getUsername());
            paramMap.put("date", new DateTime().toString("yyyy-MM-dd HH:mm:dd"));
            paramMap.put("url",url.toString());
            MailSendSingleVo mailSendVo = new MailSendSingleVo(email,paramMap);
            mailResult = mailService.sendEmail("tenantresetpassword", null, null, mailSendVo,Boolean.FALSE);
        } catch (Exception e) {
            throw new GenericException("邮件发送失败！请重新尝试");
        }
        if(!MailResult.SUCCESS.equals(mailResult.getMessage())){
            logger.error("密码重置邮件发送失败，申请邮箱"+email);
            throw new GenericException("邮件发送失败！请重新尝试");
        }
    }

    @Override
    public void addReset(String id, String password) {
        PasswordReset entity = passwordResetDao.getByPK(id);
        TenantUser tenantUser = entity.getTenantUser();
        PasswordSecurity passwordSecurity = passwordHelper.encryptPassword(password);
        tenantUser.setPassword(passwordSecurity.getPassword());
        tenantUser.setSalt(passwordSecurity.getSalt());
        tenantUserDao.update(tenantUser);
        //需要删除数据
        passwordResetDao.delete(entity);
    }
}
