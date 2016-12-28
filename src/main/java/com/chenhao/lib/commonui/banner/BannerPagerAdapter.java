
package com.chenhao.lib.commonui.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenhao.lib.commonui.R;
import com.chenhao.lib.commonui.banner.util.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by chenhao on 16/12/28.
 */

public class BannerPagerAdapter extends PagerAdapter {
    boolean mAutoLoop;

    Context mContext;
    private List<String> mData;

    public BannerPagerAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public void setAutoLoop(boolean autoLoop) {
        mAutoLoop = autoLoop;
    }

    @Override
    public int getCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        } else {
            return mAutoLoop ? (mData.size() > 1 ? Integer.MAX_VALUE : 1) : mData.size();
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View bannerItem = LayoutInflater.from(mContext).inflate(R.layout.banner_item, null);
        SimpleDraweeView imageView = (SimpleDraweeView) bannerItem.findViewById(R.id.banner_image);
        FrescoUtil.loadImage(imageView, mData.get(getRealPosition(position)), null);
        container.addView(bannerItem);
        return bannerItem;
    }

    private int getRealPosition(int position) {
        if (mData == null || mData.size() == 0) {
            return position;
        } else {
            if (!mAutoLoop) {
                return position;
            }
            int count = mData.size();
            if (count == 1) {
                return 0;
            } else {
                return position % count;
            }
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
