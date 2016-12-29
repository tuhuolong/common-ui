
package com.chenhao.lib.commonui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhao.lib.commonui.R;

/**
 * Created by chenhao on 16/12/28.
 */

public class SettingItemView extends FrameLayout
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    TextView mTitleTextView;
    TextView mSubTitleTextView;
    TextView mItemIndicator;
    TextView mValueTextView;
    SwitchButton mSwitchButton;
    CheckBox mSettingsItemCheckbox;
    ImageView mOnclickImageView;
    ImageView mSettingsItemIcon;
    View mContainerView;
    ImageView mSelectImageView;
    View mTitleContainer;
    int mType;
    OnClickListener mOnClickListener;
    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    boolean mSelected = false;
    OnSelectedListener mOnSelectedListener;

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View itemView;
        if (isInEditMode()) { // for preview in xml
            itemView = LayoutInflater.from(context).inflate(R.layout.setting_item, null);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.setting_item, null);
        }

        mContainerView = itemView;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        addView(itemView, lp);

        mTitleTextView = (TextView) itemView.findViewById(R.id.settings_item_title);
        mSubTitleTextView = (TextView) itemView.findViewById(R.id.settings_item_sub_title);
        mItemIndicator = (TextView) itemView.findViewById(R.id.setting_item_indicator);
        mSwitchButton = (SwitchButton) itemView.findViewById(R.id.settings_item_switch_btn);
        mSettingsItemCheckbox = (CheckBox) itemView.findViewById(R.id.settings_item_checkbox);
        mOnclickImageView = (ImageView) itemView.findViewById(R.id.settings_item_arrow);
        mValueTextView = (TextView) itemView.findViewById(R.id.settings_item_value);
        mSelectImageView = (ImageView) itemView.findViewById(R.id.settings_item_select);
        mSettingsItemIcon = (ImageView) itemView.findViewById(R.id.settings_item_icon);
        mTitleContainer = itemView.findViewById(R.id.title_container);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem, 0, 0);
        int iconResId = a.getResourceId(R.styleable.SettingItem_item_icon, 0);
        String title = a.getString(R.styleable.SettingItem_item_title);
        int titleSize = a.getDimensionPixelSize(R.styleable.SettingItem_item_title_textSize,
                (int) mTitleTextView.getTextSize());
        String subTitle = a.getString(R.styleable.SettingItem_item_subtitle);
        int subTitleSize = a.getDimensionPixelSize(R.styleable.SettingItem_item_subtitle_textSize,
                (int) mSubTitleTextView.getTextSize());
        String item_indicator_text = a.getString(R.styleable.SettingItem_item_indicator_text);
        int item_indicator_background = a
                .getResourceId(R.styleable.SettingItem_item_indicator_background, 0);
        String value = a.getString(R.styleable.SettingItem_item_value);
        int valueTextSize = a.getDimensionPixelSize(R.styleable.SettingItem_item_value_textSize,
                (int) mValueTextView.getTextSize());
        mType = a.getInt(R.styleable.SettingItem_item_type, 1);
        boolean isItemLineNoMargin = a.getBoolean(R.styleable.SettingItem_item_line_no_margin,
                false);
        mSelected = a.getBoolean(R.styleable.SettingItem_item_select, false);
        boolean isItemLineHidden = a.getBoolean(R.styleable.SettingItem_item_line_hidden, false);

        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        mSubTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleSize);
        mValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueTextSize);

        setIcon(iconResId);
        setTitle(title);
        setSubTitle(subTitle);
        setIndicatorText(item_indicator_text);
        setIndicatorBackground(item_indicator_background);
        setValue(value);
        setType(mType);

        if (!isItemLineHidden) {
            View view = new View(getContext());
            view.setBackgroundColor(0xffe5e5e5);
            lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
            lp.gravity = Gravity.BOTTOM;
            if (!isItemLineNoMargin) {
                int margin = getResources().getDimensionPixelSize(R.dimen.setting_item_margin);
                lp.setMargins(margin, 0, margin, 0);
            }
            addView(view, lp);
        }

        a.recycle();
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.mOnSelectedListener = listener;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public boolean isChecked() {
        if (mSwitchButton != null) {
            return mSwitchButton.isChecked();
        }
        if (mSettingsItemCheckbox != null) {
            return mSettingsItemCheckbox.isChecked();
        }
        return false;
    }

    public void setChecked(boolean checked) {
        if (mSwitchButton != null) {
            mSwitchButton.setOnCheckedChangeListener(null);
            mSwitchButton.setChecked(checked);
            mSwitchButton.setOnCheckedChangeListener(this);
        }
        if (mSettingsItemCheckbox != null) {
            mSettingsItemCheckbox.setOnCheckedChangeListener(null);
            mSettingsItemCheckbox.setChecked(checked);
            mSettingsItemCheckbox.setOnCheckedChangeListener(this);
        }
    }

    public View getInfoView() {
        return mValueTextView;
    }

    public void setTitle(String str) {
        mTitleTextView.setText(str);
    }

    public void setIcon(int icon) {
        if (icon != 0) {
            mSettingsItemIcon.setImageResource(icon);
            mSettingsItemIcon.setVisibility(View.VISIBLE);
        } else {
            mSettingsItemIcon.setVisibility(View.GONE);
        }
    }

    public void setSubTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            mSubTitleTextView.setVisibility(View.GONE);
        } else {
            mSubTitleTextView.setText(str);
            mSubTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setIndicatorText(String indicatorText) {
        if (TextUtils.isEmpty(indicatorText)) {
            mItemIndicator.setVisibility(View.GONE);
        } else {
            mItemIndicator.setVisibility(View.VISIBLE);
        }
        mItemIndicator.setText(indicatorText);
    }

    public void setIndicatorBackground(int resId) {
        if (resId > 0) {
            mItemIndicator.setBackgroundResource(resId);
        }
    }

    public void setValue(String str) {
        if (!TextUtils.isEmpty(str)) {
            mValueTextView.setText(str);
            mValueTextView.setVisibility(View.VISIBLE);
        } else {
            mValueTextView.setVisibility(View.GONE);
        }
    }

    public void setSelect(boolean select) {
        mSelected = select;
        if (select) {
            mSelectImageView.setVisibility(VISIBLE);
            mTitleTextView.setTextColor(getResources()
                    .getColor(R.color.setting_item_default_title_text_color_selected));
        } else {
            mTitleTextView.setTextColor(
                    getResources().getColor(R.color.setting_item_default_title_text_color_normal));
            mSelectImageView.setVisibility(INVISIBLE);
        }
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setType(int type) {
        mType = type;
        if (mType == 0) {// none
            mSwitchButton.setVisibility(View.GONE);
            mSettingsItemCheckbox.setVisibility(View.GONE);
            mOnclickImageView.setVisibility(View.GONE);
            mSelectImageView.setVisibility(GONE);
        } else if (mType == 1) {// arrows
            mSwitchButton.setVisibility(View.GONE);
            mSettingsItemCheckbox.setVisibility(View.GONE);
            mContainerView.setOnClickListener(this);
            mSelectImageView.setVisibility(GONE);
        } else if (mType == 2) {// switch
            mOnclickImageView.setVisibility(View.GONE);
            mSwitchButton.setOnCheckedChangeListener(this);
            mSettingsItemCheckbox.setVisibility(GONE);
            mSelectImageView.setVisibility(GONE);
        } else if (mType == 3) {// select
            mSwitchButton.setVisibility(View.GONE);
            mSettingsItemCheckbox.setVisibility(View.GONE);
            mOnclickImageView.setVisibility(View.GONE);
            mContainerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mSelected) {
                        mSelected = true;
                        setSelect(mSelected);
                        if (mOnSelectedListener != null) {
                            mOnSelectedListener.onSelected(SettingItemView.this);
                        }
                    } else {
                        mSelected = false;
                        setSelect(mSelected);
                    }
                }
            });
            setSelect(mSelected);
        } else if (mType == 4) {
            mSwitchButton.setVisibility(View.GONE);
            mSettingsItemCheckbox.setVisibility(View.VISIBLE);
            mOnclickImageView.setVisibility(View.GONE);
            mSelectImageView.setVisibility(GONE);
            mContainerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSettingsItemCheckbox.setChecked(!mSettingsItemCheckbox.isChecked());
                    if (mOnCheckedChangeListener != null) {
                        mOnCheckedChangeListener.onCheckedChanged(mSettingsItemCheckbox,
                                mSettingsItemCheckbox.isChecked());
                    }
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(SettingItemView.this);
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(buttonView, isChecked);
        }
    }

    public interface OnSelectedListener {
        void onSelected(View view);
    }
}
