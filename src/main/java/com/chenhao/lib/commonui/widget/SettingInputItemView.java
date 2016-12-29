
package com.chenhao.lib.commonui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenhao.lib.commonui.R;

/**
 * Created by chenhao on 16/12/30.
 */

public class SettingInputItemView extends FrameLayout implements View.OnClickListener {
    TextView mTitleTextView;
    EditText mSettingsItemInput;
    ImageView mArrowImage;
    View mContainerView;
    int mType;
    OnClickListener mOnClickListener;

    public SettingInputItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingInputItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater;

        if (isInEditMode()) {
            inflater = LayoutInflater.from(getContext());
        } else {
            inflater = LayoutInflater.from(getContext());
        }

        View itemView = inflater.inflate(R.layout.settings_input_item, null);
        mContainerView = itemView;
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(itemView, lp);

        mTitleTextView = (TextView) itemView.findViewById(R.id.settings_item_title);
        mSettingsItemInput = (EditText) itemView.findViewById(R.id.settings_item_input);
        mArrowImage = (ImageView) itemView.findViewById(R.id.settings_item_arrow);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingItemInput, 0, 0);
        String title = a.getString(R.styleable.SettingItemInput_item_input_title);
        int titleSize = a.getDimensionPixelSize(
                R.styleable.SettingItemInput_item_input_title_textSize,
                (int) mTitleTextView.getTextSize());
        String item_input_text = a.getString(R.styleable.SettingItemInput_item_input_text);
        int inputSize = a.getDimensionPixelSize(R.styleable.SettingItemInput_item_input_textSize,
                (int) mSettingsItemInput.getTextSize());
        String item_input_hint = a.getString(R.styleable.SettingItemInput_item_input_hint);
        mType = a.getInt(R.styleable.SettingItemInput_item_input_type, 0);
        boolean isItemLineNoMargin = a
                .getBoolean(R.styleable.SettingItemInput_item_input_line_no_margin, false);
        boolean isItemLineHidden = a
                .getBoolean(R.styleable.SettingItemInput_item_input_line_hidden, false);
        int inputType = a.getInt(R.styleable.SettingItemInput_android_inputType,
                EditorInfo.TYPE_CLASS_TEXT);
        setInputType(inputType);
        int maxLength = a.getInt(R.styleable.SettingItemInput_android_maxLength, 0);
        if (maxLength > 0) {
            setMaxLength(maxLength);
        }

        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        mSettingsItemInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputSize);

        setTitle(title);
        setInputText(item_input_text);
        setInputHint(item_input_hint);
        setType(mType);

        if (!isItemLineHidden) {
            View view = new View(getContext());
            view.setBackgroundColor(0xffe5e5e5);
            lp = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
            lp.gravity = Gravity.BOTTOM;
            if (!isItemLineNoMargin) {
                int margin = getResources()
                        .getDimensionPixelSize(R.dimen.setting_input_item_margin);
                lp.setMargins(margin, 0, margin, 0);
            }
            addView(view, lp);
        }

        a.recycle();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public void setTitle(String str) {
        mTitleTextView.setText(str);
    }

    public void setInputType(int type) {
        mSettingsItemInput.setInputType(type);
    }

    public void setMaxLength(int maxLength) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        mSettingsItemInput.setFilters(fArray);
    }

    public String getInputText() {
        return mSettingsItemInput.getText().toString().trim();
    }

    public void setInputText(String inputText) {
        mSettingsItemInput.setText(inputText);
    }

    public void showInputTextNormal() {
        mSettingsItemInput.setTextColor(ContextCompat.getColor(getContext(), R.color.class_V));
    }

    public void showInputTextWarning() {
        mSettingsItemInput.setTextColor(ContextCompat.getColor(getContext(), R.color.class_Z));
    }

    public void setInputHint(String inputHint) {
        mSettingsItemInput.setHint(inputHint);
    }

    public void setType(int type) {
        mType = type;
        if (mType == 0) {// none
            mArrowImage.setVisibility(View.GONE);
        } else if (mType == 1) {// arrows
            mSettingsItemInput.setInputType(EditorInfo.TYPE_NULL);
            mSettingsItemInput.setHint(null);
            mArrowImage.setVisibility(View.VISIBLE);
            mArrowImage.setOnClickListener(this);
            mSettingsItemInput.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }
}
