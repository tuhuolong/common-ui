
package com.chenhao.lib.commonui.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenhao.lib.commonui.R;

/**
 * Created by chenhao on 16/12/28.
 */

public class BannerPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private int mIconCommonRes = R.drawable.banner_indicator_circle;
    private int mIconSelectedRes = R.drawable.banner_indicator_circle_selected;

    public BannerPagerIndicator(Context context) {
        this(context, null);
    }

    public BannerPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIndicatorCount(int count) {
        int childcount = getChildCount();
        if (childcount == count) {
            return;
        }
        if (childcount < count) {
            for (int i = childcount; i < count; ++i) {
                ImageView imageView = new ImageView(getContext());
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setPadding(10, 10, 10, 10);
                imageView.setLayoutParams(params);
                addView(imageView);
                imageView.setImageResource(mIconCommonRes);
            }
        } else {
            for (int i = childcount - 1; i >= count; --i) {
                removeViewAt(i);
            }
        }
    }

    public void setSelectedIndicator(int sel) {
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            ImageView imageView = (ImageView) getChildAt(i);
            if (i == sel) {
                imageView.setImageResource(mIconSelectedRes);
            } else {
                imageView.setImageResource(mIconCommonRes);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setSelectedIndicator(i % getChildCount());
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
