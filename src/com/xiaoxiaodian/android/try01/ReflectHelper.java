package com.xiaoxiaodian.android.try01;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class ReflectHelper {

	public static ArrayList<Map<String, Object>> getStaticFields(
			String className) {
		ArrayList<Map<String, Object>> aData = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = null;
		Class aClass = null;

		try {
			// get class
			aClass = Class.forName(className);

			// 取得所有常量
			Field[] allFields = aClass.getFields();
			for (int i = 0; i < allFields.length; i++) {
				Log.e("Field name", allFields[i].getName());
				item = new HashMap<String, Object>();
				item.put(MyConst.ITEMKEY, allFields[i].getName());
				item.put(MyConst.ITEMVALUE, allFields[i].get(null));
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
	public static void getFields(ArrayList<Map<String, Object>> aData,
			String className, Object classObject, int excludedModifier) {

		Class aClass = null;
		try {
			// get class
			aClass = Class.forName(className);
			getFields(aData, aClass,classObject, excludedModifier);
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

	public static void getFields(ArrayList<Map<String, Object>> aData,
			Class pClass, Object classObject, int excludedModifier) {
		// ArrayList<Map<String, Object>> aData = new ArrayList<Map<String,
		// Object>>();
		Map<String, Object> item = null;
		Class aClass = pClass;
		int modifiers = 0x0;
		int mf1=0x0;
		Object aObject = classObject;
		Field[] allFields = null;

		try {
			// get class
			// aClass = Class.forName(className);
			modifiers = aClass.getModifiers();
			if (!Modifier.isStatic(modifiers) && aObject==null)
				aObject = aClass.newInstance();
			// 取得所有filed
			allFields = aClass.getDeclaredFields();
			for (int i = 0; i < allFields.length; i++) {
				try {
					Log.e("Field name", allFields[i].getName());
					mf1=allFields[i].getModifiers();
					if ((mf1 & excludedModifier)==excludedModifier && excludedModifier!=0)
						continue;
					if (!allFields[i].isAccessible())
						allFields[i].setAccessible(true);

					item = new HashMap<String, Object>();
					item.put(MyConst.ITEMKEY, allFields[i].getName());
					item.put(MyConst.ITEMVALUE, allFields[i].get(aObject));
					item.put(MyConst.ITEMEXT1, allFields[i]);
					aData.add(item);
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

	public static Class getInnerClass(String pClassname, String pInnerClassname) {
		Class aClass = null;
		Class aClass2 = null;
		try {
			aClass = Class.forName(pClassname);
			Class[] aClasses = aClass.getDeclaredClasses();
			for (Class i : aClasses) {
				String n = i.getName();
				if(n.equalsIgnoreCase(pClassname+"$"+pInnerClassname));
				aClass2=i;
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
	
	//
	public static void getAtrtribute(ArrayList<Map<String, Object>> aData,
			String className,Object classObject) {
		// ArrayList<Map<String, Object>> aData = new ArrayList<Map<String,
		// Object>>();
		Map<String, Object> item = null;
		Class aClass = null;
		int modifiers = 0x0;
		Object aObject = classObject;

		try {
			// get class
			aClass = Class.forName(className);
			modifiers = aClass.getModifiers();
			if (!Modifier.isStatic(modifiers) && aObject==null)
				aObject = aClass.newInstance();

			Method[] allMethods = aClass.getDeclaredMethods();

			for (int i = 0; i < allMethods.length; i++) {
				if (allMethods[i].getName().startsWith("get")
						&& allMethods[i].getParameterTypes() == null) {
					String back = (String) allMethods[i].invoke(aObject);
					Log.e("method name", allMethods[i].getName());
					item = new HashMap<String, Object>();
					item.put(MyConst.ITEMKEY, allMethods[i].getName());
					item.put(MyConst.ITEMVALUE, back);
					aData.add(item);
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
}
