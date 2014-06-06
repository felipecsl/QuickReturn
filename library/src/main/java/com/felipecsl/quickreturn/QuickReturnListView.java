package com.felipecsl.quickreturn;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListView;

public class QuickReturnListView extends ListView implements AbsListView.OnScrollListener {

    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private static final String TAG = "QuickReturnListView";
    private int mState = STATE_ONSCREEN;
    private int mMinRawY;
    private View quickReturnView;

    public QuickReturnListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {

    }

    public int getComputedScrollY() {
        if (getChildCount() == 0 || getAdapter() == null)
            return 0;

        int pos = getFirstVisiblePosition();
        final View view = getChildAt(0);
        return getAdapter().getPositionOffsetY(pos) - view.getTop();
    }

    public void setAdapter(final QuickReturnAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void setQuickReturnView(final View quickReturnView) {
        this.quickReturnView = quickReturnView;
    }

    @Override
    public QuickReturnAdapter getAdapter() {
        return (QuickReturnAdapter) super.getAdapter();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (getAdapter() == null || quickReturnView == null)
            return;

        final int scrollY = getComputedScrollY();
        int translationY = 0;
        final int rawY = -Math.min(getAdapter().getMaxOffsetY() - getHeight(), scrollY);
        final int quickReturnHeight = quickReturnView.getHeight();

        switch (mState) {
            case STATE_OFFSCREEN:
                if (rawY <= mMinRawY)
                    mMinRawY = rawY;
                else
                    mState = STATE_RETURNING;

                translationY = rawY;
                break;

            case STATE_ONSCREEN:
                if (rawY < -quickReturnHeight) {
                    mState = STATE_OFFSCREEN;
                    mMinRawY = rawY;
                }
                translationY = rawY;
                break;

            case STATE_RETURNING:
                translationY = (rawY - mMinRawY) - quickReturnHeight;
                if (translationY > 0) {
                    translationY = 0;
                    mMinRawY = rawY - quickReturnHeight;
                }

                if (rawY > 0) {
                    mState = STATE_ONSCREEN;
                    translationY = rawY;
                }

                if (translationY < -quickReturnHeight) {
                    mState = STATE_OFFSCREEN;
                    mMinRawY = rawY;
                }
                break;
        }

        Log.d(TAG, "Translation Y = " + translationY);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            final TranslateAnimation anim = new TranslateAnimation(0, 0, translationY, translationY);
            anim.setFillAfter(true);
            anim.setDuration(0);
            quickReturnView.startAnimation(anim);
        } else {
            quickReturnView.setTranslationY(translationY);
        }
    }
}
