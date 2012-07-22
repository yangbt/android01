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
	public static ArrayList<Map<String, Object>> getConfig() {
		ArrayList<Map<String, Object>> aListData = Config.getConfigData();		
		return aListData;		
	}
*/
	public static void addItem(ArrayList<Map<String, Object>> aData,String k,Object v)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(MyConst.ITEM_KEY, k);
		item.put(MyConst.ITEM_VALUE, v);
				aData.add(item);
	}
	
	public static void addItem(ArrayList<Map<String, Object>> aData,String k,Object v,Object o)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(MyConst.ITEM_KEY, k);
		item.put(MyConst.ITEM_VALUE, v);
		item.put(MyConst.ITEM_OBJECT, o);
		aData.add(item);
	}


	
	public static MyDataSet initModelDataA( Item data) {
		MyDataSet aData=new MyDataSet();	
		if (data == null || (data.mObject==null && data.mClassname==null))
			return aData;
		Object[] aParam=data.mParam;
		String aClass=data.mClassname;
		String aMethod=data.mMembername;
		aData = getData(aParam,aClass,aMethod);
		ArrayList<Item> i= data.getParent();
		if(i==null)i=new ArrayList<Item>();
		i.add(data);
		if(aData!=null) aData.setParent(i);
		return aData;
	}
	
	public static MyDataSet initModelData( Item data) {
		MyDataSet ads=null;
		if(data==null){
			//first page
			ads=Config.getConfigData();
		}else if(data.mObject== null & data.mMembername!=null){
			ads=initModelDataA(data);
		}else if(data.mObject!=null){
			ads=initModelDataB(data);
		}
		/*
		if(type==MyConst.BUNDLE_CAT_CAT) aData=initModelDataA(data);
		else if(type==MyConst.BUNDLE_CAT_OBJECT) aData=initModelDataB(data);
		*/
		
		return ads;
	}
	
	
	public static MyDataSet initModelDataB( Item data) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();	
		MyDataSet b=new MyDataSet();
		
		if (data == null || data.mObject==null)
			return b;
		Object aObject=data.mObject;
		
		aData=ReflectHelper.getClassData( aObject.getClass(), aObject,0);	
		
		for(Map<String, Object> m:aData){
			String k=(String)m.get(MyConst.ITEM_KEY);
			String v=(String)m.get(MyConst.ITEM_VALUE);
			Object o=m.get(MyConst.ITEM_OBJECT);
			b.add(new Item(k,v,o));
		}
		ArrayList<Item> i= data.getParent();
		if(i==null)i=new ArrayList<Item>();
		i.add(data);
		if(b!=null) b.setParent(i);
		return b;
	}
	
	
	public static MyDataSet getData(Object[] pParam,String pClass,String pMethod) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();	
		MyDataSet b=new MyDataSet();
		Object object= ReflectHelper.invokeMethod(pClass, pMethod, pParam);
		if(object !=null){
			Class cl=aData.getClass();
			Class clb=object.getClass();
			if(object instanceof MyDataSet) {
				b=(MyDataSet)object;
			}else if(cl.isAssignableFrom(clb)){
				aData=(ArrayList<Map<String, Object>>) object;
				for(Map<String, Object> m:aData){
					String k=(String)m.get(MyConst.ITEM_KEY);
					String v=(String)m.get(MyConst.ITEM_VALUE);
					Object o=m.get(MyConst.ITEM_OBJECT);
					b.add(new Item(k,v,o));
				}
			}
	
		}
	
		return b;
	}
	

}
