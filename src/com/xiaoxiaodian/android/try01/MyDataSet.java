package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Map;

public class MyDataSet {
	//public static final int TYPE_ARRAY_MAP = 1;
	//public int mDataType = 0;
	private ArrayList<Map<String, Object>> mArrayMap = null;
	private String[] mFrom=null;
	private ArrayList<Item> mParentItem=null;
	public MyDataSet(){
		mArrayMap = new ArrayList<Map<String, Object>>();
		mFrom=new String[] { MyConst.BUNDLE_ITEM_TITILE,
				MyConst.BUNDLE_ITEM_DESC };
	}

	public void setParent(ArrayList<Item> p){
		this.mParentItem=p;
	}
	
	public String getParentString(){
		String s="";
		int c=0;
		if(this.mParentItem!=null){
			 c=this.mParentItem.size();
			 if(c>=3){
				 s=this.mParentItem.get(c-3).mTitle+">"+this.mParentItem.get(c-2).mTitle+">"+this.mParentItem.get(c-1).mTitle;
			 }else if(c==2){
				 s=this.mParentItem.get(c-2).mTitle+">"+this.mParentItem.get(c-1).mTitle;
			 }else if(c==1){
				 s=this.mParentItem.get(c-1).mTitle;
			 }
		}
		return s;
		
	}
	public ArrayList<Item> getParent(){
		return this.mParentItem;
	}
	public void add(Item pItem){
		mArrayMap.add(pItem.getData());
	}
	public String[] getFrom(){
		return mFrom;
	}
	
	public ArrayList<Map<String, Object>> getData() {
		return this.mArrayMap;
	}
	
	public void setData(ArrayList<Map<String, Object>>  pArrayMap) {
		this.mArrayMap = pArrayMap;
	}

	/*	
	public void setData(ArrayList<Map<String, Object>> pArrayMap, int pDataType) {
		this.mArrayMap = pArrayMap;
		this.mDataType = pDataType;
	}
	
	public int getType() {
		return this.mDataType;
	}
	
	public void setType(int pDataType) {
		this.mDataType = pDataType;
	}*/
}
