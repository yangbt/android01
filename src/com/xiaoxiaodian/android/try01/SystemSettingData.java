package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

public class SystemSettingData {
	public static ArrayList<Map<String, Object>> getData(String pSource) {

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
			item.put(Item.ITEM_TITLE, mCursor.getString(1));
			item.put(Item.ITEM_DESC, mCursor.getString(2));
			aData2.add(item);
		}
	}

	// add other fields
	item = new HashMap<String, Object>();
	item.put(Item.ITEM_TITLE, "CONTENT_URI");
	item.put(Item.ITEM_DESC,
			android.provider.Settings.System.CONTENT_URI.toString());
	aData2.add(item);

	return aData2;
}
	
}
