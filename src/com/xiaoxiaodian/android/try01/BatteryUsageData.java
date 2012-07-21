package com.xiaoxiaodian.android.try01;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Parcel;
import android.provider.CallLog.Calls;
import android.util.Log;
import android.util.SparseArray;

public class BatteryUsageData {

	
	public static ArrayList<Map<String, Object>> getData(String pSource) {
		// Context ct = MyApplication.getAppContext();
		// PackageManager mPm;
		// mPm = ct.getPackageManager();

		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// HashMap<String, Object> item = null;

		try {

			// 获得ServiceManager类
			Class<?> ServiceManager = Class
					.forName("android.os.ServiceManager");

			// 获得ServiceManager的getService方法
			Method getService = ServiceManager.getMethod("getService",
					java.lang.String.class);

			// 调用getService获取RemoteService
			Object oRemoteService = getService.invoke(null, "batteryinfo");

			// 获得IBatteryStats.Stub类
			Class<?> cStub = Class
					.forName("com.android.internal.app.IBatteryStats$Stub");
			// 获得asInterface方法
			Method asInterface = cStub.getMethod("asInterface",
					android.os.IBinder.class);
			// 调用asInterface方法获取IUsageStats对象
			Object oIUsageStats = asInterface.invoke(null, oRemoteService);

			// 获得getStats()方法
			Method getStats = oIUsageStats.getClass().getMethod(
					"getStatistics", (Class[]) null);

			// Method getPkgUsageStats =
			// oIUsageStats.getClass().getMethod("getPkgUsageStats",
			// ComponentName.class);
			// 调用getPkgUsageStats 获取PkgUsageStats对象
			Object aStats = getStats.invoke(oIUsageStats, (Object[]) null);

			// 获得PkgUsageStats类
			Class<?> bs = Class
					.forName("com.android.internal.os.BatteryStatsImpl");

			if (aStats == null)
				return result;
			if (aStats instanceof byte[]) {
				byte[] as = (byte[]) aStats;

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(as, 0, as.length);
				parcel.setDataPosition(0);

				Constructor<?> ctor = bs.getDeclaredConstructor(Parcel.class);
				ctor.setAccessible(true);
				Object object = ctor.newInstance(parcel);

				ReflectHelper.getFieldsMore(result,bs, object,0);
			}

		} catch (Exception e) {
			Log.e("###", e.toString(), e);
		}

		return result;
	}
	
	public static ArrayList<Map<String, Object>> getDataB(String pSource) {
		// Context ct = MyApplication.getAppContext();
		// PackageManager mPm;
		// mPm = ct.getPackageManager();

		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// HashMap<String, Object> item = null;

		try {

			// 获得ServiceManager类
			Class<?> ServiceManager = Class
					.forName("android.os.ServiceManager");

			// 获得ServiceManager的getService方法
			Method getService = ServiceManager.getMethod("getService",
					java.lang.String.class);

			// 调用getService获取RemoteService
			Object oRemoteService = getService.invoke(null, "batteryinfo");

			// 获得IBatteryStats.Stub类
			Class<?> cStub = Class
					.forName("com.android.internal.app.IBatteryStats$Stub");
			// 获得asInterface方法
			Method asInterface = cStub.getMethod("asInterface",
					android.os.IBinder.class);
			// 调用asInterface方法获取IUsageStats对象
			Object oIUsageStats = asInterface.invoke(null, oRemoteService);

			// 获得getStats()方法
			Method getStats = oIUsageStats.getClass().getMethod(
					"getStatistics", (Class[]) null);

			// Method getPkgUsageStats =
			// oIUsageStats.getClass().getMethod("getPkgUsageStats",
			// ComponentName.class);
			// 调用getPkgUsageStats 获取PkgUsageStats对象
			Object aStats = getStats.invoke(oIUsageStats, (Object[]) null);

			// 获得PkgUsageStats类
			Class<?> bs = Class
					.forName("com.android.internal.os.BatteryStatsImpl");

			if (aStats == null)
				return result;
			if (aStats instanceof byte[]) {
				byte[] as = (byte[]) aStats;

				Parcel parcel = Parcel.obtain();
				parcel.unmarshall(as, 0, as.length);
				parcel.setDataPosition(0);

				Constructor<?> ctor = bs.getDeclaredConstructor(Parcel.class);
				ctor.setAccessible(true);
				Object object = ctor.newInstance(parcel);

				ReflectHelper.getBasicFields(result,bs, object,0);

				Method m = bs.getDeclaredMethod("getUidStats", (Class[]) null);
				Object uidStats = m.invoke(object, (Object[]) null);
				if (uidStats != null & uidStats instanceof SparseArray) {
					SparseArray sa = (SparseArray) uidStats;
					for (int i = 0; i < sa.size(); i++) {
						Object uidStat = sa.get(sa.keyAt(i));
					Model.addItem(result,"Uidstat",uidStat.getClass().getCanonicalName(), uidStat);
						getUIDData(result, uidStat);
					}					
				}

			}

		} catch (Exception e) {
			Log.e("###", e.toString(), e);
		}

		return result;
	}

	public static void getUIDData(ArrayList<Map<String, Object>> pResult,
			Object pObject) {
		try {
			ReflectHelper.getFields(pResult,
					"com.android.internal.os.BatteryStatsImpl$Uid", pObject,0);
			
			Class<?> bs = Class
					.forName("com.android.internal.os.BatteryStatsImpl$Uid");
			Method m = bs.getDeclaredMethod("getPackageStats", (Class[]) null);
			Object pkgStats = m.invoke(pObject, (Object[]) null);
			if (pkgStats != null & pkgStats instanceof Map) {
				Map<String,?> sa = (Map<String,?>) pkgStats;				
			     Set<String> key = sa.keySet();
			        for (Iterator<String> it = key.iterator(); it.hasNext();) {
			            String s = (String) it.next();
			            Object uidPkg=sa.get(s);
			           Model.addItem(pResult,"pkgdata",s+"------");
			            getSeviceData(pResult,uidPkg);			          
			        }			
			}

		} catch (Exception e) {
			Log.e("###", e.toString(), e);
		}		
		
	}
	
	public static void getSeviceData(ArrayList<Map<String, Object>> pResult,
			Object pObject) {
		try {
			ReflectHelper.getFields(pResult,
					"com.android.internal.os.BatteryStatsImpl$Uid$Pkg", pObject,0);
			
			Class<?> bs = Class
					.forName("com.android.internal.os.BatteryStatsImpl$Uid$Pkg");
			Method m = bs.getDeclaredMethod("getServiceStats", (Class[]) null);
			Object svrStats = m.invoke(pObject, (Object[]) null);
			if (svrStats != null & svrStats instanceof Map) {
				Map<String,?> sa = (Map<String,?>) svrStats;				
			     Set<String> key = sa.keySet();
			        for (Iterator<String> it = key.iterator(); it.hasNext();) {
			            String s = (String) it.next();
			            Object SvrPkg=sa.get(s);
			            Model.addItem(pResult,"svrdata",s+"------");
			            getPkgServData(pResult,SvrPkg);
			            //todo-------
			        }			
			}

		} catch (Exception e) {
			Log.e("###", e.toString(), e);
		}		
		
	}
	
	public static void getPkgServData(ArrayList<Map<String, Object>> pResult,
			Object pObject) {
		try {
			ReflectHelper.getFields(pResult,
					"com.android.internal.os.BatteryStatsImpl$Uid$Pkg$Serv", pObject,0);

		} catch (Exception e) {
			Log.e("###", e.toString(), e);
		}		
	}	
}
