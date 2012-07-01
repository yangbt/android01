package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Map;

public class ListData1 {
	private ArrayList<Map<String, Object>> mModelData = null;  
	public int dataCategory=0;
	
	public void setData(ArrayList<Map<String, Object>> pModelData ){
		this.mModelData=pModelData;
		}
	public ArrayList<Map<String, Object>> getData( ){
		return this.mModelData;
		}
}
