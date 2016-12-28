
package com.chenhao.lib.commonui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chenhao.lib.commonui.R;
import com.chenhao.lib.commonui.widget.CircleProgressBar;

import java.text.NumberFormat;

/**
 * Created by chenhao on 16/12/28.
 */

public class ProgressDialog extends AlertDialog {

    int mProgress;
    int mMax;
    private Context mContext;
    private ProgressBar mIndeterminateProgress;
    private CircleProgressBar mDeterminateProgress;
    private TextView mProgressMessage;
    private TextView mProgressPercent;
    private TextView mProgressCancel;
    private CharSequence mMessage = null;
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;
    private boolean mIsIndeterminate;
    private boolean mIsCancelable;
    private Handler mPercentUpdateHandler;

    public ProgressDialog(Context context) {
        this(context, R.style.AlertDialog);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);

        initFormats();
        mContext = context;
        setIndeterminate(true);
        setCancelable(true);
    }

    public static ProgressDialog show(Context context, CharSequence title,
            CharSequence message) {
        return show(context, title, message, true);
    }

    public static ProgressDialog show(Context context, CharSequence title,
            CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false);
    }

    public static ProgressDialog show(Context context, CharSequence title,
            CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static ProgressDialog show(Context context, CharSequence title,
            CharSequence message, boolean indeterminate,
            boolean cancelable, OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    private void initFormats() {
        mProgressNumberFormat = "%1d/%2d";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.progress_dialog, null);

        /*
         * Use a separate handler to update the text views as they must be updated on the same
         * thread that created them.
         */
        mPercentUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // Update the number and percent
                int max = mDeterminateProgress.getMax();
                int progress = mDeterminateProgress.getProgress();
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(
                            mProgressPercentFormat.format(percent));
                    mProgressPercent.setText(tmp);
                } else {
                    mProgressPercent.setText("");
                }
            }
        };

        mIndeterminateProgress = (ProgressBar) view.findViewById(R.id.indeterminate_progress);
        mDeterminateProgress = (CircleProgressBar) view.findViewById(R.id.determinate_progress);
        mProgressPercent = (TextView) view.findViewById(R.id.progress_percent);
        mProgressMessage = (TextView) view.findViewById(R.id.progress_message);
        mProgressCancel = (TextView) view.findViewById(R.id.cancel_btn);
        setView(view);

        mIndeterminateProgress.setIndeterminate(true);

        if (mMessage != null) {
            setMessage(mMessage);
        }

        setIndeterminate(mIsIndeterminate);
        if (mIsCancelable) {
            mProgressCancel.setVisibility(View.VISIBLE);
            mProgressCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });
        } else {
            mProgressCancel.setVisibility(View.GONE);
        }
        if (mProgress > 0) {
            mDeterminateProgress.setProgress(mProgress);
        }
        if (mMax > 0) {
            mDeterminateProgress.setMax(mMax);
        }
        onProgressChanged();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);

        mIsCancelable = cancelable;
    }

    public int getMax() {
        if (mDeterminateProgress != null) {
            return mDeterminateProgress.getMax();
        }

        return 0;
    }

    public void setMax(int max) {
        if (mIsIndeterminate) {
            return;
        }

        mMax = max;
        if (mDeterminateProgress != null) {
            mDeterminateProgress.setMax(max);
            onProgressChanged();
        }
    }

    public int getProgress() {
        if (mDeterminateProgress != null) {
            return mDeterminateProgress.getProgress();
        }

        return 0;
    }

    public void setProgress(int progress) {
        if (mIsIndeterminate) {
            return;
        }

        mProgress = progress;
        if (mDeterminateProgress != null) {
            mDeterminateProgress.setProgress(progress);
            onProgressChanged();
        }
    }

    public boolean isIndeterminate() {
        return mIsIndeterminate;
    }

    public void setIndeterminate(boolean indeterminate) {
        mIsIndeterminate = indeterminate;

        if (mIndeterminateProgress != null && mDeterminateProgress != null) {
            if (indeterminate) {
                mIndeterminateProgress.setVisibility(View.VISIBLE);
                mDeterminateProgress.setVisibility(View.GONE);
            } else {
                mIndeterminateProgress.setVisibility(View.GONE);
                mDeterminateProgress.setVisibility(View.VISIBLE);
            }
        }

        if (mProgressPercent != null && mIsIndeterminate) {
            mProgressPercent.setText("");
        }
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    public void setProgressNumberFormat(String format) {
        mProgressNumberFormat = format;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat format) {
        mProgressPercentFormat = format;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (!mIsIndeterminate) {
            if (mPercentUpdateHandler != null && !mPercentUpdateHandler.hasMessages(0)) {
                mPercentUpdateHandler.sendEmptyMessage(0);
            }
        }
    }

}
