package com.felipecsl.quickreturn.com.felipecsl.quickreturn.library;

import android.widget.AbsListView;

import java.util.ArrayList;

public class CompositeOnScrollListener
        extends ArrayList<AbsListView.OnScrollListener>
        implements AbsListView.OnScrollListener {

    public void registerOnScrollListener(final AbsListView.OnScrollListener listener) {
        add(listener);
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
