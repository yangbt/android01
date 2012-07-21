package com.xiaoxiaodian.android.try01;

import java.util.HashMap;
import java.util.Map;

//not store null object;
public class GlobalObject {

	//this item used to set/get global object which key is transfered in intent in same process.
		private  Map<String,Object> mGlobalMap=new HashMap<String,Object>();
		private  static GlobalObject mInstance=new GlobalObject();
		private GlobalObject(){
			
		}
				
		public static GlobalObject getIntance(){
			return mInstance;
		}
		
		public Object getObject(String pIndex){
			return mGlobalMap.get(pIndex);
		}
		
		public String setObject(Object pObject){
			String index=getGlobalIndex(pObject);
			if(index!=null) 
			mGlobalMap.put(index, pObject);
			return index;
		}
		private static String getGlobalIndex(Object pObject){
			String aObjectGlobalIndex=null;
			if(pObject!=null)
			      aObjectGlobalIndex="HashCode:"+Integer.toHexString(pObject.hashCode());
			return aObjectGlobalIndex;
		}
		
}
