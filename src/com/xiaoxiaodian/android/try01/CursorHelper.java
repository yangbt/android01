package com.xiaoxiaodian.android.try01;

import java.util.Date;
import java.util.HashMap;

import android.database.Cursor;


public class CursorHelper {
	
public static String getRow(Cursor cursor){
	String temp1="";
	String[] cn=cursor.getColumnNames();
	for(int i=0;i<cursor.getColumnCount();i++){
		if(i==0)
			temp1=temp1+cn[i]+":"+cursor.getString(i);
		else
			temp1=temp1+MyConst.SPLITER+cn[i]+":"+cursor.getString(i);
	}

	return temp1;
}


public static String getDate(Cursor cursor,String column){
	Date date = new Date(
			cursor.getLong(cursor.getColumnIndex(column)));
		return date.toString();
}

public static boolean isKV(Cursor cursor){
	boolean ret=false;
	if(cursor.getColumnCount()<=3 & cursor.getColumnCount()>1 & cursor.getColumnIndex("_id")>-1) ret=true;
	return ret;
}

public static  HashMap<String, Object> getKV(Cursor cursor){
	HashMap<String, Object> item = new HashMap<String, Object>();
	
	if(cursor.getColumnCount()==2){
		item.put(MyConst.ITEMKEY,cursor.getString(0));
		item.put(MyConst.ITEMVALUE, cursor.getString(1));
	}else if(cursor.getColumnCount()==3 && cursor.getColumnIndex("_id")>-1 ){
		int i=(cursor.getColumnIndex("_id")+1)%3;
		int j=(cursor.getColumnIndex("_id")+2)%3;
		item.put(MyConst.ITEMKEY,cursor.getString(i));
		item.put(MyConst.ITEMVALUE, cursor.getString(j));
	}else{}
	return item;
	}
}
