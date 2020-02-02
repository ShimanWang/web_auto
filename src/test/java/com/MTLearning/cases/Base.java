package com.MTLearning.cases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

/**
 * @program: web_auto_learning
 * @description: 接口
 * @author: Mr.Wang
 * @create: 2020-02-01 23:54
 **/
public interface Base {
    /**
     * 套件执行前，初始化webdriver对象
     * @param browserType 浏览器名字
     */
    @BeforeSuite
    @Parameters(value = {"browserType"})
    void init(String browserType) ;

    /**
     * 套件执行完，关闭浏览器，驱动实例等相关资源
     * @throws InterruptedException
     */
    @AfterSuite
    void end() throws InterruptedException;

}

