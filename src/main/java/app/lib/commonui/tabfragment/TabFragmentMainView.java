
package app.lib.commonui.tabfragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.lib.commonui.R;

/**
 * Created by chenhao on 17/1/14.
 */

public class TabFragmentMainView extends LinearLayout implements View.OnClickListener {

    FrameLayout mFragmentContainer;
    LinearLayout mIndicatorView;
    TabFragmentAdapter mTabFragmentAdapter;
    FragmentManager mFragmentManager;

    int mCurrentIndex = -1;
    View mCurrentView = null;

    TabFragmentChanged mTabFragmentChanged;

    LayoutInflater mLayoutInflater;

    public TabFragmentMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutInflater = LayoutInflater.from(getContext());
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mIndicatorView = (LinearLayout) findViewById(R.id.indicator);
    }

    public void setTabFragmentChanged(TabFragmentChanged tabFragmentChanged) {
        mTabFragmentChanged = tabFragmentChanged;
    }

    public void init(FragmentManager fragmentManager, TabFragmentAdapter adapter) {
        mFragmentManager = fragmentManager;
        mTabFragmentAdapter = adapter;

        mIndicatorView.removeAllViews();
        for (int i = 0; i < mTabFragmentAdapter.getCount(); i++) {
            CharSequence pageTitle = mTabFragmentAdapter.getTabTitle(i);
            int pageIcon = mTabFragmentAdapter.getTabIcon(i);
            int pageTitleColor = mTabFragmentAdapter.getTabTitleColor(i);
            if (pageTitleColor == 0) {
                pageTitleColor = R.color.tabfragment_tab_text_default_color;
            }

            addTabView(i, pageTitle, pageIcon, pageTitleColor);
        }
        if (mCurrentView == null) {
            setCurrentView(0);
        } else {
            setCurrentView(mCurrentView);
        }
    }

    void addTabView(int index, CharSequence title, int resIcon, int color) {
        View view = mLayoutInflater.inflate(R.layout.tabfragment_tab_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource(resIcon);
        imageView.setFocusable(true);
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(title);
        textView.setTextColor(getResources().getColorStateList(color));
        textView.setFocusable(true);
        Tag tag = new Tag();
        tag.index = index;
        view.setTag(tag);
        view.setOnClickListener(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        lp.gravity = Gravity.CENTER;
        mIndicatorView.addView(view, lp);
    }

    public int getCurrentItem() {
        return mCurrentIndex;
    }

    public void setCurrentView(int index) {
        if (index >= 0 && index < mTabFragmentAdapter.getCount()) {
            setCurrentView(mIndicatorView.getChildAt(index));
        }
    }

    public void setCurrentView(View view) {
        if (view == mCurrentView)
            return;
        Object object = view.getTag();
        if (object != null && object instanceof Tag) {
            int lastIndex = -1;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            if (mCurrentView != null) {
                mCurrentView.setSelected(false);
                Tag oldTag = (Tag) mCurrentView.getTag();
                lastIndex = oldTag.index;
                Fragment oldFragment = getFragment(oldTag.index);
                if (oldFragment != null) {
                    fragmentTransaction.hide(oldFragment);
                    oldFragment.onPause();
                    oldFragment.onStop();
                }
            }
            mCurrentView = view;
            mCurrentView.setSelected(true);
            Tag tag = (Tag) object;
            mCurrentIndex = tag.index;
            Fragment newFragment = getFragment(tag.index);
            if (newFragment != null) {
                newFragment.onStart();
                newFragment.onResume();
                fragmentTransaction.show(newFragment);
            } else {
                newFragment = mTabFragmentAdapter.getFragment(tag.index);
                fragmentTransaction.add(mFragmentContainer.getId(), newFragment,
                        getFragmentTag(tag.index));
            }

            fragmentTransaction.commit();

            if (mTabFragmentChanged != null) {
                mTabFragmentChanged.onFragmentChanged(lastIndex, mCurrentIndex);
            }
        }
    }

    public String getFragmentTag(int index) {
        return String.valueOf(index);
    }

    public Fragment getFragment(int index) {
        String tag = getFragmentTag(index);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        setCurrentView(v);
    }

    public static class Tag {
        public int index;
    }
}
