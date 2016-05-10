package com.snail.roguekiller.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.framework.BaseFragment;
import com.snail.roguekiller.presenter.ServiceListPresenter;
import com.snail.roguekiller.utils.DialogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by personal on 16/5/7.
 */
public class ServiceListFragment extends BaseFragment<ServiceListPresenter> {


    private View rootView;
    private RecyclerView mProcessList;
    private SwipeRefreshLayout swpProcessList;

    @Override
    protected ServiceListPresenter createPresenter() {
        return new ServiceListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_process_list, container, false);
        initView();
        initActionBar();
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
        mProcessList = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mProcessList.setLayoutManager(layoutManager);
        mPresenter.initAdapter();
        initSwipViews();
    }

    public void initSwipViews(){
        swpProcessList = (SwipeRefreshLayout) rootView.findViewById(R.id.swp_process_list);
        swpProcessList.setOnRefreshListener(mPresenter);
        swpProcessList.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_bright);
        swpProcessList.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        swpProcessList.setProgressBackgroundColorSchemeResource(R.color.white); // 设定下拉圆圈的背景
        swpProcessList.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
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

    private void initActionBar() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity _activity = (BaseActivity) getActivity();
            _activity.getLftTv().setVisibility(View.GONE);
            _activity.getMiddleTv().setText("运行的服务");
            _activity.getRightTv().setText("选项");
            _activity.getRightTv().setOnClickListener(mPresenter);
        }
    }

    public void showFilterSelection() {

    }

    @Override
    public void OnPageSelect() {
        initActionBar();
    }

    public void onRefreshComplete() {
        swpProcessList.setRefreshing(false);
    }
}