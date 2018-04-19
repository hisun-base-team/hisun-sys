package com.hisun.test;

import com.hisun.base.auth.Constants;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.mockito.Mockito;


/**
 * <p>Title: net.wish30.util.ShiroTestHelper</p>
 * <p>Description: shiro测试工具处理类</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 广州三零卫士信息安全有限公司</p>
 *
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年2月20日 下午5:30:25
 */
public class ShiroTestHelper {

    private static ThreadState threadState;

    private ShiroTestHelper() {
    }

    /**
     * 綁定Subject到當前線程.
     */
    private static void bindSubject(Subject subject) {
        clearSubject();
        threadState = new SubjectThreadState(subject);
        threadState.bind();
    }

    /**
     * 用Mockito快速創建一個已認證的用户.
     * @param principal  用戶名
     * @param object session中存放的數據
     */
    public static void mockSubject(String principal, Object object) {
        Subject subject = Mockito.mock(Subject.class);
        Mockito.when(subject.isAuthenticated()).thenReturn(true);
        Mockito.when(subject.getPrincipal()).thenReturn(principal);
//        UserLoginDetails userLoginDetails = new UserLoginDetails();
//        userLoginDetails.setResources(Collections.<Resource>emptyList());
//        userLoginDetails.setUsername(principal);
        Session session = new SimpleSession();
        session.setAttribute(Constants.CURRENT_USER, object);
        Mockito.when(subject.getSession()).thenReturn(session);

        bindSubject(subject);
    }

    /**
     * 清除當前線程中的Subject.
     */
    private static void clearSubject() {
        if (threadState != null) {
            threadState.clear();
            threadState = null;
        }
    }
}