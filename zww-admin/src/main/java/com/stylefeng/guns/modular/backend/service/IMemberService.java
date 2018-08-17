package com.stylefeng.guns.modular.backend.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.common.persistence.model.Member;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangchangchun
 * @since 2018-01-02
 */
public interface IMemberService extends IService<Member> {

    Member selectIdByMemberId(String memberId);

    //用户列表
    List<Map<String, Object>> selectMember(Page<Member> page,Integer id, String userName, String phone,String beginTime,String phoneModel);
    //渠道列表
    List<Map<String, Object>> selectChannel(Page<Member> page,String channelNum,String userName,String beginTime,String endTime, String phoneModel);
    //小妖精渠道
    List<Map<String, Object>> xiaoyaojingSelectChannel(Page<Member> page,String channelNum, String userId,String userName,String beginTime,String endTime,String lastLoginFrom);

    Integer updateById(Account account);

    //封号
    Integer closeMember(Integer memberId);

    //解封
    Integer openMember(Integer memberId);
}
