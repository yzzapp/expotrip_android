/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
