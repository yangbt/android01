package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.database.Cursor;

public class SystemPropertyData {
	public static ArrayList<Map<String, Object>> getData(String pSource) {

	ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
	
	HashMap<String, Object> item = null;
	
	
	Properties props = System.getProperties();

	Iterator iter=props.entrySet().iterator();
	
	while(iter.hasNext()){
		Map.Entry<?, ?> entry=(Map.Entry<?, ?>)iter.next();
		String key = entry.getKey().toString();
		Object val = entry.getValue();
		String s=val.toString(); //todo fix it;
		
		addItem(aData2,key,s,val);
	
	}

	return aData2;
}
	
	private static void addItem(ArrayList<Map<String, Object>> aData,String k,String v,Object o)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(MyConst.ITEM_KEY, k);
		item.put(MyConst.ITEM_VALUE, v);
		item.put(MyConst.ITEM_OBJECT, o);
		aData.add(item);
	}
}
