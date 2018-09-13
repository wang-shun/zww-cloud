package com.stylefeng.guns.Test;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
public class Tset {
    public static void main(String[] args) {
        Map<String, Object> map =new HashMap<>();
        map.put("userId","12345");
        map.put("orderId","2018992302");
        map.put("orderStatus","SUCCESS");
        map.put("price","10.00");
        map.put("createDate","2018-09-12 10:10:10");
        String sign = getSign(map,"ed958469e6ee8a343daca01b3c685ba1","sign");
        System.out.println(sign);
    }
    /**
     * 生成签名
     *
     * @param map
     * @param key
     * @param signKey
     * @return
     */
    public static String getSign(Map<String, Object> map, String key, String signKey) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry :  map.entrySet()) {
            if (entry.getValue()  != null && org.apache.commons.lang3.StringUtils.isNotBlank(entry.getValue().toString())
                    && !org.apache.commons.lang3.StringUtils.equals(entry.getKey(),signKey)) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        result =  DigestUtils.md5Hex(result).toUpperCase();
        return result;
    }
}
