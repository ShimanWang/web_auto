package com.MTLearning.cases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * @program: web_auto_envtest
 * @description: 浏览器切换、初始化浏览器驱动
 * @author: Mr.Wang
 * @create: 2020-01-14 19:37
 **/
public class BaseCase {
    public static WebDriver webDriver = null;

    /**
     * 套件执行前，初始化webdriver对象
     * @param browserType 浏览器名字
     */
    @BeforeSuite
    @Parameters(value = {"browserType"})
    public void init(String browserType) {
        //选择启动浏览器的类型
        if (browserType.equalsIgnoreCase("firefox")) {
            //1.启动浏览器驱动前需要指定驱动文件的路径
            //设置系统变量，指定火狐的安装路径
            System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox");
            //2.启动浏览器
            //同时会将浏览器驱动的可执行文件启动起来
            webDriver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("chrome")) {
            //1.创建webdriver对象，启动谷歌浏览器
            webDriver = new ChromeDriver();
        }
    }

    /**
     * 套件执行完，关闭浏览器，驱动实例等相关资源
     * @throws InterruptedException
     */
    @AfterSuite
    public void end() throws InterruptedException {
        Thread.sleep(3000);
        webDriver.quit();
    }
}