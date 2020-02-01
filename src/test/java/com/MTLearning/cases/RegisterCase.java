package com.MTLearning.cases;

import com.MTLearning.pojo.RegisterData;
import com.MTLearning.util.CaseUtil;
import com.MTLearning.util.ExcelUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @program: web_auto_learning
 * @description: 注册页面
 * @author: Mr.Wang
 * @create: 2020-01-30 21:02
 **/
public class RegisterCase extends BaseCase {
    /**
     * 测试方法
     *
     * @param isNegative     接收dataprovider传过来的数据
     * @param mobilephone
     * @param password
     * @param confirmPwd
     * @param verifyCode
     * @param expectResponse
     */
    @Test(dataProvider = "datas")
    public void test(String isNegative,
                     String mobilephone,
                     String password,
                     String confirmPwd,
                     String verifyCode,
                     String expectResponse) {
        //1.打开注册页面
        String url = "http://test.lemonban.com/lmcanon_web_auto/mng/register.html";
        webDriver.get(url);
        //定位账号输入框
        webDriver.findElement(By.id("mobilephone")).sendKeys(mobilephone);
        //定位密码输入框
        webDriver.findElement(By.name("password")).sendKeys(password);
        //定位再次输入密码框
        webDriver.findElement(By.id("pwdconfirm")).sendKeys(confirmPwd);
        //定位验证码输入框
        webDriver.findElement(By.id("verifycode")).sendKeys(verifyCode);
        //点击注册按钮
        webDriver.findElement(By.id("signup-button")).click();
        //页面上的实际响应结果
        String response = webDriver.findElement(By.className("tips")).getText();
        //断言
        //只有反向用例需要断言
        if (isNegative.equals("0")) {
            Assert.assertEquals(response, expectResponse);
        }else {
            //注册成功
        }


    }

    /**
     * 数据提供者
     *
     * @return
     */
    @DataProvider
    public Object[][] datas() {
        List<RegisterData> caseList = ExcelUtil.read("src/test/resources/register.xlsx", "用例", RegisterData.class);
        String[] cellName = {"IsNegative", "Mobilephone", "Password", "ConfirmPwd", "VerifyCode", "ErrorTips"};
        Object[][] datas = CaseUtil.datas(caseList, cellName);
        return datas;
    }
}

