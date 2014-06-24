/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.felipecsl.quickreturn.library.widget;

import android.view.View;
import android.view.ViewTreeObserver;

public class ScrollViewScrollTarget
        extends QuickReturnTargetView
        implements ObservableScrollView.OnScrollListener {

    private final ObservableScrollView scrollView;
    private int quickReturnHeight;
    private int maxScrollY;

    public ScrollViewScrollTarget(final ObservableScrollView scrollView, final View targetView, final int position, final int targetViewHeight) {
        super(targetView, position);
        this.scrollView = scrollView;
        scrollView.setOnScrollListener(this);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onScrollChanged(scrollView.getScrollY());
                        maxScrollY = scrollView.computeVerticalScrollRange() - scrollView.getHeight();
                        quickReturnHeight = quickReturnView.getHeight();
                    }
                }
        );
    }

    @Override
    protected int getComputedScrollY() {
        return scrollView.getScrollY();
    }

    @Override
    public void onScrollChanged(int scrollY) {
        if (quickReturnView == null)
            return;

        scrollY = Math.min(maxScrollY, scrollY);

        int rawY = -scrollY;
        final int translationY = currentTransition.determineState(rawY, quickReturnView.getHeight());

        translateTo(translationY);
    }
}
