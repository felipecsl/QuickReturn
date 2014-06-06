package com.felipecsl.quickreturn.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.felipecsl.quickreturn.QuickReturnAdapter;
import com.felipecsl.quickreturn.QuickReturnListView;


public class MainActivity extends ActionBarActivity {

    private QuickReturnListView listView;
    private QuickReturnAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (QuickReturnListView) findViewById(R.id.listView);
        TextView quickReturnTarget = (TextView) findViewById(R.id.quickReturnTarget);

        final ArrayAdapter<String> wrappedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < 100; i++)
            wrappedAdapter.add("Item " + String.valueOf(i));

        listAdapter = new QuickReturnAdapter(wrappedAdapter);
        listView.setAdapter(listAdapter);
        listView.setQuickReturnView(quickReturnTarget);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
