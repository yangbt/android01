package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog.Calls;

public class SettingSecureData {

	public static ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		HashMap<String, Object> item = null;
		Cursor aCursor = null;
		String temp = "";
		String temp1 = "";
	
		item = new HashMap<String, Object>();
		item.put(MyConst.ITEMKEY, "android.provider.Settings.Secure.CONTENT_URI");
		item.put(MyConst.ITEMVALUE,
				android.provider.Settings.Secure.CONTENT_URI.toString());
		aData2.add(item);

		try {
			aCursor = ct.getContentResolver().query(
					android.provider.Settings.Secure.CONTENT_URI, null, null,
					null, null);
			if (aCursor != null) {
				while (aCursor.moveToNext()) {
					item =CursorHelper.getKV(aCursor);
					aData2.add(item);
				}
			}
		} catch (Exception ex) {

		} finally {
			if (aCursor != null)
				aCursor.close();
		}

		return aData2;
	}

}
