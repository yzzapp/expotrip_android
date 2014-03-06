/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import android.util.Log;

public class Hall {
	private String title;
	private String hallName;
	private String date;
	private String city;

	public Hall(String title, String hallName, String city, String date){
		this.title = title;
		this.hallName = hallName;
		this.city = city;
		this.date = date;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getHallName(){
		return this.hallName;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setHallName(String hallName){
		this.hallName = hallName;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void debugPrint(){
		Log.d("Hall", "title = " + title + " hall = " + hallName + " city = " + city + " date = " + date);
	}
}
