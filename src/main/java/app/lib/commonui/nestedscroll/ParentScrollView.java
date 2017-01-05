
package app.lib.commonui.nestedscroll;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by chenhao on 16/12/28.
 */

public class ParentScrollView extends ScrollView implements NestedScrollingParent {

    private NestedScrollingParentHelper mParentHelper;

    public ParentScrollView(Context context) {
        this(context, null);
    }

    public ParentScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
            int dyUnconsumed) {
        // final int oldScrollY = getScrollY();
        scrollBy(0, dyUnconsumed);
        // final int myConsumed = getScrollY() - oldScrollY;
        // final int myUnconsumed = dyUnconsumed - myConsumed;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            int scrollHeight = getHeight();
            int contentHeight = getChildAt(0).getHeight();
            int scrollY = getScrollY();

            int maxScrollY = (contentHeight - scrollHeight);

            if (scrollY < maxScrollY) {
                // 还未滑到底
                consumed[1] = dy;
                smoothScrollBy(0, dy);
            } else {

            }

        } else if (dy < 0) {
            int childScrollY = target.getScrollY();

            int parentDy = childScrollY + dy;
            if (parentDy < 0) {
                consumed[1] = parentDy;
                smoothScrollBy(0, parentDy);
            }

        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        // if (!consumed) {
        // flingWithNestedDispatch((int) velocityY);
        // return true;
        // }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }
}
