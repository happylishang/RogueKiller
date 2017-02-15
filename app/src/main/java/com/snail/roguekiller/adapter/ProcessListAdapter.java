package com.snail.roguekiller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.roguekiller.R;
import com.snail.roguekiller.datamodel.RunningAppInfo;
import com.snail.roguekiller.viewholder.ProcessListViewHolder;

import java.util.List;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessListAdapter extends RecyclerView.Adapter<ProcessListViewHolder>{


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;
    private List<RunningAppInfo> mProcessInfos;

    public ProcessListAdapter(List<RunningAppInfo> infoList) {
        this.mProcessInfos = infoList;
    }

    @Override
    public ProcessListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_hodler_process_list, parent, false);
        return new ProcessListViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ProcessListViewHolder holder, int position) {
        holder.renderViews(mProcessInfos.get(position));
        holder.setClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mProcessInfos == null ? 20 : mProcessInfos.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


}
