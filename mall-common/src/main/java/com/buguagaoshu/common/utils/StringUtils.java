package com.buguagaoshu.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 22:43
 */
public class StringUtils {
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return simpleDateFormat.format(now);
    }

    public static void main(String[] args) {
        System.out.println(getNowTime());
    }
}
