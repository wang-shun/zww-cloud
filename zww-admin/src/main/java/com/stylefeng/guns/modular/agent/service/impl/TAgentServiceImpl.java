package com.stylefeng.guns.modular.agent.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.common.persistence.dao.TAgentMapper;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
@Service
public class TAgentServiceImpl extends ServiceImpl<TAgentMapper, TAgent> implements ITAgentService {

    @Autowired
    private  TAgentMapper tAgentMapper;

    @Override
    public TAgent selectTAgentByUId(Integer uid) {
        return tAgentMapper.selectTAgentByUId(uid);
    }

    @Override
    public TAgent selectTAgentById(Integer id) {
        return tAgentMapper.selectTAgentById(id);
    }
    /**
     * 修改账户信息
     * @param balance 变动金额
     * @param salt 1加余额 0减余额
     * @param id 代理商id
     * @return
     */
    @Override
    public synchronized  void updateAmount(Long balance, int salt, Integer id) {
        TAgent agent = tAgentMapper.selectTAgentById(id);
        if(agent != null){
            TAgent tAgent = new TAgent();
            tAgent.setId(id);
            if(salt == 0){
                tAgent.setBalance(0l);//提现直接提完
            }else{
                tAgent.setBalance(agent.getBalance() + balance);//退回
            }
            tAgentMapper.updateAmount(tAgent);
        }
    }

    @Override
    public Map getById(Integer id) {
        return tAgentMapper.selectByIdAndLevel(id);
    }

    public List<Map<String, Object>> selectByLevel(Page<TAgent> page, String condition, String phone, String createTime, Integer level, Integer id) {
        return tAgentMapper.selectByLevel(page,condition, phone, createTime, level, id);
    }


    public String selectByValue (String clod){
        return tAgentMapper.selectByValue(clod);
    }


}
