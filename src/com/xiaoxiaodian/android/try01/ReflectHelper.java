package com.xiaoxiaodian.android.try01;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

public class ReflectHelper {
	
	public static Class<?> getClass(String pClassName){
		Class<?> cls=null;
		
		try {
			cls=Class.forName(pClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cls;
		
	}
	public static ArrayList<Map<String, Object>> getClassData(
			String pClassname, Object classObject, int aExcludedModifer) {
		// int aExcludedModifer = 0x0;
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		try {
			getFieldsMore(aData, Class.forName(pClassname), classObject, aExcludedModifer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getMethodsReturn(aData, pClassname, classObject);
		return aData;
	}

	public static ArrayList<Map<String, Object>> getClassData(
			Class<?> pClass, Object classObject, int aExcludedModifer) {
		// int aExcludedModifer = 0x0;
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		getFieldsMore(aData, pClass, classObject, aExcludedModifer);
		getMethodsReturn(aData, pClass, classObject);
		return aData;
	}
	
	public static void appendClassData(ArrayList<Map<String, Object>> aData,
			String pClassname, Object classObject, int aExcludedModifer) {
		// int aExcludedModifer = 0x0;
		try {
			getFieldsMore(aData,Class.forName(pClassname), classObject, aExcludedModifer);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getMethodsReturn(aData, pClassname, classObject);
		return ;

	}

	public static ArrayList<Map<String, Object>> getStaticFields(
			String className) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = null;
		Class<?> aClass = null;

		try {
			// get class
			aClass = Class.forName(className);

			// 取得所有常量
			Field[] allFields = aClass.getFields();
			for (int i = 0; i < allFields.length; i++) {
				Log.e("Field name", allFields[i].getName());
				item = new HashMap<String, Object>();
				item.put(Item.ITEM_TITLE, allFields[i].getName());
				item.put(Item.ITEM_DESC, allFields[i].get(null));
				item.put(MyConst.ITEM_NAME, allFields[i].getName());
				aData.add(item);
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aData;
	}

	/*
	 * public static ArrayList<Map<String, Object>> getStaticFieldsNoFinal(
	 * String className) { ArrayList<Map<String, Object>> aData = new
	 * ArrayList<Map<String, Object>>(); Map<String, Object> item = null; Class
	 * aClass = null;
	 * 
	 * try { // get class aClass = Class.forName(className);
	 * 
	 * // 取得所有常量 Field[] allFields = aClass.getFields(); for (int i = 0; i <
	 * allFields.length; i++) { Log.e("Field name", allFields[i].getName()); if
	 * (isPublicStaticFinal(allFields[i])) continue; item = new HashMap<String,
	 * Object>(); item.put(MyConst.ITEMKEY, allFields[i].getName());
	 * item.put(MyConst.ITEMVALUE, allFields[i].get(null)); aData.add(item); } }
	 * catch (SecurityException e) { // throw new
	 * RuntimeException(e.getMessage()); e.printStackTrace(); } catch
	 * (IllegalArgumentException e) { // throw new
	 * RuntimeException(e.getMessage()); e.printStackTrace(); } catch (Exception
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } return
	 * aData; }
	 */
	
	/*
	public static void getFields(ArrayList<Map<String, Object>> aData,
			String className, Object classObject, int excludedModifier) {

		Class<?> aClass = null;
		try {
			// get class
			aClass = Class.forName(className);
			getBasicFields(aData, aClass, classObject, excludedModifier);
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	*/
	
	public static void getFieldsMore(ArrayList<Map<String, Object>> aData,
			Class<?> pClass, Object classObject, int excludedModifier)	{
		Class<?> aClass = pClass;
		int modifiers = 0x0;
		int mf1 = 0x0;
		Object aObject = classObject;
		Field[] allFields = null;

		try {
			// get class
			// aClass = Class.forName(className);
		
			
			modifiers = aClass.getModifiers();
			if (!Modifier.isStatic(modifiers) && aObject == null)
				aObject = aClass.newInstance();
			
			if (aObject != null){
				if( aObject instanceof SparseArray) {
			
				SparseArray<?> sa = (SparseArray<?>) aObject;
				for (int i = 0; i < sa.size(); i++) {
					Object innerObject = sa.get(sa.keyAt(i));
				//	Class<?> a=innerObject.getClass();
				//	getFieldsMore(aData,
				//			a, innerObject,  excludedModifier) ;
					String s=innerObject.toString(); //todo fix it;
					addItem(aData,"array["+i+"]:",s,innerObject);
				}					
			}else if(aObject instanceof ArrayList){
				ArrayList<?> ar=(ArrayList<?>) aObject;
				for(int i=0;i<ar.size();i++){
					Object innerObject=ar.get(i);
				//	Class<?>a=innerObject.getClass();
				//	getFieldsMore(aData,
				//			a, innerObject,  excludedModifier) ;
					String s=innerObject.toString(); //todo fix it;
					addItem(aData,"array["+i+"]:",s,innerObject);
				}
			}else if(aObject instanceof HashMap){
				HashMap<?,?> map=(HashMap<?,?>)aObject;
				Iterator iter=map.entrySet().iterator();
				
				while(iter.hasNext()){
					Map.Entry<?, ?> entry=(Map.Entry<?, ?>)iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					String s=val.toString(); //todo fix it;
					addItem(aData,(String)key,s,val);
					
				}				
							
			
			}else if(aObject instanceof List){
				List<?> ar=(List<?>) aObject;
				for(int i=0;i<ar.size();i++){
					Object innerObject=ar.get(i);
				//	Class<?>a=innerObject.getClass();
				//	getFieldsMore(aData,
				//			a, innerObject,  excludedModifier) ;
					String s=innerObject.toString(); //todo fix it;
					addItem(aData,"array["+i+"]:",s,innerObject);
				}
			}else if(aObject.getClass().isArray()){
				
				for(int i=0;i<Array.getLength(aObject);i++){
					Object innerObject=Array.get(aObject, i);
				//	Class<?>a=innerObject.getClass();
				//	getFieldsMore(aData,
				//			a, innerObject,  excludedModifier) ;
					String s=innerObject.toString(); //todo fix it;
					addItem(aData,"array["+i+"]:",s,innerObject);
				}
			}
			else{
				getBasicFields(aData,pClass, classObject,  excludedModifier) ;
			}
			}else{
				getBasicFields(aData,pClass, classObject,  excludedModifier) ;
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public static void getBasicFields(ArrayList<Map<String, Object>> aData,
			Class<?> pClass, Object classObject, int excludedModifier) {
		
		Class<?> aClass = pClass;
		int modifiers = 0x0;
		int mf1 = 0x0;
		Object aObject = classObject;
		Field[] allFields = null;

		try {
			// get class
			// aClass = Class.forName(className);
			modifiers = aClass.getModifiers();
			if (!Modifier.isStatic(modifiers) && aObject == null)
				aObject = aClass.newInstance();
			// 取得所有filed
			allFields = aClass.getDeclaredFields();
			for (int i = 0; i < allFields.length; i++) {
				try {
					Log.d("Field name", allFields[i].getName());
					mf1 = allFields[i].getModifiers();
					if ((mf1 & excludedModifier) == excludedModifier
							&& excludedModifier != 0)
						continue;
					if (!allFields[i].isAccessible())
						allFields[i].setAccessible(true);

					Object 	aObjectB=allFields[i].get(aObject);	
					String n=allFields[i].getName();
					Type t=allFields[i].getGenericType();
					String v=simpleTypeToString(aObjectB,t);
					addItem(aData,n,v,aObjectB);
				
				} catch (SecurityException e) {
					// throw new RuntimeException(e.getMessage());
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// throw new RuntimeException(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}



	/*
	 * public static void getFieldsNoFinal(ArrayList<Map<String, Object>> aData,
	 * String className, Object pObject) { // ArrayList<Map<String, Object>>
	 * aData = new ArrayList<Map<String, // Object>>(); Map<String, Object> item
	 * = null; Class aClass = null; int modifiers = 0x0; Object aObject =
	 * pObject; Field[] allFields = null;
	 * 
	 * try { // get class aClass = Class.forName(className); modifiers =
	 * aClass.getModifiers(); if (!Modifier.isStatic(modifiers) && aObject ==
	 * null) aObject = aClass.newInstance(); // 取得所有filed allFields =
	 * aClass.getDeclaredFields(); for (int i = 0; i < allFields.length; i++) {
	 * Log.e("Field name", allFields[i].getName()); if
	 * (isPublicStaticFinal(allFields[i])) continue; item = new HashMap<String,
	 * Object>(); item.put(MyConst.ITEMKEY, allFields[i].getName());
	 * item.put(MyConst.ITEMVALUE, allFields[i].get(aObject)); aData.add(item);
	 * } } catch (SecurityException e) { // throw new
	 * RuntimeException(e.getMessage()); e.printStackTrace(); } catch
	 * (IllegalArgumentException e) { // throw new
	 * RuntimeException(e.getMessage()); e.printStackTrace(); } catch (Exception
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } return; }
	 */

	private static void addItem(ArrayList<Map<String, Object>> aData,String k,Object v)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(Item.ITEM_TITLE, k);
		item.put(Item.ITEM_DESC, v);
		aData.add(item);
	}

	private static void addItem(ArrayList<Map<String, Object>> aData,String k,Object v,Object o)	{
		Map<String, Object>  item = new HashMap<String, Object>();
		item.put(Item.ITEM_TITLE, k);
		item.put(Item.ITEM_DESC, v);
		item.put(Item.ITEM_MEMBER_VALUE, o);
		aData.add(item);
	}
	
	
	
	public static void getMethodsReturn(ArrayList<Map<String, Object>> aData,
			String className, Object classObject) {
		getMethodsReturn(aData,
				className, classObject,0);
	}

	public static void getMethodsReturn(ArrayList<Map<String, Object>> aData,
			String className, Object classObject,int pRetFilter) {
		try {
			Class<?> aClass = Class.forName(className);
			getMethodsReturn(aData,aClass, classObject);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getMethodsReturn(ArrayList<Map<String, Object>> aData,
			Class<?> pClass, Object classObject){
		getMethodsReturn(aData,
				pClass, classObject,0);
	}
	
	public static void getMethodsReturn(ArrayList<Map<String, Object>> aData,
			Class<?> pClass, Object classObject,int pRetFilter) {
		// ArrayList<Map<String, Object>> aData = new ArrayList<Map<String,
		// Object>>();
	//	Map<String, Object> item = null;
		Class<?> aClass = pClass;
		int modifiers = 0x0;
		String aValueString = "";
		Type t = null;
		Object aObject = classObject;
		String mName = "";
		Class<?>[] cs = null;
		Class<?>	classA=null;		
		boolean flagA = false;
		boolean flagB = false;
		String tmpA="";
		try {
			// get class
			
			modifiers = aClass.getModifiers();
			if (!Modifier.isStatic(modifiers) && aObject == null)
				aObject = aClass.newInstance();

			Method[] allMethods = aClass.getDeclaredMethods();

			for (Method aMethod:allMethods)  {
				try {
					flagA = false;
					mName = aMethod.getName();
					cs = aMethod.getParameterTypes();
					/*
					if (cs == null || cs.length == 0)
						flagA = true;
					flagB = mName.startsWith("get");
					*/
					
					Log.d("method:", mName);
					//if (mName.startsWith("get")) {
						if (cs == null || cs.length == 0) {
							Log.d("get atrribute from method: ", mName);	
							if(! aMethod.isAccessible()) aMethod.setAccessible(true);

							t = aMethod.getGenericReturnType();
							classA=aMethod.getReturnType();
							if (t == null) {
								Log.d("return type  is null by method:", mName);
								continue;
							}
							
							Object o=aMethod.invoke(aObject);							
							aValueString=simpleTypeToString(o,t);
						
							addItem(aData,mName,aValueString,o);
					
						}
					//}
				} catch (SecurityException e) {
					// throw new RuntimeException(e.getMessage());
					Log.e("erro", e.toString());
					e.printStackTrace();
				
					addItem(aData,mName,e.toString());
				} catch (IllegalArgumentException e) {
					// throw new RuntimeException(e.getMessage());
					e.printStackTrace();
					Log.e("erro", e.toString());
					addItem(aData,mName,e.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("erro", e.toString());
					addItem(aData,mName,e.toString());
				}
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			Log.e("erro", e.toString());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public static boolean isSimpleType(Class<?> pClass,Type t){
		
		boolean result=false;
		if (t == String.class || t == int.class||t == long.class||t == short.class||t == byte.class
				||t == boolean.class||t == float.class||t == double.class||t == char.class){
					result=true;
		}
				return result;
	}
	
     public static boolean isSimpleType(Object pObject){
    	 boolean result=false;
		Class t=pObject.getClass();
		if(t.isArray()){
			result=false;
		}else
		if(t.isPrimitive()) {
			result=true;
		}else		
		if (t == String.class || t == Integer.class||t == Long.class||t == Short.class||t == Byte.class
				||t == Boolean.class||t == Float.class||t == Double.class||t == Character.class){
					result=true;
		}
				return result;
	}
	
	public static String simpleTypeToString(Object pObject,Type t){
		String result=null;
		//String aString=null;
			
		if (t == String.class) {
			result = (String) pObject;
		} else if (t == int.class) {
			result = ((Integer) pObject)
					.toString();
		} else if (t == long.class) {
			result = ((Long) pObject)
					.toString();
		} else if (t == short.class) {
			result = ((Short) pObject)
					.toString();
		} else if (t == byte.class) {
			result = ((Byte)pObject)
					.toString();
		} else if (t == boolean.class) {
			result = ((Boolean) pObject)
					.toString();
		} else if (t == float.class) {
			result = ((Float) pObject)
					.toString();
		} else if (t == double.class) {
			result = ((Double) pObject)
					.toString();
		} else if (t == char.class) {
			result = ((Character) pObject).toString();
		} else {
			result=pObject.getClass().getName();
		}
		return result;
	}
	
	public static String simpleObjectToString(Object pObject){
		String result=null;
		//String aString=null;
			Type t=pObject.getClass();
		if (t == String.class) {
			result = (String) pObject;
		} else if (t == int.class) {
			result = ((Integer) pObject)
					.toString();
		} else if (t == long.class) {
			result = ((Long) pObject)
					.toString();
		} else if (t == short.class) {
			result = ((Short) pObject)
					.toString();
		} else if (t == byte.class) {
			result = ((Byte)pObject)
					.toString();
		} else if (t == boolean.class) {
			result = ((Boolean) pObject)
					.toString();
		} else if (t == float.class) {
			result = ((Float) pObject)
					.toString();
		} else if (t == double.class) {
			result = ((Double) pObject)
					.toString();
		} else if (t == char.class) {
			result = ((Character) pObject).toString();
		} else {
			result=pObject.getClass().getName();
		}
		return result;
	}
	
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier
				.isFinal(modifiers));
	}

	public static boolean isString(Field field) {
		if (field.getType().getName().equalsIgnoreCase("java.lang.String")) {
			return true;
		}
		return false;
	}
	
	
	public static Object invokeMethod(Class<?> pClass,Object pObject,Method pMethod,Object[] pParam){
		Object value=null;
		Object aObject=pObject;
		Method m=pMethod;
		int modifiers=0;
		Object[] p=pParam;
		
		Class<?> aClass=pClass;
		// Class<?> partypes[];
		if(m==null) return null;
		try {
				
				if(aClass==null) aClass=pObject.getClass();
				modifiers = m.getModifiers();		
				if (!Modifier.isStatic(modifiers) && aObject==null )
					aObject = aClass.newInstance();	
				 value = m.invoke(aObject, p);  
	
	 /*	} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return value;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
	*/	} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;		
		}catch(InvocationTargetException e){
			Log.e("error:",e.toString());
			return value;
		}catch(Exception e){
			Log.e("error:",e.toString());
			return value;
		}
		return value;
	}
	
	public  static Method findSimpleMethod( Class<?> c,String pMethod,Object[] pParam){
		Method result=null;
		try {		   
		    Method[] allMethods = c.getDeclaredMethods();
		    for (Method m : allMethods) {
			String mname = m.getName();
			if (!mname.equalsIgnoreCase(pMethod)) {
			    continue;
			}
	 		Type[] pType = m.getGenericParameterTypes();
	 		if (pType.length != pParam.length)   continue;
	 		for(int i=0;i<pType.length;i++){
	 			if(pParam[i].getClass().isAssignableFrom(pType[i].getClass()))continue;
	 	
	 		}
	 		if(m!=null){
	 		m.setAccessible(true);
	 		result=m;
	 		break;
	 		}
		    }    // production code should handle these exceptions more gracefully
	
		} catch (Exception x) {
		    x.printStackTrace();
		}
		return result;
	}
	
	public static Class getInnerClass(String pClassname, String pInnerClassname) {
		Class aClass = null;
		Class aClass2 = null;
		try {
			aClass = Class.forName(pClassname);
			Class[] aClasses = aClass.getDeclaredClasses();
			for (Class i : aClasses) {
				String n = i.getName();
				if (n.equalsIgnoreCase(pClassname + "$" + pInnerClassname))
					;
				aClass2 = i;
				break;
			}
		} catch (SecurityException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aClass2;
	}
}
