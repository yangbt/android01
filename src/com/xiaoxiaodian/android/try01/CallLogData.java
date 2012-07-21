package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog.Calls;

public class CallLogData {

	public static ArrayList<Map<String, Object>> getData(String pSource) {
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		HashMap<String, Object> item = null;
		Cursor aCursor = null;
		String temp = "";
		String temp1 = "";
	
		item = new HashMap<String, Object>();
		item.put(MyConst.ITEM_KEY, "android.provider.CallLog.Calls.CONTENT_URI");
		item.put(MyConst.ITEM_VALUE,
				android.provider.CallLog.Calls.CONTENT_URI.toString());
		aData2.add(item);

		try {
			aCursor = ct.getContentResolver().query(
					android.provider.CallLog.Calls.CONTENT_URI, null, null,
					null, Calls.DEFAULT_SORT_ORDER);
			if (aCursor != null) {

				while (aCursor.moveToNext()) {
					item = new HashMap<String, Object>();
					temp = CursorHelper.getDate(aCursor, Calls.DATE);

					temp1 = CursorHelper.getRow(aCursor);

					item.put(MyConst.ITEM_KEY, temp);
					item.put(MyConst.ITEM_VALUE, temp1);
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
