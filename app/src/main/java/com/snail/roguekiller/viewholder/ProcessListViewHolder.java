package com.snail.roguekiller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.RuningTaskInfo;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mProcessName;
    private TextView mPid;

    private ImageView mIconImg;

    public void setClickListener(ProcessListAdapter.OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private ProcessListAdapter.OnItemClickListener mClickListener;

    public ProcessListViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootView) {
        mProcessName = (TextView) rootView.findViewById(R.id.tv_process_name);
        mPid = (TextView) rootView.findViewById(R.id.tv_pid);
        mIconImg = (ImageView) rootView.findViewById(R.id.icon_img);
        rootView.setOnClickListener(this);
    }

    public void renderViews(RuningTaskInfo info) {
        mProcessName.setText(info.applicationName);
        mPid.setText("pid: " + String.valueOf(info.pid));
        mIconImg.setImageDrawable(info.appIcon);
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
