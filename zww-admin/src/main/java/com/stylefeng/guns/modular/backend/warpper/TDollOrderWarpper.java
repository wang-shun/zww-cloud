package com.stylefeng.guns.modular.backend.warpper;


import com.stylefeng.guns.common.constant.factory.ZwwContentFactory;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.Map;


public class TDollOrderWarpper extends BaseControllerWarpper {

	 public TDollOrderWarpper(Object list) {
	        super(list);
	 }
	 
	@Override
	protected void warpTheMap(Map<String, Object> map) {
              Integer memberId = (Integer) map.get("memberId");
              Account account = ZwwContentFactory.me().getAccountById(memberId);
              if(account.getTester() == 0){
				  map.put("testerName", "普通用户");
			  }else{
				  map.put("testerName", "测试人员");
			  }

	}

}
