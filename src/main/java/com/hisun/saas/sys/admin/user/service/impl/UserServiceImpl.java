/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.user.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hisun.base.auth.Constants;
import com.hisun.base.auth.service.PasswordHelper;
import com.hisun.base.dao.BaseDao;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.AbstractResource;
import com.hisun.base.entity.AbstractRole;
import com.hisun.base.service.impl.BaseServiceImpl;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.saas.sys.admin.role.entity.RoleResource;
import com.hisun.saas.sys.admin.user.dao.UserDao;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.admin.user.vo.UserVo;
import com.hisun.saas.sys.auth.UserLoginDetails;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;
    @javax.annotation.Resource
    private PasswordHelper passwordHelper;

    @Override
    public String save(User entity) {
        //加密密码
        passwordHelper.encryptPassword(entity);
        String pk = super.save(entity);
        return pk;
    }

    @Override
    public void update(User entity, boolean changePassword) {
        if (changePassword) {
            //加密密码
            passwordHelper.encryptPassword(entity);
        }
        super.update(entity);
    }

    @Override
    public void update(User entity) {
        super.update(entity);
    }


    @javax.annotation.Resource
    public void setBaseDao(BaseDao<User, String> userDao) {
        this.baseDao = userDao;
        this.userDao = (UserDao) userDao;
    }

    @Override
    public User findByUsername(String username) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" User.username = :username",
                "username", username));
        List<User> userList = userDao.list(query, null);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }


    @Override
    public UserLoginDetails findUserLoginDetails(String username) {
        User user = this.findByUsername(username);
        if (user != null) {
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            try {
                BeanUtils.copyProperties(userLoginDetails, user);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error(e, e);
            }
            userLoginDetails.setUserid(user.getId());
            userLoginDetails.setUser(user);
            userLoginDetails.setUserType(Constants.USER_TYPE_ADMIN);
            List<AbstractRole> roles = Lists.newArrayList();
            List<UserRole> userRoles = user.getUserRoles();
            List<Resource> allResources = Lists.newArrayList();
            for (UserRole userRole : userRoles) {
                roles.add(userRole.getRole());
                for(RoleResource roleResource : userRole.getRole().getRoleResources()){
                    allResources.add(roleResource.getResource());
                }

            }
            userLoginDetails.setRoles(roles);
            Set<Resource> set = Sets.newLinkedHashSet();
            set.addAll(allResources);
            List<Resource> resources = new ArrayList<Resource>();
            for (Resource resource : set) {
                if (resource.getStatus() == AbstractResource.ENABLE) {
                    //可用资源
                    resources.add(resource);
                }
            }
            Collections.sort(resources);
            List<AbstractResource> abstractResources = Lists.newArrayList();
            for(Resource resource : resources){
                abstractResources.add((AbstractResource)resource);
            }
            userLoginDetails.setResources(abstractResources);
            return userLoginDetails;
        }
        return null;
    }

    @Override
    public boolean credentialsPassword(User user, String inputPassword) {
        return passwordHelper.credentialsPassword(user, inputPassword);
    }

    @Override
    public User findByEmail(String email) {
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" User.email = :email",
                "email", email));
        List<User> userList = userDao.list(query, null);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public PagerVo<UserVo> searchUserByName(int pageSize, int pageNum,
                                            UserVo userVo) throws Exception {
        Session session = userDao.getSession();
        PagerVo<UserVo> pager = null;
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuilder countSql = new StringBuilder();
        int count;
        StringBuilder sql = new StringBuilder();
        map.put("name", "%" + userVo.getUsername() + "%");

        countSql.append(" select count(1) from SYS_USER t");
        countSql.append(" where ");
        countSql.append("  t.user_name <>'admin' ");
        countSql.append(" and ( t.user_name like :name");
        countSql.append(" or t.real_name like :name");
        countSql.append(" or t.tel like :name");
        countSql.append(" or t.email like :name )");
        sql.append(" select t.id,t.email,t.real_name,t.tel,t.user_name,t.locked from SYS_USER t ");
        sql.append(" where ");
        sql.append("  t.user_name <>'admin' ");
        sql.append(" and ( t.user_name like :name ");
        sql.append(" or t.real_name like :name");
        sql.append(" or t.tel like :name");
        sql.append(" or t.email like :name )");
        Query query = session.createSQLQuery(countSql.toString());
        query.setProperties(map);
        count = Integer.parseInt(query.uniqueResult().toString());

        query = session.createSQLQuery(sql.toString());
        query.setProperties(map);
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNum - 1) * pageSize);
        List<Object[]> objects = query.list();

        List<UserVo> list = new ArrayList<UserVo>();
        UserVo userVos = null;
        if (objects != null && objects.size() > 0) {
            for (Object[] obs : objects) {
                int index = 0;
                userVos = new UserVo();
                userVos.setId((String) obs[index++]);
                userVos.setEmail((String) obs[index++]);
                userVos.setRealname((String) obs[index++]);
                userVos.setTel((String) obs[index++]);
                userVos.setUsername((String) obs[index++]);
                userVos.setLocked((Boolean) obs[index++]);
                list.add(userVos);
            }
        }

        pager = new PagerVo<UserVo>(list, count, pageNum, pageSize);
        return pager;
    }

}
