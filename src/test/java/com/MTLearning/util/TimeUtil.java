package com.MTLearning.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: web_auto_learning
 * @description: 时间工具类
 * @author: Mr.Wang
 * @create: 2020-06-01 20:46
 **/
public class TimeUtil {
    /**
     * 获取当前时间，格式为：2020-02-23 18:40:09
     *
     * @return
     */
    public static String currentTime() {
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(current));
        return time;
    }
}

