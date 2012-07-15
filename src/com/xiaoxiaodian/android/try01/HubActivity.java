package com.xiaoxiaodian.android.try01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HubActivity extends Activity implements OnItemClickListener {

	SimpleAdapter adapter = null;
	private ArrayList<Map<String, Object>> mModelData = null;
	ListView lv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initModelData();
		
		setContentView(R.layout.mylist);
		lv = (ListView) findViewById(R.id.listview);
		lv.setOnItemClickListener(this);
		
		adapter = new SimpleAdapter(this, mModelData,
				R.layout.two_line_list_item, new String[] { MyConst.ITEMKEY,
						MyConst.ITEMVALUE }, new int[] { R.id.tv1, R.id.tv2 });

		lv.setAdapter(adapter);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = (Map<String, Object>) parent
				.getItemAtPosition(position);

		Intent intent = (Intent) map.get(MyConst.ITEMINTENT);
		startActivity(intent);
	}

	private void initModelData() {
		Intent aIntent = null;
		Model aAllData = new Model();
		ListData1 aData = aAllData.initModelData();
		if (aData.dataCategory == MyConst.CATIINTENT) {
			for (Map<String, Object> i : aData.getData()) {
				aIntent = (Intent) i.get(MyConst.ITEMINTENT);
				if (aIntent != null) {
					aIntent.setClass(this, InfoActivity.class);
				}
			}
		}
		mModelData=aData.getData();
	}
}
