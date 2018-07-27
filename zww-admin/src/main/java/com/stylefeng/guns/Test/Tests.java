package com.stylefeng.guns.Test;

import com.stylefeng.guns.common.persistence.dao.TAgentMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.TAgent;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/Tests")
public class Tests {
    @Autowired
    private TAgentMapper tAgentMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/Test")
    @ResponseBody
    public Object test2() {
        ImportParams params = new ImportParams();
        List<Map<String, Object>> list = ExcelImportUtil.importExcel(
                new File("C:\\Users\\Administrator\\Desktop\\需要冻结、失效的三代名单.xlsx"), Map.class, params);
        for (int i=0;i<list.size();i++) {
            Map<String, Object> map = list.get(i);
            String phone =  (String)map.get("phone");
            String username =  (String)map.get("username");
            String status =  (String) map.get("status");
            System.out.println(phone + "," + username + "," + status);
            if("失效".equals(status)){
                userMapper.updateStatusByUsername(status,username);
            }
            tAgentMapper.updateStatusByUsername(status,username,phone);
        }
        System.out.println( "success" );
        return "success";
    }

}
