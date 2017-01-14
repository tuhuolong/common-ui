
package app.lib.commonui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import app.lib.commonui.R;

/**
 * Created by chenhao on 17/1/14.
 */

public class TitleBar extends FrameLayout {
    private TextView mLeftTextView;
    private ImageView mLeftImageView;
    private TextView mTitleTextView;
    private ImageView mTitleImageView;
    private TextView mRightTextView;
    private ImageView mRightImageView;
    private ImageView mSearchImageView;
    private EditText mSearchEditView;
    private LayoutInflater mInflater;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundResource(R.drawable.title_bar_bg);

        Drawable leftImage = null;
        String leftText = null;
        Drawable titleImage = null;
        String titleText = null;
        Drawable rightImage = null;
        String rightText = null;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            leftImage = a.getDrawable(R.styleable.TitleBar_leftImage);
            leftText = a.getString(R.styleable.TitleBar_leftText);
            titleImage = a.getDrawable(R.styleable.TitleBar_titleImage);
            titleText = a.getString(R.styleable.TitleBar_titleText);
            rightImage = a.getDrawable(R.styleable.TitleBar_rightImage);
            rightText = a.getString(R.styleable.TitleBar_rightText);
            a.recycle();
        }
        if (isInEditMode()) {
            mInflater = LayoutInflater.from(getContext());
        } else {
            mInflater = LayoutInflater.from(getContext());
        }

        if (leftImage != null) {
            setupLeftImageButton(leftImage);
        } else if (!TextUtils.isEmpty(leftText)) {
            setupLeftTextButton(leftText);
        }

        if (rightImage != null) {
            setupRightImageButton(rightImage);
        } else if (!TextUtils.isEmpty(rightText)) {
            setupRightTextButton(rightText);
        }

        if (titleImage != null) {
            setupTitleImage(titleImage);
        } else if (!TextUtils.isEmpty(titleText)) {
            setupTitleText(titleText);
        }

    }

    /**
     * 设置左边按钮图片
     * 
     * @param drawable
     */
    public void setupLeftImageButton(Drawable drawable) {
        if (mLeftImageView == null) {
            mLeftImageView = (ImageView) mInflater.inflate(R.layout.title_bar_left_image, this,
                    false);
            mLeftImageView.setId(R.id.bar_left_button);
            addView(mLeftImageView);
        }
        mLeftImageView.setImageDrawable(drawable);
    }

    /**
     * 设置左边按钮文字
     * 
     * @param text
     */
    public void setupLeftTextButton(String text) {
        if (mLeftTextView == null) {
            mLeftTextView = (TextView) mInflater.inflate(R.layout.title_bar_left_text, this, false);
            mLeftTextView.setId(R.id.bar_left_button);
            addView(mLeftTextView);
        }
        mLeftTextView.setText(text);
    }

    /**
     * 设置右边按钮图片
     * 
     * @param drawable
     */
    public void setupRightImageButton(Drawable drawable) {
        if (mRightImageView == null) {
            mRightImageView = (ImageView) mInflater.inflate(R.layout.title_bar_right_image, this,
                    false);
            mRightImageView.setId(R.id.bar_right_button);
            addView(mRightImageView);
        }
        mRightImageView.setImageDrawable(drawable);
    }

    /**
     * 设置右边按钮文字
     * 
     * @param text
     */
    public void setupRightTextButton(String text) {
        if (mRightTextView == null) {
            mRightTextView = (TextView) mInflater.inflate(R.layout.title_bar_right_text, this,
                    false);
            mRightTextView.setId(R.id.bar_right_button);
            addView(mRightTextView);
        }
        mRightTextView.setText(text);
    }

    /**
     * 设置标题图片
     * 
     * @param drawable
     */
    public void setupTitleImage(Drawable drawable) {
        if (mTitleImageView == null) {
            mTitleImageView = (ImageView) mInflater.inflate(R.layout.title_bar_title_image, this,
                    false);
            mTitleImageView.setId(R.id.bar_title);
            addView(mTitleImageView);
        }
        mTitleImageView.setImageDrawable(drawable);
    }

    /**
     * 设置标题文字
     * 
     * @param text
     */
    public void setupTitleText(String text) {
        if (mTitleTextView == null) {
            mTitleTextView = (TextView) mInflater.inflate(R.layout.title_bar_title_text, this,
                    false);
            mTitleTextView.setId(R.id.bar_title);
            addView(mTitleTextView);
        }
        mTitleTextView.setText(text);
    }

    /**
     * 设置左边按钮点击事件
     * 
     * @param listener
     */
    public void setLeftClickListener(OnClickListener listener) {
        if (mLeftImageView != null) {
            mLeftImageView.setOnClickListener(listener);
        }
        if (mLeftTextView != null) {
            mLeftTextView.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边按钮点击事件
     * 
     * @param listener
     */
    public void setRightClickListener(OnClickListener listener) {
        if (mRightImageView != null) {
            mRightImageView.setOnClickListener(listener);
        }
        if (mRightTextView != null) {
            mRightTextView.setOnClickListener(listener);
        }
    }

    /**
     * 设置title按钮点击事件
     * 
     * @param listener
     */
    public void setTitleClickListener(OnClickListener listener) {
        if (mTitleImageView != null) {
            mTitleImageView.setOnClickListener(listener);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setOnClickListener(listener);
        }
    }

    public TextView getLeftTextView() {
        return mLeftTextView;
    }

    public ImageView getLeftImageView() {
        return mLeftImageView;
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public ImageView getTitleImageView() {
        return mTitleImageView;
    }

    public TextView getRightTextView() {
        return mRightTextView;
    }

    public ImageView getRightImageView() {
        return mRightImageView;
    }
}
