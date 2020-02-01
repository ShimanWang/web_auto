package com.MTLearning.pojo;

import lombok.Data;

/**
 * @program: web_auto_learning
 * @description: 注册模块测试用例的实体类，pojo:实体类，没有任何业务的java类，里面就是一些属性，作用：封装数据
 * @author: Mr.Wang
 * @create: 2020-01-31 16:34
 **/
@Data
public class RegisterData {
    private String isNegative; // 是否为正向用例
    private String desc; //用例描述
    private String mobilephone; //手机号
    private String password; //密码
    private String confirmPwd; //重复密码
    private String verifyCode; //验证码
    private String errorTips; //错误提示期望值

}