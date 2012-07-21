package com.xiaoxiaodian.android.try01;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog.Calls;
import android.util.Log;

public class appUsageData {	
	 public static ArrayList<Map<String, Object>> getData(String pSource) {
		// Context ct = MyApplication.getAppContext();
		 // PackageManager mPm;
		// mPm = ct.getPackageManager();	 
         int aLaunchCount=0;
         long aUseTime=0;
   		ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		HashMap<String, Object> item = null;
         
          try {
              
              //获得ServiceManager类
              Class<?> ServiceManager = Class
                 .forName("android.os.ServiceManager");
              
              //获得ServiceManager的getService方法
              Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
              
              //调用getService获取RemoteService
              Object oRemoteService = getService.invoke(null, "usagestats");
              
              //获得IUsageStats.Stub类
              Class<?> cStub = Class
                 .forName("com.android.internal.app.IUsageStats$Stub");
              //获得asInterface方法
              Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
              //调用asInterface方法获取IUsageStats对象
              Object oIUsageStats = asInterface.invoke(null, oRemoteService);
              
               //获得getPkgUsageStats(ComponentName)方法
              
               Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getAllPkgUsageStats", (Class[])null);
          
          //    Method getPkgUsageStats = oIUsageStats.getClass().getMethod("getPkgUsageStats", ComponentName.class);
              //调用getPkgUsageStats 获取PkgUsageStats对象
              Object aStats = getPkgUsageStats.invoke(oIUsageStats, (Object[])null);
              
              //获得PkgUsageStats类
              Class<?> PkgUsageStats = Class.forName("com.android.internal.os.PkgUsageStats");
              
              if(aStats ==null) return result;
              if(aStats instanceof Object[] ){
            	  Object[] as=(Object[]) aStats;
            	  for(Object a:as){
            		  
            		  
            		  String pname= (String) PkgUsageStats.getDeclaredField("packageName").get(a);
                      aLaunchCount = PkgUsageStats.getDeclaredField("launchCount").getInt(a);
              
                      aUseTime = PkgUsageStats.getDeclaredField("usageTime").getLong(a);
                      String info="launchCount:"+aLaunchCount+";usageTime:"+aUseTime+";\n";
                     
                   @SuppressWarnings("unchecked")
				HashMap<String, Long> ctime= 
                    		  (HashMap<String, Long>) PkgUsageStats.getDeclaredField("componentResumeTimes").get(a);
                      
                      if(ctime !=null){
                    	  Iterator<Entry<String, Long>> iter = ctime.entrySet().iterator();                    	  
                    	  while(iter.hasNext()){
                    		  Map.Entry<String, Long> entry= (Map.Entry<String, Long>) iter.next();
                    		  String key=entry.getKey();
                    		  Long v=entry.getValue();
                    		  Date d=new Date(v);
                    		  info=info+key+":"+"lastResumeTime="+FormatHelper.formatDate(d)+";\n";
                    	  }                 	  
                    }
                  	item = new HashMap<String, Object>();
            		item.put(MyConst.ITEM_KEY,pname );
            		item.put(MyConst.ITEM_VALUE, info);
            		result.add(item);
            	  }
              }                    
            
          } catch (Exception e) {
               Log.e("###", e.toString(), e);
           }

         return result;
     }


}
