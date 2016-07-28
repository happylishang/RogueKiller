package com.snail.roguekiller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.RuningAppInfo;
import com.snail.roguekiller.threadpool.DrawableFecherUtils;

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

    public void renderViews(RuningAppInfo info) {
        mProcessName.setText(info.applicationName);
        StringBuilder builder = new StringBuilder();
        builder.append("pid: ").append(String.valueOf(info.pid));
        if (info.taskID > 0) {
            builder.append("  TaskId: ").append(info.taskID);
        }
        mPid.setText(builder.toString());
        DrawableFecherUtils.loadDrawable(mIconImg,info.mApplicationInfo);
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
