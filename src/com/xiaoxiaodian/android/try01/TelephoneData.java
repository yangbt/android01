package com.xiaoxiaodian.android.try01;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.CallLog.Calls;
import android.telephony.TelephonyManager
;
import android.util.DisplayMetrics;

public class TelephoneData {

	public static ArrayList<Map<String, Object>> getData(String pSource) {
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		
		
		TelephonyManager tel=(TelephonyManager)ct.getSystemService(Context.TELEPHONY_SERVICE);
		int exModifier=Modifier.STATIC;
			 
		aData2=ReflectHelper.getClassData("android.telephony.TelephonyManager",tel,exModifier);
		
		return aData2;
	}

}
