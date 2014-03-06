/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

/* example

		City.getCityCode("南昌"));
				
		City.getCityNames();

 */

public class City {
	private String cityName;
	private String cityCode;
	
	static HashMap<String, Object> cityMap = null;

	private City(String cityName, String cityCode){
		this.cityName = cityName;
		this.cityCode = cityCode;
	}
	
	static private void initCityMap(){
		if(cityMap != null){
			return;
		}
		
		cityMap = new HashMap<String, Object>();
		cityMap.put("北京","BJS");             
		cityMap.put("上海","SHA");             
		cityMap.put("广州","CAN");             
		cityMap.put("深圳","SZX");             
		cityMap.put("成都","CTU");             
		cityMap.put("哈尔滨","HRB");             
		cityMap.put("长春","CGQ");             
		cityMap.put("沈阳","SHE");             
		cityMap.put("大连","DLC");             
		cityMap.put("天津","TSN");             
		cityMap.put("青岛","TAO");             
		cityMap.put("厦门","XMN");             
		cityMap.put("福州","FOC");             
		cityMap.put("杭州","HGH");             
		cityMap.put("苏州","SZV");             
		cityMap.put("宁波","NGB");             
		cityMap.put("南京","NKG");             
		cityMap.put("南昌","KHN");             
		cityMap.put("长沙","CSX");             
		cityMap.put("贵阳","KWE");             
		cityMap.put("太原","TYN");             
		cityMap.put("武汉","WUH");             
		cityMap.put("郑州","CGO");             
		cityMap.put("石家庄","SJW");             
		cityMap.put("西安","SIA");             
		cityMap.put("重庆","CKG");             
		cityMap.put("兰州","LHW");             
		cityMap.put("西宁","XNN");             
		cityMap.put("银川","INC");             
		cityMap.put("乌鲁木齐","URC");             
		cityMap.put("拉萨","LXA");             
		cityMap.put("昆明","KMG");             
		cityMap.put("南宁","NNG");             
		cityMap.put("珠海","ZUH");             
		cityMap.put("三亚","SYX");             
		cityMap.put("海口","HAK");             
		cityMap.put("香港","HKG");             
		cityMap.put("澳门","MFM");             
		cityMap.put("台北","TPE");             
		cityMap.put("合肥","HFE");             
		
	}
	
	static String getCityCode(String cityName){
		if(cityMap == null){
			initCityMap();
		}
		
		return (String)cityMap.get(cityName);
	}
	
	static List getCityNames(){
		if(cityMap == null){
			initCityMap();
		}		
		ArrayList<String> ret = new ArrayList<String>();
		for(String key:cityMap.keySet()){
			ret.add(key);
		}
		return ret;
	}
}
