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

public class AllData {

	/*
	 * public static ArrayList<Map<String, Object>> getAndroidOsBuild(){ return
	 * ReflectHelper.getStaticFields("android.os.Build"); }
	 */

	public static ArrayList<Map<String, Object>> getClassData(String pClassname,Object classObject,int aExcludedModifer) {
		//int aExcludedModifer = 0x0;
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		ReflectHelper.getFields(aData, pClassname, classObject,aExcludedModifer);
		ReflectHelper.getAtrtribute(aData, pClassname,classObject);
		return aData;

	}

	
	private static void addItem(List<Map<String, Object>> data, String name,
			String value, String intentdata) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put(MyConst.ITEMKEY, name);
		temp.put(MyConst.ITEMVALUE, value);
		Intent result = new Intent();
		result.putExtra(MyConst.INTENTDATA, intentdata);
		temp.put(MyConst.ITEMINTENT, result);
		data.add(temp);
	}

	public static ListData1 initModelData() {
		ListData1 aListData = new ListData1();
		ArrayList<Map<String, Object>> mModelData = new ArrayList<Map<String, Object>>();
		addItem(mModelData, "Build", "android.os.Build", "android.os.Build");
		addItem(mModelData, "BuildVersion", "android.os.Build.VERSION",
				"android.os.Build$VERSION");
		addItem(mModelData, "Environment", "android.os.Environment",
				"android.os.Environment");
	
		addItem(mModelData, "Settings", "android.provider.Settings$System",
				"android.provider.Settings$System");
		addItem(mModelData, "Telephony", "android.telephony.TelephonyManager",
				"android.telephony.TelephonyManager");
		addItem(mModelData, "Activity", "android.app.ActivityManager",
				"android.app.ActivityManager");
		addItem(mModelData, "Runtime", "java.lang.Runtime", "java.lang.Runtime");
		addItem(mModelData, "Browser", "android.provider.Browser",
				"android.provider.Browser");
		addItem(mModelData, "Permission", "android.Manifest$permission",
				"android.Manifest$permission");
		addItem(mModelData, "Permission", "android.Manifest$permission_group",
				"android.Manifest$permission_group");
		addItem(mModelData, "Configuration", "android.content.res.Configuration",
				"android.content.res.Configuration");
		addItem(mModelData, "DisplayMetrics", "android.util.DisplayMetrics",
				"android.util.DisplayMetrics");
		
		addItem(mModelData, "Calls", "android.provider.CallLog.Call",
				"android.provider.CallLog.Call");		
		addItem(mModelData, "Calls", "android.provider.Settings.Secure",
				"android.provider.Settings.Secure");	
		
		aListData.setData(mModelData);
		aListData.dataCategory = MyConst.CATIINTENT;
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
		}
		else
			aData = getClassData(target,null,0);
		return aData;
	}

	private static ArrayList<Map<String, Object>> getSystemSetting() {
		int aExcludedModifer = 0x0;
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		String aTemp = null;
		HashMap<String, Object> item = null;

		// Class
		// b=ReflectHelper.getInnerClass("android.provider.Settings","System");
		ReflectHelper.getFields(aData, MyConst.SETTINGCLASS,null, aExcludedModifer);

		// get content by getString() method
		for (Map<String, Object> i : aData) {
			Field f = (Field) (i.get(MyConst.ITEMEXT1));
			if (ReflectHelper.isPublicStaticFinal(f)
					&& ReflectHelper.isString(f)) {
				aTemp = android.provider.Settings.System.getString(
						ct.getContentResolver(),
						(String) i.get(MyConst.ITEMVALUE));
				item = new HashMap<String, Object>();
				item.put(MyConst.ITEMKEY, i.get(MyConst.ITEMKEY));
				item.put(MyConst.ITEMVALUE, aTemp);
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
		 
		 aData2=getClassData("android.content.res.Configuration",conf,modifier);
		 return aData2;
		
	}
	
	private static ArrayList<Map<String, Object>> getDisplayMetrics(){
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();		
		int modifier=Modifier.STATIC;
		 Resources res = ct.getResources();
		  DisplayMetrics dm = res.getDisplayMetrics();
		  		 
		 aData2=getClassData("android.util.DisplayMetrics",dm,modifier);
		 return aData2;
		
	}
}
