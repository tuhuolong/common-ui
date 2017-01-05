
package app.lib.commonui.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by chenhao on 16/12/28.
 */

public class BannerViewPager extends ViewPager {
    private static final int MIN_LOOP_INTERVAL = 1000;
    private static final int LOOP_INTERVAL = 5000;

    private int mLoopInterval = LOOP_INTERVAL;
    private int mActualCount = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mLooperRunnable = new Runnable() {
        @Override
        public void run() {
            onLoop();
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        mActualCount = adapter.getCount();

        setCurrentItem((Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % mActualCount)), false);

        startLoop();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                startLoop();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    stopLoop();
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    startLoop();
                }
            }
        });
    }

    public void setLoopInterval(int loopInterval) {
        if (loopInterval > MIN_LOOP_INTERVAL) {
            mLoopInterval = loopInterval;
        }
    }

    private void onLoop() {
        int current = getCurrentItem();
        setCurrentItem(current + 1);
    }

    private void startLoop() {
        if (mActualCount > 1) {
            mHandler.removeCallbacks(mLooperRunnable);
            mHandler.postDelayed(mLooperRunnable, mLoopInterval);
        }
    }

    private void stopLoop() {
        mHandler.removeCallbacks(mLooperRunnable);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        startLoop();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        stopLoop();
    }
}
