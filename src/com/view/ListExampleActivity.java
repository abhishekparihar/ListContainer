package com.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListExampleActivity extends ListActivity {

    private EditText mUserText;
    private ListView listView;
    private ArrayAdapter<String> mAdapter;
    
    private ArrayList<String> mStrings = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);
        
        setListAdapter(mAdapter);
        listView=getListView();
        
        mUserText = (EditText) findViewById(R.id.userText);
        listView = (ListView) findViewById(R.id.list);
        /*mUserText.setOnClickListener(this);*/
        //mUserText.setOnKeyListener(this);
    

    final Button button = (Button) findViewById(R.id.addBtn);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
		public void onClick(View v) {
        	 sendText();
        }
    });
    
    

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
            onListItemClick(v,pos,id);
        }
    });
  
    } 
    protected void onListItemClick(View v, int pos, long id) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Are you sure you want to exit?")
    	       .setCancelable(false)
    	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	           @Override
				public void onClick(DialogInterface dialog, int id) {
    	                ListExampleActivity.this.finish();
    	           }
    	       })
    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
    	           @Override
				public void onClick(DialogInterface dialog, int id) {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
        Log.i("TAG", "onListItemClick id=" + id);
    }


    private void sendText() {
        String text = mUserText.getText().toString();
        mAdapter.add(text);
        mUserText.setText(null);
    }

  /*  public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    sendText();
                    return true;
            }
        }
        return false;
    }*/
    

}
