package com.stylefeng.guns.modular.backend.warpper;

import java.util.*;

import com.stylefeng.guns.common.constant.factory.ZwwContentFactory;
import com.stylefeng.guns.common.persistence.dao.MemberVipMapper;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.common.persistence.model.MemberVip;
import com.stylefeng.guns.core.base.enums.GenderType;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberWarpper extends BaseControllerWarpper {

	public MemberWarpper(Object list) {
	        super(list);
	 }
	 
	@Override
	protected void warpTheMap(Map<String, Object> map) {
		String gender = (String) map.get("gender");
		map.put("genderName", GenderType.valueStrOf(gender));
		Integer userId = (Integer)map.get("id");
		Account account = ZwwContentFactory.me().getAccountById(userId);
		if (account == null) {
			map.put("Acoins", 0);
			//map.put("superTicket", 0);
			map.put("tester",0);
		} else {
			Integer acoins = account.getCoins() == null ? 0 : account.getCoins();
			Integer superTicket = account.getSuperTicket() == null ? 0 : account.getSuperTicket();
			map.put("Acoins", acoins);
		//	map.put("superTicket", superTicket);
			map.put("tester",account.getTester());
		}
		Long checkIn = (Long) map.get("checkIn");
		if(StringUtils.isEmpty(checkIn)){
			map.put("checkIn",0);
		}

		//vip等级划分
		MemberVip memberVip = ZwwContentFactory.me().getVipByMemberId(userId);
		if(memberVip == null){
			map.put("vipGroup","非vip");
		} else {
			map.put("vipGroup",memberVip.getName());
		}
		Integer agentSuperId = (Integer) map.get("agentSuperId");
		Integer agentOneId = (Integer) map.get("agentOneId");
		Integer agentTwoId = (Integer) map.get("agentTwoId");
		Integer agentThreeId = (Integer) map.get("agentThreeId");
		if(agentThreeId != 0){
			map.put("agentName", ZwwContentFactory.me().selectTAgentById(agentThreeId).getNickName());
		}else{
            if(agentTwoId != 0){
				map.put("agentName", ZwwContentFactory.me().selectTAgentById(agentTwoId).getNickName());
			}else{
            	if(agentOneId != 0){
					map.put("agentName", ZwwContentFactory.me().selectTAgentById(agentOneId).getNickName());
				}else{
            		if(agentSuperId != 0){
						map.put("agentName", ZwwContentFactory.me().selectTAgentById(agentSuperId).getNickName());
					}else{
						map.put("agentName","无");
					}
				}
			}
		}
	}

}
