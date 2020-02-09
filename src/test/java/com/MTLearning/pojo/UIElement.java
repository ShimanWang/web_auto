package com.MTLearning.pojo;

import lombok.Data;

/**
 * @program: web_auto_learning
 * @description:解析xml时，将uiElement元素封装成UIElement对象
 * @author: Mr.Wang
 * @create: 2020-02-08 11:34
 **/
@Data
public class UIElement {
    private String keyword;
    private String by;
    private String value;

    public UIElement() {
    }

    public UIElement(String keyword, String by, String value) {
        this.keyword = keyword;
        this.by = by;
        this.value = value;
    }
}

