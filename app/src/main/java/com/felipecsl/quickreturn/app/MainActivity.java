package com.felipecsl.quickreturn.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.felipecsl.quickreturn.library.AbsListViewQuickReturnAttacher;
import com.felipecsl.quickreturn.library.QuickReturnAttacher;
import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;
import com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;
import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;

public class MainActivity extends ActionBarActivity implements AbsListView.OnScrollListener,
                                                               ActionBar.OnNavigationListener,
                                                               View.OnClickListener,
                                                               AdapterView.OnItemClickListener,
                                                               AdapterView.OnItemLongClickListener {

  private ArrayAdapter<String> adapter;
  private int offset;
  private QuickReturnTargetView topTargetView;
  private TextView topTextView;
  private TextView bottomTextView;
  private int currentPos;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle("");
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    String[] actionBarItems = {"ListView", "GridView", "ScrollView"};
    SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.action_bar_spinner_text,
                                                             actionBarItems);

    actionBar.setListNavigationCallbacks(spinnerAdapter, this);

    initialize(R.layout.activity_main);
  }

  private void initialize(int layoutId) {
    setContentView(layoutId);
    offset = 0;
    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.listView);
    topTextView = (TextView) findViewById(R.id.quickReturnTopTarget);
    bottomTextView = (TextView) findViewById(R.id.quickReturnBottomTarget);

    adapter = new ArrayAdapter<>(this, R.layout.list_item);
    addMoreItems(100);

    if (viewGroup instanceof AbsListView) {
      int numColumns = (viewGroup instanceof GridView) ? 3 : 1;
      AbsListView absListView = (AbsListView) viewGroup;
      absListView.setAdapter(new QuickReturnAdapter(adapter, numColumns));
    }

    QuickReturnAttacher quickReturnAttacher = QuickReturnAttacher.forView(viewGroup);
    quickReturnAttacher.addTargetView(bottomTextView, AbsListViewScrollTarget.POSITION_BOTTOM);
    topTargetView = quickReturnAttacher.addTargetView(topTextView,
                                                      AbsListViewScrollTarget.POSITION_TOP,
                                                      dpToPx(this, 50));

    if (quickReturnAttacher instanceof AbsListViewQuickReturnAttacher) {
      // This is the correct way to register an OnScrollListener.
      // You have to add it on the QuickReturnAttacher, instead
      // of on the viewGroup directly.
      AbsListViewQuickReturnAttacher
          attacher =
          (AbsListViewQuickReturnAttacher) quickReturnAttacher;
      attacher.addOnScrollListener(this);
      attacher.setOnItemClickListener(this);
      attacher.setOnItemLongClickListener(this);
    }
  }

  public static int dpToPx(Context context, float dp) {
    // Took from http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) ((dp * scale) + 0.5f);
  }

  private void addMoreItems(int amount) {
    for (int i = 0; i < amount; i++) {
      adapter.add("Item " + String.valueOf(offset + i));
    }

    offset += amount;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.add_more) {
      addMoreItems(10);
    } else if (id == R.id.add_a_lot_more) {
      addMoreItems(100);
    } else if (id == R.id.reset) {
      reset();
    } else if (id == R.id.animated) {
      topTargetView.setAnimatedTransition(!topTargetView.isAnimatedTransition());
      item.setTitle(topTargetView.isAnimatedTransition() ? "Disable animated transition"
                                                         : "Enable animated transition");
      reset();
    } else if (id == R.id.bottom_view) {
      bottomTextView.setVisibility(bottomTextView.getVisibility() == View.GONE
                                   ? View.VISIBLE : View.GONE);
    } else if (id == R.id.top_view) {
      topTextView.setVisibility(topTextView.getVisibility() == View.GONE
                                ? View.VISIBLE : View.GONE);
    }
    return true;
  }

  private void reset() {
    adapter.clear();
    offset = 0;
    addMoreItems(10);
  }

  @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

  }

  @Override public void onScroll(@NonNull AbsListView view, int firstVisibleItem, int visibleItemCount,
                       int totalItemCount) {
  }

  @Override public boolean onNavigationItemSelected(int itemPos, long itemId) {
    if (itemPos == currentPos) {
      return false;
    }

    currentPos = itemPos;
    switch (itemPos) {
      case 0:
        currentPos = R.layout.activity_main;
        break;
      case 1:
        currentPos = R.layout.activity_main_grid;
        break;
      case 2:
        currentPos = R.layout.activity_main_scrollview;
        break;
    }
    initialize(currentPos);

    return true;
  }

  @Override public void onClick(@NonNull View v) {

  }

  @Override public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view,
                                    int position, long id) {
    Toast.makeText(this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
  }

  @Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
    Toast.makeText(this, "Item " + position + " long clicked", Toast.LENGTH_SHORT).show();
    return true;
  }
}
