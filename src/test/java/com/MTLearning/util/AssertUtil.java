package com.MTLearning.util;

import com.MTLearning.cases.BaseCase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * @program: web_auto_learning
 * @description: 封装一些自己常用的断言方法
 * @author: Mr.Wang
 * @create: 2020-02-02 22:22
 **/
public class AssertUtil {
    /**
     * 正向用例的断言，操作成功后页面跳转，判断跳转后的页面URL是否包含某字段
     * @param exceptedUrlText 期望URL中包含的字段
     */
    public static void assertUrlContains(String exceptedUrlText) {
        //设置一个标志，是否跳转到期望页面
        boolean isInPage = true;
        //若注册成功后跳转到登录页面,跳转过去是需要时间的
        WebDriverWait wait = new WebDriverWait(BaseCase.webDriver, 5);
        //如果5s后还是没有跳转，会抛timeout异常
        try {
            wait.until(ExpectedConditions.urlContains(exceptedUrlText));
        } catch (Exception e) {
            //若5s还未跳转，更新标志位
            isInPage = false;
            e.printStackTrace();
        }
        //进行断言
        Assert.assertTrue(isInPage);
    }

}
