package com.xiaoxiaodian.android.try01;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatHelper {

	public static String formatDate(Date date){
		Format formatter = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss Z");
		String s = formatter.format(date);
		return s;
	}
}
