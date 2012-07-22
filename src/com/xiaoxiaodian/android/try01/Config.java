package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;

public class Config {
	private static final String DEFAULT_METHOD_NAME="getData";
	private static final String DEFAULT_CLASS_NAME="CommonModelData";
	private static String getDefaultClassName() {
		String aClassname = null;
		aClassname = DEFAULT_CLASS_NAME;

		String p = Config.class.getCanonicalName();
		p = p.substring(0, p.lastIndexOf(".") + 1);
		if (aClassname.lastIndexOf(".") < 0)
			aClassname = p + aClassname;
		return aClassname;
	}

	
	private static String getDefaultPackageName() {
		String p=Config.class.getPackage().getName();

		//String p = Config.class.getCanonicalName();
		//p = p.substring(0, p.lastIndexOf(".") );
		
		return p;
	}
	
	private static String getDefaultMethodName() {
		return DEFAULT_METHOD_NAME;
	}

	public static MyDataSet getConfigData() {
		MyDataSet aResult = new MyDataSet();
		addItem(aResult, "Build", "android.os.Build",
				"android.os.Build", "");
		addItem(aResult, "BuildVersion", "android.os.Build.VERSION",
				"android.os.Build$VERSION", "");
		addItem(aResult, "Environment", "android.os.Environment",
				"android.os.Environment", "");
		addItem(aResult, "SystemProperty",
				"java.lang.System.getProperties()",
				"java.lang.System.getProperties()", "SystemPropertyData");

		addItem(aResult, "SystemEnv",
				"java.lang.System.getEnv()",
				"java.lang.System.getEnv()", "SystemEnvData");
		
		
		String[] filenames=new String[]{"/proc/cpuinfo","/proc/app_info","/proc/buddyinfo",
				"/proc/bus/input/devices","/proc/bus/input/handlers",
				"/proc/cgroups","/proc/cmdline","/proc/devices","/proc/diskstats","/proc/driver/rtc",
				"/proc/fb","/proc/filesystems","/proc/interrupts","/proc/iomem","/proc/ioports",
				"/proc/loadavg","/proc/locks","/proc/meminfo","/proc/misc","/proc/mounts","/proc/mtd",
				"/proc/pagetypeinfo","/proc/partitions","/proc/sched_debug","/proc/softirqs",
				"/proc/stat","/proc/timer_list","/proc/uptime","/proc/version","/proc/vmallocinfo",
				"/proc/vmstat","/proc/wakelocks","/proc/zoneinfo"
				};
		addItem(aResult, "ProcInfo",
				"/proc/*",
				filenames, "ProcInfo");
		
		addItem(aResult, "Settings", "android.provider.Settings$System",
				"android.provider.Settings$System", "SystemSettingData");
		addItem(aResult, "Secure", "android.provider.Settings.Secure",
				"android.provider.Settings.Secure", "SettingSecureData");
		addItem(aResult, "Telephony", "android.telephony.TelephonyManager",
				"android.telephony.TelephonyManager", "TelephoneData");
		addItem(aResult, "Activity", "android.app.ActivityManager",
				"android.app.ActivityManager", "ActivityData");
		addItem(aResult, "Runtime", "java.lang.Runtime", "java.lang.Runtime",
				"RuntimeData");
		addItem(aResult, "Browser", "android.provider.Browser",
				"android.provider.Browser", "BrowserData");
		addItem(aResult, "Permission", "android.Manifest$permission",
				"android.Manifest$permission", "");
		addItem(aResult, "Permission", "android.Manifest$permission_group",
				"android.Manifest$permission_group", "");
		addItem(aResult, "Configuration", "android.content.res.Configuration",
				"android.content.res.Configuration", "ConfigurationData");
		addItem(aResult, "DisplayMetrics", "android.util.DisplayMetrics",
				"android.util.DisplayMetrics", "DisplayData");

		addItem(aResult, "Calls", "android.provider.CallLog.Call",
				"android.provider.CallLog.Call", "CallLogData");
		addItem(aResult, "AndroidPermission", "Permission of Package Android",
				"android", "AndroidPermissionData");
		addItem(aResult, "appUsageData",
				"com.android.internal.os.PkgUsageStats",
				"com.android.internal.os.PkgUsageStats", "appUsageData");

		addItem(aResult, "BatteryStats",
				"com.android.internal.os.BatteryStatsImpl",
				"com.android.internal.os.BatteryStatsImpl", "BatteryUsageData");
		
		addItem(aResult, "SystemService",
				"jandroid.content.Context",
				"android.content.Context", "SystemServiceData");
		return aResult;
	}

	
	private static void addItem(MyDataSet ds, String name,
			String value, Object pTarget, String pClass) {

		// MyRowItem(String pTitle,String pDesc,Object pParam,
				// String pClassname,Object pObject,String pMembername,
				// String pMemberType )
		Object[] p=new Object[]{pTarget};
		addItemB(ds, name,value, p, pClass);
	}
	
	private static void addItemB(MyDataSet ds, String name,
			String value, Object[] pTarget, String pClass) {

		// MyRowItem(String pTitle,String pDesc,Object pParam,
				// String pClassname,Object pObject,String pMembername,
				// String pMemberType )
		String pM=getDefaultMethodName();
		String aClassname=(pClass==null || pClass.isEmpty())?getDefaultClassName():pClass;
		if(aClassname.indexOf(".")<0) aClassname=getDefaultPackageName()+"."+aClassname;
		ds.add(new Item(name,value,pTarget,aClassname, null,pM, MyConst.TYPE_METHOD));
	}
}
