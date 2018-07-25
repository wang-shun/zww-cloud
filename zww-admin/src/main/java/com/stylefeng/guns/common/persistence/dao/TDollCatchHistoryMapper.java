package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TDollCatchHistory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-14
 */
public interface TDollCatchHistoryMapper extends BaseMapper<TDollCatchHistory> {

    List<Map<String, Object>> selectDollCatchHistoryList(@Param("page") Page<TDollCatchHistory> page, @Param("memberId")Integer memberId, @Param("dollId")Integer dollId, @Param("catchStatus")String catchStatus, @Param("beginDate")String beginDate, @Param("endtime")String endtime);

    //抓取次数
    Integer selectCatchNum(@Param("memberId") Integer memberId, @Param("beginDate")String beginDate, @Param("endtime") String endtime);

    //抓取成功次数
    Integer selectCatchSuccessNum(@Param("memberId") Integer memberId, @Param("beginDate")String beginDate, @Param("endtime") String endtime);

    List<Map<String, Object>> selectDollCatchHistorys(@Param("page") Page<TDollCatchHistory> page,@Param("dollId") Integer dollId, @Param("dollName") String dollName, @Param("machineCode") String machineCode, @Param("dollCatchStates") String dollCatchStates,@Param("machineType") Integer machineType,@Param("memberName") String memberName, @Param("beginDate")String beginDate, @Param("endtime")String endtime);

}