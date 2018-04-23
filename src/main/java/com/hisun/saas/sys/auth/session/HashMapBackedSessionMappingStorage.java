/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public final class HashMapBackedSessionMappingStorage {
	
    /**
     * Maps the ID from the CAS server to the Session ID.
     */
    private final Map<String,Serializable> MANAGED_SESSIONS_ID = new HashMap<String,Serializable>();

	public synchronized void addSessionById(String mappingId, Session session) {
        MANAGED_SESSIONS_ID.put(mappingId, session.getId());

	}                               

	public synchronized Serializable getSessionIDByMappingId(String mappingId) {
        return MANAGED_SESSIONS_ID.get(mappingId);
	}
}
