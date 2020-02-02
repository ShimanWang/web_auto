package com.MTLearning.cases;

import com.MTLearning.pojo.LoginData;
import com.MTLearning.util.AssertUtil;
import com.MTLearning.util.CaseUtil;
import com.MTLearning.util.ExcelUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: web_auto_learning
 * @description: 登录模块测试类
 * @author: Mr.Wang
 * @create: 2020-02-01 23:52
 **/
public class LoginCase extends BaseCase {
    /**
     * @param isNegative
     * @param mobilephone
     * @param password
     * @param expectResponse
     */
    @Test(dataProvider = "datas")
    public void test(String isNegative,
                     String mobilephone,
                     String password,
                     String expectResponse,
                     ITestContext context) {
        //1.访问登录页面
        String url = context.getSuite().getParameter("loginUrl");
        webDriver.get(url);
        //2.输入手机号
        webDriver.findElement(By.id("mobilephone")).sendKeys(mobilephone);
        //3.输入密码
        webDriver.findElement(By.id("password")).sendKeys(password);
        //4.点击登录按钮
        webDriver.findElement(By.id("login")).click();

        //5.进行断言
        //反向用例需要实际响应结果与期望响应结果比较
        if (isNegative.equals("0")) {
            //6.拿到页面实际响应结果
            //当输入错误的账号密码时，后端查询需要时间，前端返回结果慢，所以在这里加个延时
            boolean isTips = true;
            WebDriverWait wait = new WebDriverWait(webDriver, 5);
            try {
                WebElement tipsElement = webDriver.findElement(By.className("tips"));
                //判断tips元素中的text是否包含了预期的字符串,若包含，则用例通过，否则会抛Timeout异常
                wait.until(ExpectedConditions.textToBePresentInElement(tipsElement, expectResponse));
            } catch (Exception e) {
                isTips = false;
                e.printStackTrace();
            }
            Assert.assertTrue(isTips);
        } else {
            //正向用例的断言
            AssertUtil.assertUrlContains("index.html");
        }
    }


    @DataProvider
    public Object[][] datas() {
        //1.将登录模块用例从Excel中全部读出来，以对象的形式存储在list中
        List<LoginData> caseList = ExcelUtil.read("src/test/resources/login.xlsx", "用例", LoginData.class);
        //需要取出来的测试数据 的列名
        String[] cellName = {"IsNegative", "Mobilephone", "Password", "ErrorTips"};
        Object[][] data = CaseUtil.datas(caseList, cellName);
        return data;
    }
}










