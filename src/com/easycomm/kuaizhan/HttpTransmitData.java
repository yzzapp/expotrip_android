/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;


import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.StrictMode;
import android.util.Log;


/**
 * 网络传输数据类
 * @author liuliang
 * 可以通过post，get传输数据到服务器，返回string或者json。
 * 错误时直接返回null
 * 详细原因可以用getReturnCode()检查。
 * getReturnCode返回
 * 类自定义错误，或者网络返回错误码
 *
 * 访问Intent网络 记得注册事件
 * <uses-permission android:name="android.permission.INTERNET" />
 * 
 */

public class HttpTransmitData {
	private String url = null;
	private int returnCode=0;
	
	//错误标志位
	public int FlagNetworkError=-1;
	public int FlagJsonOprError=-2;
	public int FlagUnKnownError=-3;
	public int FlagUrlError=-4;
	
	public void setUrl(String url) {
		this.url = url;
	}
	

	
	
	/**
	 * @return the returnCode
	 * 如果发现返回null，可以来查看返回代码
	 * 正常情况下是200；
	 */
	public int getReturnCode() {
		return returnCode;
	}




	/**
	 * @param params
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("name","张三"));
	 * @return
	 * return null if error; 
	 */
	public JSONObject doPost_SendList_returnJsonObject(List<NameValuePair> params)
	{
		String returnString=null;
		returnString=doPost_SendList_returnString(params);
		if (null==returnString)return null;
			try {
				JSONObject responseJson = new JSONObject(returnString);
				return responseJson;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returnCode=FlagJsonOprError;
		return null;
	}
	
	/**
	 * @param params
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("name","张三"));
	 * @return
	 * return null if error; 
	 */
	public JSONObject doGet_SendList_returnJsonObject(List<NameValuePair> params)
	{
		String returnString=null;
		returnString=doGet_SendList_returnString(params);
		if (null==returnString)return null;
			try 
			{
				JSONObject responseJson = new JSONObject(returnString);
				return responseJson;
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returnCode=FlagJsonOprError;
		return null;
	}
	
	public JSONArray doGet_SendList_returnJSONArray(List<NameValuePair> params)
	{
		//Log.i("--CeleryZhao--", "Json Init");
		String returnString=null;
		returnString=doGet_SendList_returnString(params);
		//Log.i("--CeleryZhao--", "Json 获取数据:"+returnString);
		if (null==returnString)return null;
			try 
			{
				JSONArray ja = new JSONArray(returnString);
				//Log.i("--CeleryZhao--", "Json Array:"+ja);
				return ja;
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returnCode=FlagJsonOprError;
		return null;
	}
	
	public JSONArray  GetJsonArrayByString(String JsonData)
	{	
			try 
			{
				JSONArray ja = new JSONArray(JsonData);
				//Log.i("--CeleryZhao--", "JsonArray String:"+ja);
				return ja;
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returnCode=FlagJsonOprError;
		return null;
	}
	
	/**
	 * @param params
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("name","张三"));
	 * @return 
	 * 	return null if error; 
	 */
	public String doPost_SendList_returnString(List<NameValuePair> params) {
		HttpClient httpclient = new DefaultHttpClient();
		returnCode=FlagUnKnownError;
		// 创建HttpPost对象
		if (null==url){
			returnCode=FlagUrlError;
			return null;
		}
		HttpPost httppost = new HttpPost(url);
		try {
			// 设置httpPost请求参数
			httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 连接超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			// 读取超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 5000);
			// 使用execute方法发送HTTP Post请求，并返回HttpResponse对象
			HttpResponse httpResponse = httpclient.execute(httppost);
			returnCode = httpResponse.getStatusLine().getStatusCode();
			if (returnCode == HttpStatus.SC_OK) {
				// 获得返回结果
				String responseString = new String(EntityUtils.toString(httpResponse.getEntity()));
				return responseString;
			} else {
				//returnCode直接等于网络的错误码
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//默认返回未知错误
		return null;
	}
	
	
	/**
	 * @param params
	 * List<NameValuePair> params = new ArrayList<NameValuePair>();
	 * params.add(new BasicNameValuePair("name","张三"));
	 * @return 
	 * 	return null if error; 
	 */
	public String doGet_SendList_returnString(List<NameValuePair> params) 
	{
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		HttpClient httpclient = new DefaultHttpClient();
		
		returnCode=FlagUnKnownError;
		// 创建HttpPost对象
		if (null==url){
			returnCode=FlagUrlError;
			return null;
		}
		try {
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			// 读取超时
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 5000);
			String urltoserver=url+"?";
			for (int i=0;i<params.size();i++)
			{
				urltoserver=urltoserver+ URLEncoder.encode(params.get(i).getName())+"="+ URLEncoder.encode(params.get(i).getValue())+"&";
			}
//			Log.i("autoall","urltoserver="+urltoserver);
		    HttpGet httpget=new HttpGet(urltoserver);  
			// 使用execute方法发送HTTP Post请求，并返回HttpResponse对象
			HttpResponse httpResponse = httpclient.execute(httpget);
			returnCode = httpResponse.getStatusLine().getStatusCode();
			Log.i("autoall","returnCode="+returnCode);
			if (returnCode == HttpStatus.SC_OK) {
				// 获得返回结果
				String responseString = new String(EntityUtils.toString(httpResponse.getEntity()));
				return responseString;
			} else {
				//returnCode等于网络错误返回码
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}


