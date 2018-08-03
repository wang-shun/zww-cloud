package com.stylefeng.guns.common.weixin;

import com.stylefeng.guns.common.persistence.dao.TMemberMapper;
import com.stylefeng.guns.common.persistence.dao.TOemMapper;
import com.stylefeng.guns.common.persistence.model.Member;
import com.stylefeng.guns.common.persistence.model.TMember;
import com.stylefeng.guns.core.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

@Component
public class TaskWork {
    private static final Logger logger = LoggerFactory.getLogger(TaskWork.class);

    @Autowired
    private TMemberMapper memberMapper;

    //[秒] [分] [小时] [日] [月] [周] [年]
    @Scheduled(cron = "0 0 0 1 * ?")
    //  @Scheduled(cron = "0 33 10 20 7 ?")
    public void job1() {
        logger.warn("[秒] [分] [小时] [日] [月] [周] [年]");
        try {
            if(!isSystemIp()){
                 return;
            }

            String jsonStr = WXUtil.doPost("","http://lanao.nat300.top/icrane/api/wx/getAccessToken","POST");
            //  [{ "code": "lanaokj","accessToken": "UxAjDRGUOMJjAEAFDA" }, {"code": "lanaocs", "accessToken": "7zMvA15BMujIRQhAJAQZY" }]
            logger.warn("jsonStr = {}" , jsonStr);
            JSONArray jsonArr = JSONArray.fromObject(jsonStr);
            for (int i=0;i<jsonArr.size();i++) {
                JSONObject json = jsonArr.getJSONObject(i);
                String code = json.getString("code");
                String accessToken = json.getString("accessToken");
                if(!StringUtils.isEmpty(accessToken) && !StringUtils.isEmpty(code)){
                    List<TMember> members = memberMapper.selectByRegisterChannel(code);
                    for (TMember member: members) {
                            JSONObject userInfoJson = WXUtil.getUserInfo(member.getWeixinId(),accessToken);
                            if (!StringUtils.isEmpty(userInfoJson) && userInfoJson.containsKey("unionid")) {
                                if(userInfoJson.getInt("subscribe") == 1){
                                    String nikeName = userInfoJson.getString("nickname");
                                    String headimgurl = userInfoJson.getString("headimgurl");
                                    member.setName(nikeName);
                                    member.setIconRealPath(headimgurl);
                                    memberMapper.updateById(member);
                                }else{
                                    logger.warn("该用户未关注公众号={}", userInfoJson);
                                }
                            } else {
                                logger.error("获取用户微信信息出错={}", userInfoJson);
                            }
                    }
                }
            }
            logger.warn("批量更新用户微信信息成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private  boolean isSystemIp() throws  Exception{
        InetAddress ia=InetAddress.getLocalHost();
        String localname=ia.getHostName();
        String localip=ia.getHostAddress();
        logger.warn("本机名称是："+ localname);
        logger.warn("本机的ip是 ："+localip);
        if (!"47.106.39.237".equals(localip)) {
            return false;
        }
        return  true;
    }
}
