package com.snail.roguekiller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseFragment;
import com.snail.roguekiller.presenter.ProcessListPresenter;
import com.snail.roguekiller.utils.DialogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by personal on 16/5/7.
 */
public class ProcessListFragment extends BaseFragment<ProcessListPresenter> {


    private View rootView;
    private RecyclerView mProcessList;

    @Override
    protected ProcessListPresenter createPresenter() {
        return new ProcessListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_process_list, container, false);
        initView();
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    private void initView() {
        mProcessList = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProcessList.setLayoutManager(layoutManager);
        mPresenter.initAdapter();

    }

    public void initAdapter(RecyclerView.Adapter adapter) {
        mProcessList.setAdapter(adapter);
    }

    private Dialog mConfirmDialog;

    public void showConfirmDialog(int postion) {
        mConfirmDialog = DialogUtils.createBtnDialog(this.getContext(), "选择操作", "关闭", "查看详情", mPresenter);
        mConfirmDialog.setCancelable(true);
        mConfirmDialog.setCanceledOnTouchOutside(true);
        mConfirmDialog.show();
    }

    public void hideConfirmDialog() {
        if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
            mConfirmDialog.dismiss();
        }
    }

    @OnClick(R.id.fab_refresh)
    void refresh() {
        mPresenter.refresh();
    }
}
