package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class InfoActivity extends Activity {

	SimpleAdapter adapter = null;
	private ArrayList<Map<String, Object>> mModelData = null;
	ListView lv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String data = intent.getStringExtra(MyConst.INTENTDATA);
		
		
		/*
		 * if (data == null) { data = ""; }
		 */
		initModelData(data);
		setContentView(R.layout.mylist);
		lv = (ListView) findViewById(R.id.listview);

		adapter = new SimpleAdapter(this, mModelData,
				R.layout.two_line_list_item, new String[] { MyConst.ITEMKEY,
						MyConst.ITEMVALUE }, new int[] { R.id.tv1, R.id.tv2 });

		lv.setAdapter(adapter);

	}

	/*
	 * private void initModelData() { mModelData = AllData.getAndroidOsBuild();
	 * }
	 */
	private void initModelData(String data) {
		if (data == null || data.isEmpty())
			return;
		Model aData=new Model();
		mModelData = aData.getData(data);
	}
}
