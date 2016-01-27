package com.felipecsl.quickreturn.library;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;
import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;

public class AbsListViewQuickReturnAttacher extends QuickReturnAttacher
    implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
  private final CompositeAbsListViewOnScrollListener onScrollListener =
      new CompositeAbsListViewOnScrollListener();
  private AbsListView.OnItemClickListener onItemClickListener;
  private final AbsListView absListView;
  private AdapterView.OnItemLongClickListener onItemLongClickListener;

  public AbsListViewQuickReturnAttacher(AbsListView listView) {
    this.absListView = listView;
    listView.setOnScrollListener(onScrollListener);
    listView.setOnItemClickListener(this);
    listView.setOnItemLongClickListener(this);
  }

  public QuickReturnTargetView addTargetView(View view, int position) {
    return addTargetView(view, position, 0);
  }

  public QuickReturnTargetView addTargetView(View view, int position, int viewHeight) {
    AbsListViewScrollTarget targetView =
        new AbsListViewScrollTarget(absListView, view, position, viewHeight);
    onScrollListener.registerOnScrollListener(targetView);

    return targetView;
  }

  public void removeTargetView(AbsListViewScrollTarget targetView) {
    onScrollListener.unregisterOnScrollListener(targetView);
  }

  public void addOnScrollListener(AbsListView.OnScrollListener listener) {
    onScrollListener.registerOnScrollListener(listener);
  }

  public void setOnItemClickListener(AbsListView.OnItemClickListener listener) {
    onItemClickListener = listener;
  }

  public void setOnItemLongClickListener(AbsListView.OnItemLongClickListener listener) {
    onItemLongClickListener = listener;
  }

  @Override public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view,
      int position, long id) {
    if (onItemClickListener != null) {
      // TODO: Sending incorrect view` and `id` args below.
      onItemClickListener.onItemClick(parent, view,
          position - getClickPositionOffset(parent), id);
    }
  }

  @Override
  public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    if (onItemLongClickListener != null) {
      // TODO: Sending incorrect view` and `id` args below.
      onItemLongClickListener.onItemLongClick(parent, view,
          position - getClickPositionOffset(parent), id);
    }
    return false;
  }

  private int getClickPositionOffset(AdapterView<?> parent) {
    if (parent instanceof ListView) {
      return 1;
    }

    // TODO: getNumColumns may return AUTO_FIT.
    // TODO: Need fallback for Gingerbread.
    if (parent instanceof GridView) {
      return ((GridView) parent).getNumColumns();
    }

    return 0;
  }
}