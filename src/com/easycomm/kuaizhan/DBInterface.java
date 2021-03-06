/*******************************************************************************
 *    
 *    http://www.easycomm.cn/
 *    
 *    Copyright (C) 2013-2014 北京和易讯科技有限公司。 版权所有。
 *    
 ******************************************************************************/
package com.easycomm.kuaizhan;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* example (use DBInterface in Activity)
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.easycomm.kuaizhan.DBInterface.DBHelper;

		//get db
 		DBHelper dbHelper = DBInterface.getInstance(this).getDBHelper();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		//get record count
		dbHelper.getHallCountByCity(db, "北京");
		dbHelper.getHallCountByCity(db, "成都");
		dbHelper.getHallCountByCity(db, "上海");
		dbHelper.getHallCountByCity(db, "广州");
		dbHelper.getHallCountByCity(db, "深圳");

		//get list of Hall
		dbHelper.getHallByCity(db, "北京");
		dbHelper.getHallByCity(db, "成都");
		dbHelper.getHallByCity(db, "上海");
		dbHelper.getHallByCity(db, "广州");
		dbHelper.getHallByCity(db, "深圳");
 */

public class DBInterface {
	private static DBHelper dbhelper = null;
	private static DBInterface dbinterface = null;
	private static final Object lockObject = new Object();
	
	public static final String LOG_TAG = "DBInterface";
	
	public static final class E13CNTable{
		public E13CNTable(){}
		public static final String TABLE_NAME = "E13CN";
		public static final String COLUMN_NAME_TITLE = "TITLE";
		public static final String COLUMN_NAME_CATEGORY = "CATEGORY";
		public static final String COLUMN_NAME_CITY = "CITY";
		public static final String COLUMN_NAME_HALL = "HALL";
		public static final String COLUMN_NAME_DATE = "DATE";
		public static final String COLUMN_NAME_URL = "URL";
		
		public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "(" +
				COLUMN_NAME_TITLE + " TEXT, " +
				COLUMN_NAME_CATEGORY + " TEXT, " +
				COLUMN_NAME_CITY + " TEXT, " +
				COLUMN_NAME_HALL + " TEXT, " +
				COLUMN_NAME_DATE + " TEXT, " +
				COLUMN_NAME_URL + " TEXT" +
		");";
	} 
	
	public static DBInterface getInstance(Context context) {
		synchronized (lockObject) {
			if (dbinterface == null) {
				//Log.d(LOG_TAG, "DBHelper.getInstance");
				dbinterface = new DBInterface(context.getApplicationContext());
			}
		}
		
		return dbinterface;
	}

	public DBHelper getDBHelper() {
		synchronized(this) {
			if (dbhelper == null) {
				dbhelper = new DBHelper(this.context);
			}
		}
		
		return dbhelper;
	}
	
	public Context context;
	
	private DBInterface(Context context) {
		this.context = context;
	}	

