package com.felipecsl.quickreturn.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * A custom ScrollView that can accept a scroll listener.
 */
public class ObservableScrollView extends ScrollView {
    private OnScrollListener mCallbacks;

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null)
            mCallbacks.onScrollChanged(t);
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mCallbacks = listener;
    }

    public static interface OnScrollListener {
        public void onScrollChanged(int scrollY);
    }
}