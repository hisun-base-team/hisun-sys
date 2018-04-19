package com.hisun.test.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;

/**
 * <p>Title: LoginTest.java</p>
 * <p>Description: selenium测试登录</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @version v0.1
 * @email jason4j@qq.com
 * @date 2016-01-25 11:06
 */
public class LoginTest {

    static WebDriver wd;
    static String url = "http://172.16.1.24";

    //@AfterMethod
    public static void setDown() throws Exception {
        wd.quit();//关闭浏览器
    }

    //@Test
    public static void testLogin() {

        try {
            wd = new FirefoxDriver();//创建一个firefox实例
        } catch (Exception e) {
            System.setProperty("webdriver.firefox.bin", "/home/firefox");
            wd = new FirefoxDriver();//创建一个firefox实例
        }
        
        String username = "test1";

        String password = "123test";

        String kaptcha = "selenium-test";

        //让浏览器访问指定地址
        //wd.get("http://172.16.1.12/30ServiceCloud/login");
        wd.get(url + "/login");

        //获取页面title
        String oldTitle = wd.getTitle();

        //设置表单元素值
        wd.findElement(By.id("username")).sendKeys(username);
        wd.findElement(By.name("password")).sendKeys(password);
        wd.findElement(By.id("kaptcha")).sendKeys(kaptcha);

        //提交表单
        wd.findElement(By.className("loginbuttom")).click();


        //轮询30s判断
        /*new WebDriverWait(wd,30).until(new ExpectedCondition<Boolean>(){

            @Override
            public Boolean apply(WebDriver input) {
                return false;
            }
        });*/

        //对比登陆后页面title
        //System.out.println(wd.getTitle());

        AssertJUnit.assertFalse(StringUtils.equalsIgnoreCase(oldTitle,wd.getTitle()));
        //AssertJUnit.assertTrue(StringUtils.equalsIgnoreCase("首页",wd.getTitle()));

        //获取页面链接
        AssertJUnit.assertTrue(StringUtils.equalsIgnoreCase(url+"/dashboard",wd.getCurrentUrl()));

    }
}
