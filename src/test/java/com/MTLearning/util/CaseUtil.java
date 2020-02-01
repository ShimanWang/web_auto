package com.MTLearning.util;

import com.MTLearning.pojo.RegisterData;

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
     * @param caseList
     * @param cellName
     * @return
     */
    public static Object[][] datas(List<RegisterData> caseList, String[] cellName) {

        Object[][] datas = new Object[caseList.size()][cellName.length];
        //从caseList这个集合中去取数据
        try {
            for (int i = 0; i < caseList.size(); i++) {
                RegisterData cs = caseList.get(i);
                for (int j = 0; j < cellName.length; j++) {
                    String methodName = "get" + cellName[j];
                    //获取要反射的方法对象
                    Method method = RegisterData.class.getDeclaredMethod(methodName);
                    datas[i][j] = method.invoke(cs);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }
}

