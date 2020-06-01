package com.MTLearning.util;

import com.MTLearning.cases.BaseCase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

/**
 * @program: web_auto_learning
 * @description: 屏幕截图工具类
 * @author: Mr.Wang
 * @create: 2020-06-01 21:16
 **/
public class ScreenshotUtil {
    public static Logger logger = Logger.getLogger(ScreenshotUtil.class);

    /**
     * 获取屏幕截图，并保存至目标地址
     *
     * @param savePath
     * @return 将目标文件返回
     */
    public static File saveScreenshot(String savePath) {
        File screenshorFile = null;
        //1.获取屏幕截图文件
        //由于截图的方法在WebDriver的子类中，所以首先判断浏览器类型
        if (BaseCase.webDriver instanceof FirefoxDriver) {
            FirefoxDriver driver = (FirefoxDriver) BaseCase.webDriver;
            screenshorFile = driver.getScreenshotAs(OutputType.FILE);
        } else if (BaseCase.webDriver instanceof ChromeDriver) {
            ChromeDriver driver = (ChromeDriver) BaseCase.webDriver;
            screenshorFile = driver.getScreenshotAs(OutputType.FILE);
        }

        //2.将截图文件拷贝到目标地址
        //commons-io中有操作文件的工具方法
        //由目标地址new一个目标文件
        File destScreenshotFile = new File(savePath);
        try {
            FileUtils.copyFile(screenshorFile, destScreenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("文件屏幕截图拷贝失败");
        }
        return destScreenshotFile;
    }
}

