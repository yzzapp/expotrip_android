/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author liuliang
 *
 */
public class RecommendActivity extends BaseActivity 
{
	String strServerAddress = "42.120.7.54:8080";
	String strStartDate=null;
	String strEndDate=null;
	String strSDate = "";	//机票返回值中要用此值做为关键字
	String strEDate = "";
	int numberOfDays=0;
	int go_air_price=0;
	int back_air_price=0;
	int hotel_price=0;

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
		setContentView(R.layout.activity_recommend);
		titleRightImageButtonVisibility=false;
		titleTextTextString="行程推荐";
		
		// 数据传递
		Intent intent = getIntent();
		String strConferenceName = intent.getStringExtra("title");
		String strConferenceRoom = intent.getStringExtra("hall");
		strStartDate = intent.getStringExtra("startdate");
		Log.i("--CeleryZhao--", "开始日期："+strStartDate);
		strEndDate = intent.getStringExtra("enddate");
		Log.i("--CeleryZhao--", "结束日期："+strEndDate);
		String strStartCity = intent.getStringExtra("startcity");
		String strEndCity = intent.getStringExtra("endcity");
		Log.i("--CeleryZhao--", "出发城市："+City.getCityCode(strStartCity));
		Log.i("--CeleryZhao--", "到达城市："+City.getCityCode(strEndCity));
		Log.i("--CeleryZhao--", "展馆："+strConferenceRoom);
		numberOfDays=intent.getIntExtra("days", 0);
//		/* 放开此段代码并将后面的测试代码删除即可接收传递过来的数据
//		 * 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("'R'MMdd");
		
		try
		{
			strSDate = format2.format(format1.parse(strStartDate));
			strEDate = format2.format(format1.parse(strEndDate));
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		// 出发地
		TextView tv01 = (TextView)this.findViewById(R.id.textView_recommend_start_city);
		tv01.setText(strStartCity);
		// 目的地
		TextView tv02 = (TextView)this.findViewById(R.id.textView_recommend_destination_city);
		tv02.setText(strEndCity);
		// 会议名称
		TextView tv03 = (TextView)this.findViewById(R.id.textView_recommend_confrence_name);
		tv03.setText(strConferenceName);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("date_off", strSDate));
		params.add(new BasicNameValuePair("city_src", City.getCityCode(strStartCity)));
		params.add(new BasicNameValuePair("city_dst", City.getCityCode(strEndCity)));
		params.add(new BasicNameValuePair("expo_hll", strConferenceRoom));
		params.add(new BasicNameValuePair("mntn_pwd", "pwdpwd"));
		params.add(new BasicNameValuePair("date_bck", strEDate));
//		*/
		//<-- 测试代码 start
		//
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		String strSDate = "R0527";
//		String strEDate = "R0605";
//		params.add(new BasicNameValuePair("date_off", strSDate));
//		params.add(new BasicNameValuePair("city_src", "BJS"));
//		params.add(new BasicNameValuePair("city_dst", "SHA"));
//		params.add(new BasicNameValuePair("expo_hll", "北京国家会议中心"));
//		params.add(new BasicNameValuePair("mntn_pwd", "pwdpwd"));
//		params.add(new BasicNameValuePair("date_bck", strEDate));
		//--> 测试代码 end
		
		// 酒店信息
		HttpTransmitData htaHotels = new HttpTransmitData();
		htaHotels.setUrl("http://"+ strServerAddress +"/android/hotels/");
		JSONArray jh = htaHotels.doGet_SendList_returnJSONArray(params);
		Log.i("--CeleryZhao--", "酒店信息完成");
		//Log.i("--CeleryZhao--", "酒店信息"+htaHotels.doGet_SendList_returnString(params));
		
