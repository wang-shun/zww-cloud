package com.stylefeng.guns.modular.backend.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.common.persistence.model.MemberChargeHistory;
import com.stylefeng.guns.common.persistence.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-01-03
 */
public interface IMemberChargeHistoryService extends IService<MemberChargeHistory> {
    List<Map<String, Object>> selectList(Page<MemberChargeHistory> page, @Param("memberId")Integer memberId, @Param("name")String name,@Param("machineCode")String machineCode, @Param("chargeDate")String chargeDate);

    //生成消费记录
    Integer insertChargeHistory(Account account,User userdto);
}
