package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.Map;

public class CommonModelData {
	public static ArrayList<Map<String, Object>> getData(String pSource){
		ArrayList<Map<String, Object>> aData = null;
		aData = ReflectHelper.getClassData(pSource,null,0);
		return aData;
	}
}
