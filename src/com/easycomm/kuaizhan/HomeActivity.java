/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;


import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.easycomm.kuaizhan.DBInterface.DBHelper;

/**
 * @author liuliang
 * 
 */
public class HomeActivity extends BaseActivity {

	RelativeLayout beijingRelativeLayout, shanghaiRelativeLayout,
			chengduRelativeLayout,shenzhenRelativeLayout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easycomm.kuaizhan.BaseActivity#onTitleLeftImageButtonClick()
	 */
	@Override
	void onTitleLeftImageButtonClick() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easycomm.kuaizhan.BaseActivity#onTitleRightImageButtonClick()
	 */
	@Override
	void onTitleRightImageButtonClick() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easycomm.kuaizhan.BaseActivity#initView()
	 */
	@Override
	void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_home);
		//get db
 		DBHelper dbHelper = DBInterface.getInstance(this).getDBHelper();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Button lsButton=null;
		titleTextTextString="快展Pro";
		titleLeftImageButtonImage=R.drawable.ui_homepage_nav_about;
		titleRightImageButtonVisibility=false;
		
		//get record count
		dbHelper.getHallCountByCity(db, "北京");
		dbHelper.getHallCountByCity(db, "成都");
		dbHelper.getHallCountByCity(db, "上海");
		dbHelper.getHallCountByCity(db, "广州");
		dbHelper.getHallCountByCity(db, "深圳");
		
		
		lsButton=(Button)findViewById(R.id.button_beijing_conf_number);
		lsButton.setText(String.valueOf(dbHelper.getHallCountByCity(db, "北京")));
		beijingRelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_beijing);
		beijingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity_city("北京");
			}
		});
		
		lsButton=(Button)findViewById(R.id.button_guangzhou_conf_number);
		lsButton.setText(String.valueOf(dbHelper.getHallCountByCity(db, "广州")));
		beijingRelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_guangzhou);
		beijingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity_city("广州");
			}
		});
		
		lsButton=(Button)findViewById(R.id.button_shanghai_conf_number);
		lsButton.setText(String.valueOf(dbHelper.getHallCountByCity(db, "上海")));
		beijingRelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_shanghai);
		beijingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity_city("上海");
			}
		});
		
		lsButton=(Button)findViewById(R.id.button_shenzhen_conf_number);
		lsButton.setText(String.valueOf(dbHelper.getHallCountByCity(db, "深圳")));
		beijingRelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_shenzhen);
		beijingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity_city("深圳");
			}
		});
		
		lsButton=(Button)findViewById(R.id.button_chengdu_conf_number);
		lsButton.setText(String.valueOf(dbHelper.getHallCountByCity(db, "成都")));
		beijingRelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_chengdu);
		beijingRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity_city("成都");
			}
		});
		
		//Log.d("zhangnan", "北京:" + City.getCityCode("北京"));
		//Log.d("zhangnan", "大连:" + City.getCityCode("大连"));
		//Log.d("zhangnan", "南昌:" + City.getCityCode("南昌"));
				
		List cityNames = City.getCityNames();
		int count = cityNames.size();
		for(int i=0;i<count;i++){
			//Log.d("zhangnan", cityNames.get(i).toString());
		}

	}
	
	private void startActivity_city(String city)
	{
		Intent i = new Intent(this,ConferenceListActivity.class);
		//传递手机号码
		i.putExtra("city", city);
		//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}

}
