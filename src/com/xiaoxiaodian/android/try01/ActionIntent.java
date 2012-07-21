package com.xiaoxiaodian.android.try01;

import android.content.Intent;
import android.os.Bundle;

public class ActionIntent {
	public static final int TYPE_MAP = 1;

	//public int mDataType = 0;
	private Intent mIntent = null;
	

	/****
	 * 
	 * @param pClassname
	 * @param pObject
	 * @param pMembername
	 * @param pParam
	 * @param pMemberType is a method or field?
	 * @return
	 */
	/*
	private Bundle createBundle(String pClassname,Object pObject,String pMembername,Object pParam,String pMemberType){
			
		Bundle bundle=new Bundle();
		bundle.putString(MyConst.BUNDLE_CLASS_NAME,pClassname);	
		bundle.putString(MyConst.BUNDLE_MEMBER_TYPE,pMemberType);
		bundle.putString(MyConst.BUNDLE_MEMBER_NAME,pMembername);	
		bundle.putString(MyConst.BUNDLE_PARAMETER_INDEX,GlobalObject.getIntance().setObject(pParam));		
		//bundle.putInt(MyConst.BUNDLE_CAT_NAME,MyConst.BUNDLE_CAT_CAT);
		bundle.putString(MyConst.BUNDLE_OBJECT_KEY,GlobalObject.getIntance().setObject(pObject));	
	
		return bundle;
	}
	*/
		
	private Bundle createBundle(Item p){
		
		Bundle bundle=new Bundle();
		bundle.putString(MyConst.BUNDLE_ITEMOBJECT_INDEX,GlobalObject.getIntance().setObject(p));		
		return bundle;
	}
	
	public  ActionIntent(Item p) {
		//this.mDataType=TYPE_MAP;
			
		//construct the item's action intent;
		Bundle aBundle=createBundle(p);
		Intent aActionIntent = new Intent();	
		aActionIntent.putExtra(MyConst.INTENT_DATA, aBundle);
		this.mIntent=aActionIntent;
	}
	
	public static Item getItem(Intent p){
		Item ri=null;
		Bundle data = p.getBundleExtra(MyConst.INTENT_DATA);
		if(data==null) return null;
		String index=data.getString(MyConst.BUNDLE_ITEMOBJECT_INDEX);
		if(index!=null){
		Object a=GlobalObject.getIntance().getObject(index);
		if(a!=null & a instanceof Item) ri=(Item)a;
		}
		return ri;
	}
	
	/*
	public  MyActionIntent(String pClassname,Object pClassObject,String pMembername,Object pParam,String pMemberType) {
		//this.mDataType=TYPE_MAP;
			
		//construct the item's action intent;
		Bundle aBundle=createBundle(pClassname, pClassObject, pMembername, pParam, pMemberType);
		Intent aActionIntent = new Intent();	
		aActionIntent.putExtra(MyConst.INTENT_DATA, aBundle);
		this.mIntent=aActionIntent;
	}
	*/
	
	public Intent getData() {
		return this.mIntent;
	}

/*	public int getType() {
		return this.mDataType;
	}
	
	public void setType(int pDataType) {
		this.mDataType = pDataType;
	}*/
}
