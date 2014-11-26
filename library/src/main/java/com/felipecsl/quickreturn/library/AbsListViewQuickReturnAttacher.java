package com.felipecsl.quickreturn.library;

import android.net.Uri.Builder;
import android.os.Build;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Adapter;

import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;
import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;
import com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;

public class AbsListViewQuickReturnAttacher
        extends QuickReturnAttacher
        implements AdapterView.OnItemClickListener {
    private static final String TAG = "AbsListViewQuickReturnAttacher";

    private final CompositeAbsListViewOnScrollListener onScrollListener = new CompositeAbsListViewOnScrollListener();
    private AbsListView.OnItemClickListener onItemClickListener;
    private final AbsListView absListView;

    public AbsListViewQuickReturnAttacher(final AbsListView listView) {
        this.absListView = listView;
        listView.setOnScrollListener(onScrollListener);
        listView.setOnItemClickListener(this);
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

    public void setOnItemClickListener(final AbsListView.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        int positionOffset;
        if (onItemClickListener != null) {
            if (parent instanceof ListView)
                positionOffset = 1;
            else if (parent instanceof GridView)
                // TODO: getNumColumns may return AUTO_FIT.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    positionOffset = ((GridView) parent).getNumColumns();
                } else {
                    Adapter adapter = absListView.getAdapter();
                    if (adapter instanceof QuickReturnAdapter) {
                        positionOffset = ((QuickReturnAdapter) absListView.getAdapter()).getNumColumns();
                    } else {
                        positionOffset = 0;
                    }
                }
            else
                positionOffset = 0;

            // TODO: Sending incorrect view` and `id` args below.
            onItemClickListener.onItemClick(parent, view, position - positionOffset, id);
        }
    }
}