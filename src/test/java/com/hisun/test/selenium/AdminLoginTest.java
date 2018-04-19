package com.hisun.test.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;

/**
 * <p> Title : AdminLoginTest</p>
 * <p> Description: admin测试登录</p>
 * <p> Copyright: Copyright (c) 2016</p>
 * <p> Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @email jason4j@qq.com
 * @date 2016年03月10 10:38
 */
public class AdminLoginTest {

    static WebDriver wd;
    static String ip = "172.16.1.24";
    static String url = "http://" + ip + "/admin";

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

        String username = "admin";

        String password = "admin123";

        String kaptcha = "selenium-test";

        String code = "selenium-test";
        //让浏览器访问指定地址
        //wd.get("http://172.16.1.12/30ServiceCloud/login");
        wd.get(url + "/login");

        //获取页面title
        String oldTitle = wd.getTitle();

        //设置表单元素值
//        wd.findElement(By.id("username")).sendKeys(username);
//        wd.findElement(By.name("password")).sendKeys(password);
//        wd.findElement(By.id("kaptcha")).sendKeys(kaptcha);
//        wd.findElement(By.id("code")).sendKeys(kaptcha);

        wd.findElement(By.id("username")).sendKeys("admin");
        wd.findElement(By.id("password")).sendKeys("admin123");
        wd.findElement(By.id("code")).sendKeys("selenium-test");
        wd.findElement(By.cssSelector("button.loginbuttom")).click();

        //轮询30s判断
        /*new WebDriverWait(wd,30).until(new ExpectedCondition<Boolean>(){

            @Override
            public Boolean apply(WebDriver input) {
                return false;
            }
        });*/

        //对比登陆后页面title
        //System.out.println(wd.getTitle());

        AssertJUnit.assertFalse(StringUtils.equalsIgnoreCase(oldTitle, wd.getTitle()));
        //AssertJUnit.assertTrue(StringUtils.equalsIgnoreCase("首页",wd.getTitle()));

        //获取页面链接
        AssertJUnit.assertTrue(StringUtils.equalsIgnoreCase("http://" + ip + "/30ServiceCloud/platform/admin/user/list", wd.getCurrentUrl()));

    }
}

