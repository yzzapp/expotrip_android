/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import android.content.Intent;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleAdapter;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.easycomm.kuaizhan.DBInterface.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuliang
 *
 */
public class ConferenceListActivity extends BaseActivity implements OnConferenceSelectedListener{
	ListView conferenceList=null;
	OnConferenceSelectedListener callback;
	ArrayList<HashMap<String, String>> mylist;
	String city;
	


	/* (non-Javadoc)
	 * @see com.easycomm.kuaizhan.BaseActivity#onTitleLeftImageButtonClick()
	 */
	@Override
	void onTitleLeftImageButtonClick() {
		// TODO Auto-generated method stub
		finish();

	}

	/* (non-Javadoc)
	 * @see com.easycomm.kuaizhan.BaseActivity#onTitleRightImageButtonClick()
	 */
	@Override
	void onTitleRightImageButtonClick() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.easycomm.kuaizhan.BaseActivity#initView()
	 */
	@Override
	void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_conference_list);
		
		callback = this;
		titleRightImageButtonVisibility=false;
		
		conferenceList = (ListView) findViewById(R.id.listView_conference_list);
		this.city=this.getIntent().getStringExtra("city");
		titleTextTextString=city;
		
 		DBHelper dbHelper = DBInterface.getInstance(this).getDBHelper();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Hall> hallList = dbHelper.getHallByCity(db, this.city);
		int hallCount = hallList.size();
		Log.d("zhangnan", "initView hallCount = " + hallCount);
		
		//生成动态数组，并且转载数据  
	    mylist = new ArrayList<HashMap<String, String>>();  
	    for(int i=0;i<hallCount;i++)  
	    {  
	        HashMap<String, String> map = new HashMap<String, String>();
	        if(DATE_PAST == isRecent(hallList.get(i).getDate())){
	        	Log.d("zhangnan", "initView continue");
	        	continue;
	        }else if(DATE_RECENT == isRecent(hallList.get(i).getDate())){
	        	map.put("textView_recent_show", "近期");
	        }else{
	        	map.put("textView_recent_show", "预告");
	        }
	        
	        map.put("ItemTitle", "This is Title.....");  
	        map.put("ItemText", "This is text.....");  
	        map.put("textView_conferenceinfo_1", hallList.get(i).getTitle());
	        map.put("textView_conferenceinfo_2", hallList.get(i).getHallName());
	        map.put("textView_conferenceinfo_3", hallList.get(i).getDate());
	        mylist.add(map);  
	    }  
	    
	    Log.d("zhangnan", "initView mylist.size() = " + mylist.size());
	    
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释  
	                                                mylist,//数据来源   
	                                                R.layout.conference_info,//ListItem的XML实现  
	                                                  
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"textView_recent_show", "ItemTitle", "ItemText", "textView_conferenceinfo_1", "textView_conferenceinfo_2", "textView_conferenceinfo_3"},   
	                                                  
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.textView_recent_show, R.id.ItemTitle, R.id.ItemText, R.id.textView_conferenceinfo_1, R.id.textView_conferenceinfo_2, R.id.textView_conferenceinfo_3});  
	    //添加并且显示  
	    conferenceList.setAdapter(mSchedule);  
	    
	    //Log.d("zhangnan", "******");
	    //isRecent("2013-05-24");
	    //isRecent("2013-05-23");
	    //isRecent("2013-05-25");
	    //isRecent("2013-06-24");
	    //isRecent("2013-06-08");
	    //Log.d("zhangnan", "******");
	    
	    conferenceList.setOnItemClickListener(new OnItemClickListener(){
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View view, int position,
					long id){
	    		Log.d("zhangnan", "onItemClick position=" + position);
	    		callback.onConferenceSelected(mylist.get(position));
	    	}
	    });
	}
	
	private final static int DATE_PAST = -1;
	private final static int DATE_RECENT = 1;
	private final static int DATE_ADVANCE = 2;
	
	public int isRecent(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long betweenTime = -1;
		Date today = Calendar.getInstance().getTime();
		try {
			Date target_day = sdf.parse(date);
			betweenTime = target_day.getTime() - today.getTime();
			betweenTime  = betweenTime  / 1000 / 60 / 60 / 24;
			//Log.d("zhangnan", "between " + sdf.format(today) + " and " + sdf.format(target_day) + " is " + (int)betweenTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(betweenTime < 0){
			return DATE_PAST;
		}else if(betweenTime < 45){
			return DATE_RECENT;
		}else{
			return DATE_ADVANCE;
		}
	}

	@Override
	public void onConferenceSelected(HashMap<String, String> conference) {
		// TODO Auto-generated method stub
		Log.d("zhangnan", "enter onConferenceSelected.");
		if(mylist != null){
			Log.d("zhangnan", "mylist != null");
			Log.d("zhangnan", "title = " + conference.get("textView_conferenceinfo_1"));
			Log.d("zhangnan", "hall = " + conference.get("textView_conferenceinfo_2"));
			Log.d("zhangnan", "date = " + conference.get("textView_conferenceinfo_3"));
			Log.d("zhangnan", "city = " + this.city);
			Intent intent = new Intent(this, PlanActivity.class);
			intent.putExtra("title", conference.get("textView_conferenceinfo_1"));
			intent.putExtra("hall", conference.get("textView_conferenceinfo_2"));
			intent.putExtra("date", conference.get("textView_conferenceinfo_3"));
			intent.putExtra("city", this.city);
			startActivity(intent);
		}
	}
}

interface OnConferenceSelectedListener {
	public void onConferenceSelected(HashMap<String, String> conference);
}