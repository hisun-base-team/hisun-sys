package com.hisun.test.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p> Title : ResourceFunctionTest</p>
 * <p> Description: 资源功能测试</p>
 * <p> Copyright: Copyright (c) 2016</p>
 * <p> Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @email jason4j@qq.com
 * @date 2016年03月09 11:13
 */
public class ResourceFunctionTest {
    @BeforeClass
    public static void beforeClass() {

        LoginTest.testLogin();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        LoginTest.setDown();
    }

    @Test
    public void testResourceAdd() {

    }


}
