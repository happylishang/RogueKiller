package com.snail.roguekiller.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessInfo;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mProcessName;

    public void setClickListener(ProcessListAdapter.OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    private ProcessListAdapter.OnItemClickListener mClickListener;

    public ProcessListViewHolder(View itemView) {
        super(itemView);
        bindViews(itemView);
    }

    public void bindViews(View rootView) {
        mProcessName = (TextView) rootView.findViewById(R.id.bt_info);
        mProcessName.setOnClickListener(this);
    }

    public void renderViews(ProcessInfo info) {
        mProcessName.setText(info.appliationName);

    }

    @Override
    public void onClick(View view) {
        if(mClickListener !=null){
            mClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
