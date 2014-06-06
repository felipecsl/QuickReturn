package com.felipecsl.quickreturn.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.felipecsl.quickreturn.com.felipecsl.quickreturn.library.QuickReturnAttacher;
import com.felipecsl.quickreturn.com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private int offset = 0;
    private QuickReturnAttacher quickReturnAttacher;
    private TextView quickReturnTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        quickReturnTarget = (TextView) findViewById(R.id.quickReturnTarget);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        addMoreItems(100);

        listView.setAdapter(new QuickReturnAdapter(adapter));
        quickReturnAttacher = new QuickReturnAttacher(listView, quickReturnTarget);
    }

    private void addMoreItems(final int amount) {
        for (int i = 0; i < amount; i++)
            adapter.add("Item " + String.valueOf(offset + i));

        offset += amount;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_more) {
            addMoreItems(100);
        } else if (id == R.id.reset) {
            reset();
        } else if (id == R.id.animated) {
            quickReturnAttacher.setAnimatedTransition(!quickReturnAttacher.isAnimatedTransition());
            item.setTitle(quickReturnAttacher.isAnimatedTransition() ? "Disable animated transition" : "Enable animated transition");
            reset();
        } else if (id == R.id.position) {
            int newPos;
            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) quickReturnTarget.getLayoutParams();
            String newTitle;
            if (quickReturnAttacher.getPosition() == QuickReturnAttacher.POSITION_TOP) {
                newPos = QuickReturnAttacher.POSITION_BOTTOM;
                layoutParams.gravity = Gravity.BOTTOM;
                newTitle = "Move to top";
            } else {
                newPos = QuickReturnAttacher.POSITION_TOP;
                layoutParams.gravity = Gravity.TOP;
                newTitle = "Move to bottom";
            }
            item.setTitle(newTitle);
            quickReturnTarget.setLayoutParams(layoutParams);
            quickReturnAttacher = new QuickReturnAttacher(listView, quickReturnTarget, newPos);
        }
        return true;
    }

    private void reset() {
        adapter.clear();
        offset = 0;
        addMoreItems(100);
    }
}
