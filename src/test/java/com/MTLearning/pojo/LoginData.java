package com.MTLearning.pojo;

import lombok.Data;

/**
 * @program: web_auto_learning
 * @description: 用来封装登录用例
 * @author: Mr.Wang
 * @create: 2020-02-02 11:34
 **/
@Data
public class LoginData {
    private String isNegative; // 是否为正向用例
    private String desc; //用例描述
    private String mobilephone; //手机号
    private String password; //密码
    private String errorTips; //错误提示期望值
}

