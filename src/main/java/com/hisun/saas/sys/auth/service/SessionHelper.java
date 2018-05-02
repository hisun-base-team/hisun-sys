/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.service;

import com.google.common.collect.Maps;
import com.hisun.saas.sys.auth.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Component
public class SessionHelper {

	@Resource
	private SessionDAO sessionDAO;
	
	/**
	 * 获取所有session的用户名
	 * @return
	 */
	public Map<String,String> sessionPrincipal(){
		Map<String,String> maps = Maps.newHashMap();
		Collection<Session> sessions =  sessionDAO.getActiveSessions();
		Iterator<Session> it = sessions.iterator();
		while(it.hasNext()){
			Session session = it.next();
			String loginName  = principal(session);
			if(StringUtils.isNotBlank(loginName)){
				maps.put(loginName,session.getId().toString());
			}
		}
		return maps;
	}
	
	private String principal(Session session) {
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if(principalCollection!=null){
        	 return (String)principalCollection.getPrimaryPrincipal();
        }
        return "";
    }

    public void kickOutSession(String userName) throws Exception{
        Map<String,String> session = this.sessionPrincipal();
        if(session.containsKey(userName)){
            Session s = sessionDAO.readSession(session.get(userName));
            if (s != null){
                s.setAttribute(Constants.SESSION_FORCE_LOGOUT_KEY,Boolean.TRUE);
            }
        }
    }
}
