package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.*;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.DisplayMetrics;

public class Model {

	/*
	 * public static ArrayList<Map<String, Object>> getAndroidOsBuild(){ return
	 * ReflectHelper.getStaticFields("android.os.Build"); }
	 */



	


	public static ListData1 initModelData() {
		ListData1 aListData = ConfigData.getConfigData();
		
		return aListData;		
	}

	public static ArrayList<Map<String, Object>> getData(String target) {
		ArrayList<Map<String, Object>> aData = null;	
		if (target.equalsIgnoreCase(MyConst.SETTINGCLASS)) {
			aData = getSystemSettingB();
		} else if (target.equalsIgnoreCase(MyConst.BROWSER)) {
			aData = getBrowserData();
		} else if(target.equalsIgnoreCase(MyConst.CONFIG)){
			aData=getConfiguration();
		} else if(target.equalsIgnoreCase(MyConst.DISPLAY)){
			aData=getDisplayMetrics();
		}else if(target.equalsIgnoreCase(MyConst.CALLS)){
			aData=CallLogData.getData();
		}else if(target.equalsIgnoreCase(MyConst.SECURE)){
			aData=SettingSecureData.getData();
		}else if(target.equalsIgnoreCase(MyConst.TEL)){
			aData=TelephoneData.getData();
		}
		else
			aData = ReflectHelper.getClassData(target,null,0);
		return aData;
	}

		private static ArrayList<Map<String, Object>> getSystemSettingB() {

		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		HashMap<String, Object> item = null;
		Cursor mCursor = null;
		mCursor = ct.getContentResolver().query(
				android.provider.Settings.System.CONTENT_URI, null, null, null,
				null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				item = new HashMap<String, Object>();
				item.put(MyConst.ITEMKEY, mCursor.getString(1));
				item.put(MyConst.ITEMVALUE, mCursor.getString(2));
				aData2.add(item);
			}
		}

		// add other fields
		item = new HashMap<String, Object>();
		item.put(MyConst.ITEMKEY, "CONTENT_URI");
		item.put(MyConst.ITEMVALUE,
				android.provider.Settings.System.CONTENT_URI.toString());
		aData2.add(item);

		return aData2;
	}

	private static ArrayList<Map<String, Object>> getBrowserData() {
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		HashMap<String, Object> item = null;
		Cursor mCursor = null;
		String temp = null;
		String temp1 = null;
		Date  date=null;

		// add other fields
		item = new HashMap<String, Object>();
		item.put(MyConst.ITEMKEY, "android.provider.Browser.BOOKMARKS_URI");
		item.put(MyConst.ITEMVALUE,
				android.provider.Browser.BOOKMARKS_URI.toString());
		aData2.add(item);
		mCursor = ct.getContentResolver().query(
				android.provider.Browser.BOOKMARKS_URI,
				android.provider.Browser.HISTORY_PROJECTION, null, null, null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				item = new HashMap<String, Object>();
				temp = mCursor
						.getString(android.provider.Browser.HISTORY_PROJECTION_TITLE_INDEX);
				date=new Date(mCursor.
						getLong(android.provider.Browser.HISTORY_PROJECTION_DATE_INDEX));
				temp1 = "date:"+date.toString()
						+ MyConst.SPLITER
						+ "cout:"+mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_VISITS_INDEX)
						+ MyConst.SPLITER
						+ mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_ID_INDEX)
						+ MyConst.SPLITER
						+ mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_URL_INDEX)
						;
				item.put(MyConst.ITEMKEY, temp);
				item.put(MyConst.ITEMVALUE, temp1);
				aData2.add(item);
			}
			mCursor.close();
		}

		item.put(MyConst.ITEMKEY, "android.provider.Browser.SEARCHES_URI");
		item.put(MyConst.ITEMVALUE,
				android.provider.Browser.SEARCHES_URI.toString());
		aData2.add(item);
		mCursor = ct.getContentResolver().query(
				android.provider.Browser.SEARCHES_URI,
				android.provider.Browser.SEARCHES_PROJECTION, null, null, null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				item = new HashMap<String, Object>();
				temp = mCursor
						.getString(android.provider.Browser.SEARCHES_PROJECTION_DATE_INDEX);
				date=new Date(mCursor.
						getLong(android.provider.Browser.HISTORY_PROJECTION_DATE_INDEX));
				temp=date.toString();
				
				temp1 = mCursor
						.getString(android.provider.Browser.SEARCHES_PROJECTION_SEARCH_INDEX);
				item.put(MyConst.ITEMKEY, temp);
				item.put(MyConst.ITEMVALUE, temp1);
				aData2.add(item);
			}
			mCursor.close();
		}

		return aData2;
	}

	private static ArrayList<Map<String, Object>> getConfiguration(){
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();		
		int modifier=Modifier.STATIC;
		 Resources res = ct.getResources();
		  DisplayMetrics dm = res.getDisplayMetrics();
		  
		 android.content.res.Configuration conf = res.getConfiguration();
		 
		 aData2=ReflectHelper.getClassData("android.content.res.Configuration",conf,modifier);
		 return aData2;
		
	}
	
	private static ArrayList<Map<String, Object>> getDisplayMetrics(){
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();		
		int modifier=Modifier.STATIC;
		 Resources res = ct.getResources();
		  DisplayMetrics dm = res.getDisplayMetrics();
		  		 
		 aData2=ReflectHelper.getClassData("android.util.DisplayMetrics",dm,modifier);
		 return aData2;
		
	}
}
