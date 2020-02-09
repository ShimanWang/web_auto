package com.MTLearning.cases;

import com.MTLearning.pojo.PageObject;
import com.MTLearning.pojo.UIElement;
import com.MTLearning.util.XMLLoadUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.util.List;

/**
 * @program: web_auto_envtest
 * @description: 浏览器切换、初始化浏览器驱动
 * @author: Mr.Wang
 * @create: 2020-01-14 19:37
 **/
public class BaseCase implements Base {
    public static WebDriver webDriver = null;
    public Logger logger = Logger.getLogger(getClass());

    /**
     * 套件执行前，初始化webdriver对象
     *
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
     *
     * @throws InterruptedException
     */
    @AfterSuite
    public void end() throws InterruptedException {
        Thread.sleep(3000);
        webDriver.quit();
    }

    /**
     * 根据页面keyword和元素keyword定位元素
     *
     * @param pageKeyword      页面keyword
     * @param uiElementKeyWord 元素keyword
     * @return
     */
    public WebElement getElement(String pageKeyword, String uiElementKeyWord) {
        //遍历List集合，根据页面keyword和元素keyword定位到期望页面元素，如密码输入框、手机号输入框
        for (PageObject page : XMLLoadUtil.pageObjectList) {
            //判断满足条件的页面
            if ((page.getKeyword()).equals(pageKeyword)) {
                List<UIElement> uiElementsList = page.getUiElements();
                for (UIElement uiElement : uiElementsList) {
                    //判断满足条件的页面元素
                    if (uiElement.getKeyword().equals(uiElementKeyWord)) {
                        //找到后,根据UIElement类型的对象,定位到页面元素
                        try {
                            WebElement webElement = locateWebElement(uiElement);
                            return webElement;
                        } catch (Exception e) {
                            //如果这里有异常，就是locateWebElement方法中定位元素没有定位到，抛出的TimeOut异常
                            e.printStackTrace();
                            String by = uiElement.getBy();
                            String value = uiElement.getValue();
                            logger.info(pageKeyword+"页面的"+uiElementKeyWord+"定位不到；定位元素的方式：by"+by+"value为"+value);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据UIElement类型的对象,定位到页面元素
     *
     * @param uiElement
     * @return
     */
    public WebElement locateWebElement(UIElement uiElement) throws Exception {
        String by = uiElement.getBy();//也就是methodname
        String value = uiElement.getValue();
        By locator = null;
        if (by.equals("id")) {
            locator = By.id(value);
        } else if (by.equals("name")) {
            locator = By.name(value);
        } else if (by.equals("className")) {
            locator = By.className(value);
        }
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        //等到locator可见
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return webElement;
    }
}