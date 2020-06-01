package com.MTLearning.listener;

import com.MTLearning.util.ScreenshotUtil;
import com.MTLearning.util.TimeUtil;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;

/**
 * @program: web_auto_learning
 * @description: 自定义监听器，监听执行失败的用例
 * @author: Mr.Wang
 * @create: 2020-06-01 17:35
 **/
public class CustomListener extends TestListenerAdapter {

    /**
     * 失败的时候要做的事情
     * 用例一旦执行失败就会调用这个方法
     */

    @Override
    public void onTestFailure(ITestResult tr) {
        //文档根路径
        String documentRoot = "/Users/shimanwang/IdeaProjects/web_auto_learning/target/surefire-reports";
        //存放截图的文件夹
        String screenShotDir = "screenshot";
        //拿到文件夹的名字（按模块划分的模块）
        String testModuleName = tr.getTestContext().getCurrentXmlTest().getName();
        //拿到当前日期
        String currentData = TimeUtil.currentTime();
        //图片名字
        String imageName = System.currentTimeMillis() + ".jpg";
        //得到截图文件的最终路径（截图文件的保存地址）
        String imagePath = documentRoot + File.separator + screenShotDir + File.separator
                + testModuleName + File.separator + currentData + File.separator + imageName;
        //将截图存入最终路径中
        File screenshotFile = ScreenshotUtil.saveScreenshot(imagePath);
        //将本地路径替换成一个HTTP的URL地址（将文档根路径替换成ip:端口）http://127.0.0.1/screenshot/注册模块/2020-06-01%2021:55:53/1591019753497.jpg
        String imageUrl = getAccessPath(imagePath);
        //将截图写入测试报告中
        Reporter.log("<img src=\"" + imageUrl + "\" width=100 height=100 /><br/>\n" +
                "<a href=\"" + imageUrl + "\" target=\"_blank\">点击查看大图</a>");
    }

    /**
     * 将本机的文件路径替换成一个HTTP的URL地址
     *
     * @param imagePath /Users/shimanwang/IdeaProjects/web_auto_learning/target/surefire-reports/screenshot/注册模块/2020-06-01 21:55:53/1591019753497.jpg
     * @return http://127.0.0.1/screenshot/注册模块/2020-06-01%2021:55:53/1591019753497.jpg
     */
    public static String getAccessPath(String imagePath) {
        String[] screenshots = imagePath.split("screenshot");
        String host = " http://127.0.0.1";
        String imageUrl = host + File.separator + "screenshot" + screenshots[1];
        System.out.println(imageUrl);
        return imageUrl;
    }
}

