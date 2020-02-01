package com.MTLearning.pojo;

import lombok.Data;

/**
 * @program: web_auto_learning
 * @description: 注册模块测试用例的实体类，pojo:实体类，没有任何业务的java类，里面就是一些属性，作用：封装数据
 * @author: Mr.Wang
 * @create: 2020-01-31 16:34
 **/
@Data
public class RegisterCases {
    private String isNegative; // 是否为正向用例
    private String desc; //用例描述
    private String mobilephone; //手机号
    private String password; //密码
    private String confirmPwd; //重复密码
    private String verifyCode; //验证码
    private String errorTips; //错误提示期望值

    public String getIsNegative() {
        return isNegative;
    }

    public void setIsNegative(String isNegative) {
        this.isNegative = isNegative;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }
}