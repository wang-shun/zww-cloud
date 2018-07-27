package com.stylefeng.guns.modular.backend.warpper;


import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.factory.ZwwContentFactory;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.DateUtil;

import java.util.Date;
import java.util.Map;


public class TDollInfoWarpper extends BaseControllerWarpper {

	 public TDollInfoWarpper(Object list) {
	        super(list);
	 }
	 
	@Override
	protected void warpTheMap(Map<String, Object> map) {
          Integer modifyer = (Integer)map.get("modifyer");
		  User u = ZwwContentFactory.me().selectUserById(modifyer);
		  if(u == null){
			  map.put("modifyName","-");
		  }else{
			  map.put("modifyName",u.getName());
		  }
		Date date = (Date)map.get("stockDate");
		String dataStr = DateUtil.formatDate(date,"yyyy-MM-dd");
		map.put("stockDateStr",dataStr);

	}

}
