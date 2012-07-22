package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import android.content.Context;
import android.database.Cursor;

public class SystemEnvData {
	public static ArrayList<Map<String, Object>> getData(String pSource) {

	ArrayList<Map<String, Object>> aData2 = new ArrayList<Map<String, Object>>();
	
	HashMap<String, Object> item = null;
	
	
	Map<String,String> props = System.getenv();

	Iterator iter=props.entrySet().iterator();
	
	while(iter.hasNext()){
		Map.Entry<String, String> entry=(Map.Entry<String, String>)iter.next();
		String key = entry.getKey().toString();
		String s = entry.getValue();
		
		
		addItem(aData2,key,s,s);
	
	}

	return aData2;
}
	
	private static void addItem(ArrayList<Map<String, Object>> aData,String k,String v,Object o)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(Item.ITEM_TITLE, k);
		item.put(Item.ITEM_DESC, v);
		item.put(Item.ITEM_MEMBER_VALUE, o);
		aData.add(item);
	}
}