	public class DatabaseUninitialisedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public DatabaseUninitialisedException(String s) {
			super(s);
		}
	}	
	
	public class DBHelper extends SQLiteOpenHelper {
		public static final String DATABASE_NAME = "kuaizhan.db";
		public static final int DATABASE_VERSION = 1;		

		private DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			//Log.d(LOG_TAG, "DBHelper.onCreate");
			createTables(db, true);
			loadDefaultData(db);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//Log.d(LOG_TAG, "DBHelper.onUpgrade");
		}
		
		public void createTables(SQLiteDatabase db, boolean includeLogTable) {
			//Log.d(LOG_TAG, "DBHelper.createTables");
			db.execSQL(E13CNTable.CREATE_SQL);
		}
		
		public void loadDefaultData(SQLiteDatabase db) {
			//Log.d(LOG_TAG, "DBHelper.loadDefaultData");

			db.execSQL("INSERT INTO E13CN VALUES('北京农展馆年货大集','购物年货','北京','北京全国农业展览馆','2013-01-31','/zhanhui/html/60235_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('黑龙江绿色食品2013（北京）年货大集','购物年货','北京','北京全国农业展览馆','2013-01-01','/zhanhui/html/60232_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第39届中国国际裘皮革皮制品交易会','皮革鞋业','北京','北京国家会议中心','2013-01-31','/zhanhui/html/60042_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届亚洲运动用品与时尚展','体育休闲','北京','北京国家会议中心','2013-02-01','/zhanhui/html/62088_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国(北京)国际绿色水产食品展览会','食品饮料','北京','北京国际会议中心','2013-02-28','/zhanhui/html/62682_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十三届中国国际钓鱼用品贸易展览会','农林牧渔','北京','中国国际展览中心(新馆)','2013-02-01','/zhanhui/html/53501_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际模型博览会','礼品玩具','北京','北京展览馆','2013-04-01','/zhanhui/html/60397_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国特许加盟展览会','创业加盟','北京','北京国家会议中心','2013-04-30','/zhanhui/html/58090_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际五金工具展览会','建材五金','北京','中国国际展览中心','2013-04-01','/zhanhui/html/58609_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际照明展览会','光电技术','北京','中国国际展览中心','2013-04-30','/zhanhui/html/54378_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际水泥技术及装备展览会中','建材五金','北京','北京展览馆','2013-04-25','/zhanhui/html/61962_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际纤体美容展','美容美发','北京','北京国家会议中心','2013-04-25','/zhanhui/html/56996_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际机床展览会','机械工业','北京','中国国际展览中心(新馆)','2013-04-25','/zhanhui/html/56735_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届中国国际照相机械影像器材与技术博','消费电子','北京','北京国家会议中心','2013-04-25','/zhanhui/html/60692_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国国际茶业及茶艺博览会','食品饮料','北京','北京全国农业展览馆','2013-04-24','/zhanhui/html/58014_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届中国国际石材产品及石材技术装备展览会','建材五金','北京','中国国际展览中心','2013-04-23','/zhanhui/html/58185_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国（北京）国际工业防腐蚀设备展览','工业制造','北京','中国国际展览中心','2013-04-22','/zhanhui/html/57340_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十二届北京国际美容化妆品博览会','美容美发','北京','中国国际展览中心','2013-04-19','/zhanhui/html/58265_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际酒业技术装备博览会','食品饮料','北京','中国国际展览中心','2013-04-19','/zhanhui/html/61192_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京春季珠宝展','奢侈品','北京','北京全国农业展览馆','2013-04-18','/zhanhui/html/61765_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届北京国际珠宝首饰展览会','孕婴童','北京','中国国际展览中心(新馆)','2013-04-18','/zhanhui/html/55037_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际视听集成设备与技术展览会','房产家居','北京','北京展览馆','2013-04-18','/zhanhui/html/60396_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国出境旅游交易会','音响乐器','北京','北京国家会议中心','2013-04-17','/zhanhui/html/60468_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际供热通风空调、卫生洁具及城建','旅游行业','北京','北京全国农业展览馆','2013-04-13','/zhanhui/html/56997_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际云计算技术和应用展览会','暖通制冷','北京','中国国际展览中心(新馆)','2013-04-12','/zhanhui/html/55429_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届国际高端食用油及橄榄油(北京)博览会','信息通信','北京','中国国际展览中心','2013-04-11','/zhanhui/html/60023_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届北京营养健康产业博览会','食品饮料','北京','中国国际展览中心','2013-04-10','/zhanhui/html/57771_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届中国国际康复护理产品及老年用品辅具','医疗保健','北京','中国国际展览中心','2013-04-09','/zhanhui/html/57126_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第19届北京国际连锁加盟展览会','食品饮料','北京','中国国际展览中心','2013-04-07','/zhanhui/html/54292_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第31届中国国际体育用品博览会','体育休闲','北京','中国国际展览中心(新馆)','2013-05-01','/zhanhui/html/60433_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际餐饮博览会','食品饮料','北京','中国国际展览中心','2013-05-31','/zhanhui/html/60445_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国国际软件博览会','信息通信','北京','北京展览馆','2013-05-01','/zhanhui/html/60401_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国国际现代农业博览会','农林牧渔','北京','中国国际展览中心','2013-05-31','/zhanhui/html/61958_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国（北京）国际服务贸易交易会','贸易进出口','北京','北京国家会议中心','2013-05-31','/zhanhui/html/57322_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第24届中国国际玻璃工业技术展览会','建材五金','北京','中国国际展览中心(新馆)','2013-05-31','/zhanhui/html/58866_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国华夏家博会','房产家居','北京','北京展览馆','2013-05-30','/zhanhui/html/60472_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际道路运输、城市公交车辆及零部件展','运输物流','北京','北京国家会议中心','2013-05-29','/zhanhui/html/58092_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届中国北京国际科技产业博览会','电子电力','北京','中国国际展览中心','2013-05-28','/zhanhui/html/62720_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际科学仪器及实验室装备展览','仪器仪表','北京','中国国际展览中心','2013-05-24','/zhanhui/html/59422_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届北京酒店用品展览会','酒店用品','北京','中国国际展览中心','2013-05-22','/zhanhui/html/61455_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国(北京)国际警用装备及反恐技术装备展','安全防护','北京','北京展览馆','2013-05-21','/zhanhui/html/60399_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届北京国际印刷技术展览会','印刷包装','北京','中国国际展览中心(新馆)','2013-05-21','/zhanhui/html/62012_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第15届北京国际玩具及幼教用品展览会','孕婴童','北京','北京全国农业展览馆','2013-05-15','/zhanhui/html/53504_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届国际化学工程和生物技术展览暨会议','化工橡塑','北京','北京国家会议中心','2013-05-15','/zhanhui/html/60707_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际彩钢、冷弯型钢技术及设备博览会','钢铁冶金','北京','中国国际展览中心','2013-05-15','/zhanhui/html/58586_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际平板显示产业展览会','消费电子','北京','中国国际展览中心','2013-05-14','/zhanhui/html/54048_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际软件及系统管理展览会','信息通信','北京','中国国际展览中心','2013-05-13','/zhanhui/html/58341_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国国际建筑保温技术及产品展览会','建材五金','北京','中国国际展览中心','2013-05-13','/zhanhui/html/60569_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国国际物流博览会','运输物流','北京','中国国际展览中心','2013-05-09','/zhanhui/html/61957_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届亚洲（北京）国际信息网络及技术设备','信息通信','北京','中国国际展览中心','2013-05-09','/zhanhui/html/57712_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国国际新型墙体材料技术装备及产品','建材五金','北京','中国国际展览中心','2013-05-09','/zhanhui/html/60572_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届亚洲（北京）国际物联网展览会','电子电力','北京','中国国际展览中心','2013-05-09','/zhanhui/html/57708_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际触摸屏展览会','光电技术','北京','中国国际展览中心','2013-05-09','/zhanhui/html/56736_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际天然气汽车、加气站设备展','能源矿产','北京','北京全国农业展览馆','2013-05-09','/zhanhui/html/60605_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届国际消防设备技术交流展览会','安全防护','北京','北京国家会议中心','2013-05-09','/zhanhui/html/60703_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际养老服务业博览会','医疗保健','北京','北京国家会议中心','2013-05-08','/zhanhui/html/60700_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京农展馆年货大集','购物年货','北京','北京全国农业展览馆','2013-01-31','/zhanhui/html/60235_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第39届中国国际裘皮革皮制品交易会','皮革鞋业','北京','北京国家会议中心','2013-01-31','/zhanhui/html/60042_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京图书订货会','文化教育','北京','中国国际展览中心','2013-01-26','/zhanhui/html/58344_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届国际电磁兼容与微波技术交流展览会暨2','化工橡塑','北京','北京国际会议中心','2013-07-01','/zhanhui/html/60778_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际大屏幕系统集成设备展览会','消费电子','北京','北京展览馆','2013-07-31','/zhanhui/html/60395_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际妇女儿童产业博览会','孕婴童','北京','北京国家会议中心','2013-07-01','/zhanhui/html/57647_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际环保展览会','环保水处理','北京','中国国际展览中心','2013-07-31','/zhanhui/html/58408_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届北京国际珠宝展览会','奢侈品','北京','北京国家会议中心','2013-07-30','/zhanhui/html/58093_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('司法监狱防范技术设备展览会','安全防护','北京','中国国际展览中心','2013-07-26','/zhanhui/html/61464_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('首届中国中小企业投融资交易会','创业加盟','北京','北京国家会议中心','2013-07-25','/zhanhui/html/62098_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际轻工消费品展览会','消费电子','北京','中国国际展览中心','2013-07-23','/zhanhui/html/62765_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际汽车制造技术与关键零部件展','汽摩配件','北京','北京国家会议中心','2013-07-19','/zhanhui/html/60713_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届北京国际纯电动车、混合动力车暨新能','交通工具','北京','中国国际展览中心','2013-07-18','/zhanhui/html/61090_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国(北京)国际时尚家居装饰展览会','房产家居','北京','中国国际展览中心','2013-07-13','/zhanhui/html/61089_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际清洁能源博览会','能源矿产','北京','北京国家会议中心','2013-07-08','/zhanhui/html/59455_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国国际高端瓶装饮用水交易会','食品饮料','北京','北京国际会议中心','2013-07-06','/zhanhui/html/62679_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（北京）国际光伏展览会','能源矿产','北京','北京国家会议中心','2013-07-04','/zhanhui/html/54211_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国(北京)国际名酒展览会','食品饮料','北京','北京国际会议中心','2013-07-03','/zhanhui/html/60781_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国(北京)国际绿色水产食品展览会','食品饮料','北京','北京国际会议中心','2013-07-03','/zhanhui/html/62680_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十五届北京教育装备展示会','文化教育','北京','北京展览馆','2013-03-01','/zhanhui/html/58211_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第25届国际医疗仪器设备展览会','医疗保健','北京','北京国家会议中心','2013-03-31','/zhanhui/html/60390_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届国际泵、清洗设备与水刀机床展览会','机械工业','北京','北京全国农业展览馆','2013-03-01','/zhanhui/html/52647_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际纺织面料及辅料（春夏）博览会','纺织纺机','北京','中国国际展览中心','2013-03-31','/zhanhui/html/59382_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际纺织纱线（春夏）展览会','服装配饰','北京','北京全国农业展览馆','2013-03-28','/zhanhui/html/60394_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际服装服饰博览会','服装配饰','北京','中国国际展览中心(新馆)','2013-03-28','/zhanhui/html/54306_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国高尔夫球博览会','体育休闲','北京','北京国家会议中心','2013-03-27','/zhanhui/html/60690_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国国际薯业博览会','食品饮料','北京','北京全国农业展览馆','2013-03-27','/zhanhui/html/60671_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际灌溉展览会','农林牧渔','北京','北京展览馆','2013-03-27','/zhanhui/html/57130_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际广播电视信息网络展览会','信息通信','北京','中国国际展览中心','2013-03-26','/zhanhui/html/59423_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际低碳产业博览会','环保水处理','北京','北京展览馆','2013-03-22','/zhanhui/html/60218_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际创意礼品及工艺品展览会','礼品玩具','北京','北京展览馆','2013-03-21','/zhanhui/html/60213_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（国际）游乐设施设备博览会','能源矿产','北京','中国国际展览中心(新馆)','2013-03-21','/zhanhui/html/52200_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('春季中国北京婚博会','动漫游戏','北京','北京国家会议中心','2013-03-21','/zhanhui/html/60388_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际珠宝玉石首饰展览会暨2013首届台湾','婚庆婚博','北京','北京国家会议中心','2013-03-21','/zhanhui/html/60686_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际门业展览会','房产家居','北京','北京展览馆','2013-03-17','/zhanhui/html/60211_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际设施农业暨园艺资材展览会2013中国国','礼品玩具','北京','中国国际展览中心','2013-03-16','/zhanhui/html/57678_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届北京家居.装饰.建材博览会','建材五金','北京','中国国际展览中心(新馆)','2013-03-15','/zhanhui/html/54315_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际建筑工程新技术、新工艺、新材料及','环保水处理','北京','北京国家会议中心','2013-03-15','/zhanhui/html/57321_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国墙纸行业（北京）博览会','农林牧渔','北京','北京国家会议中心','2013-03-15','/zhanhui/html/56993_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京婚博会','文化教育','北京','北京全国农业展览馆','2013-03-14','/zhanhui/html/52937_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第16届中国汽车用品暨改装汽车展览会','房产家居','北京','北京全国农业展览馆','2013-03-13','/zhanhui/html/61970_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲国际智能制造装备产业展览会','工业制造','北京','中国国际展览中心','2013-06-01','/zhanhui/html/60622_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('夏季中国北京婚博会','婚庆婚博','北京','北京国家会议中心','2013-06-30','/zhanhui/html/60687_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（北京）国际煤炭装备及矿山技术设备','能源矿产','北京','中国国际展览中心','2013-06-01','/zhanhui/html/57300_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届亚洲国际工业自动化展览会','工业制造','北京','中国国际展览中心','2013-06-30','/zhanhui/html/60606_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际煤矿安全“六大系统”暨救生','能源矿产','北京','中国国际展览中心','2013-06-28','/zhanhui/html/60415_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（北京）国际动力传动与控制技术','工业制造','北京','中国国际展览中心','2013-06-28','/zhanhui/html/60413_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际新材料工业技术展览会','工业制造','北京','北京展览馆','2013-06-28','/zhanhui/html/62106_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('工业智能、机器人及自动化展览会','机械工业','北京','北京展览馆','2013-06-28','/zhanhui/html/61966_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国国际安全生产应急救援技术与装备','安全防护','北京','北京全国农业展览馆','2013-06-28','/zhanhui/html/61871_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际超市采购暨家庭时尚用品博览会','房产家居','北京','中国国际展览中心','2013-06-28','/zhanhui/html/56926_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际窗帘布艺及家饰用品展览会','纺织纺机','北京','中国国际展览中心','2013-06-26','/zhanhui/html/59700_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国肉业博览会','酒店用品','北京','中国国际展览中心','2013-06-25','/zhanhui/html/58593_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际家用纺织品展览会','农林牧渔','北京','中国国际展览中心','2013-06-22','/zhanhui/html/62764_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际旅游博览会','体育休闲','北京','中国国际展览中心','2013-06-22','/zhanhui/html/57017_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际茶业展','房产家居','北京','中国国际展览中心','2013-06-22','/zhanhui/html/61462_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际老年健康产业博览会','旅游行业','北京','北京国家会议中心','2013-06-22','/zhanhui/html/60712_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际食品安全与创新技术展览会','食品饮料','北京','北京展览馆','2013-06-22','/zhanhui/html/60406_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国（北京）国际电池产品及原辅材料、','医疗保健','北京','中国国际展览中心','2013-06-22','/zhanhui/html/57855_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国国际智能电网建设分布式能源及储能技','食品饮料','北京','中国国际展览中心','2013-06-22','/zhanhui/html/57797_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('渠道网创业加盟博览会','电子电力','北京','中国国际展览中心','2013-06-21','/zhanhui/html/57504_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国改装汽车展览会、2013中国房车展览会','电子电力','北京','北京国家会议中心','2013-06-20','/zhanhui/html/58422_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际涂料、油墨及胶粘剂展览会暨研','创业加盟','北京','中国国际展览中心','2013-06-17','/zhanhui/html/62347_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届中国国际口腔设备材料展览会暨技术','交通工具','北京','北京国家会议中心','2013-06-17','/zhanhui/html/60711_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国北京供热节能与新能源应用产业博览会','能源矿产','北京','北京展览馆','2013-06-16','/zhanhui/html/61965_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十二届中国国际专业音响、灯光、乐器及技术','化工橡塑','北京','中国国际展览中心','2013-06-15','/zhanhui/html/56713_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国国际道路交通安全产品博览会暨交通安','安全防护','北京','中国国际展览中心','2013-08-01','/zhanhui/html/61959_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际屋面和建筑防水技术展览会','建材五金','北京','北京国家会议中心','2013-08-31','/zhanhui/html/59462_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京秋季喷印雕刻标识技术展','印刷包装','北京','北京国家会议中心','2013-08-01','/zhanhui/html/60722_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第24届中国国际测量控制与仪器仪表展览会（MIC','仪器仪表','北京','中国国际展览中心','2013-08-31','/zhanhui/html/60028_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国（北京）国际广告及LED展览会','广告媒体','北京','中国国际展览中心','2013-08-28','/zhanhui/html/62800_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国（北京）国际LED照明展览会','光电技术','北京','中国国际展览中心','2013-08-28','/zhanhui/html/59173_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际葡萄酒展览会','食品饮料','北京','北京全国农业展览馆','2013-08-28','/zhanhui/html/62729_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国国际餐饮、食品及饮品博览会','食品饮料','北京','北京全国农业展览馆','2013-08-27','/zhanhui/html/62726_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际乳业及设备博览会','食品饮料','北京','北京全国农业展览馆','2013-08-27','/zhanhui/html/62752_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际有机食品、进出口食品博览会','食品饮料','北京','北京全国农业展览馆','2013-08-27','/zhanhui/html/56634_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('数字世界亚洲博览会','信息通信','北京','北京国家会议中心','2013-08-23','/zhanhui/html/60719_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十二届中国国际医用仪器设备展览会暨技','医疗保健','北京','北京国家会议中心','2013-08-23','/zhanhui/html/60718_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届北京国际钓鱼用品消费展览会','体育休闲','北京','北京国家会议中心','2013-08-23','/zhanhui/html/60695_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十八届中国北京国际礼品、赠品及家庭用','礼品玩具','北京','中国国际展览中心','2013-08-23','/zhanhui/html/62777_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京汽车展览会','交通工具','北京','北京国家会议中心','2013-08-22','/zhanhui/html/60716_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际沉香文化博览会','文化艺术','北京','中国国际展览中心','2013-08-16','/zhanhui/html/62771_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京环球旅游展览会','旅游行业','北京','北京全国农业展览馆','2013-08-16','/zhanhui/html/61766_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('首届中国国际咖啡展','食品饮料','北京','中国国际展览中心','2013-08-14','/zhanhui/html/61197_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届北京国际中、小型电机暨微电机博览','电子电力','北京','中国国际展览中心','2013-08-09','/zhanhui/html/57702_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际电子半导体工业展览会2013北京国际','工业制造','北京','中国国际展览中心','2013-08-09','/zhanhui/html/62768_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际茶文化暨紫砂工艺展览会','食品饮料','北京','中国国际展览中心','2013-08-09','/zhanhui/html/62129_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届中国(北京)国际红木古典家具展','房产家居','北京','中国国际展览中心','2013-08-09','/zhanhui/html/61530_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际饭店业博览会暨2013第十六届','酒店用品','北京','中国国际展览中心','2013-08-08','/zhanhui/html/61091_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国北京国际家具展览会','房产家居','北京','中国国际展览中心','2013-08-08','/zhanhui/html/59171_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际清洁产业博览会','环保水处理','北京','北京国家会议中心','2013-08-02','/zhanhui/html/58094_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届亚洲运动用品与时尚展','体育休闲','北京','北京国家会议中心','2013-02-01','/zhanhui/html/62088_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国(北京)国际绿色水产食品展览会','食品饮料','北京','北京国际会议中心','2013-02-28','/zhanhui/html/62682_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际模型博览会','礼品玩具','北京','北京展览馆','2013-04-01','/zhanhui/html/60397_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国特许加盟展览会','创业加盟','北京','北京国家会议中心','2013-04-30','/zhanhui/html/58090_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际五金工具展览会','建材五金','北京','中国国际展览中心','2013-04-01','/zhanhui/html/58609_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届北京国际温泉泳池SPA展览会','体育休闲','北京','中国国际展览中心','2013-04-29','/zhanhui/html/53978_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(北京)国际热处理、工业炉展览会','工业制造','北京','中国国际展览中心','2013-04-26','/zhanhui/html/58641_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际水泥技术及装备展览会中','建材五金','北京','北京展览馆','2013-04-25','/zhanhui/html/61962_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际纤体美容展','美容美发','北京','北京国家会议中心','2013-04-25','/zhanhui/html/56996_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际机床展览会','机械工业','北京','中国国际展览中心(新馆)','2013-04-25','/zhanhui/html/56735_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届中国国际照相机械影像器材与技术博','消费电子','北京','北京国家会议中心','2013-04-25','/zhanhui/html/60692_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国国际茶业及茶艺博览会','食品饮料','北京','北京全国农业展览馆','2013-04-24','/zhanhui/html/58014_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届中国国际石材产品及石材技术装备展览会','建材五金','北京','中国国际展览中心','2013-04-23','/zhanhui/html/58185_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国（北京）国际工业防腐蚀设备展览','工业制造','北京','中国国际展览中心','2013-04-22','/zhanhui/html/57340_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十二届北京国际美容化妆品博览会','美容美发','北京','中国国际展览中心','2013-04-19','/zhanhui/html/58265_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际酒业技术装备博览会','食品饮料','北京','中国国际展览中心','2013-04-19','/zhanhui/html/61192_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京春季珠宝展','奢侈品','北京','北京全国农业展览馆','2013-04-18','/zhanhui/html/61765_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届北京国际珠宝首饰展览会','孕婴童','北京','中国国际展览中心(新馆)','2013-04-18','/zhanhui/html/55037_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('春季第81届中国电子展','奢侈品','北京','中国国际展览中心','2013-04-18','/zhanhui/html/56810_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际视听集成设备与技术展览会','房产家居','北京','北京展览馆','2013-04-18','/zhanhui/html/60396_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国出境旅游交易会','音响乐器','北京','北京国家会议中心','2013-04-17','/zhanhui/html/60468_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际供热通风空调、卫生洁具及城建','旅游行业','北京','北京全国农业展览馆','2013-04-13','/zhanhui/html/56997_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际云计算技术和应用展览会','暖通制冷','北京','中国国际展览中心(新馆)','2013-04-12','/zhanhui/html/55429_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届国际高端食用油及橄榄油(北京)博览会','信息通信','北京','中国国际展览中心','2013-04-11','/zhanhui/html/60023_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届北京营养健康产业博览会','食品饮料','北京','中国国际展览中心','2013-04-10','/zhanhui/html/57771_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届中国国际康复护理产品及老年用品辅具','医疗保健','北京','中国国际展览中心','2013-04-09','/zhanhui/html/57126_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国北京国际烘焙展2013年第十一届','食品饮料','北京','中国国际展览中心','2013-04-08','/zhanhui/html/53843_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第19届北京国际连锁加盟展览会','食品饮料','北京','中国国际展览中心','2013-04-07','/zhanhui/html/54292_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国汽车工程学会暨中国汽车工程及制造技术','工业制造','北京','北京国家会议中心','2013-11-01','/zhanhui/html/60732_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届北京国际热处理展览会','钢铁冶金','北京','北京国家会议中心','2013-11-30','/zhanhui/html/60729_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届（北京）国际有机食品和绿色食品博','食品饮料','北京','中国国际展览中心','2013-11-01','/zhanhui/html/59808_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际营养健康产业（北京）博览','医疗保健','北京','中国国际展览中心','2013-11-30','/zhanhui/html/59300_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届北京国际高端葡萄酒博览会','食品饮料','北京','中国国际展览中心','2013-11-26','/zhanhui/html/58689_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际城市轨道交通建设运营及装备展览会','交通工具','北京','中国国际展览中心','2013-11-25','/zhanhui/html/61961_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际老龄产业博览会','医疗保健','北京','北京展览馆','2013-11-25','/zhanhui/html/62456_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际食品加工和包装机械展览会中','纸业制品','北京','中国国际展览中心','2013-11-25','/zhanhui/html/59552_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际礼品、赠品及家用精品（年底）展览','礼品玩具','北京','北京国家会议中心','2013-11-25','/zhanhui/html/58781_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际葡萄酒、烈酒展览会','食品饮料','北京','北京国家会议中心','2013-11-19','/zhanhui/html/60728_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国在线分析仪器应用及发展国际论坛','仪器仪表','北京','北京国际会议中心','2013-11-15','/zhanhui/html/62683_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际教育展','文化教育','北京','北京国家会议中心','2013-11-13','/zhanhui/html/60391_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际信息通信展览会','信息通信','北京','中国国际展览中心','2013-09-01','/zhanhui/html/58286_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第22届中国国际造纸科技展览会及会议','纸业制品','北京','北京全国农业展览馆','2013-09-30','/zhanhui/html/61782_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际葡萄酒及烈酒展览会','食品饮料','北京','北京全国农业展览馆','2013-09-01','/zhanhui/html/61779_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际轮胎工业展','汽摩配件','北京','中国国际展览中心(新馆)','2013-09-30','/zhanhui/html/62013_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际电动汽车充(换)电站建设展览会','汽摩配件','北京','中国国际展览中心(新馆)','2013-09-24','/zhanhui/html/62023_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际汽车内饰件及材料加工设备展览会','汽摩配件','北京','中国国际展览中心(新馆)','2013-09-23','/zhanhui/html/62019_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际汽车模具及制造设备应用技术展览会','工业制造','北京','中国国际展览中心(新馆)','2013-09-18','/zhanhui/html/60602_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际汽车制造业博览会、2013中国国际汽','工业制造','北京','中国国际展览中心(新馆)','2013-09-18','/zhanhui/html/59396_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际应急救灾装备技术展','能源矿产','北京','北京全国农业展览馆','2013-09-18','/zhanhui/html/58653_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('秋季中国北京婚博会','婚庆婚博','北京','北京国家会议中心','2013-09-18','/zhanhui/html/60688_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国国际汽车零部件博览会','汽摩配件','北京','中国国际展览中心','2013-09-18','/zhanhui/html/58227_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际复合材料工业技术展览会','机械工业','北京','中国国际展览中心(新馆)','2013-09-18','/zhanhui/html/56801_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十四届中国国际五金博览会暨2012秋季(124届','建材五金','北京','中国国际展览中心(新馆)','2013-09-16','/zhanhui/html/58273_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（北京）国际地质与矿山技术装备展览','能源矿产','北京','北京全国农业展览馆','2013-09-14','/zhanhui/html/61767_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际内燃机及零部件展览会','机械工业','北京','北京国家会议中心','2013-09-13','/zhanhui/html/60721_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际储能产业博览会及电池技术展览会、','电子电力','北京','中国国际展览中心(新馆)','2013-09-11','/zhanhui/html/59378_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('IHWE中国（北京）国际健康饮用水产业及水环','环保水处理','北京','北京全国农业展览馆','2013-09-11','/zhanhui/html/62138_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('NRCE中国新农村建设、配套设施、设备、服务','农林牧渔','北京','北京全国农业展览馆','2013-09-10','/zhanhui/html/62252_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（取消）2013首届中国国际新能源汽车产业展览会','能源矿产','北京','中国国际展览中心(新馆)','2013-09-09','/zhanhui/html/61102_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第13届中国光伏大会暨北京国际光伏展览会','能源矿产','北京','中国国际展览中心(新馆)','2013-09-05','/zhanhui/html/61078_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届国际矿业展览会暨矿山装备展','能源矿产','北京','北京国家会议中心','2013-09-05','/zhanhui/html/62100_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十一届中国国际纸浆造纸林业展览会及会议','纸业制品','北京','中国国际展览中心','2013-09-05','/zhanhui/html/55031_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国国际方舱技术与设备展览订货会','汽摩配件','北京','中国国际展览中心','2013-09-05','/zhanhui/html/62410_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届北京国际核电工业及电力设备展览会','电子电力','北京','中国国际展览中心','2013-09-05','/zhanhui/html/61960_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际指挥调度暨军民结合技术装备展览会','安全防护','北京','中国国际展览中心','2013-09-04','/zhanhui/html/62804_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际军交装备及第四届车辆管理展','交通工具','北京','中国国际展览中心','2013-09-02','/zhanhui/html/62408_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届北京医疗器械展览会','医疗保健','北京','中国国际展览中心','2013-09-02','/zhanhui/html/59412_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际珠宝展','奢侈品','北京','中国国际展览中心','2013-10-01','/zhanhui/html/60253_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(北京)进口汽车博览会','交通工具','北京','北京国家会议中心','2013-10-31','/zhanhui/html/60727_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际现代教育新技术装备展览会','文化教育','北京','中国国际展览中心','2013-10-01','/zhanhui/html/60439_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际住宅产业博览会','房产家居','北京','北京国家会议中心','2013-10-31','/zhanhui/html/60726_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际木材及木制品展览会','建材五金','北京','北京国家会议中心','2013-10-31','/zhanhui/html/60928_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际品牌鞋及配饰展','服装配饰','北京','北京国家会议中心','2013-10-30','/zhanhui/html/60725_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国国际煤炭采矿技术交流及设备展览会','能源矿产','北京','北京全国农业展览馆','2013-10-26','/zhanhui/html/61787_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国工艺礼品、积分礼品、旅游休闲用品展览','礼品玩具','北京','中国国际展览中心','2013-10-23','/zhanhui/html/62153_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届北京国际马业马术展览会','体育休闲','北京','中国国际展览中心(新馆)','2013-10-23','/zhanhui/html/61264_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际新能源汽车展览会','交通工具','北京','北京国家会议中心','2013-10-23','/zhanhui/html/60723_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届中国（北京）国际钛工业展览会','钢铁冶金','北京','中国国际展览中心','2013-10-22','/zhanhui/html/60586_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届北京国际风能大会暨展览会','能源矿产','北京','中国国际展览中心(新馆)','2013-10-21','/zhanhui/html/62024_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国（北京）国际钢管工业展览会','钢铁冶金','北京','中国国际展览中心','2013-10-17','/zhanhui/html/60585_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国（北京）国际冶金工业博览会暨第十届','工业制造','北京','中国国际展览中心','2013-10-17','/zhanhui/html/57905_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届IEOE中国(北京)国际食用油、营养保健食品','食品饮料','北京','北京全国农业展览馆','2013-10-16','/zhanhui/html/62696_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际光电产业博览会暨第十八届中国国际激光','光电技术','北京','中国国际展览中心','2013-10-16','/zhanhui/html/60443_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('秋季北京耀莱奢华品牌文化博览会','奢侈品','北京','北京国家会议中心','2013-10-16','/zhanhui/html/58780_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(北京)国际海洋石油天然气技术展览会','能源矿产','北京','中国国际展览中心','2013-10-16','/zhanhui/html/60614_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第31届中国国际体育用品博览会','体育休闲','北京','中国国际展览中心(新馆)','2013-05-01','/zhanhui/html/60433_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际餐饮博览会','食品饮料','北京','中国国际展览中心','2013-05-31','/zhanhui/html/60445_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国国际软件博览会','信息通信','北京','北京展览馆','2013-05-01','/zhanhui/html/60401_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国国际现代农业博览会','农林牧渔','北京','中国国际展览中心','2013-05-31','/zhanhui/html/61958_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国（北京）国际服务贸易交易会','贸易进出口','北京','北京国家会议中心','2013-05-31','/zhanhui/html/57322_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第24届中国国际玻璃工业技术展览会','建材五金','北京','中国国际展览中心(新馆)','2013-05-31','/zhanhui/html/58866_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际道路运输、城市公交车辆及零部件展','运输物流','北京','北京国家会议中心','2013-05-29','/zhanhui/html/58092_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届中国北京国际科技产业博览会','电子电力','北京','中国国际展览中心','2013-05-28','/zhanhui/html/62720_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际科学仪器及实验室装备展览','仪器仪表','北京','中国国际展览中心','2013-05-24','/zhanhui/html/59422_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届北京酒店用品展览会','酒店用品','北京','中国国际展览中心','2013-05-22','/zhanhui/html/61455_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国(北京)国际警用装备及反恐技术装备展','安全防护','北京','北京展览馆','2013-05-21','/zhanhui/html/60399_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届北京国际印刷技术展览会','印刷包装','北京','中国国际展览中心(新馆)','2013-05-21','/zhanhui/html/62012_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第15届北京国际玩具及幼教用品展览会','孕婴童','北京','北京全国农业展览馆','2013-05-15','/zhanhui/html/53504_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届国际化学工程和生物技术展览暨会议','化工橡塑','北京','北京国家会议中心','2013-05-15','/zhanhui/html/60707_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际彩钢、冷弯型钢技术及设备博览会','钢铁冶金','北京','中国国际展览中心','2013-05-15','/zhanhui/html/58586_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际平板显示产业展览会','消费电子','北京','中国国际展览中心','2013-05-14','/zhanhui/html/54048_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际软件及系统管理展览会','信息通信','北京','中国国际展览中心','2013-05-13','/zhanhui/html/58341_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国国际建筑保温技术及产品展览会','建材五金','北京','中国国际展览中心','2013-05-13','/zhanhui/html/60569_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国国际物流博览会','运输物流','北京','中国国际展览中心','2013-05-09','/zhanhui/html/61957_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届亚洲（北京）国际信息网络及技术设备','信息通信','北京','中国国际展览中心','2013-05-09','/zhanhui/html/57712_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国国际新型墙体材料技术装备及产品','建材五金','北京','中国国际展览中心','2013-05-09','/zhanhui/html/60572_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届亚洲（北京）国际物联网展览会','电子电力','北京','中国国际展览中心','2013-05-09','/zhanhui/html/57708_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（北京）国际触摸屏展览会','光电技术','北京','中国国际展览中心','2013-05-09','/zhanhui/html/56736_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际天然气汽车、加气站设备展','能源矿产','北京','北京全国农业展览馆','2013-05-09','/zhanhui/html/60605_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届国际消防设备技术交流展览会','安全防护','北京','北京国家会议中心','2013-05-09','/zhanhui/html/60703_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国国际养老服务业博览会','医疗保健','北京','北京国家会议中心','2013-05-08','/zhanhui/html/60700_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('北京国际璀璨珠宝展览会','奢侈品','北京','北京展览馆','2013-12-01','/zhanhui/html/60216_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('冬季中国北京婚博会','婚庆婚博','北京','北京国家会议中心','2013-12-31','/zhanhui/html/60689_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国水博览会暨中国国际膜与水处理技术及装','环保水处理','北京','北京国家会议中心','2013-12-31','/zhanhui/html/59458_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届成都国际珠宝首饰展览会第7届西部珠宝业','奢侈品','成都','成都世纪城新国际会展中心','2013-04-01','/zhanhui/html/58247_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国成都创意设计展暨建筑装饰博览会暨201','房产家居','成都','成都世纪城新国际会展中心','2013-04-30','/zhanhui/html/60330_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第12届国际口腔设备与材料展览会暨口腔医学','文化教育','成都','成都世纪城新国际会展中心','2013-04-30','/zhanhui/html/47006_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国成都国际社会公共安全产品与','医疗保健','成都','成都世纪城新国际会展中心','2013-04-19','/zhanhui/html/60538_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国西部国际化工与石化工业博览会','安全防护','成都','成都世纪城新国际会展中心','2013-04-18','/zhanhui/html/59663_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第10届中国(成都)国际阀门+管件+流体工业展','化工橡塑','成都','成都世纪城新国际会展中心','2013-04-11','/zhanhui/html/60317_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届成都国际照明博览会2013第五届成','工业制造','成都','成都世纪城新国际会展中心','2013-04-11','/zhanhui/html/60320_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（成都）国际给排水水处理技术设','光电技术','成都','成都世纪城新国际会展中心','2013-04-11','/zhanhui/html/54380_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（成都）国际泵阀、管道及流体流','环保水处理','成都','成都世纪城新国际会展中心','2013-04-10','/zhanhui/html/60322_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国（四川）新春年货购物节','购物年货','成都','成都世纪城新国际会展中心','2013-01-01','/zhanhui/html/58739_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国成都给排水、管材管件及水处理','环保水处理','成都','成都世纪城新国际会展中心','2013-05-01','/zhanhui/html/60326_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国成都供热通风与空调热泵技术设备','暖通制冷','成都','成都世纪城新国际会展中心','2013-05-31','/zhanhui/html/57796_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国成都建筑装饰材料（夏季）博览会','建材五金','成都','成都世纪城新国际会展中心','2013-05-01','/zhanhui/html/57331_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国成都集成住宅、木别墅及轻钢房屋','房产家居','成都','成都世纪城新国际会展中心','2013-05-31','/zhanhui/html/57639_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届金蜘蛛(成都)紧固件交易展','工业制造','成都','成都世纪城新国际会展中心','2013-05-30','/zhanhui/html/62578_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国西部文化办公用品（成都）展览会','办公用品','成都','成都世纪城新国际会展中心','2013-05-30','/zhanhui/html/56355_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第88届成都春季糖酒会','食品饮料','成都','成都世纪城新国际会展中心','2013-03-01','/zhanhui/html/58142_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国四川国际节能减排博览会','环保水处理','成都','成都世纪城新国际会展中心','2013-03-31','/zhanhui/html/60306_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届成都国际广告四新展览会','广告媒体','成都','成都世纪城新国际会展中心','2013-03-01','/zhanhui/html/55333_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届成都国际照明及LED展览会','光电技术','成都','成都世纪城新国际会展中心','2013-03-31','/zhanhui/html/60736_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国西部国际印刷包装产业博览会','印刷包装','成都','成都世纪城新国际会展中心','2013-03-28','/zhanhui/html/60738_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国成都国际办公用品、文具、打印耗材展览','办公用品','成都','成都世纪城新国际会展中心','2013-03-14','/zhanhui/html/55321_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(成都)电子展','电子电力','成都','成都世纪城新国际会展中心','2013-06-01','/zhanhui/html/59660_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（成都）国际酒店设备用品及旅游','酒店用品','成都','成都世纪城新国际会展中心','2013-06-30','/zhanhui/html/59381_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（成都）礼品及家居用品展览会','礼品玩具','成都','成都世纪城新国际会展中心','2013-06-20','/zhanhui/html/60741_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国食品博览会','食品饮料','成都','成都世纪城新国际会展中心','2013-09-01','/zhanhui/html/60898_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国（西部）国际瓦楞纸箱及彩盒包装','印刷包装','成都','成都世纪城新国际会展中心','2013-07-01','/zhanhui/html/62853_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中西部（成都）国际环保技术与设备展览会','环保水处理','成都','成都世纪城新国际会展中心','2013-07-31','/zhanhui/html/58469_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国西部国际装备制造业博览会','工业制造','成都','成都世纪城新国际会展中心','2013-07-01','/zhanhui/html/56096_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国西部国际管道工程暨非开挖技术装备展览','建材五金','成都','成都世纪城新国际会展中心','2013-07-31','/zhanhui/html/60329_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国华夏家博会','房产家居','成都','成都世纪城新国际会展中心','2013-10-01','/zhanhui/html/62727_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国西部墙纸软装饰博览会','房产家居','成都','成都世纪城新国际会展中心','2013-10-31','/zhanhui/html/62643_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届成都国际孕婴童产品暨儿童产业博览会','孕婴童','成都','成都世纪城新国际会展中心','2013-08-01','/zhanhui/html/58348_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国成都塑胶及化工新材料展览会','化工橡塑','成都','成都世纪城新国际会展中心','2013-08-31','/zhanhui/html/61309_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第11届中国西部国际水处理技术设备及工程展','环保水处理','成都','成都世纪城新国际会展中心','2013-08-01','/zhanhui/html/58371_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际农用化学品及植保展览会','农林牧渔','上海','上海新国际博览中心','2013-02-01','/zhanhui/html/57952_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('地中海国际葡萄酒及烈酒亚洲展','食品饮料','上海','上海新国际博览中心','2013-02-28','/zhanhui/html/59954_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国(上海)国际眼镜业展览会','奢侈品','上海','上海世博展览馆','2013-02-01','/zhanhui/html/59244_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届华港迎春食品（光大）大联展','食品饮料','上海','上海光大会展中心','2013-01-01','/zhanhui/html/52701_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海高端珠宝首饰展览会','奢侈品','上海','上海光大会展中心','2013-01-31','/zhanhui/html/60124_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十三届中国上海国际婚纱摄影器材展览会','婚庆婚博','上海','上海世博展览馆','2013-01-01','/zhanhui/html/58330_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届上海国际医疗设备与生物技术展览会、第1','创业加盟','上海','上海光大会展中心','2013-05-31','/zhanhui/html/58005_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第18届中国国际厨房、卫浴设施展览会','创业加盟','上海','上海光大会展中心','2013-05-01','/zhanhui/html/58004_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海零售业博览会、2013上海国际商业设施及','房产家居','上海','上海新国际博览中心','2013-05-31','/zhanhui/html/58514_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际授权品牌及衍生消费品交易博览会','建材五金','上海','上海世博展览馆','2013-05-31','/zhanhui/html/60202_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际物流技术及设备展览会','贸易进出口','上海','上海国际展览中心','2013-05-31','/zhanhui/html/60116_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（首届）2013上海国际危化品供应链与物流装备展','创业加盟','上海','上海世博展览馆','2013-05-29','/zhanhui/html/59411_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际离合器及刹车系统展览会','运输物流','上海','上海世博展览馆','2013-05-28','/zhanhui/html/62703_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际重型汽车、卡车及零部件展览会','电子电力','上海','上海世博展览馆','2013-05-28','/zhanhui/html/57342_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届国际生物医药产业创新论坛暨（上海）展览','汽摩配件','上海','上海世博展览馆','2013-05-28','/zhanhui/html/57028_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际物流技术及设备展览会','汽摩配件','上海','上海世博展览馆','2013-05-28','/zhanhui/html/57031_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海幼儿教育暨用品展','医疗保健','上海','上海光大会展中心','2013-05-27','/zhanhui/html/59080_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国国际核电工业展览会','运输物流','上海','上海光大会展中心','2013-05-27','/zhanhui/html/62702_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第101届中国上海国际鞋业皮具展CIFL暨GDS精','孕婴童','上海','上海世贸商城','2013-05-27','/zhanhui/html/55315_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际糖酒食品饮料展览会、201','能源矿产','上海','上海世博展览馆','2013-05-27','/zhanhui/html/59425_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际有机食品博览会','皮革鞋业','上海','上海展览中心','2013-05-27','/zhanhui/html/61922_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届上海国际胶粘带、保护膜及光学膜展览会(','食品饮料','上海','上海世贸商城','2013-05-27','/zhanhui/html/56842_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届亚洲辐射固化国际会议暨第五届中国国际','食品饮料','上海','上海国际展览中心','2013-05-24','/zhanhui/html/60106_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际淀粉及淀粉衍生物展览会暨新技术、','化工橡塑','上海','上海世博展览馆','2013-05-23','/zhanhui/html/59424_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（上海）人居舒适环境科技博览会','化工橡塑','上海','上海光大会展中心','2013-05-23','/zhanhui/html/58413_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国（上海）建筑节能展览会','化工橡塑','上海','上海光大会展中心','2013-05-23','/zhanhui/html/57165_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届上海社会公共安全产品国际博览会','环保水处理','上海','上海光大会展中心','2013-05-23','/zhanhui/html/58615_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海机箱机柜、综合布线及光纤通信展','建材五金','上海','上海光大会展中心','2013-05-22','/zhanhui/html/59492_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海数据中心技术设备展览会','安全防护','上海','上海世博展览馆','2013-05-22','/zhanhui/html/57327_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际焙烤展览会','信息通信','上海','上海光大会展中心','2013-05-22','/zhanhui/html/59486_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届中国国际美容化妆洗涤用品博览会','环保水处理','上海','上海光大会展中心','2013-05-22','/zhanhui/html/57740_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('假日楼市—上海房地产春季展示会','房产家居','上海','上海展览中心','2013-04-01','/zhanhui/html/61909_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际粉末冶金与精细陶瓷展览会','钢铁冶金','上海','上海光大会展中心','2013-04-30','/zhanhui/html/58416_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际绿色环保铸造工业展览会暨','钢铁冶金','上海','上海光大会展中心','2013-04-01','/zhanhui/html/60665_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际婴幼儿营养食品及用品展览会暨20','孕婴童','上海','上海国际展览中心','2013-04-30','/zhanhui/html/59147_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国功能饮料及茶饮料展览会','食品饮料','上海','上海世贸商城','2013-04-26','/zhanhui/html/59032_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国高端瓶装水及瓶装水定制展览会','食品饮料','上海','上海世贸商城','2013-04-26','/zhanhui/html/59030_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('高性能纤维产业展暨论坛','化工橡塑','上海','上海世贸商城','2013-04-26','/zhanhui/html/60061_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十三届中国国际电子生产设备暨微电子工业展','电子电力','上海','上海世博展览馆','2013-04-26','/zhanhui/html/54375_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际衡器展览会','仪器仪表','上海','上海光大会展中心','2013-04-25','/zhanhui/html/57004_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届ReChina亚洲打印耗材展','食品饮料','上海','上海国际展览中心','2013-04-25','/zhanhui/html/60104_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届上海国际汽车工业展览会','印刷包装','上海','上海展览中心','2013-04-24','/zhanhui/html/60804_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(上海)国际奖励旅游及大会博览会','汽摩配件','上海','上海新国际博览中心','2013-04-23','/zhanhui/html/58407_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第86届中国劳动保护用品交易会','旅游行业','上海','上海世博展览馆','2013-04-22','/zhanhui/html/59401_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国国际花卉园艺展览会','安全防护','上海','上海光大会展中心','2013-04-22','/zhanhui/html/60436_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际染料工业暨有机染料、纺织化学','农林牧渔','上海','上海世博展览馆','2013-04-22','/zhanhui/html/58920_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('春季上海纺织服装展览会','纺织纺机','上海','上海世博展览馆','2013-04-21','/zhanhui/html/59294_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际康复无障碍生活博览会','纺织纺机','上海','上海世贸商城','2013-04-18','/zhanhui/html/58998_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海绿色家博会','医疗保健','上海','上海展览中心','2013-04-17','/zhanhui/html/62127_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届上海教育博览会','医疗保健','上海','上海国际展览中心','2013-04-17','/zhanhui/html/57800_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际游艇展、第十八届中国国际','房产家居','上海','上海世博展览馆','2013-04-17','/zhanhui/html/62569_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('表面活性剂和洗涤剂展览会2013第三届上海','文化教育','上海','上海展览中心','2013-04-17','/zhanhui/html/61908_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际风能展览会暨研讨会','奢侈品','上海','上海世博展览馆','2013-04-13','/zhanhui/html/59280_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国瓦楞展','化工橡塑','上海','上海光大会展中心','2013-04-12','/zhanhui/html/57166_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第24届国际制冷、空调、供暖、通风机食品冷冻加','能源矿产','上海','上海新国际博览中心','2013-04-12','/zhanhui/html/58502_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际电力电工设备与技术展览会','印刷包装','上海','上海新国际博览中心','2013-04-12','/zhanhui/html/58506_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海第十二届中国国际动力设备发电机组展览','暖通制冷','上海','上海新国际博览中心','2013-04-11','/zhanhui/html/58463_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海首届文化艺术收藏博览交易会','电子电力','上海','上海新国际博览中心','2013-04-10','/zhanhui/html/54376_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届上海连锁加盟展览会','创业加盟','上海','上海国际展览中心','2013-03-01','/zhanhui/html/59176_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('首届上海婚庆博览会','婚庆婚博','上海','上海世贸商城','2013-03-31','/zhanhui/html/61446_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届国际眼科学术会议','医疗保健','上海','上海光大会展中心','2013-03-01','/zhanhui/html/56789_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届上海珠宝玉石首饰展览会','奢侈品','上海','上海展览中心','2013-03-31','/zhanhui/html/62119_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（取消）第二届上海汽车内饰与外饰展览会','安全防护','上海','上海国际展览中心','2013-03-31','/zhanhui/html/54303_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国国际食品添加剂和配料展览会暨','汽摩配件','上海','上海世贸商城','2013-03-30','/zhanhui/html/59500_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际遮阳与节能技术博览会中国国际门','食品饮料','上海','上海世博展览馆','2013-03-29','/zhanhui/html/56943_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第15届中国国际地面材料及铺装技术展览会','建材五金','上海','上海新国际博览中心','2013-03-28','/zhanhui/html/58498_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第14届上海国际广告四新展览会','纺织纺机','上海','上海新国际博览中心','2013-03-27','/zhanhui/html/58481_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届上海珠宝首饰展览会','美容美发','上海','上海光大会展中心','2013-03-26','/zhanhui/html/59704_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国上海（春季）国际礼品、家用品展览会','奢侈品','上海','上海展览中心','2013-03-26','/zhanhui/html/60087_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际健康产品展览会','礼品玩具','上海','上海世贸商城','2013-03-26','/zhanhui/html/57579_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海欧洲精品面料小型展示会（春季）','医疗保健','上海','上海国际展览中心','2013-03-22','/zhanhui/html/60088_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第22届中国国际电子电路展览会','服装配饰','上海','上海世贸商城','2013-03-22','/zhanhui/html/60497_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国家电博览会','电子电力','上海','上海世博展览馆','2013-03-22','/zhanhui/html/54367_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际太阳能技术展览会','消费电子','上海','上海新国际博览中心','2013-03-21','/zhanhui/html/54310_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('国际半导体设备、材料、制造和服务展览暨研','能源矿产','上海','上海新国际博览中心','2013-03-21','/zhanhui/html/58478_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('慕尼黑上海光博会','电子电力','上海','上海新国际博览中心','2013-03-20','/zhanhui/html/58476_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国上海首届优质禽类（土鸡）采购交易会','光电技术','上海','上海新国际博览中心','2013-03-19','/zhanhui/html/54377_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海饰博会','农林牧渔','上海','上海光大会展中心','2013-03-19','/zhanhui/html/58302_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届上海海外置业及移民投资展','房产家居','上海','上海国际展览中心','2013-03-19','/zhanhui/html/62101_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（取消）上海国际理想家园博览会','房产家居','上海','上海展览中心','2013-03-19','/zhanhui/html/62116_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海电热元器件及电热技术与设备展','房产家居','上海','上海展览中心','2013-03-19','/zhanhui/html/54564_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届上海之春房产展示交易会','电子电力','上海','上海世贸商城','2013-03-17','/zhanhui/html/58373_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际宠物犬博览会','房产家居','上海','上海展览中心','2013-03-16','/zhanhui/html/57847_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际件杂货运输展览会','礼品玩具','上海','上海世博展览馆','2013-03-15','/zhanhui/html/59269_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国热泵展览会2013中国（上海）供暖、通','运输物流','上海','上海国际展览中心','2013-03-15','/zhanhui/html/60086_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十三届中国国际五金博览会','能源矿产','上海','上海光大会展中心','2013-03-15','/zhanhui/html/58218_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际奇石珠宝玉石首饰展览会','奢侈品','上海','上海展览中心','2013-06-01','/zhanhui/html/62748_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲移动通信大会暨2013亚洲移动通信博览会','信息通信','上海','上海新国际博览中心','2013-06-30','/zhanhui/html/59457_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际混凝土产业展览会','建材五金','上海','上海世博展览馆','2013-06-01','/zhanhui/html/59721_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海邮政器材及速递物流技术展、2013上海国','运输物流','上海','上海国际展览中心','2013-06-30','/zhanhui/html/60833_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际邮政通信器材及信息技术展览会','信息通信','上海','上海国际展览中心','2013-06-28','/zhanhui/html/59688_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际刀具暨工具展览会','钢铁冶金','上海','上海光大会展中心','2013-06-26','/zhanhui/html/57169_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届世界制药原料中国展','贸易进出口','上海','上海新国际博览中心','2013-06-26','/zhanhui/html/59456_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲食品配料、健康原料、天然原料中国展','食品饮料','上海','上海新国际博览中心','2013-06-26','/zhanhui/html/58632_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(上海)重型机械装备展览会','机械工业','上海','上海世博展览馆','2013-06-26','/zhanhui/html/59446_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届高空作业大会暨应急救援展览会','工业制造','上海','上海光大会展中心','2013-06-26','/zhanhui/html/62717_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届上海国际微纳米MEMS、粉体展览会','电子电力','上海','上海光大会展中心','2013-06-25','/zhanhui/html/62710_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国上海国际古典家具展览会上海','房产家居','上海','上海展览中心','2013-06-25','/zhanhui/html/61932_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）种植博览会、中国（上海）养殖博览','农林牧渔','上海','上海世博展览馆','2013-06-25','/zhanhui/html/59445_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届（上海）紧固件专业展暨上海汽车紧固','建材五金','上海','上海世博展览馆','2013-06-25','/zhanhui/html/58102_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国纸制品展','纸业制品','上海','上海国际展览中心','2013-06-22','/zhanhui/html/54214_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届中国（上海）国际肉类工业展览会','食品饮料','上海','上海光大会展中心','2013-06-21','/zhanhui/html/59550_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际电影技术展览会','广告媒体','上海','上海国际展览中心','2013-06-20','/zhanhui/html/60440_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际电力元件、可再生能源管理暨嵌入式','能源矿产','上海','上海世博展览馆','2013-06-20','/zhanhui/html/59443_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际海上风电及风电产业链大会暨展览会','能源矿产','上海','上海新国际博览中心','2013-06-20','/zhanhui/html/58622_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际模具、模具设备展览会及相关工业展','工业制造','上海','上海新国际博览中心','2013-06-20','/zhanhui/html/58646_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('夏季中国婚博会','婚庆婚博','上海','上海世博展览馆','2013-06-19','/zhanhui/html/59435_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际游乐设备展览会','旅游行业','上海','上海光大会展中心','2013-06-18','/zhanhui/html/58129_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国安全食品产业博览会暨争创世博品牌展','食品饮料','上海','上海光大会展中心','2013-06-18','/zhanhui/html/61104_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海（国际）城市园林景观设计及设施展览会','农林牧渔','上海','上海国际展览中心','2013-06-15','/zhanhui/html/60662_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海国际石英材料展览会','电子电力','上海','上海新国际博览中心','2013-06-15','/zhanhui/html/59092_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届上海国际纺织工业展览会','纺织纺机','上海','上海新国际博览中心','2013-06-15','/zhanhui/html/59533_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海国际破碎机及配套装备展览','机械工业','上海','上海新国际博览中心','2013-06-14','/zhanhui/html/56029_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际电梯（上海）展览会','电子电力','上海','上海新国际博览中心','2013-06-13','/zhanhui/html/54219_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国上海（秋季）国际礼品、家用品展览会','礼品玩具','上海','上海世贸商城','2013-07-01','/zhanhui/html/60064_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际数码互动娱乐产品及技术应用展览会','体育休闲','上海','上海新国际博览中心','2013-07-31','/zhanhui/html/58645_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('国际电子变压器暨电感器展览会','电子电力','上海','上海光大会展中心','2013-07-01','/zhanhui/html/59947_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国（上海）国际汽车内饰产品及应用','汽摩配件','上海','上海世博展览馆','2013-07-31','/zhanhui/html/56919_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第23届全国电磁兼容学术会议暨上海微波理论、射','电子电力','上海','上海光大会展中心','2013-07-25','/zhanhui/html/58785_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届上海电子散热器材及热电制冷器件展览','暖通制冷','上海','上海光大会展中心','2013-07-25','/zhanhui/html/58853_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届非晶与纳米晶材料及磁铁芯（上海）展','能源矿产','上海','上海光大会展中心','2013-07-18','/zhanhui/html/58852_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际电机博览会暨发展论坛','电子电力','上海','上海世博展览馆','2013-07-18','/zhanhui/html/59722_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际食品机械设备展览会','食品饮料','上海','上海新国际博览中心','2013-07-18','/zhanhui/html/58644_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际儿童、婴儿、孕妇产业博览会20','服装配饰','上海','上海新国际博览中心','2013-07-18','/zhanhui/html/58638_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际品牌服装服饰展会','服装配饰','上海','上海世贸商城','2013-07-18','/zhanhui/html/59496_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际动漫游戏博览会','动漫游戏','上海','上海世博展览馆','2013-07-18','/zhanhui/html/59506_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十一届上海国际广告印刷包装纸业展览会','广告媒体','上海','上海新国际博览中心','2013-07-17','/zhanhui/html/57846_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海国际LED照明展览会暨论坛','光电技术','上海','上海新国际博览中心','2013-07-17','/zhanhui/html/58011_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海品牌加盟暨创业投资展览会','创业加盟','上海','上海展览中心','2013-07-17','/zhanhui/html/62705_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国上海工艺美术精品、收藏品及古典家具展','文化艺术','上海','上海光大会展中心','2013-07-11','/zhanhui/html/59995_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十四届中国上海国际婚纱摄影器材展览会','婚庆婚博','上海','上海世博展览馆','2013-07-10','/zhanhui/html/59489_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届IEOE中国（上海）国际食用油产业博览会','食品饮料','上海','上海展览中心','2013-07-10','/zhanhui/html/61918_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届INIE中国(上海）国际营养食品及保健','食品饮料','上海','上海展览中心','2013-07-06','/zhanhui/html/61913_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第15届上海国际机床展览会','机械工业','上海','上海新国际博览中心','2013-07-05','/zhanhui/html/58637_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际铝工业展览会暨上海国际工业材料展览会','工业制造','上海','上海新国际博览中心','2013-07-04','/zhanhui/html/58636_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海校车及安全座椅展览会、2013教育装备及','孕婴童','上海','上海光大会展中心','2013-07-03','/zhanhui/html/60143_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('秋季中国婚博会','婚庆婚博','上海','上海世博展览馆','2013-08-01','/zhanhui/html/59436_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际珠宝首饰展暨上海进口商品博览会','奢侈品','上海','上海展览中心','2013-08-31','/zhanhui/html/62760_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际家用纺织品及辅料（秋冬）博览会','纺织纺机','上海','上海新国际博览中心','2013-08-01','/zhanhui/html/58788_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国（上海）国际劳动防护与安全装备','安全防护','上海','上海世博展览馆','2013-08-31','/zhanhui/html/60830_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届上海国际模型展览会展','礼品玩具','上海','上海国际展览中心','2013-08-31','/zhanhui/html/60834_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际养老及福祉产业展览会','医疗保健','上海','上海世博展览馆','2013-08-29','/zhanhui/html/59517_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届亚洲宠物展览会','礼品玩具','上海','上海世博展览馆','2013-08-27','/zhanhui/html/59509_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届上海国际聚氨酯、粘接技术、纳米氟硅材','化工橡塑','上海','上海新国际博览中心','2013-08-27','/zhanhui/html/59544_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海国际阻燃技术纤维工业化纤展','化工橡塑','上海','上海新国际博览中心','2013-08-23','/zhanhui/html/59536_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国上海国际电池产品及技术展览会','电子电力','上海','上海新国际博览中心','2013-08-22','/zhanhui/html/60701_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国国际海洋石油天然气展览会','能源矿产','上海','上海新国际博览中心','2013-08-22','/zhanhui/html/59972_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届国际质量检测分析技术及测量测试仪','仪器仪表','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/58864_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国(上海)国际石油化工技术装备展览','化工橡塑','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/60382_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际汽车材料及装备技术展览会','汽摩配件','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/58631_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际涂料博览会暨第十七届中国国际涂料','建材五金','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/57799_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国（上海）国际建筑模板、脚手架及','化工橡塑','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/58221_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际建筑给排水展览会','建材五金','上海','上海新国际博览中心','2013-08-20','/zhanhui/html/57299_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届中国（上海）国际墙纸、布艺展览会','医疗保健','上海','上海光大会展中心','2013-08-20','/zhanhui/html/57171_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第107届中国日用百货商品交易会暨中国现代家庭','环保水处理','上海','上海新国际博览中心','2013-08-15','/zhanhui/html/57768_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际家庭用品、促销品及工艺品创意设','建材五金','上海','上海新国际博览中心','2013-08-15','/zhanhui/html/60444_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际汽车零配件、维修检测诊断设备及服','汽摩配件','上海','上海新国际博览中心','2013-12-01','/zhanhui/html/60026_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届亚洲国际标签印刷展览会','印刷包装','上海','上海新国际博览中心','2013-12-31','/zhanhui/html/60200_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际海事技术学术会议和展览会','运输物流','上海','上海新国际博览中心','2013-12-01','/zhanhui/html/60022_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国健康食用油产业博览会','食品饮料','上海','上海光大会展中心','2013-11-01','/zhanhui/html/60652_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国（上海）葡萄酒及烈酒展览会','食品饮料','上海','上海光大会展中心','2013-11-30','/zhanhui/html/60615_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届中国食品安全控制及检测仪器设备展览','食品饮料','上海','上海光大会展中心','2013-11-01','/zhanhui/html/60613_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第12届上海时尚家居用品、装饰品及工艺礼品展览','礼品玩具','上海','上海光大会展中心','2013-11-30','/zhanhui/html/58534_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届上海电子商务及网货交易会','购物年货','上海','上海光大会展中心','2013-11-29','/zhanhui/html/61201_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际压铸、铸造、锻造展览会','工业制造','上海','上海国际展览中心','2013-11-29','/zhanhui/html/60846_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际建筑五金展','建材五金','上海','上海新国际博览中心','2013-11-29','/zhanhui/html/58258_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('冬季中国婚博会','婚庆婚博','上海','上海世博展览馆','2013-11-29','/zhanhui/html/59437_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际饮料工业科技展','食品饮料','上海','上海新国际博览中心','2013-11-29','/zhanhui/html/55370_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国（上海）国际保温材料与节能技','环保水处理','上海','上海新国际博览中心','2013-11-28','/zhanhui/html/58007_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届外墙装饰材料及粘结技术展览会','建材五金','上海','上海新国际博览中心','2013-11-26','/zhanhui/html/61098_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国（上海）国际地坪工业展览会','建材五金','上海','上海新国际博览中心','2013-11-23','/zhanhui/html/58010_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际门窗幕墙博览会','房产家居','上海','上海新国际博览中心','2013-11-20','/zhanhui/html/59747_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届上海生物发酵产品与技术装备展览会暨','食品饮料','上海','上海光大会展中心','2013-11-20','/zhanhui/html/61453_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际网印及数字化印刷展/2013FESPA中国','印刷包装','上海','上海世博展览馆','2013-11-20','/zhanhui/html/61881_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际精细化工及定制化学品展览会中国国','化工橡塑','上海','上海光大会展中心','2013-11-20','/zhanhui/html/62725_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届上海艺术博览会','文化艺术','上海','上海世贸商城','2013-11-19','/zhanhui/html/60442_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际孕婴童营养食品及用品博览会','孕婴童','上海','上海国际展览中心','2013-11-18','/zhanhui/html/60844_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际食品饮料及餐饮设备展览会','食品饮料','上海','上海新国际博览中心','2013-11-18','/zhanhui/html/60019_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国国际市政装备技术博览会暨广州','电子电力','上海','上海新国际博览中心','2013-11-18','/zhanhui/html/59661_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际橡胶技术展、第七届亚洲埃森轮','化工橡塑','上海','上海新国际博览中心','2013-11-14','/zhanhui/html/60198_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际水处理化学品、水溶高分子、造纸化','环保水处理','上海','上海世博展览馆','2013-11-14','/zhanhui/html/59518_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际车用空调及冷藏技术展览会','暖通制冷','上海','上海世博展览馆','2013-11-13','/zhanhui/html/59515_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际黄金珠宝玉石展览会','奢侈品','上海','上海世博展览馆','2013-11-13','/zhanhui/html/59725_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际新光源&新能源照明展览会暨论坛','能源矿产','上海','上海展览中心','2013-11-13','/zhanhui/html/61946_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海国际进出口食品及饮料展览会','食品饮料','上海','上海国际展览中心','2013-11-12','/zhanhui/html/61886_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际物联网技术与智慧城市应用博览会','信息通信','上海','上海世博展览馆','2013-11-12','/zhanhui/html/62742_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际工业博览会','工业制造','上海','上海新国际博览中心','2013-11-08','/zhanhui/html/59355_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('黄金周名特优产品交易会暨购物节','食品饮料','上海','上海光大会展中心','2013-09-01','/zhanhui/html/61199_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届世界氢能技术大会','能源矿产','上海','上海光大会展中心','2013-09-30','/zhanhui/html/60664_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届中国国际流体机械展览会','机械工业','上海','上海国际展览中心','2013-09-01','/zhanhui/html/59642_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际医疗设备设计与技术展','医疗保健','上海','上海世博展览馆','2013-09-30','/zhanhui/html/59723_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际建筑给排水、水处理技术及设备展览','环保水处理','上海','上海新国际博览中心','2013-09-29','/zhanhui/html/58922_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国国际文具及办公用品展览会','办公用品','上海','上海新国际博览中心','2013-09-26','/zhanhui/html/58257_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国医用敷料耗材大会暨展览会','医疗保健','上海','上海世贸商城','2013-09-26','/zhanhui/html/58462_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际缝制设备展览会','纺织纺机','上海','上海新国际博览中心','2013-09-25','/zhanhui/html/59466_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海第七届国际智能建筑展览会','建材五金','上海','上海新国际博览中心','2013-09-25','/zhanhui/html/61882_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际线缆工业展览会','电子电力','上海','上海光大会展中心','2013-09-25','/zhanhui/html/60482_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际金属成形展览会','建材五金','上海','上海世博展览馆','2013-09-25','/zhanhui/html/59526_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国上海工艺美术大师精品展暨古玩字','文化艺术','上海','上海展览中心','2013-09-25','/zhanhui/html/61941_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第六届中国上海国际红木艺术家具展览会','房产家居','上海','上海展览中心','2013-09-25','/zhanhui/html/61937_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际民间海外投资展览会','创业加盟','上海','上海世贸商城','2013-09-25','/zhanhui/html/61447_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国抗菌消毒技术暨预防医学感染控制展览','医疗保健','上海','上海世贸商城','2013-09-24','/zhanhui/html/58594_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲生活用纸（上海）展览会','纸业制品','上海','上海世贸商城','2013-09-20','/zhanhui/html/58454_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国医用非织造布及制品（上海）展览会','医疗保健','上海','上海世贸商城','2013-09-20','/zhanhui/html/60206_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国绿色医院建筑设计与装备(上海)展览','医疗保健','上海','上海世贸商城','2013-09-19','/zhanhui/html/58595_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届上海国际冷冻冷藏食品博览会暨2013上','食品饮料','上海','上海光大会展中心','2013-09-16','/zhanhui/html/58411_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届上海国际调味品及食品配料展览会','食品饮料','上海','上海光大会展中心','2013-09-16','/zhanhui/html/59490_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('国际特许加盟（上海）展览会','创业加盟','上海','上海国际展览中心','2013-09-16','/zhanhui/html/60836_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十九届中国国际家具生产设备及原辅材料','房产家居','上海','上海世博展览馆','2013-09-16','/zhanhui/html/59521_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十九届中国国际家具展览会','房产家居','上海','上海新国际博览中心','2013-09-15','/zhanhui/html/58588_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际跨国采购大会','贸易进出口','上海','上海世贸商城','2013-09-15','/zhanhui/html/60066_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届汽车测试及质量监控博览会','汽摩配件','上海','上海光大会展中心','2013-09-14','/zhanhui/html/60971_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际养生食品博览会','医疗保健','上海','上海世贸商城','2013-09-11','/zhanhui/html/60655_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届上海国际数字标牌及触摸技术展览会','消费电子','上海','上海世博展览馆','2013-09-11','/zhanhui/html/58378_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（第十二届）中国国际化工展览会','化工橡塑','上海','上海世博展览馆','2013-09-10','/zhanhui/html/58224_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国国际轮胎博览会','化工橡塑','上海','上海光大会展中心','2013-09-10','/zhanhui/html/61108_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届上海国际电力设备及技术展览会','电子电力','上海','上海世博展览馆','2013-10-01','/zhanhui/html/59523_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际供热及热动力技术展览会','钢铁冶金','上海','上海世博展览馆','2013-10-31','/zhanhui/html/60805_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届中国国际质量控制与测试工业设备展览会','仪器仪表','上海','上海光大会展中心','2013-10-01','/zhanhui/html/54560_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届中国国际医药（工业）展览会暨技术交流','医疗保健','上海','上海新国际博览中心','2013-10-31','/zhanhui/html/60008_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届中国先进复合材料制品、原材料工装及','化工橡塑','上海','上海光大会展中心','2013-10-30','/zhanhui/html/61200_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届亚洲太阳能光伏光热发电技术展会暨论','能源矿产','上海','上海世博展览馆','2013-10-30','/zhanhui/html/54316_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海国际石油石化天然气技术装备展览','能源矿产','上海','上海新国际博览中心','2013-10-30','/zhanhui/html/60178_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国上海国际分布式能源及储能技术设备展览','电子电力','上海','上海新国际博览中心','2013-10-29','/zhanhui/html/60179_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲国际动力传动展和亚洲物流展','工业制造','上海','上海新国际博览中心','2013-10-29','/zhanhui/html/60001_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际汽车改装博览会','汽摩配件','上海','上海世贸商城','2013-10-28','/zhanhui/html/60073_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际奢华生活博览会','奢侈品','上海','上海展览中心','2013-10-28','/zhanhui/html/62178_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届上海国际汽车改装博览会','汽摩配件','上海','上海国际展览中心','2013-10-28','/zhanhui/html/60841_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('上海国际电磁兼容暨微波天线技术交流展览会','电子电力','上海','上海光大会展中心','2013-10-28','/zhanhui/html/56794_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国国际口腔器材展览会暨学术研讨会','医疗保健','上海','上海世博展览馆','2013-10-25','/zhanhui/html/59527_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届上海国际非织造材料展览会','工业制造','上海','上海世博展览馆','2013-10-25','/zhanhui/html/59587_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际零售博览会（上海首届）','贸易进出口','上海','上海世博展览馆','2013-10-25','/zhanhui/html/49563_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('欧洲精品面料(上海)展览会（秋季）','服装配饰','上海','上海世贸商城','2013-10-23','/zhanhui/html/60499_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际纺织面料及辅料（秋冬）博览会','纺织纺机','上海','上海新国际博览中心','2013-10-23','/zhanhui/html/58630_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际产业用纺织品及非织造布展览会','纺织纺机','上海','上海新国际博览中心','2013-10-23','/zhanhui/html/58862_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际针织博览会','纺织纺机','上海','上海新国际博览中心','2013-10-22','/zhanhui/html/58629_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届(上海)国际减灾及与安全博览会','安全防护','上海','上海世博展览馆','2013-10-22','/zhanhui/html/59531_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国糖果文化节、第十届中国国际甜食及休闲','食品饮料','上海','上海世博展览馆','2013-10-21','/zhanhui/html/62738_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届全国农药交流会暨农化产品展览会','农林牧渔','上海','上海光大会展中心','2013-10-21','/zhanhui/html/57172_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届国际粉体工业/散装技术展览会暨会','化工橡塑','上海','上海国际展览中心','2013-10-21','/zhanhui/html/60838_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国童车及婴童用品展览会','孕婴童','上海','上海新国际博览中心','2013-10-17','/zhanhui/html/60196_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十二届中国国际玩具及模型展览会、中国玩','孕婴童','上海','上海新国际博览中心','2013-10-17','/zhanhui/html/60181_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届上海国际渔业博览会暨2013第八届上海','农林牧渔','上海','上海新国际博览中心','2013-10-16','/zhanhui/html/61885_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届中国（上海）国际玻璃工业展览会','建材五金','上海','上海国际展览中心','2013-10-15','/zhanhui/html/58392_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（上海）国际乐器展览会','音响乐器','上海','上海新国际博览中心','2013-10-15','/zhanhui/html/58802_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国冰淇淋冷冻食品设备与原辅料展览会','食品饮料','上海','上海光大会展中心','2013-10-15','/zhanhui/html/60483_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第113届中国进出口商品交易会（第二期）','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-04-30','/zhanhui/html/58183_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第113届中国进出口商品交易会（第一期）','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-04-30','/zhanhui/html/58180_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届广州台球及配套设施展','体育休闲','广州','中国进出口商品交易会场馆（琶洲馆）','2013-04-14','/zhanhui/html/57571_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际网络及数字互动游戏展2013第九届','动漫游戏','广州','中国进出口商品交易会场馆（琶洲馆）','2013-04-13','/zhanhui/html/57758_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届珠三角房地产博览会、2013珠三角房地','房产家居','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-01','/zhanhui/html/60612_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届年中国(广州)国际名酒展览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-31','/zhanhui/html/57590_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国广州国际陶瓷工业技术与产品展览会','皮革鞋业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-01','/zhanhui/html/57697_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际眼镜展览会','文化艺术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-31','/zhanhui/html/57594_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十届广州国际表面处理、电镀、涂装展览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-29','/zhanhui/html/54240_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届广州婚纱摄影器材展览会暨相册相框、','印刷包装','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-21','/zhanhui/html/57696_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（广州）第九届中国国际食品饮料展','建材五金','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-21','/zhanhui/html/52704_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国（广州）葡萄酒与烈酒展','体育休闲','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-15','/zhanhui/html/57581_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国（广州）食品机械与包装展','机械工业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-15','/zhanhui/html/57580_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十七届中国烘焙展览会','房产家居','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-10','/zhanhui/html/57715_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届广州（国际）演艺设备、智能声光产','农林牧渔','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-10','/zhanhui/html/55679_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(广州)国际家用纺织品及辅料博览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-10','/zhanhui/html/57577_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（广州）际绕线设备与技术展览会','光电技术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-10','/zhanhui/html/57578_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际马业博览会','运输物流','广州','中国进出口商品交易会场馆（琶洲馆）','2013-05-09','/zhanhui/html/59301_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届广州紫砂陶瓷艺术文化节暨工艺美术精','文化艺术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-07-01','/zhanhui/html/61165_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十五届中国（广州）国际建筑装饰博览会','建材五金','广州','中国进出口商品交易会场馆（琶洲馆）','2013-07-01','/zhanhui/html/54210_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国(广州)国际进出口食品交易展览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-01','/zhanhui/html/59380_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届中国(广州)国际食品工业博览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-31','/zhanhui/html/59631_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届华南国际医疗器械（广州）展览会','医疗保健','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-01','/zhanhui/html/59357_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('华南国际老年用品及康复保健器械展览会','医疗保健','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-31','/zhanhui/html/59258_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('南国书香节广州教育展','文化教育','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-26','/zhanhui/html/62371_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国（广州）国际电子化学品展览会','电子电力','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-21','/zhanhui/html/59803_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际缝制制衣设备展广州国际服装面辅','纺织纺机','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-21','/zhanhui/html/59141_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际纺织品印花工业技术展广州国际纺','服装配饰','广州','中国进出口商品交易会场馆（琶洲馆）','2013-08-16','/zhanhui/html/59137_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届广州清洁设备及用品展','酒店用品','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-01','/zhanhui/html/57633_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届广州咖啡设备及用品展览会','酒店用品','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-30','/zhanhui/html/57634_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届广州国际酒店设备及用品展览会、第三届','酒店用品','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-01','/zhanhui/html/54213_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州珠宝首饰展览会','奢侈品','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-30','/zhanhui/html/61317_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届广州国际机床及汽车加工装备展','机械工业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-27','/zhanhui/html/61261_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届中国(广州)国际金融交易博览会','创业加盟','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-27','/zhanhui/html/62817_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第4届广州国际物流装备与信息化展览会','运输物流','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-21','/zhanhui/html/58729_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第9届广州国际品牌叉车及配件展览会','运输物流','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-21','/zhanhui/html/57707_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届广州国际金属暨冶金工业展览会','钢铁冶金','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-21','/zhanhui/html/57600_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广东家电配件采购博览会','消费电子','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-20','/zhanhui/html/57598_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际建筑电气技术展览会2013广州国际光电','光电技术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-18','/zhanhui/html/57586_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国广州国际电线电缆及电缆附件采购展览会','光电技术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-18','/zhanhui/html/57585_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际润滑油、脂及相关技术设备展览会','电子电力','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-16','/zhanhui/html/55637_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第13届广州国际食品展暨广州进口食品展览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-05','/zhanhui/html/56925_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（广州）国际饮料饮品展览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-05','/zhanhui/html/56923_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第22届全国（广州）药品保健品交易会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-05','/zhanhui/html/58730_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第七届广州食用油及橄榄油展览会','医疗保健','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-05','/zhanhui/html/57718_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('夏季中国（广州）婚博会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-06-04','/zhanhui/html/59450_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第114届中国进出口商品交易会（第三期）','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-10-01','/zhanhui/html/61064_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第114届中国进出口商品交易会（第二期）','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-10-31','/zhanhui/html/61063_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第114届中国进出口商品交易会（第一期）','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-10-31','/zhanhui/html/61062_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际调味品及食品配料博览会','食品饮料','广州','中国进出口商品交易会场馆（琶洲馆）','2013-11-01','/zhanhui/html/62811_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('高工LED展','光电技术','广州','中国进出口商品交易会场馆（琶洲馆）','2013-11-30','/zhanhui/html/58225_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国(广州)国际汽车展览会','交通工具','广州','中国进出口商品交易会场馆（琶洲馆）','2013-11-28','/zhanhui/html/61190_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国国际绿色创新技术产品展','环保水处理','广州','中国进出口商品交易会场馆（琶洲馆）','2013-11-25','/zhanhui/html/61065_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十七届中国广州特许连锁加盟展览会','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-30','/zhanhui/html/61700_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（广州）世界品牌进口博览会','贸易进出口','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-01','/zhanhui/html/61437_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十三届广州国际木工机械及配件展览会','机械工业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-25','/zhanhui/html/59806_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三十九届广东国际美容美发化妆用品进出口','美容美发','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-24','/zhanhui/html/61060_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第一届广州保健产业博览会','医疗保健','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-23','/zhanhui/html/62026_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第32届中国（广州）国际家具博览会、2013中','房产家居','广州','中国进出口商品交易会场馆（琶洲馆）','2013-09-16','/zhanhui/html/61059_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届华南国际口腔展','医疗保健','广州','中国进出口商品交易会场馆（琶洲馆）','2013-02-01','/zhanhui/html/57605_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届广州国际汽车改装服务业展览会','汽摩配件','广州','中国进出口商品交易会场馆（琶洲馆）','2013-02-28','/zhanhui/html/54354_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国广州国际木工机械、家具配料展览会','机械工业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-29','/zhanhui/html/57568_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国（广州）衣柜展览会第四届中国（广','房产家居','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-29','/zhanhui/html/57705_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国国际化妆品个人及家庭护理用品原料展览会','纺织纺机','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-27','/zhanhui/html/57759_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州国际分析测试及实验室设备展览会暨技术','体育休闲','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-24','/zhanhui/html/57701_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国（广州）国际专业音响、灯光展','暖通制冷','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-18','/zhanhui/html/57514_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('春季中国（广州）婚博会','美容美发','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-18','/zhanhui/html/57699_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第25届广州国际礼品、家居用品及室内装饰展第','音响乐器','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-13','/zhanhui/html/55808_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十六届广州特许连锁加盟展览会2013第十届','音响乐器','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-13','/zhanhui/html/55236_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届中国国际包装工业展第十七届中国','旅游行业','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-09','/zhanhui/html/57602_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国（广州）国际给排水、水处理技','礼品玩具','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-09','/zhanhui/html/56403_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（取消）2013第九届中国（广州）国际环保展览会','创业加盟','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-09','/zhanhui/html/57738_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届广州（国际）演艺设备、智能声光产','工业制造','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-09','/zhanhui/html/57566_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（广州）国际广告标识展览会','印刷包装','广州','中国进出口商品交易会场馆（琶洲馆）','2013-03-07','/zhanhui/html/57564_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('广州智慧城市及物联网展览会','信息通信','广州','中国进出口商品交易会场馆（琶洲馆）','2013-12-31','/zhanhui/html/62370_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届广州国际酒店设备用品展览会、第二','酒店用品','广州','中国进出口商品交易会场馆（琶洲馆）','2013-12-01','/zhanhui/html/61069_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十八届国际集成电路研讨会暨展览会','电子电力','深圳','深圳会展中心','2013-02-01','/zhanhui/html/54363_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届深圳国际黄金珠宝玉石展览会','奢侈品','深圳','深圳会展中心','2013-02-28','/zhanhui/html/58990_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届深圳国际物流设备与技术博览会','运输物流','深圳','深圳会展中心','2013-05-01','/zhanhui/html/59529_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届生活用纸国际科技展览及会议','纸业制品','深圳','深圳会展中心','2013-05-31','/zhanhui/html/59516_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届深圳国际智能交通与卫星导航位置服务','交通工具','深圳','深圳会展中心','2013-05-01','/zhanhui/html/59953_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第九届中国（深圳）国际文化产业博览交易会','电子电力','深圳','深圳会展中心','2013-05-31','/zhanhui/html/58993_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第八届中国（深圳）国际品牌内衣展览会','文化教育','深圳','深圳会展中心','2013-05-28','/zhanhui/html/58992_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(深圳)国际胶粘带、保护膜及印刷包装展','服装配饰','深圳','深圳会展中心','2013-05-28','/zhanhui/html/58877_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际触控面板展览会','化工橡塑','深圳','深圳会展中心','2013-05-26','/zhanhui/html/56956_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国（深圳）国际佛事文化用品展览会','光电技术','深圳','深圳会展中心','2013-05-23','/zhanhui/html/57795_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(深圳)专业无线通信展','文化教育','深圳','深圳会展中心','2013-05-17','/zhanhui/html/58339_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国深圳（春季）房地产交易会暨（总第40届','信息通信','深圳','深圳会展中心','2013-05-10','/zhanhui/html/59463_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第4届迎春年货博览会','购物年货','深圳','深圳会展中心','2013-01-01','/zhanhui/html/58775_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十届么集名品折扣购物展','购物年货','深圳','深圳会展中心','2013-01-31','/zhanhui/html/59540_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十四届中国（深圳）国际钟表展览会','奢侈品','深圳','深圳会展中心','2013-06-01','/zhanhui/html/59541_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）钣金工业博览会','机械工业','深圳','深圳会展中心','2013-06-30','/zhanhui/html/59522_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届深圳国际小电机及电机工业展览会','电子电力','深圳','深圳会展中心','2013-06-01','/zhanhui/html/58996_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第17届华南工业控制自动化国际展览会第十','工业制造','深圳','深圳会展中心','2013-06-30','/zhanhui/html/59468_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('国际（深圳）稀土材料、磁性材料及稀土应用','印刷包装','深圳','深圳会展中心','2013-06-27','/zhanhui/html/58997_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际投资贸易洽谈会','创业加盟','深圳','深圳会展中心','2013-06-26','/zhanhui/html/59551_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第五届中国(深圳)国际品牌特许加盟展','创业加盟','深圳','深圳会展中心','2013-06-26','/zhanhui/html/62420_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳（国际）集成电路技术创新与应用展','电子电力','深圳','深圳会展中心','2013-06-26','/zhanhui/html/58995_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳-香港-澳门国际汽车博览会','交通工具','深圳','深圳会展中心','2013-06-26','/zhanhui/html/60505_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届深圳国际机械制造工业展览会暨201','机械工业','深圳','深圳会展中心','2013-03-01','/zhanhui/html/59464_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十八届深圳国际家具展览会','房产家居','深圳','深圳会展中心','2013-03-31','/zhanhui/html/59461_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('（春）深圳国际家纺布艺暨墙纸家居饰品展览','纺织纺机','深圳','深圳会展中心','2013-03-01','/zhanhui/html/59542_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十一届么集名品折扣购物展','购物年货','深圳','深圳会展中心','2013-04-01','/zhanhui/html/59539_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十一届中国（深圳）国际礼品、工艺品、钟表','礼品玩具','深圳','深圳会展中心','2013-04-30','/zhanhui/html/59471_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第69届中国国际医疗器械（春季）博览会','医疗保健','深圳','深圳会展中心','2013-04-01','/zhanhui/html/59353_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国电容器与电阻器展览会','电子电力','深圳','深圳会展中心','2013-04-30','/zhanhui/html/58499_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳国际纺织面料及辅料博览会','电子电力','深圳','深圳会展中心','2013-04-30','/zhanhui/html/59659_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳（春季）婚博会','婚庆婚博','深圳','深圳会展中心','2013-04-25','/zhanhui/html/59548_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国(深圳)国际模型展览会','礼品玩具','深圳','深圳会展中心','2013-04-17','/zhanhui/html/58991_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('多人行国际触摸屏展览会','光电技术','深圳','深圳会展中心','2013-07-01','/zhanhui/html/56064_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('秋季(第82届)中国电子展','纺织纺机','深圳','深圳会展中心','2013-07-31','/zhanhui/html/59387_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳国际LED展览会','光电技术','深圳','深圳会展中心','2013-07-01','/zhanhui/html/60714_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第四届深圳国际户外运动暨骑行装备展览会','体育休闲','深圳','深圳会展中心','2013-09-01','/zhanhui/html/60861_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳国际珠宝展览会','奢侈品','深圳','深圳会展中心','2013-09-30','/zhanhui/html/58580_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十四届中国国际社会公共安全产品博览会','安全防护','深圳','深圳会展中心','2013-10-01','/zhanhui/html/58901_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二十一届中国（深圳）国际礼品及家居用品展览','礼品玩具','深圳','深圳会展中心','2013-10-31','/zhanhui/html/60746_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际烘焙展览会','食品饮料','深圳','深圳会展中心','2013-10-01','/zhanhui/html/60803_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('深圳国际名酒饮品博览会暨国际竞赛','食品饮料','深圳','深圳会展中心','2013-08-01','/zhanhui/html/60369_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际节能减排和新能源科技博览','环保水处理','深圳','深圳会展中心','2013-08-31','/zhanhui/html/59734_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际新能源展览会','能源矿产','深圳','深圳会展中心','2013-08-01','/zhanhui/html/59739_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际节能照明展览会','光电技术','深圳','深圳会展中心','2013-08-31','/zhanhui/html/59736_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('亚洲国际高尔夫球博览会','体育休闲','深圳','深圳会展中心','2013-11-01','/zhanhui/html/60720_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('中国（深圳）国际多媒体互动技术展览会','信息通信','深圳','深圳会展中心','2013-11-30','/zhanhui/html/60385_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十六届深圳国际手机产业展览会暨研讨会','电子电力','深圳','深圳会展中心','2013-11-01','/zhanhui/html/59695_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第三届中国(深圳)国际辐射固化材料技术及设','化工橡塑','深圳','深圳会展中心','2013-11-30','/zhanhui/html/59678_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第十一届中国深圳国际触摸屏展览会','消费电子','深圳','深圳会展中心','2013-11-30','/zhanhui/html/60643_0.html');");
			db.execSQL("INSERT INTO E13CN VALUES('第二届深圳国际酒店设备及用品展览会','酒店用品','深圳','深圳会展中心','2013-11-25','/zhanhui/html/59841_0.html');");

		}
		
		public int getHallCountByCity(SQLiteDatabase db, String city){
			//Log.d(LOG_TAG, "DBHelper.getHallCountByCity");
			int ret = 0;
			
			Cursor hallCursor = db.rawQuery("SELECT COUNT(*)" + 
			" FROM " + DBInterface.E13CNTable.TABLE_NAME +
			" WHERE " + DBInterface.E13CNTable.COLUMN_NAME_CITY + " = ?" + " AND " + DBInterface.E13CNTable.COLUMN_NAME_DATE + " >= (select date('now'))", 
			new String[]{city});
			
	        if (hallCursor == null || hallCursor.getCount() < 1) {
	        	throw new DatabaseUninitialisedException("No Hall found.");
	        }
			
	        hallCursor.moveToFirst();
	        ret = hallCursor.getInt(0);
	        hallCursor.close();
	        Log.d(LOG_TAG, city + " count = " + ret);
	        
	        return ret;
		}
		
		public List<Hall> getHallByCity(SQLiteDatabase db, String city){
			//Log.d(LOG_TAG, "DBHelper.getHallByCity");
			List<Hall> ret = new ArrayList<Hall>();
			
			Cursor hallCursor = db.rawQuery("SELECT " + 
			DBInterface.E13CNTable.COLUMN_NAME_TITLE + ", " +
			DBInterface.E13CNTable.COLUMN_NAME_HALL +  ", " +
			DBInterface.E13CNTable.COLUMN_NAME_DATE + 
			" FROM " + DBInterface.E13CNTable.TABLE_NAME +
			" WHERE " + DBInterface.E13CNTable.COLUMN_NAME_CITY + " = ?" + " AND " + DBInterface.E13CNTable.COLUMN_NAME_DATE + " >= (select date('now'))" +
			" ORDER BY " + DBInterface.E13CNTable.COLUMN_NAME_DATE, new String[]{city});
	        if (hallCursor == null || hallCursor.getCount() < 1) {
	        	throw new DatabaseUninitialisedException("No Hall found.");
	        }
			
	        //Log.d(LOG_TAG, "count = " + hallCursor.getCount());
	        
	        if (hallCursor.moveToFirst()) {
	        	int hallColumnNum = hallCursor.getColumnIndex(DBInterface.E13CNTable.COLUMN_NAME_HALL);
	        	int titleColumnNum = hallCursor.getColumnIndex(DBInterface.E13CNTable.COLUMN_NAME_TITLE);
	        	int dateColumnNum = hallCursor.getColumnIndex(DBInterface.E13CNTable.COLUMN_NAME_DATE);
	        	String title;
	        	String hallName;
	        	String date;
	        	do{
	        		title = hallCursor.getString(titleColumnNum);
	        		hallName = hallCursor.getString(hallColumnNum);
	        		date = hallCursor.getString(dateColumnNum);
	        		//Log.d(LOG_TAG, "title = " + title + " hall = " + hallName + " date = " + date);
	        		ret.add(new Hall(title, hallName, city, date));
	        	}while(hallCursor.moveToNext());
	        }	        

	        int retSize = ret.size();
	        //Log.d(LOG_TAG, "******zhangnan******");
	        //Log.d(LOG_TAG, "retSize = " + retSize);
	        //for(int i=0;i<retSize;i++){
	        //	ret.get(i).debugPrint();
	        //}
	        //Log.d(LOG_TAG, "********************");
	        
	        hallCursor.close();
			return ret;
		}
	}	
}
