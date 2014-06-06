package com.felipecsl.quickreturn;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private final int emptyMeasureSpec;
    private int[] mItemOffsetY;

    public QuickReturnAdapter(final ListAdapter wrappedAdapter) {
        this.wrappedAdapter = wrappedAdapter;
        emptyMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mItemOffsetY = new int[wrappedAdapter.getCount()];
    }

    @Override
    public boolean areAllItemsEnabled() {
        return wrappedAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(final int position) {
        return wrappedAdapter.isEnabled(position);
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        wrappedAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        wrappedAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return wrappedAdapter.getCount();
    }

    @Override
    public Object getItem(final int position) {
        return wrappedAdapter.getItem(position);
    }

    @Override
    public long getItemId(final int position) {
        return wrappedAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return wrappedAdapter.hasStableIds();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View v = wrappedAdapter.getView(position, convertView, parent);
        v.measure(emptyMeasureSpec, emptyMeasureSpec);

        if (position + 1 < getCount())
            mItemOffsetY[position + 1] = mItemOffsetY[position] + v.getMeasuredHeight();

        return v;
    }

    @Override
    public int getItemViewType(final int position) {
        return wrappedAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return wrappedAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return wrappedAdapter.isEmpty();
    }

    public int getPositionOffsetY(int position) {
        if (position >= mItemOffsetY.length)
            return 0;

        return mItemOffsetY[position];
    }

    public int getMaxOffsetY() {
        final List<Integer> items = new ArrayList<>(mItemOffsetY.length);
        for (final int aMItemOffsetY : mItemOffsetY) items.add(aMItemOffsetY);
        return Collections.max(items);
    }
}
