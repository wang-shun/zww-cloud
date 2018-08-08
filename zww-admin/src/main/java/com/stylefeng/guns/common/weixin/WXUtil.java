package com.stylefeng.guns.common.weixin;

import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SUN on 2017/12/27.
 */
public class WXUtil {
    private static final Logger logger = LoggerFactory.getLogger(WXUtil.class);


    public static void main(String[] args) {
        try {
            String    jsonStr = WXUtil.doPost("","http://lanao.nat300.top/icrane/api/wx/getAccessToken","POST");
            //  [{ "code": "lanaokj","accessToken": "UxAjDRGUOMJjAEAFDA" }, {"code": "lanaocs", "accessToken": "7zMvA15BMujIRQhAJAQZY" }]
            System.out.println("jsonStr = {}" + jsonStr);
            JSONArray jsonArr = JSONArray.fromObject(jsonStr);
            for (int i=0;i<jsonArr.size();i++) {
                JSONObject json = jsonArr.getJSONObject(i);
                String code = json.getString("code");
                String accessToken = json.getString("accessToken");
                if(!StringUtils.isEmpty(accessToken) && "lanaokj".equals(code)){
                    JSONObject jsonsend =sendTemplate("ItetnetU0PZPL2i0pW96XFyFYS2qBMr8uUvxAcqKQSc","http://weixin.qq.com/download","o_-591nmBVcc6SxgaoMb_jrC08L8",accessToken
                    ,"订单发货通知","恭喜您支付成功! 预估五分钟后到帐，如有疑问请返回至在线客服联系我们或致电0551-62675556","1213223","Q币10元充值","2件","17.2元",DateUtil.format(new Date(),"yyyy-MM-dd"));
                    System.out.println(jsonsend);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送模板消息
     * @param templateId 模板id
     * @param url 点击模板需要跳转的地址
     * @param openid  接收者openid
     * @param accessToken
     * @return
     */
    public  static  JSONObject sendTemplate(String templateId ,String url,String openid,String accessToken
         ,String first,String remark,String keyword1,String keyword2,String keyword3,String keyword4,String keyword5) throws  Exception{
        String info = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
            if (StringUtils.isEmpty(accessToken)) {
                return null;
            }
            info = info.replace("ACCESS_TOKEN", accessToken);
            JSONObject json = new JSONObject();
            JSONObject data = new JSONObject();
            json.put("touser",openid);
            json.put("template_id",templateId);
            json.put("url",url);
            data.put("first", getJson(first, "#173177"));
            data.put("keyword1", getJson(keyword1, "#173177"));
            data.put("keyword2", getJson(keyword2, "#173177"));
            if(!StringUtils.isEmpty(keyword3)){
                data.put("keyword3", getJson(keyword3, "#173177"));
            }
            if(!StringUtils.isEmpty(keyword4)){
                data.put("keyword4", getJson(keyword4, "#173177"));
            }
            if(!StringUtils.isEmpty(keyword5)){
                data.put("keyword5", getJson(keyword5, "#173177"));
            }
            data.put("remark", getJson(remark, "#173177"));
            json.put("data",data);
            System.out.println(json.toString());
            String msg = doPost(json.toString(), info, "POST");
            JSONObject resp4 = JSONObject.fromObject(msg);
            if (resp4.getInt("errcode") == 0 && "ok".equals(resp4.getString("errmsg"))) {
                return resp4;
            } else {
                logger.info("发送模板消息异常:" + resp4.toString());
            }
        return null;
    }
private static JSONObject getJson(String value,String color){
    JSONObject jsons = new JSONObject();
    jsons.put("value",value);
    jsons.put("color",color);
    return jsons;
}


    /**
     * 获取用户信息
     *
     * @return
     */
    public static JSONObject getUserInfo(String openid,String accessToken) {
        String info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        try {
            if (StringUtils.isEmpty(accessToken)) {
                return null;
            }
            info = info.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
            String msg = doPost("", info, "GET");
            JSONObject resp4 = JSONObject.fromObject(msg);
            if (!resp4.containsKey("errcode")) {
                resp4.put("access_token", accessToken);
                return resp4;
            } else {
                logger.info("获取获取用户信息异常:" + resp4.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否为节假日
     * @param date  日期
     * @return 工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
     */
    public  static  Integer isHostory(Date date){
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
            String url = "http://tool.bitefu.net/jiari/?d=" + sim.format(date);
            String json = doPost("",url,"GET");
            return Integer.parseInt(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否为周末
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    public static String doPost(String pa, String url, String method) throws Exception {
        String parameterData = pa;

        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterData.length()));

        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");

            outputStreamWriter.write(parameterData.toString());
            outputStreamWriter.flush();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }

            if (outputStream != null) {
                outputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }
}