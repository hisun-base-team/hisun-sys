package com.hisun.test.saas.sys.auth;

import com.google.common.collect.Maps;
import com.hisun.test.selenium.LoginTest;
import org.junit.*;

import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * <p>Title: QuartzManagerUtilTest.java</p>
 * <p>Description: 测试任务调度Util</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @version v0.1
 * @email jason4j@qq.com
 * @date 2015-11-26 09:47
 */
public class QuartzManagerUtilTest {

    //    @BeforeClass
    public static void beforeClass() {
        LoginTest.testLogin();
    }

    //    @AfterClass
    public static void afterClass() throws Exception {
        LoginTest.setDown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testJob() throws InterruptedException {
        /*String job_name = "动态任务调度";
        System.out.println("【系统启动】开始(每1秒输出一次)...");
        QuartzManagerUtil.addJob(job_name, QuartzJob.class, "0/1 * * * * ?",new HashMap<String, Object>(){{
            put("swj0881","jason");
        }});

        Thread.sleep(5000);
        System.out.println("【修改时间】开始(每2秒输出一次)...");
        QuartzManagerUtil.modifyJobTime(job_name, "10/2 * * * * ?",DynamicQuarz.class);
        Thread.sleep(6000);
        System.out.println("【移除定时】开始...");
        QuartzManagerUtil.removeJob("123");
        System.out.println("【移除定时】成功");

        System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
        QuartzManagerUtil.addJob(job_name, QuartzJob.class, "*//*10 * * * * ?");
        Thread.sleep(60000);
        System.out.println("【移除定时】开始...");
        QuartzManagerUtil.removeJob(job_name);
        System.out.println("【移除定时】成功");*/

        String usrKey = "30SCloud_test_m6oEGd";
        String apiKey = "jWIkt0ZhyR7iQdSU";
        String url = "http://sendcloud.sohu.com/webapi/mail.send.json";
        //String from = "30SCloud@30wish.net";
        //String to = "592907750@qq.com";
        String html="测试";
        String subject="测试";
        Map<String, String> paramsMap = Maps.newHashMap();
        paramsMap.put("api_user",usrKey);
        paramsMap.put("api_key",apiKey);
        //paramsMap.put("from",from);
        //paramsMap.put("to",to);
        paramsMap.put("html",html);
        paramsMap.put("subject",subject);
        //Map<String,Object> map = HttpClientUtil.post(url,paramsMap,Map.class);
//        System.out.print(HttpClientUtil.post(url,paramsMap,String.class));


        Assert.assertThat("目标值50不大于20且小于40", 50, allOf(lessThan(60), greaterThan(20)));
    }
}

