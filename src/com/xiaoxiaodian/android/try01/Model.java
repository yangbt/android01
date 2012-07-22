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
import android.os.Bundle;
import android.util.DisplayMetrics;

public class Model {

	/*
	 * public static ArrayList<Map<String, Object>> getConfig() {
	 * ArrayList<Map<String, Object>> aListData = Config.getConfigData(); return
	 * aListData; }
	 */
	public static void addItem(ArrayList<Map<String, Object>> aData, String k,
			Object v) {
		Map<String, Object> item = new HashMap<String, Object>();
		item.put(Item.ITEM_TITLE, k);
		item.put(Item.ITEM_DESC, v);
		aData.add(item);
	}

	public static void addItem(ArrayList<Map<String, Object>> aData, String k,
			Object v, Object o) {
		Map<String, Object> item = new HashMap<String, Object>();
		item.put(Item.ITEM_TITLE, k);
		item.put(Item.ITEM_DESC, v);
		item.put(Item.ITEM_MEMBER_VALUE, o);
		aData.add(item);
	}

	public static MyDataSet getMethodValue(Item data) {
		Class<?> aClass = null;
		Object[] aParam = data.mParam;
		Object aObject = data.mBelongToObject;
		MyDataSet aData = new MyDataSet();
		if (data == null
				|| (data.mBelongToObject == null && data.mDeclaringClass == null)
				|| data.mMember == null)
			return aData;

		if (data.mBelongToObject == null) {
			aClass = data.mDeclaringClass;
		} else {
			aClass = data.mBelongToObject.getClass();
		}
		Method aMethod = (Method) data.mMember;
		aData = getMethodReturnValue(aClass, aObject, aMethod, aParam);		
		ArrayList<Item> i = data.getParent();
		if (i == null)
			i = new ArrayList<Item>();
		i.add(data);
		if (aData != null)
			aData.setParent(i);
		return aData;
	}

	public static MyDataSet getData(Item data) {
		MyDataSet ads = null;
		if (data == null) {
			// first page
			ads = Config.getConfigData();
		} else if (data.mMemberValue == null && data.mMember != null
				&& (data.mMember instanceof Method)) {
			ads = getMethodValue(data);
		} else if (data.mMemberValue != null) {
			ads = getObjectMember(data);
		}
		/*
		 * if(type==MyConst.BUNDLE_CAT_CAT) aData=initModelDataA(data); else
		 * if(type==MyConst.BUNDLE_CAT_OBJECT) aData=initModelDataB(data);
		 */

		return ads;
	}

	public static MyDataSet getObjectMember(Item data) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		MyDataSet b = new MyDataSet();

		if (data == null || data.mMemberValue == null)
			return b;
		Object aObject = data.mMemberValue;

		aData = ReflectHelper.getClassData(aObject.getClass(), aObject, 0);

		for (Map<String, Object> m : aData) {
			String k = (String) m.get(Item.ITEM_TITLE);
			String v = (String) m.get(Item.ITEM_DESC);
			Object o = m.get(Item.ITEM_MEMBER_VALUE);
			b.add(new Item(k, v, o));
		}
		ArrayList<Item> i = data.getParent();
		if (i == null)
			i = new ArrayList<Item>();
		i.add(data);
		if (b != null)
			b.setParent(i);
		return b;
	}

	public static MyDataSet getMethodReturnValue(Class<?> pClass,
			Object pObject, Method pMethod, Object[] pParam) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		MyDataSet b = new MyDataSet();

		Object object = ReflectHelper.invokeMethod(pClass, pObject, pMethod,
				pParam);
		if (object != null) {
			Class cl = aData.getClass();
			Class clb = object.getClass();
			if (object instanceof MyDataSet) {
				b = (MyDataSet) object;
			} else if (cl.isAssignableFrom(clb)) {
				aData = (ArrayList<Map<String, Object>>) object;
				for (Map<String, Object> m : aData) {
					String k = (String) m.get(Item.ITEM_TITLE);
					String v = (String) m.get(Item.ITEM_DESC);
					Object o = m.get(Item.ITEM_MEMBER_VALUE);
					b.add(new Item(k, v, o));
				}
			}
		}
		return b;
	}
}
