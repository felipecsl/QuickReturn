package com.felipecsl.quickreturn.com.felipecsl.quickreturn.library;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import com.felipecsl.quickreturn.com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;

public class QuickReturnAttacher implements AbsListView.OnScrollListener {

    private static final String TAG = "QuickReturnListView";
    private static final int STATE_ONSCREEN = 0;
    private static final int STATE_OFFSCREEN = 1;
    private static final int STATE_RETURNING = 2;
    private static final int STATE_EXPANDED = 3;

    public static final int POSITION_TOP = 0;
    public static final int POSITION_BOTTOM = 1;
    private final ListView listView;

    private int currentState = STATE_ONSCREEN;
    private int position;
    private int minRawY;
    private View quickReturnView;
    private boolean noAnimation;
    private boolean isAnimatedTransition;

    private final SimpleQuickReturnStateTransition defaultTransition = new SimpleQuickReturnStateTransition();
    private final AnimatedQuickReturnStateTransition animatedTransition = new AnimatedQuickReturnStateTransition();
    private final BottomQuickReturnStateTransition bottomTransition = new BottomQuickReturnStateTransition();

    private final CompositeOnScrollListener onScrollListener = new CompositeOnScrollListener();

    public QuickReturnAttacher(final ListView listView, final View targetView, final int position) {
        this.listView = listView;
        this.quickReturnView = targetView;
        this.position = position;

        onScrollListener.registerOnScrollListener(this);
        listView.setOnScrollListener(onScrollListener);
    }

    public QuickReturnAttacher(final ListView listView, final View targetView) {
        this(listView, targetView, POSITION_TOP);
    }

    public View getTargetView() {
        return quickReturnView;
    }

    public void setTargetView(final View quickReturnView) {
        this.quickReturnView = quickReturnView;
    }

    private QuickReturnAdapter getAdapter() {
        ListAdapter adapter = listView.getAdapter();

        if (adapter instanceof WrapperListAdapter)
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();

        if (!(adapter instanceof QuickReturnAdapter))
            throw new UnsupportedOperationException("Your QuickReturn ListView adapter must be an instance of QuickReturnAdapter.");

        return (QuickReturnAdapter) adapter;
    }

