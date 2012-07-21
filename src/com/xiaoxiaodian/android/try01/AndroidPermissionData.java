package com.xiaoxiaodian.android.try01;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;

import android.util.Log;

public class AndroidPermissionData {	
	 public static MyDataSet getData(String pSource) {
		 Context ct = MyApplication.getAppContext();
		  PackageManager mPm;
		 mPm = ct.getPackageManager();	 
        MyDataSet result = new MyDataSet();
 
 	// Get the permissions for the core android package
 	PackageInfo packageInfo=null;
	try {
		packageInfo = mPm.getPackageInfo(pSource, PackageManager.GET_PERMISSIONS);
	} catch (NameNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 	if (packageInfo!=null & packageInfo.permissions != null) {
 	  // For each defined permission
 	  for (PermissionInfo permission : packageInfo.permissions) {
 	    // Dump permission info
 	    String protectionLevel;
 	    switch(permission.protectionLevel) {
 	    case PermissionInfo.PROTECTION_NORMAL : protectionLevel = "normal"; break;
 	    case PermissionInfo.PROTECTION_DANGEROUS : protectionLevel = "dangerous"; break;
 	    case PermissionInfo.PROTECTION_SIGNATURE : protectionLevel = "signature"; break;
 	    case PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM : protectionLevel = "signatureOrSystem"; break;
 	    default : protectionLevel = "<unknown>"; break;
 	    }
 	    Item item=new Item(permission.name, "protectionLevel:"+ protectionLevel, permission);
 		result.add(item);
 	    Log.i("PermissionCheck", permission.name + " " + protectionLevel);
 	  }
 	}
         return result;
     }

}
