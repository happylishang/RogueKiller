package com.snail.roguekiller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.presenter.ServiceListPresenter;
import com.snail.roguekiller.utils.DialogUtils;

import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by personal on 16/5/7.
 */
public class ServiceListFragment extends HomeFragmentItem<ServiceListPresenter> {


    private View rootView;
    private RecyclerView mProcessList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected ServiceListPresenter createPresenter() {
        return new ServiceListPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_process_list, container, false);
        initView();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        initSwipViews();
        mProcessList = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProcessList.setLayoutManager(layoutManager);
        mProcessList.setItemAnimator(new FadeInAnimator());
        mPresenter.initAdapter();

    }

    public void initSwipViews() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swp_process_list);
        mSwipeRefreshLayout.setOnRefreshListener(mPresenter);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
        mSwipeRefreshLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white); // 设定下拉圆圈的背景
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
    }

    public void initAdapter(ProcessListAdapter adapter) {
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

    public void onRefreshComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void OnPageRefresh() {
        mPresenter.refresh();
    }

    public void onRefreshStart() {

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showKillMessage(@NonNull String content) {
        Snackbar.make(mSwipeRefreshLayout, content, Snackbar.LENGTH_SHORT).show();
    }


}