		// 航班信息
		HttpTransmitData htaAirs = new HttpTransmitData();
		htaAirs.setUrl("http://"+ strServerAddress +"/android/airs/");
		// 请求航班数据
		String strAirsDouble = htaAirs.doGet_SendList_returnString(params);
		if (null != strAirsDouble) {
			// 获取到的数据是由“<AND>”连接的往返航班数据
			String[] saAirs = strAirsDouble.split("<AND>");
			// Log.i("--CeleryZhao--", "分割数组长度：" + saAirs.length);
			// Log.i("--CeleryZhao--", "第二个元素内容：" + saAirs[2]);
			JSONArray ja1 = htaAirs.GetJsonArrayByString(saAirs[0]);
			JSONArray ja2 = htaAirs.GetJsonArrayByString(saAirs[1]);
			Log.i("--CeleryZhao--", "航班信息完成");

			// GetHotels("R0523", "R0605", "BJS", "SHA", "北京国家会议中心");
			// GetAirs("R0523", "R0605", "BJS", "SHA", "北京国家会议中心");

			// 将请求回来的数据绑定到界面上

			try {
				// 酒店
				SetHotel(jh.getJSONObject(0));// 需要切换酒店或机票信息只需要传递不同的数据即可

				// --去程机票--
				// "CITYPAIR": "BJS_SHA",
				// String strCitys = ja1.getJSONObject(0).getString("CITYPAIR");
				// String[] saCity = strCitys.split("_");
				// "R0527":
				// "BJS_SHA_0527   BJS,SHA,2013-05-27T06:50:00,2013-05-27T09:10:00,HO1252  450,1130,0.40,50,110,10  100  Y,Q,Q  PEK,3,SHA,35   BJS,SHA,2013-05-27T07:30:00,2013-05-27T09:40:00,CA1831  790,1130,0.70,50,110,10  89.29  Y,L,L  PEK,3,SHA,35   BJS,SHA,2013-05-27T07:35:00,2013-05-27T09:45:00,MU5183  680,1130,0.60,50,110,10  93.33  Y,V,V  PEK,2,PVG,6   BJS,SHA,2013-05-27T08:35:00,2013-05-27T10:50:00,CZ3907  450,1130,0.40,50,110,10  86.67  Y,X,X  PEK,2,SHA,35   BJS,SHA,2013-05-27T21:05:00,2013-05-27T23:15:00,HU7603  570,1130,0.50,50,110,10  86.67  Y,X,X  PEK,1,SHA,35"
				String[] saAir01 = ja1.getJSONObject(0).getString(strSDate)
						.split("   ");
				// BJS,SHA,2013-05-27T07:30:00,2013-05-27T09:40:00,CA1831
				// 790,1130,0.70,50,110,10 89.29 Y,L,L PEK,3,SHA,35
				if (saAir01.length > 1) {
					SetAirGo(saAir01[1]);// 需要切换酒店或机票信息只需要传递不同的数据即可
				}else{
					
				}

				// --回程机票--
				// SHA_BJS_0605
				// SHA,BJS,2013-06-05T07:55:00,2013-06-05T10:15:00,CA1858
				// 790,1130,0.70,50,110,10 92.86 Y,L,L SHA,35,PEK,3
				// SHA,BJS,2013-06-05T12:40:00,2013-06-05T15:05:00,MU564
				// 490,1130,0.43,50,110,10 86.67 Y,X,X PVG,6,PEK,2
				// SHA,BJS,2013-06-05T14:10:00,2013-06-05T16:40:00,MU272
				// 490,1130,0.43,50,110,10 86.67 Y,X,X PVG,6,PEK,2
				String[] saAir11 = ja2.getJSONObject(0).getString(strEDate)
						.split("   ");
				if (saAir11.length > 1) {
					SetAirBak(saAir11[1]);// 需要切换酒店或机票信息只需要传递不同的数据即可
				}else{
					
				}
			} catch (Exception e) {
				Log.e("--CeleryZhao--", e.getMessage());
			}
			//设置总价
			TextView tvtmp=(TextView)findViewById(R.id.textViewtextView_recommend_total_pay);
			tvtmp.setText("￥"+String.valueOf(go_air_price+back_air_price+hotel_price));
		}else{
			Log.e("dlbn","htaAirs 访问结果是"+htaAirs.getReturnCode());
		}
	}
	
	// 设置酒店信息
	void SetHotel(JSONObject oh)
	{
		try
		{
		// 1、酒店类型
		String strCommendType = "";
		if( oh.getString("RECOMMENDTYPE").equals("big"))
		{
			strCommendType = "商务豪华";
		}
		else if(oh.getString("RECOMMENDTYPE").equals("big"))
		{
			strCommendType = "商务";
		}
		else
		{
			strCommendType = "商务经济";
		}
		TextView tv1 = (TextView)this.findViewById(R.id.textViewrecommendtype);
		tv1.setText(strCommendType);
		
		// 2、推荐酒店
		TextView tv2 = (TextView)this.findViewById(R.id.textView_recommend_hotelinfo_3);
		tv2.setText("推荐 " + oh.getString("HOTELNAME"));
		
		// 3、房型
		TextView tv3 = (TextView)this.findViewById(R.id.textView_recommend_hotelinfo_4);
		tv3.setText("房型 " + oh.getString("ROOMNAME"));
		
		// 4、酒店总价？
		/* 从入住开始日期 加到 离开日期
		 * 
		 * */
		int nTotal = oh.getInt(strSDate);
		TextView tv4 = (TextView)this.findViewById(R.id.textView_recommend_hotelinfo_5);
		tv4.setText("总价："+nTotal*numberOfDays+"￥");
		hotel_price=nTotal*numberOfDays;
		
		TextView tv5 = (TextView)this.findViewById(R.id.textView_recommend_hotelinfo_1);
		tv5.setText(""+strStartDate);
		
		TextView tv6 = (TextView)this.findViewById(R.id.textView_recommend_hotelinfo_2);
		tv6.setText(""+strEndDate);
		
		
		
		
		// 5、开始日期
		// 6、退房日期
		}
		catch(Exception e)
		{
			
		}
	}
	
	//--设置去程机票信息--
	void SetAirGo(String AirData)
	{
		String[] saAir02 = AirData.split("  ");//其中一条机票
		//PEK,3,SHA,35
		String[] saAir03 = saAir02[4].split(",");
		//BJS,SHA,2013-05-27T07:30:00,2013-05-27T09:40:00,CA1831
		String[] saAir04 = saAir02[0].split(",");
		//450,1130,0.40,50,110,10
		String[] saAir05 = saAir02[1].split(",");
		
		// 10、起飞机场
		TextView tv10 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_1);
		tv10.setText(saAir03[0]);
		Log.i("--CeleryZhao--", "起飞机场："+saAir03[0]);
		//     航战楼
		TextView tv11 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_2);
		tv11.setText("航站："+saAir03[1]);
		// 12、降落机场
		TextView tv12 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_4);
		tv12.setText(saAir03[2]);
		Log.i("--CeleryZhao--", "降落机场："+saAir03[2]);
		//		     航战楼
		TextView tv13 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_5);
		tv13.setText("航站："+saAir03[3]);
		// 14、起飞时间
		TextView tv14 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_3);
		tv14.setText("起飞:"+saAir04[2].substring(11,16));
		// 15、降落时间
		TextView tv15 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_6);
		tv15.setText("降落:"+saAir04[3].substring(11,16));
		// 航班
		TextView tv16 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_7);
		tv16.setText("航班:"+saAir04[4]);
		// 准点率
		TextView tv18 = (TextView)this.findViewById(R.id.textView_recommend_planeinfo_9);
		tv18.setText("准点率:"+saAir02[2]);
		// 原价
		TextView tv19 = (TextView)this.findViewById(R.id.textView_recommend_go_price_1);
		tv19.setText("原价:"+saAir05[1]);
		// 税价
		TextView tv20 = (TextView)this.findViewById(R.id.textView_recommend_go_price_2);
		tv20.setText("税价:"+saAir05[3]);
		// 油价
		TextView tv21 = (TextView)this.findViewById(R.id.textView_recommend_go_price_3);
		tv21.setText("油价:"+saAir05[4]);
		// 折扣
		TextView tv22 = (TextView)this.findViewById(R.id.textView_recommend_go_price_4);
		tv22.setText("折扣:"+saAir05[0]);
		// 总计
		TextView tv23 = (TextView)this.findViewById(R.id.textView_recommend_go_price_total);
		tv23.setText("总价:"+saAir05[0]+"￥");
		go_air_price=Integer.parseInt(saAir05[0]);
	}
	//--设置返程机要信息--
	//SHA,BJS,2013-06-05T07:55:00,2013-06-05T10:15:00,CA1858  790,1130,0.70,50,110,10  92.86  Y,L,L  SHA,35,PEK,3   SHA,BJS,2013-06-05T12:40:00,2013-06-05T15:05:00,MU564  490,1130,0.43,50,110,10  86.67  Y,X,X  PVG,6,PEK,2   SHA,BJS,2013-06-05T14:10:00,2013-06-05T16:40:00,MU272  490,1130,0.43,50,110,10  86.67  Y,X,X  PVG,6,PEK,2
	void SetAirBak(String AirData)
	{
		Log.i("--CeleryZhao--", "回程机票："+AirData);
		String[] saAir22 = AirData.split("  ");//其中一条机票
		//PEK,3,SHA,35
		//起飞机场，起飞航站楼，降落机场，降落航站楼
		String[] saAir23 = saAir22[4].split(",");
		//BJS,SHA,2013-05-27T07:30:00,2013-05-27T09:40:00,CA1831
		//起飞城市，降落城市，起飞年月日时分秒，落地年月日时分秒，航班号
		String[] saAir24 = saAir22[0].split(",");
		//450,1130,0.40,50,110,10
		//显示价格，原价格，折扣率，税价，油价，余票数量
		String[] saAir25 = saAir22[1].split(",");
		
		// 总价
		// debug:此处需要计算，算法未知
		TextView tv51 = (TextView)this.findViewById(R.id.textView_recommend_back_price_total);
		tv51.setText("总价:"+saAir25[0]+"￥");
		back_air_price=Integer.parseInt(saAir25[0]);
		// 原价
		TextView tv52 = (TextView)this.findViewById(R.id.textView_recommend_back_price_1);
		tv52.setText("原价:"+saAir25[1]);
		// 税价
		TextView tv53 = (TextView)this.findViewById(R.id.textView_recommend_back_price_2);
		tv53.setText("税价:"+saAir25[3]);
		// 油价
		TextView tv54 = (TextView)this.findViewById(R.id.textView_recommend_back_price_3);
		tv54.setText("油价:"+saAir25[4]);
		// 折扣
		TextView tv55 = (TextView)this.findViewById(R.id.textView_recommend_back_price_4);
		tv55.setText("折扣:"+saAir25[0]);
		// 起飞机场
		TextView tv56 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_1);
		tv56.setText(saAir23[0]);
		// 航战
		TextView tv57 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_2);
		tv57.setText("航站:"+saAir23[1]);
		// 起飞时间
		TextView tv58 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_3);
		tv58.setText("起飞:"+saAir24[2].substring(11,16));
		// 降落机场
		TextView tv59 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_4);
		tv59.setText(saAir23[2]);
		// 航战
		TextView tv60 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_5);
		tv60.setText("航站:"+saAir23[3]);
		// 降落
		TextView tv61 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_6);
		tv61.setText("降落:"+saAir24[3].substring(11,16));
		// 航班
		TextView tv62 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_7);
		tv62.setText("航班:"+saAir24[4]);
		// 准点率:
		TextView tv63 = (TextView)this.findViewById(R.id.textView_recommend_back_planeinfo_9);
		tv63.setText("准点率:"+saAir22[2]);
	}
	
	// 获取航班信息
	//StartMD	: 出发日期,R0523
	//EndMD		: 返回日期,R0605
	//StartCity : 出发城市,BJS
	//EndCity	: 到达城市,SHA
	//ExpoHll	: 会展名称,北京国家会议中心
	String GetAirs(String StartMD, String EndMD, String StartCity, String EndCity, String ExpoHll)
	{
		String strResult = new String();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		
		Log.i("--CeleryZhao--", "Init");
		String uriAPI = "http://"+ strServerAddress +"/android/airs/" + 
				"?date_off=" + StartMD +
				"&city_src=" + StartCity +
				"&city_dst=" + EndCity +
				"&expo_hll=" + ExpoHll +
				"&mntn_pwd=pwdpwd" +
				"&date_bck=" + EndMD;
		
		HttpGet httpRequest = new HttpGet(uriAPI); 
		
		try 
        { 
          /*发出HTTP request*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*若状态码为200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*取出响应字符串*/
            strResult = EntityUtils.toString(httpResponse.getEntity());
            /*删除多余字符*/
            //strResult = eregi_replace("(/r/n|/r|/n|/n/r)","",strResult);
            Log.i("--CeleryZhao--", "DataContent:"+strResult);
            
          } 
          else 
          { 
        	  Log.e("--CeleryZhao--", "Error Response: "+httpResponse.getStatusLine().toString()); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
        	Log.e("--CeleryZhao--", "Error1:"+e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (IOException e) 
        {  
        	Log.e("--CeleryZhao--", "Error2:"+e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (Exception e) 
        {
        	Log.e("--CeleryZhao--", "Error3:"+e.getMessage().toString()); 
          e.printStackTrace();  
        }
		
		Log.i("--CeleryZhao--", "exit");  
		return strResult;
	}
	
	// 获取宾馆信息
	//StartMD	: 出发日期,R0523
	//EndMD		: 返回日期,R0605
	//StartCity : 出发城市,BJS
	//EndCity	: 到达城市,SHA
	//ExpoHll	: 会展名称,北京国家会议中心
	String GetHotels(String StartMD, String EndMD, String StartCity, String EndCity, String ExpoHll)
	{
		String strResult = new String();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		
		Log.i("--CeleryZhao--", "Init");
		String uriAPI = "http://"+ strServerAddress +"/android/hotels/" + 
				"?date_off=" + StartMD +
				"&city_src=" + StartCity +
				"&city_dst=" + EndCity +
				"&expo_hll=" + ExpoHll +
				"&mntn_pwd=pwdpwd" +
				"&date_bck=" + EndMD;
		
		HttpGet httpRequest = new HttpGet(uriAPI); 
		
		try 
        { 
          /*发出HTTP request*/
          HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
          /*若状态码为200 ok*/
          if(httpResponse.getStatusLine().getStatusCode() == 200)  
          { 
            /*取出响应字符串*/
            strResult = EntityUtils.toString(httpResponse.getEntity());
            /*删除多余字符*/
            //strResult = eregi_replace("(/r/n|/r|/n|/n/r)","",strResult);
            Log.i("--CeleryZhao--", "DataContent:"+strResult);
            
          } 
          else 
          { 
        	  Log.e("--CeleryZhao--", "Error Response: "+httpResponse.getStatusLine().toString()); 
          } 
        } 
        catch (ClientProtocolException e) 
        {  
        	Log.e("--CeleryZhao--", "Error1:"+e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (IOException e) 
        {  
        	Log.e("--CeleryZhao--", "Error2:"+e.getMessage().toString()); 
          e.printStackTrace(); 
        } 
        catch (Exception e) 
        {
        	Log.e("--CeleryZhao--", "Error3:"+e.getMessage().toString()); 
          e.printStackTrace();  
        }
		
		Log.i("--CeleryZhao--", "exit");  
		return strResult;
	}

}
