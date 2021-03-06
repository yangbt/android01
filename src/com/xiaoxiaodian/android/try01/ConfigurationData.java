package com.xiaoxiaodian.android.try01;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ConfigurationData {
	public static ArrayList<Map<String, Object>> getData(String pSource){
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();		
		int modifier=Modifier.STATIC;
		 Resources res = ct.getResources();
		  DisplayMetrics dm = res.getDisplayMetrics();
		  
		 android.content.res.Configuration conf = res.getConfiguration();
		 
		 aData2=ReflectHelper.getClassData("android.content.res.Configuration",conf,modifier);
		 return aData2;
		
	}
	
}
