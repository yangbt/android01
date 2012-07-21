package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

public class BrowserData {
	public static ArrayList<Map<String, Object>> getData(String pSource) {
		ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
		Context ct = MyApplication.getAppContext();
		HashMap<String, Object> item = null;
		Cursor mCursor = null;
		String temp = null;
		String temp1 = null;
		Date date = null;

		// add other fields
		item = new HashMap<String, Object>();
		item.put(MyConst.ITEM_KEY, "android.provider.Browser.BOOKMARKS_URI");
		item.put(MyConst.ITEM_VALUE,
				android.provider.Browser.BOOKMARKS_URI.toString());
		aData2.add(item);
		mCursor = ct.getContentResolver().query(
				android.provider.Browser.BOOKMARKS_URI,
				android.provider.Browser.HISTORY_PROJECTION, null, null, null);
		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				item = new HashMap<String, Object>();
				date = new Date(
						mCursor.getLong(android.provider.Browser.HISTORY_PROJECTION_DATE_INDEX));
				temp = FormatHelper.formatDate(date);
				

				temp1 =  mCursor
						.getString(android.provider.Browser.HISTORY_PROJECTION_TITLE_INDEX)
						+ MyConst.SPLITER
						+ "visits:"
						+ mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_VISITS_INDEX)
						+ MyConst.SPLITER
						+ "id:"+mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_ID_INDEX)
						+ MyConst.SPLITER
						+ mCursor
								.getString(android.provider.Browser.HISTORY_PROJECTION_URL_INDEX);

				item.put(MyConst.ITEM_KEY, temp);
				item.put(MyConst.ITEM_VALUE, temp1);
				aData2.add(item);
			}
			mCursor.close();
		}

		item.put(MyConst.ITEM_KEY, "android.provider.Browser.SEARCHES_URI");
		item.put(MyConst.ITEM_VALUE,
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
				date = new Date(
						mCursor.getLong(android.provider.Browser.HISTORY_PROJECTION_DATE_INDEX));
				temp = FormatHelper.formatDate(date);

				temp1 = mCursor
						.getString(android.provider.Browser.SEARCHES_PROJECTION_SEARCH_INDEX);
				item.put(MyConst.ITEM_KEY, temp);
				item.put(MyConst.ITEM_VALUE, temp1);
				aData2.add(item);
			}
			mCursor.close();
		}

		return aData2;
	}

}
