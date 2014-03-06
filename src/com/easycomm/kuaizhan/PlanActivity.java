/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.PrivateCredentialPermission;

import android.R.string;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author liuliang
 *
 */
public class PlanActivity extends BaseActivity {
	
	EditText startDateEditText=null;
	EditText endDateEditText=null;
	Button numberofdaysButton=null;
	
	 private int StartYear=2013;
	 private int StartMonth=05;
	 private int StartDay=04;
	 
	 private int EndYear=2014;
	 private int EndMonth=05;
	 private int EndDay=04;
	 
	 
	 String conferenceNameString=null;
		String conferenceLocationString=null;
		String conferenceRoomString=null;
		String conferenceDateString=null;
		
	 Spinner startLocationSpinner=null;
	 
	

	    //准备对话框Id
	    static final int DATE_START_ID = 1;
	    static final int DATE_END_ID = 0;

	    //准备日期设置监听器
	    private DatePickerDialog.OnDateSetListener endDataListener = new DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				EndYear =year;
				EndMonth= monthOfYear+1;
				EndDay= dayOfMonth;
//				dateDisplay.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
				endDateEditText.setText(String.valueOf(EndYear)+"-"+String.valueOf(EndMonth)+"-"+String.valueOf(EndDay));
				numberofdaysButton.setText(String.valueOf(calculateNumberOfDays(EndYear, EndMonth, EndDay, StartYear, StartMonth, StartDay)));
				
				
				Log.e("dlbn", "结束日期="+String.valueOf(EndYear)+" "+String.valueOf(EndMonth));
			}
	    };
	    
	    //准备日期设置监听器
	    private DatePickerDialog.OnDateSetListener startDataListener = new DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				StartYear =year;
				StartMonth= monthOfYear+1;
				StartDay= dayOfMonth;
//				dateDisplay.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
				startDateEditText.setText(String.valueOf(StartYear)+"-"+String.valueOf(StartMonth)+"-"+String.valueOf(StartDay));
				numberofdaysButton.setText(String.valueOf(calculateNumberOfDays(EndYear, EndMonth, EndDay, StartYear, StartMonth, StartDay)));
				
				Log.e("dlbn", "开始日期="+String.valueOf(StartYear)+" "+String.valueOf(StartMonth));
			}
	    };

	    //重写创建对话框方法
	    @Override
		protected Dialog onCreateDialog(int id){
	    	switch(id){
	    	case DATE_START_ID:
	    		//把Activity上下文、日期设置监听器、以及日期的年月日都作为参数传给那个新成立的对话框组件
	    		return new DatePickerDialog(this, startDataListener, StartYear, StartMonth-1, StartDay);
	    	case DATE_END_ID:
	    		//把Activity上下文、日期设置监听器、以及日期的年月日都作为参数传给那个新成立的对话框组件
	    		return new DatePickerDialog(this, endDataListener, EndYear, EndMonth-1, EndDay);
	    	}
	    	return null;
	    }

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
		startNextActivity();
		//finish();

	}

	/* (non-Javadoc)
	 * @see com.easycomm.kuaizhan.BaseActivity#initView()
	 */
	@Override
	void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_plan);
		titleTextTextString="商旅规划";
		titleRightImageButtonImage=R.drawable.ui_exotrip_nav_recommend;
		titleLeftImageButtonImage=R.drawable.ui_exotrip_nav_back;
		 //测试数据
