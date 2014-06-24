package com.felipecsl.quickreturn.library;

import android.view.View;

import com.felipecsl.quickreturn.library.widget.ObservableScrollView;
import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;
import com.felipecsl.quickreturn.library.widget.ScrollViewScrollTarget;

public class ScrollViewQuickReturnAttacher extends QuickReturnAttacher {

    private static final String TAG = "ScrollViewQuickReturnAttacher";

    private final CompositeOnScrollListener onScrollListener = new CompositeOnScrollListener();
    private final ObservableScrollView scrollView;

    public ScrollViewQuickReturnAttacher(final ObservableScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public QuickReturnTargetView addTargetView(final View view, final int position) {
        return addTargetView(view, position, 0);
    }

    public QuickReturnTargetView addTargetView(final View view, final int position, final int viewHeight) {
        final ScrollViewScrollTarget targetView = new ScrollViewScrollTarget(scrollView, view, position, viewHeight);
        onScrollListener.registerOnScrollListener(targetView);

        return targetView;
    }

    public void removeTargetView(final ScrollViewScrollTarget targetView) {
        onScrollListener.unregisterOnScrollListener(targetView);
    }

    public void addOnScrollListener(final ObservableScrollView.OnScrollListener listener) {
        onScrollListener.registerOnScrollListener(listener);
    }
}
