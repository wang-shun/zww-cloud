package com.stylefeng.guns.Test;

import com.stylefeng.guns.common.weixin.WXUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("HHmmss");
        long start = Long.valueOf("083000");
        long end = Long.valueOf("173000");
        long nowTime = Long.valueOf(sim.format(now));
        System.out.println(start);
        System.out.println(end);
        System.out.println(nowTime);
    }
}