    public void addOnScrollListener(final AbsListView.OnScrollListener listener) {
        onScrollListener.registerOnScrollListener(listener);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {

    }

    public int getComputedScrollY() {
        if (listView.getChildCount() == 0 || listView.getAdapter() == null)
            return 0;

        int pos = listView.getFirstVisiblePosition();
        final View view = listView.getChildAt(0);
        return getAdapter().getPositionVerticalOffset(pos) - view.getTop();
    }


    /**
     * Returns the quick return target view alignment.
     * Will be either 0 (POSITION_TOP) or 1 (POSITION_BOTTOM)
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the quick return target view alignment to
     * either 0 (POSITION_TOP) or 1 (POSITION_BOTTOM).
     *
     * @param newPosition The new position
     */
    public void setPosition(int newPosition) {
        position = newPosition;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (listView.getAdapter() == null || quickReturnView == null)
            return;

        final int scrollY = getComputedScrollY();
        final int rawY = -Math.min(getAdapter().getMaxVerticalOffset() - listView.getHeight(), scrollY);
        final int quickReturnHeight = quickReturnView.getHeight();
        int translationY;

        if (position == POSITION_TOP) {
            if (isAnimatedTransition)
                translationY = animatedTransition.determineState(rawY, quickReturnHeight);
            else
                translationY = defaultTransition.determineState(rawY, quickReturnHeight);
        } else {
            translationY = bottomTransition.determineState(rawY, quickReturnHeight);
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            final TranslateAnimation anim = new TranslateAnimation(0, 0, translationY, translationY);
            anim.setFillAfter(true);
            anim.setDuration(0);
            quickReturnView.startAnimation(anim);
        } else {
            quickReturnView.setTranslationY(translationY);
        }
    }

    public boolean isAnimatedTransition() {
        return isAnimatedTransition;
    }

    public void setAnimatedTransition(final boolean isAnimatedTransition) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            Log.w(TAG, "Animated QuickReturn is only supported by Android API Level 11+");
            return;
        }
        this.isAnimatedTransition = isAnimatedTransition;
        currentState = STATE_ONSCREEN;
        minRawY = 0;
    }

    class SimpleQuickReturnStateTransition implements QuickReturnStateTransition {
        public int determineState(int rawY, int quickReturnHeight) {
            int translationY = 0;

            switch (currentState) {
                case STATE_OFFSCREEN:
                    if (rawY <= minRawY)
                        minRawY = rawY;
                    else
                        currentState = STATE_RETURNING;

                    translationY = rawY;
                    break;

                case STATE_ONSCREEN:
                    if (rawY < -quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    }
                    translationY = rawY;
                    break;

                case STATE_RETURNING:
                    translationY = (rawY - minRawY) - quickReturnHeight;
                    if (translationY > 0) {
                        translationY = 0;
                        minRawY = rawY - quickReturnHeight;
                    }

                    if (rawY > 0) {
                        currentState = STATE_ONSCREEN;
                        translationY = rawY;
                    }

                    if (translationY < -quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    }
                    break;
            }
            return translationY;
        }
    }

    class AnimatedQuickReturnStateTransition implements QuickReturnStateTransition {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public int determineState(final int rawY, int quickReturnHeight) {
            int translationY = 0;

            switch (currentState) {
                case STATE_OFFSCREEN:
                    if (rawY <= minRawY) {
                        minRawY = rawY;
                    } else {
                        currentState = STATE_RETURNING;
                    }
                    translationY = rawY;
                    break;

                case STATE_ONSCREEN:
                    if (rawY < -quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    }
                    translationY = rawY;
                    break;

                case STATE_RETURNING:
                    if (translationY > 0) {
                        translationY = 0;
                        minRawY = rawY - quickReturnHeight;
                    } else if (rawY > 0) {
                        currentState = STATE_ONSCREEN;
                        translationY = rawY;
                    } else if (translationY < -quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;

                    } else if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && quickReturnView.getTranslationY() != 0) &&
                            !noAnimation) {

                        noAnimation = true;
                        final TranslateAnimation anim = new TranslateAnimation(0, 0, -quickReturnHeight, 0);
                        anim.setFillAfter(true);
                        anim.setDuration(250);
                        quickReturnView.startAnimation(anim);
                        anim.setAnimationListener(new SimpleAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                noAnimation = false;
                                minRawY = rawY;
                                currentState = STATE_EXPANDED;
                            }
                        });
                    }
                    break;

                case STATE_EXPANDED:
                    if (rawY < minRawY - 2 && !noAnimation) {
                        noAnimation = true;
                        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -quickReturnHeight);
                        anim.setFillAfter(true);
                        anim.setDuration(250);
                        anim.setAnimationListener(new SimpleAnimationListener() {
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                noAnimation = false;
                                currentState = STATE_OFFSCREEN;
                            }
                        });
                        quickReturnView.startAnimation(anim);
                    } else if (translationY > 0) {
                        translationY = 0;
                        minRawY = rawY - quickReturnHeight;
                    } else if (rawY > 0) {
                        currentState = STATE_ONSCREEN;
                        translationY = rawY;
                    } else if (translationY < -quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    } else {
                        minRawY = rawY;
                    }
            }
            return translationY;
        }
    }

    class BottomQuickReturnStateTransition implements QuickReturnStateTransition {
        public int determineState(final int _rawY, int quickReturnHeight) {
            int rawY = getComputedScrollY();
            int translationY = 0;

            switch (currentState) {
                case STATE_OFFSCREEN:
                    if (rawY >= minRawY)
                        minRawY = rawY;
                    else
                        currentState = STATE_RETURNING;

                    translationY = rawY;
                    break;

                case STATE_ONSCREEN:
                    if (rawY > quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    }
                    translationY = rawY;
                    break;

                case STATE_RETURNING:
                    translationY = (rawY - minRawY) + quickReturnHeight;

                    if (translationY < 0) {
                        translationY = 0;
                        minRawY = rawY + quickReturnHeight;
                    }

                    if (rawY == 0) {
                        currentState = STATE_ONSCREEN;
                        translationY = 0;
                    }

                    if (translationY > quickReturnHeight) {
                        currentState = STATE_OFFSCREEN;
                        minRawY = rawY;
                    }
                    break;
            }

            return translationY;
        }
    }
}
