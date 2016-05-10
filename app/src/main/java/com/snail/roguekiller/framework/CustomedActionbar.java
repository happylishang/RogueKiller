package com.snail.roguekiller.framework;

import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;

import com.snail.roguekiller.utils.AppProfile;

/**
 * Created by personal on 16/5/9.
 */
public class CustomedActionbar {

    public ActionBar mActionBar;

    //
//    public TextView getLeftTv() {
//    }
//
//    public TextView getRightTv() {
//
//    }
//
//    public TextView getMiddlerTv() {
//
//    }
    public void setContentView(View view) {
        mActionBar.setCustomView(view);
    }

    public void setContentView(@LayoutRes int laytoutId) {
        LayoutInflater inflater = LayoutInflater.from(AppProfile.getContext());
        View _actionbar = inflater.inflate(laytoutId, null);
        mActionBar.setCustomView(_actionbar);
    }
}
