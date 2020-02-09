package com.MTLearning.pojo;

import java.util.List;
import lombok.Data;

/**
 * @program: web_auto_learning
 * @description: 页面的pojo类，有哪些属性根据UILibrary决定
 * @author: Mr.Wang
 * @create: 2020-02-08 11:03
 **/

@Data
public class PageObject {
    private String keyword;
    private List<UIElement> uiElements;

    public PageObject() {
    }

    public PageObject(String keyword, List<UIElement> uiElements) {
        this.keyword = keyword;
        this.uiElements = uiElements;
    }
}

