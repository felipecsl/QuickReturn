package com.felipecsl.quickreturn.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.felipecsl.quickreturn.library.QuickReturnAttacher;
import com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;

public class MainActivity extends ActionBarActivity implements AbsListView.OnScrollListener, ActionBar.OnNavigationListener {

    private AbsListView listView;
    private ArrayAdapter<String> adapter;
    private int offset;
    private QuickReturnAttacher quickReturnAttacher;
    private TextView quickReturnTarget;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        final String[] actionBarItems = {"QuickReturn w/ ListView", "QuickReturn w/ GridView"};
        final SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.action_bar_spinner_text, actionBarItems);

        actionBar.setListNavigationCallbacks(spinnerAdapter, this);

        initialize();
    }

    private void initialize() {
        offset = 0;
        listView = (AbsListView) findViewById(R.id.listView);
        quickReturnTarget = (TextView) findViewById(R.id.quickReturnTarget);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        addMoreItems(20);

        listView.setAdapter(new QuickReturnAdapter(adapter));
        quickReturnAttacher = new QuickReturnAttacher(listView, quickReturnTarget);

        // This is the correct way to register an OnScrollListener.
        // You have to add it on the QuickReturnAttacher, instead
        // of on the listView directly.
        quickReturnAttacher.addOnScrollListener(this);
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
            addMoreItems(10);
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
        addMoreItems(10);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {

    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
    }

    @Override
    public boolean onNavigationItemSelected(final int itemPos, final long itemId) {
        if (itemPos == currentPos)
            return false;

        if (itemPos == 0)
            setContentView(R.layout.activity_main);
        else
            setContentView(R.layout.activity_main_grid);

        currentPos = itemPos;
        initialize();

        return true;
    }
}
