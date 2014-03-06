/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author liuliang
 *
 */
public abstract class BaseActivity extends Activity {
	ImageButton titleLeftImageButton=null;
	ImageButton titleRightImageButton=null;
	TextView titleTextTextView=null;
	public String titleTextTextString="哈哈";
	public int titleLeftImageButtonImage=0;
	public int titleRightImageButtonImage=0;
	public boolean titleLeftImageButtonVisibility=true;
	public boolean titleRightImageButtonVisibility=true;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initTitle();
		
	}
	
	
	private void initTitle() {
		// TODO Auto-generated method stub
		titleLeftImageButton=(ImageButton)findViewById(R.id.imageButton_title_leftbutton);
		titleRightImageButton=(ImageButton)findViewById(R.id.imageButton_title_rightbutton);
		titleTextTextView=(TextView)findViewById(R.id.text_title_text);
		
		titleTextTextView.setText(titleTextTextString);
		if (titleLeftImageButtonImage!=0)titleLeftImageButton.setImageResource(titleLeftImageButtonImage);
		if (titleRightImageButtonImage!=0)titleRightImageButton.setImageResource(titleRightImageButtonImage);
		if (titleLeftImageButtonVisibility==false)titleLeftImageButton.setVisibility(ImageButton.INVISIBLE);
		if (titleRightImageButtonVisibility==false)titleRightImageButton.setVisibility(ImageButton.INVISIBLE);
		
		titleLeftImageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTitleLeftImageButtonClick();
				
			}
		});
		
		titleRightImageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onTitleRightImageButtonClick();
				
			}
		});
	}

	/**
	 * 定义title按钮点击之后的动作
	 */
	abstract void onTitleLeftImageButtonClick();
	/**
	 * 定义title按钮点击之后的动作
	 */
	abstract void onTitleRightImageButtonClick();
	


	/**
	 * 用于初始化显示的内容
	 */
	abstract void initView();



	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