//		String conferenceNameString="测试会议";
//		String conferenceLocationString="北京";
//		String conferenceRoomString="农展馆";
//		String conferenceDateString="2013-05-07";
		
		//读取上一个界面发送的信息
		conferenceNameString=getIntent().getStringExtra("title");
		conferenceLocationString=getIntent().getStringExtra("city");
		conferenceRoomString=getIntent().getStringExtra("hall");
		conferenceDateString=getIntent().getStringExtra("date");
		
		//提取信息信息
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date ConferencedateDate=null;

		try {
			ConferencedateDate = format.parse(conferenceDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		StartYear=ConferencedateDate.getYear()+1900;
		StartMonth=ConferencedateDate.getMonth()+1;
		StartDay=ConferencedateDate.getDate();
		
		Log.e("dlbn", "开始日期："+StartYear+"-"+StartMonth+"-"+StartDay);
		
		EndYear=ConferencedateDate.getYear()+1900;
		EndMonth=ConferencedateDate.getMonth()+1;
		EndDay=ConferencedateDate.getDate();
		Log.e("dlbn", "开始日期："+EndYear+"-"+EndMonth+"-"+EndDay);
		
		
		
		//设置界面
		TextView tmpTextView=null;
		tmpTextView=(TextView)findViewById(R.id.textViewconferencename);
		tmpTextView.setText(conferenceNameString);
		tmpTextView=(TextView)findViewById(R.id.textViewconferencedate);
		tmpTextView.setText(conferenceDateString);
		tmpTextView=(TextView)findViewById(R.id.textViewconferencenroomname);
		tmpTextView.setText(conferenceRoomString);
		numberofdaysButton=(Button)findViewById(R.id.buttonnumberofdays);
		
		Button tmpButton=null;
		tmpButton=(Button)findViewById(R.id.buttondestination);
		tmpButton.setText(conferenceLocationString);
		
		startDateEditText=(EditText)findViewById(R.id.editTextstartdate);
		endDateEditText=(EditText)findViewById(R.id.editTextenddate);
		
		startDateEditText.setText(String.valueOf(StartYear)+"-"+String.valueOf(StartMonth)+"-"+String.valueOf(StartDay));
		endDateEditText.setText(String.valueOf(EndYear)+"-"+String.valueOf(EndMonth)+"-"+String.valueOf(EndDay));
		numberofdaysButton.setText(String.valueOf(calculateNumberOfDays(EndYear, EndMonth, EndDay, StartYear, StartMonth, StartDay)));
		//给按钮绑定点击监听器
		startDateEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_START_ID);
            }
        });
		
		endDateEditText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_END_ID);
            }
        });
		
		startLocationSpinner=(Spinner)findViewById(R.id.spinnerstart);
		
		ArrayAdapter<String>adapter;

		//将可选内容与ArrayAdapter连接起来，使用android系统提供的布局

		adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, City.getCityNames());  

		//设置下拉列表的风格   

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  

		 

		//将adapter添加到spinner中   

		startLocationSpinner.setAdapter(adapter);   

		//3．  为Spinner绑定监听器，其某项被选中时执行什么操作。

		//startLocationSpinner.getSelectedItem();

	
		
		
		
//		InputStream deckFile=null;
//		try {
//			deckFile = this.getAssets().open("city_location.txt");
//			deckFile.
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	
		
		
		
		

	}
	
	
	/*
	 * 跳转到下一个界面
	 */
	private void startNextActivity()
	{
		Intent intent = new Intent(this, RecommendActivity.class);
		intent.putExtra("title", conferenceNameString);
		intent.putExtra("hall", conferenceRoomString);
		intent.putExtra("startdate", String.valueOf(StartYear)+"-"+String.valueOf(StartMonth)+"-"+String.valueOf(StartDay));
		intent.putExtra("enddate", String.valueOf(EndYear)+"-"+String.valueOf(EndMonth)+"-"+String.valueOf(EndDay));
		
		intent.putExtra("startcity", conferenceLocationString);
		intent.putExtra("days", calculateNumberOfDays(EndYear, EndMonth, EndDay, StartYear, StartMonth, StartDay));
		intent.putExtra("endcity", (String)startLocationSpinner.getSelectedItem());
		
		startActivity(intent);
	}
	
	private int calculateNumberOfDays(int endyear,int endmonth,int endday,int startyear,int startmonth,int startday)
	{
		
		Calendar c1 = Calendar.getInstance();
		c1.set(startyear, startmonth, startday);
				
		//Date dateCreatedStop = new java.sql.Date(new java.util.Date().getTime());  
				
		Calendar c2 = Calendar.getInstance();
		c2.set(endyear, endmonth, endday);
				
		long l1 = c1.getTimeInMillis();
		long l2 = c2.getTimeInMillis();
		// 计算天数
		long days = (l2+10000 - l1) / (24 * 60 * 60 * 1000);
		
		return (int)days;
		
	}

}
