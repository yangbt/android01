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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class InfoActivity extends Activity implements OnItemClickListener{

	SimpleAdapter adapter = null;
	private MyDataSet mModelData = null;
	ListView lv = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Item mr=ActionIntent.getItem(intent);
	
		/*
		 * if (data == null) { data = ""; }
		 */
		mModelData=Model.initModelData(mr);
		if(mModelData!=null) this.setTitle(mModelData.getParentString());
		setContentView(R.layout.mylist);
		lv = (ListView) findViewById(R.id.listview);
		lv.setOnItemClickListener(this);

		adapter = new SimpleAdapter(this, mModelData.getData(),
				R.layout.two_line_list_item, mModelData.getFrom(), new int[] { R.id.tv1, R.id.tv2 });

		lv.setAdapter(adapter);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Map<String, Object> map = (Map<String, Object>) parent
				.getItemAtPosition(position);
		Item aRI = (Item) map.get(MyConst.ITEM_ROWITEM);
		ArrayList<Item> p= mModelData.getParent();
		aRI.setParent(p);
		if (aRI != null) {
			ActionIntent ai = new ActionIntent(aRI);
			if (ai != null) {
				Intent intent = ai.getData();
				intent.setClass(this, InfoActivity.class);
				startActivity(intent);
			}
		}
	}
	
}
