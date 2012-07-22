package com.xiaoxiaodian.android.try01;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Item {
	public static final int TYPE_MAP = 1;
	
	public static final String ITEM_TITLE="ITEM_TITLE";
	public static final String ITEM_DESC="ITEM_DESC";
	
	
	public static final String ITEM_MEMBER_VALUE="ITEM_MEMBER_VALUE";
	public static final String ITEM_MEMBER="ITEM_MEMBER";
	public static final String ITEM_DECLARING_CLASS="ITEM_DECLARING_CLASS";
	public static final String ITEM_BELONGTOOBJECT="ITEM_BELONGTOOBJECT";
	
	public static final String ITEM_PARAMETER="ITEM_PARAMETER";
	public static final String ITEM_OBJECT_KEY="ITEM_OBJECT_KEY";
	
	//public static final String ITEM_PARAMETER_INDEX="BUNDLE_PARAMETER_INDEX";
	//public static final String BUNDLE_MEMBER_TYPE="BUNDLE_MEMBER_TYPE";
	//public static final String BUNDLE_ITEMOBJECT_INDEX="BUNDLE_ITEMOBJECT_INDEX";
	
	//public static final String BUNDLE_MEMBER_NAME="BUNDLE_MEMBER_NAME";
	
	public int mDataType = 0;
	private Map<String, Object> mMap = null;
	public String mTitle=null;
	public String mDesc=null;
	
	/****/
	public Object mMemberValue=null;
	public Member mMember=null;
	public Object mBelongToObject=null;
	public Class<?> mDeclaringClass=null;
	
	public Object[] mParam=null;
//	public String mMemberType=null;
	private ArrayList<Item> mParentItem=null;
	
	public Item(String pTitle,String pDesc,Object[] pParam,
			Class<?> pClass,Object pBelongToObject,Member pMember) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(ITEM_TITLE,pTitle);
		aItem.put(ITEM_DESC,pDesc);
		
		aItem.put(ITEM_DECLARING_CLASS,pClass);
		aItem.put(ITEM_BELONGTOOBJECT,pBelongToObject);
		
		aItem.put(ITEM_MEMBER,pMember);
		aItem.put(ITEM_PARAMETER,pParam);
	//	aItem.put(MyConst.BUNDLE_MEMBER_TYPE,pMemberType);			
		aItem.put(MyConst.ITEM_ROWITEM, this);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mDeclaringClass=pClass;
		 mBelongToObject=pBelongToObject;
		 mMember=pMember;
		 mParam=pParam;
	//	 mMemberType=pMemberType;		
		this.mMap=aItem;
	}

	public Item(String pTitle,String pDesc,
			Class<?> pClass,Object pMemeberValue) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(ITEM_TITLE,pTitle);
		aItem.put(ITEM_DESC,pDesc);
		aItem.put(ITEM_DECLARING_CLASS,pClass);
		aItem.put(ITEM_MEMBER_VALUE,pMemeberValue);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mDeclaringClass=pClass;
		 mMemberValue=pMemeberValue;
		this.mMap=aItem;
	}
	
	public Item(String pTitle,String pDesc,Object pMemeberValue) {
		this.mDataType=TYPE_MAP;
		Map<String, Object> aItem = new HashMap<String, Object>();		
		aItem.put(ITEM_TITLE,pTitle);
		aItem.put(ITEM_DESC,pDesc);	
		aItem.put(ITEM_MEMBER_VALUE,pMemeberValue);			
		aItem.put(MyConst.ITEM_ROWITEM, this);
		 mTitle=pTitle;
		 mDesc=pDesc;
		 mMemberValue=pMemeberValue;		
		this.mMap=aItem;
	}
	
	public void setParent(ArrayList<Item> p){
		this.mParentItem=p;
	}
	
	public ArrayList<Item> getParent(){
		return this.mParentItem;
	}
	public boolean noMoreExplore(){
		boolean result=false;
		if(this.mMember==null)
			if(this.mMemberValue==null || ReflectHelper.isSimpleType(this.mMemberValue))
		{
			result=true;
		}
		return result;
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
