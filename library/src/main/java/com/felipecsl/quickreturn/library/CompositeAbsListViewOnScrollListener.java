package com.felipecsl.quickreturn.library;

import android.widget.AbsListView;

import java.util.ArrayList;

public class CompositeAbsListViewOnScrollListener
        extends ArrayList<AbsListView.OnScrollListener>
        implements AbsListView.OnScrollListener {

    public void registerOnScrollListener(final AbsListView.OnScrollListener listener) {
        add(listener);
    }

    public void unregisterOnScrollListener(final AbsListView.OnScrollListener listener) {
        remove(listener);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        for (AbsListView.OnScrollListener listener : this)
            listener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        for (AbsListView.OnScrollListener listener : this)
            listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }
}
