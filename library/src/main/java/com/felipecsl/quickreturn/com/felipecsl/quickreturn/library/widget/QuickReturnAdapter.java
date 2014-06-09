package com.felipecsl.quickreturn.com.felipecsl.quickreturn.library.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private final int emptyMeasureSpec;
    private int[] itemsVerticalOffset;

    public QuickReturnAdapter(final ListAdapter wrappedAdapter) {
        this.wrappedAdapter = wrappedAdapter;
        emptyMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        itemsVerticalOffset = new int[wrappedAdapter.getCount()];
        wrappedAdapter.registerDataSetObserver(this);
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
            itemsVerticalOffset[position + 1] = itemsVerticalOffset[position] + v.getMeasuredHeight();

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

    public int getPositionVerticalOffset(int position) {
        if (position >= itemsVerticalOffset.length)
            return 0;

        return itemsVerticalOffset[position];
    }

    public int getMaxVerticalOffset() {
        if (isEmpty())
            return 0;

        final List<Integer> items = new ArrayList<>(itemsVerticalOffset.length);
        for (final int aMItemOffsetY : itemsVerticalOffset) items.add(aMItemOffsetY);
        return Collections.max(items);
    }

    @Override
    public void onChanged() {
        super.onChanged();

        if (wrappedAdapter.getCount() < itemsVerticalOffset.length)
            return;

        int[] newArray = new int[wrappedAdapter.getCount()];
        System.arraycopy(itemsVerticalOffset, 0, newArray, 0, Math.min(itemsVerticalOffset.length, newArray.length));
        itemsVerticalOffset = newArray;
    }
}
