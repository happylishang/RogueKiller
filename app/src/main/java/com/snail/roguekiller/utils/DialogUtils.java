package com.snail.roguekiller.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.snail.roguekiller.R;

/**
 * Created by personal on 16/5/8.
 */
public class DialogUtils {

    public interface CONFIRM_ACTION {
        int LETT_ACTION = 0;
        int RIGHT_ACTION = 1;
    }


    @NonNull
    public static Dialog createBtnDialog(Context context, String title, String left, String right, View.OnClickListener onClickListener) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm);
        TextView titleTv = (TextView) dialog.findViewById(R.id.tv_title);
        titleTv.setText(title);
        TextView leftTv = (TextView) dialog.findViewById(R.id.tv_left);
        leftTv.setText(left);
        leftTv.setTag(CONFIRM_ACTION.LETT_ACTION);
        leftTv.setOnClickListener(onClickListener);
        TextView rightTv = (TextView) dialog.findViewById(R.id.tv_right);
        rightTv.setText(right);
        rightTv.setTag(CONFIRM_ACTION.RIGHT_ACTION);
        rightTv.setOnClickListener(onClickListener);
        return dialog;
    }
}
