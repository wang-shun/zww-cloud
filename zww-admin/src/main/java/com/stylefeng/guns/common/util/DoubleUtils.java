package com.stylefeng.guns.common.util;

import java.text.DecimalFormat;

public class DoubleUtils {

    /**
     * 将数据保留两位小数
     */
    public static  double getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("######0.00");
        String yearString = dFormat.format(num);
        Double temp = Double.valueOf(yearString);
        return temp;

    }
}
