package com.MTLearning.cases;

import com.MTLearning.pojo.RegisterData;
import com.MTLearning.util.AssertUtil;
import com.MTLearning.util.CaseUtil;
import com.MTLearning.util.ExcelUtil;
import org.testng.Assert;
import org.testng.ITestContext;
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
                     String expectResponse,
                     ITestContext context) {
        //1.打开注册页面
        String url = context.getSuite().getParameter("registerUrl");
        access(url);
        //根据页面关键字和元素关键字,定位账号输入框
        //getElement("registerPage","mobileInput").sendKeys(mobilephone);//更改为行为驱动
        input("registerPage", "mobileInput", mobilephone);
        //根据页面关键字和元素关键字,定位密码输入框
        input("registerPage", "pwdInput", password);
        //根据页面关键字和元素关键字,定位再次输入密码框
        input("registerPage", "confirmPwdInput", confirmPwd);
        //正向用例的验证码从cookie中取，开发留的后门
        if (isNegative.equals("1")) {
            verifyCode = webDriver.manage().getCookieNamed("verifycode").getValue();
        }
        //根据页面关键字和元素关键字,定位验证码输入框
        input("registerPage", "verifyCodeInput", verifyCode);
        //根据页面关键字和元素关键字,点击注册按钮
        click("registerPage", "signUpButton");
        //页面上的实际响应结果
        String response = getText("registerPage", "tipsText");
        //断言
        //反向用例断言
        if (isNegative.equals("0")) {
            Assert.assertEquals(response, expectResponse);
        } else {
            //正向用例的断言
            AssertUtil.assertUrlContains("login.html");
        }
    }

    /**
     * 数据提供者
     *
     * @return
     */
    @DataProvider
    public Object[][] datas() {
        //将注册模块用例从Excel中全部读出来，以对象的形式存储在list中
        List<RegisterData> caseList = ExcelUtil.read("src/test/resources/register.xlsx", "用例", RegisterData.class);
        //需要取出来的测试数据 的列名
        String[] cellName = {"IsNegative", "Mobilephone", "Password", "ConfirmPwd", "VerifyCode", "ErrorTips"};
        Object[][] datas = CaseUtil.datas(caseList, cellName);
        return datas;
    }
}

