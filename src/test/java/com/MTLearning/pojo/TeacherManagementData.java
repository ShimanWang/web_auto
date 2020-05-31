package com.MTLearning.pojo;

import lombok.Data;

/**
 * @program: web_auto_learning
 * @description: 老师管理系统的测试用例实体类
 * @author: Mr.Wang
 * @create: 2020-05-30 14:44
 **/

@Data
public class TeacherManagementData {
    private String isNegative; // 是否为正向用例
    private String desc; //用例描述
    private String nickName;//昵称
    private String mobilephone; //手机号
    private String qQ; //QQ号
    private String type; //类型
    private String errorLocator; //校验定位器
    private String expectedError; //期望错误提示
}

