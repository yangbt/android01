package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Item {
	public static final int TYPE_MAP = 1;
	public int mDataType = 0;
	private Map<String, Object> mMap = null;
	public String mTitle=null;
	public String mDesc=null;
	public String mClassname=null;
	public Object mObject=null;
	public String mMembername=null;
	public Object[] mParam=null;
	public String mMemberType=null;
	private ArrayList<Item> mParentItem=null;
	
	public Item(String pTitle,String pDesc,Object[] pParam,
			String pClassname,Object pObject,String pMembername,
			String pMemberType ) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(MyConst.BUNDLE_ITEM_TITILE,pTitle);
		aItem.put(MyConst.BUNDLE_ITEM_DESC,pDesc);
		aItem.put(MyConst.BUNDLE_CLASS_NAME,pClassname);
		aItem.put(MyConst.BUNDLE_OBJECT,pObject);
		aItem.put(MyConst.BUNDLE_MEMBER_NAME,pMembername);
		aItem.put(MyConst.BUNDLE_PARAMETER,pParam);
		aItem.put(MyConst.BUNDLE_MEMBER_TYPE,pMemberType);			
		aItem.put(MyConst.ITEM_ROWITEM, this);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mClassname=pClassname;
		 mObject=pObject;
		 mMembername=pMembername;
		 mParam=pParam;
		 mMemberType=pMemberType;		
		this.mMap=aItem;
	}

	public Item(String pTitle,String pDesc,
			String pClassname,Object pObject) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(MyConst.BUNDLE_ITEM_TITILE,pTitle);
		aItem.put(MyConst.BUNDLE_ITEM_DESC,pDesc);
		aItem.put(MyConst.BUNDLE_CLASS_NAME,pClassname);
		aItem.put(MyConst.BUNDLE_OBJECT,pObject);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mClassname=pClassname;
		 mObject=pObject;
		this.mMap=aItem;
	}
	
	public Item(String pTitle,String pDesc,Object pObject) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(MyConst.BUNDLE_ITEM_TITILE,pTitle);
		aItem.put(MyConst.BUNDLE_ITEM_DESC,pDesc);	
		aItem.put(MyConst.BUNDLE_OBJECT,pObject);			
		aItem.put(MyConst.ITEM_ROWITEM, this);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mObject=pObject;		
		this.mMap=aItem;
	}
	
	public void setParent(ArrayList<Item> p){
		this.mParentItem=p;
	}
	
	public ArrayList<Item> getParent(){
		return this.mParentItem;
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
	
	public Map<String, Object> getData() {
		return this.mMap;
	}

	public int getType() {
		return this.mDataType;
	}
	
	public void setType(int pDataType) {
		this.mDataType = pDataType;
	}
}
