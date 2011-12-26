package com.view;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ListExampleActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	
	private final String LOG_TAG = getClass().getSimpleName();
	private EditText mUserText;
	private ListView listView;
	ListData c;
	// private ArrayAdapter<String> mAdapter;

	//private ArrayList<String> mStrings = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// mAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, mStrings);

		// setListAdapter(mAdapter);
		// listView = getListView();

		mUserText = (EditText) findViewById(R.id.userText);
		listView = (ListView) findViewById(android.R.id.list);

		final Button button = (Button) findViewById(R.id.addBtn);
		

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					ListData listData = saveToObj();
					Dao<ListData, Integer> dao = getHelper().getListDao();
					boolean alreadyCreated = false;
					if (listData.getId() != null) {
						ListData dbCount = dao.queryForId(listData.getId());
						if (dbCount != null) {
							listData.changeValue(dbCount.getValue());
							dao.update(listData);
							alreadyCreated = true;
						}
					}

					if (alreadyCreated) {
						finish();
					} else {
						dao.create(listData);
						Log.i(LOG_TAG, "data inserted(" + listData + ")");
						
						// CounterScreen.callMe(CreateCounter.this,
						// clickCount.getId());
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} 

				// sendText();
			}
		});
	}
		
		

		/*
		 * listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		 * {
		 * 
		 * @Override public void onItemClick(AdapterView<?> av, View v, int pos,
		 * long id) { onListItemClick(v, pos, id); } });
		 */

	

	/*
	 * protected void onListItemClick(View v, int pos, long id) {
	 * 
	 * Log.i("TAG", "onListItemClick id=" + id); }
	 */

	/*
	 * private void sendText() { //RuntimeExceptionDao<ListData, Integer> dao =
	 * getListDataDao(); String text = mUserText.getText().toString();
	 * //dao.create(text); //mAdapter.add(text); mUserText.setText(null); }
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		c = saveToObj();
		// outState.putSerializable("CLICK_COUNT", c);
	}

	private ListData saveToObj() {
		c = new ListData();

		/*
		 * int clickCountId = getClickCountId(); if (clickCountId > -1) {
		 * c.setId(clickCountId); } if (clickGroup.getSelectedItem() != null) {
		 * ClickGroup group = (ClickGroup) clickGroup.getSelectedItem(); if
		 * (group != null) { c.setGroup(group); } }
		 */

		// c.setDescription(clickDescription.getText().toString());
		c.setName(mUserText.getText().toString());
		mUserText.setText(null);
		return c;
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		try {
			fillList();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private void fillList() throws SQLException {
		Log.i(ListExampleActivity.class.getName(), "Show list again");
		Dao<ListData, Integer> dao = getHelper().getListDao();
		QueryBuilder<ListData, Integer> builder = dao.queryBuilder();
		//builder.orderBy(ListData.List_Name, false).limit(30L);
		List<ListData> list = dao.query(builder.prepare());
		ArrayAdapter<ListData> arrayAdapter = new CountsAdapter(this, R.layout.list_style, list);
		listView.setAdapter(arrayAdapter);
	}
	
	private class CountsAdapter extends ArrayAdapter<ListData> {

		public CountsAdapter(Context context, int textViewResourceId, List<ListData> items) {
			super(context, textViewResourceId, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = inflater.inflate(R.layout.list_style, null);
			}
			ListData list = getItem(position);
			fillText(v, R.id.listName, list.getName());
			return v;
		}
		private void fillText(View v, int id, String text) {
			TextView textView = (TextView) v.findViewById(id);
			textView.setText(text == null ? "" : text);
		}
	}
}
