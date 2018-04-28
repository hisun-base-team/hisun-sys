package com.hisun.saas.sys.tenant.tenant.service.impl;

import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.saas.sys.admin.communication.vo.MailSendSingleVo;
import com.hisun.saas.sys.tenant.role.dao.TenantRoleDao;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.tenant.dao.TenantDao;
import com.hisun.saas.sys.tenant.tenant.dao.TenantRegisterDao;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.dao.TenantUserDao;
import com.hisun.saas.sys.tenant.user.dao.TenantUserRoleDao;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantRegisterServiceImpl extends BaseServiceImpl<TenantRegister,String> implements TenantRegisterService {



    private TenantRegisterDao tenantRegisterDao;

    @Resource
    private TenantUserDao tenantUserDao;

    @Resource
    private TenantRoleDao tenantRoleDao;

    @Resource
    private TenantDao tenantDao;

    @Resource
    private TenantUserRoleDao tenantUserRoleDao;

    @Resource
    private MailService mailService;

    @Value("${sys.domain}")
    private String domain;



    @Resource
    @Override
    public void setBaseDao(BaseDao<TenantRegister, String> baseDao) {
        this.baseDao = baseDao;
        this.tenantRegisterDao = (TenantRegisterDao)baseDao;
    }

    @Override
    public void saveActivate(String id)  throws GenericException {
        TenantRegister tenantRegister = tenantRegisterDao.getByPK(id);
        TenantRole role = tenantRoleDao.getByCode("ROLE_TENANTADMIN");
        Tenant tenant = new Tenant();
        tenant.setName(tenantRegister.getTenantName());
        tenant.setCreateDate(new Date());
        tenantDao.save(tenant);
        TenantUser tenantUser = new TenantUser();
        BeanUtils.copyProperties(tenantRegister, tenantUser);
        tenantUser.setTenant(tenant);
        tenantUserDao.save(tenantUser);
        TenantUserRole userRole = new TenantUserRole();
        userRole.setUser(tenantUser);
        userRole.setRole(role);
        tenantUserRoleDao.save(userRole);
        int effectRow = tenantRegisterDao.updateRegisterActivate(id);
        if(effectRow == 0){
            throw new GenericException("已激活，请勿重复激活");
        }
    }

    @Override
    public String saveRegister(TenantRegisterVo vo)  throws GenericException {
        String uuid = tenantRegisterDao.save(vo);
        if(uuid == null){
            throw new GenericException("用户名或邮箱已存在，请重新填写");
        }
        StringBuilder url = new StringBuilder();
        url.append(domain + "/sys/tenant/register/activate/"+ uuid);
        String content = "您好，你注册\""+ vo.getTenantName() +"\"新租户以成功，请点击如下地址进行激活。\n" + url    ;
        MailResult resultCode = null;
        try {
            //TODO 邮件模板
            // MailResult resultCode = mailService.sendEmail("【三零优异服务网】租户管理员激活", content,vo.getEmail());//发送邀请邮件
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("tenantName",vo.getTenantName());
            paramMap.put("url",url.toString());
            MailSendSingleVo mailSendVo = new MailSendSingleVo(vo.getEmail(),paramMap);
            resultCode =  mailService.sendEmail("registertenant", null, null, mailSendVo,Boolean.FALSE);
        } catch (Exception e) {
            throw new GenericException("邮件发送失败,请稍后再试");
        }
        if(!resultCode.getMessage().equals(MailResult.SUCCESS)){
            throw new GenericException("邮件发送失败,请稍后再试");
        }
        return uuid;
    }

    @Override
    public TenantRegister getByUsername(String username) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and("username = :username", "username", username));
        query.add(CommonRestrictions.and("status = :status", "status", TenantRegister.STATUS_NO));
        List<TenantRegister> list = tenantRegisterDao.list(query,null,1,1);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
