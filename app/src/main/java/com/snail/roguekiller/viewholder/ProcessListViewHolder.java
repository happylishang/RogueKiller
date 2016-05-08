package com.snail.roguekiller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.snail.roguekiller.R;
import com.snail.roguekiller.datamodel.ProcessInfo;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessListViewHolder extends RecyclerView.ViewHolder {

    private TextView mProcessName;

    public ProcessListViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootView) {
        mProcessName = (TextView) rootView.findViewById(R.id.bu);
    }

    public void renderViews(ProcessInfo info) {
        mProcessName.setText(info.processName);
    }
}
