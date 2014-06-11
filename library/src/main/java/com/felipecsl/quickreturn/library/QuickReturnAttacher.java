package com.felipecsl.quickreturn.library;

import android.view.View;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;

public class QuickReturnAttacher {

    private static final String TAG = "QuickReturnAttacher";

    private final CompositeOnScrollListener onScrollListener = new CompositeOnScrollListener();
    private final AbsListView listView;

    public QuickReturnAttacher(final AbsListView listView) {
        this.listView = listView;
        listView.setOnScrollListener(onScrollListener);
    }

    public QuickReturnTargetView addTargetView(final View view, final int position) {
        final QuickReturnTargetView targetView = new QuickReturnTargetView(listView, view, position);
        onScrollListener.registerOnScrollListener(targetView);
        return targetView;
    }

    public void removeTargetView(final QuickReturnTargetView targetView) {
        onScrollListener.unregisterOnScrollListener(targetView);
    }

    public void addOnScrollListener(final AbsListView.OnScrollListener listener) {
        onScrollListener.registerOnScrollListener(listener);
    }
}
