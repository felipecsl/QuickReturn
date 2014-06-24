package com.felipecsl.quickreturn.library;

import com.felipecsl.quickreturn.library.widget.ObservableScrollView;

import java.util.ArrayList;

public class CompositeOnScrollListener
        extends ArrayList<ObservableScrollView.OnScrollListener>
        implements ObservableScrollView.OnScrollListener {

    public void registerOnScrollListener(final ObservableScrollView.OnScrollListener listener) {
        add(listener);
    }

    public void unregisterOnScrollListener(final ObservableScrollView.OnScrollListener listener) {
        remove(listener);
    }

    @Override
    public void onScrollChanged(final int scrollY) {
        for (ObservableScrollView.OnScrollListener listener : this)
            listener.onScrollChanged(scrollY);
    }
}
