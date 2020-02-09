package com.MTLearning.util;

import com.MTLearning.pojo.PageObject;
import com.MTLearning.pojo.UIElement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: web_auto_learning
 * @description: 解析XML文件的工具类
 * @author: Mr.Wang
 * @create: 2020-02-08 14:02
 **/
public class XMLLoadUtil {
    //这个List里面放所有page对象，后面需要经常从里面取数据，那就是作为共享的定义为static
    public static List<PageObject> pageObjectList = new ArrayList<>();

    //为了保证直接调用pageObjectList的时候List中有数据，将load方法放到静态代码块中，保证类加载的时候就执行一次load方法，属于初始化的工作
    static {
        load();
    }

    public static void load() {
        String path = "src/test/resources/UILibrary.xml";

        try {
            //1.创建解析器SAXReader
            SAXReader saxReader = new SAXReader();
            //2.获取document文档对象
            Document document = saxReader.read(new File(path));
            //3.获取根节点（pages）
            Element rootElement = document.getRootElement();
            //4.获取子节点(名为page的子节点),拿到的所有节点都是Element类型的
            List<Element> pageElements = rootElement.elements("page");
            //循环处理每一个page元素
            for (Element pageElement : pageElements) {
                //获取page元素的属性值
                String pageKeyWord = pageElement.attributeValue("keyword");
                List<Element> uiElements = pageElement.elements("uiElement");
                //存储UIElement对象
                List<UIElement> uiElementList = new ArrayList<>();
                //循环处理每一个uiElement元素
                for (Element uiElement : uiElements) {
                    //取出uiElement元素中,所有属性的值
                    String elementKeyWord = uiElement.attributeValue("keyword");
                    String by = uiElement.attributeValue("by");
                    String value = uiElement.attributeValue("value");
                    //封装进UIElement类型的对象
                    UIElement uie = new UIElement(elementKeyWord, by, value);
                    //for循环结束后会封装3个UIElement类型的对象
                    //将封装好的UIElement类型的对象放进list
                    uiElementList.add(uie);
                }
                //封装page对象
                PageObject pageObject = new PageObject(pageKeyWord, uiElementList);
                //每次for循环都封装好一个page对象
                pageObjectList.add(pageObject);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}