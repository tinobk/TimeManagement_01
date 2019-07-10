package pomobox.base;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public abstract class BaseAlertDialog extends AlertDialog.Builder {
    private Context mContext;
    private String mTitle;
    private String mMessage;
    private String mTextNegative;
    private String mTextPositive;
    private int mIconResId;

    protected BaseAlertDialog(@NonNull Context context, String title, String message,
              String textNegative, String textPositive, int icon) {
        super(context);
        mContext = context;
        mTitle = title;
        mMessage = message;
        mTextNegative = textNegative;
        mTextPositive = textPositive;
        mIconResId = icon;
    }

    protected abstract void actionClickPositive();

    protected abstract void actionClickNegative();

    public void showDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(mTitle)
                .setMessage(mMessage)
                .setIcon(mIconResId)
                .setCancelable(false)
                .setNegativeButton(mTextNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actionClickNegative();
                    }
                })
                .setPositiveButton(mTextPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int which) {
                        actionClickPositive();
                    }
                }).create().show();
    }
}
