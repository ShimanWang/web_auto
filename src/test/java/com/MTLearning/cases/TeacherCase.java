package com.MTLearning.cases;

import com.MTLearning.pojo.TeacherManagementData;
import com.MTLearning.util.CaseUtil;
import com.MTLearning.util.ExcelUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @program: web_auto_learning
 * @description: 老师信息管理测试类
 * @author: Mr.Wang
 * @create: 2020-05-30 12:26
 **/
public class TeacherCase extends BaseCase {
    @Test(dataProvider = "datas")
    public void test(String isNegative,
                     String nickName,
                     String mobilephone,
                     String qQ,
                     String type,
                     String errorLocator,
                     String expectedError,
                     ITestContext context) {
        //1.进行登录操作(在执行login的case的时候已经登录成功了)
        String homeUrl = context.getSuite().getParameter("homeUrl");
        access(homeUrl);
        //2.点击"老师信息管理"一级菜单
        click("home", "teacherInformationManagement");
        //3.点击"老师信息"二级菜单
        click("home", "teacherInformation");
        //4.点击"添加老师"按钮,需要先跳转iframe
        switchToIframe("home", "addTeacherIframe");
        click("teacherList", "addTeacherButton");
        //5.在添加页面中输入数据
        //先切换iframe
        switchToIframe("teacherList", "addTeacherIframe");
        input("teacherAdd", "nickName", nickName);
        input("teacherAdd", "mobilephone", mobilephone);
        input("teacherAdd", "qq", qQ);
        //type是select选择框
        if (type != null && type.trim().length() != 0) {
            selectByText("teacherAdd", "type", type);
        }
        //6.点击保存按钮
        click("teacherAdd", "saveButton");
        //执行反向用例时，断言报错提示与期望的报错提示是否一致
        if (isNegative.equalsIgnoreCase("0")) {
            //通过上层div定位到的文本值包括：下面嵌套的div的文本值，将它们拼接在一起
            String actual = webDriver.findElement(By.className(errorLocator)).getText();
            //如果实际结果中包含期望的错误提示，则该条用例通过
            if (!actual.contains(expectedError)) {
                Assert.assertEquals(actual, expectedError, "此为反向用例，期望给出的错误提示：" + expectedError + "实际页面响应的错误提示：" + actual);
            }
        }
    }

    @DataProvider
    public Object[][] datas() {
        List<TeacherManagementData> teacherManagementSysData = ExcelUtil.read("src/test/resources/testcases/teacher.xlsx", "添加老师", TeacherManagementData.class);
        //需要取出来的测试数据 的列名
        String[] cellName = {"IsNegative", "NickName", "Mobilephone", "QQ", "Type", "ErrorLocator", "ExpectedError"};
        Object[][] datas = CaseUtil.datas(teacherManagementSysData, cellName);
        return datas;
    }
}

