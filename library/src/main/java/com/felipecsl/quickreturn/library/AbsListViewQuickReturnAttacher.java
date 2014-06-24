package com.felipecsl.quickreturn.library;

import android.view.View;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;
import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;

public class AbsListViewQuickReturnAttacher extends QuickReturnAttacher {
    private static final String TAG = "AbsListViewQuickReturnAttacher";

    private final CompositeAbsListViewOnScrollListener onScrollListener = new CompositeAbsListViewOnScrollListener();
    private final AbsListView absListView;

    public AbsListViewQuickReturnAttacher(final AbsListView listView) {
        this.absListView = listView;
        listView.setOnScrollListener(onScrollListener);
    }

    public QuickReturnTargetView addTargetView(final View view, final int position) {
        return addTargetView(view, position, 0);
    }

    public QuickReturnTargetView addTargetView(final View view, final int position, final int viewHeight) {
        final AbsListViewScrollTarget targetView = new AbsListViewScrollTarget(absListView, view, position, viewHeight);
        onScrollListener.registerOnScrollListener(targetView);

        return targetView;
    }

    public void removeTargetView(final AbsListViewScrollTarget targetView) {
        onScrollListener.unregisterOnScrollListener(targetView);
    }

    public void addOnScrollListener(final AbsListView.OnScrollListener listener) {
        onScrollListener.registerOnScrollListener(listener);
    }
}