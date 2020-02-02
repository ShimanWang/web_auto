package com.MTLearning.util;

import com.MTLearning.pojo.RegisterData;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: web_auto_learning
 * @description: 用例工具类
 * @author: Mr.Wang
 * @create: 2020-01-31 21:30
 **/
public class CaseUtil {

    /**
     * 从Excel中挑选出需要的数据，以 Object[][]的格式返回
     *
     * @param caseList 为了兼容各种类型的测试用例，写为泛型类
     * @param cellName
     * @return
     */
    public static <T> Object[][] datas(List<T> caseList, String[] cellName) {

        Object[][] datas = new Object[caseList.size()][cellName.length];
        //list集合中的数据都是同一个类型的，取出集合中的第一个数据，拿到它的字节码
        Class clazz = caseList.get(0).getClass();
        //从caseList这个集合中去取数据
        try {
            for (int i = 0; i < caseList.size(); i++) {
                T t = caseList.get(i);
                for (int j = 0; j < cellName.length; j++) {
                    String methodName = "get" + cellName[j];
                    //获取要反射的方法对象
                    Method method = clazz.getDeclaredMethod(methodName);
                    datas[i][j] = method.invoke(t);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }
}
