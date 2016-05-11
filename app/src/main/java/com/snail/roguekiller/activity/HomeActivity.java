package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.presenter.HomeActivityPresenter;
import com.snail.roguekiller.view.SlidingTabLayout;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        getSupportActionBar().hide();
        initListener();
        if (savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt("pos"));
        }
    }

    @Override
    protected HomeActivityPresenter createPresenter() {
        return new HomeActivityPresenter(this);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.setOffscreenPageLimit(3);
        mPresenter.initViewPager();
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sld_tab_layout);
        mSlidingTabLayout.setCustomTabView(R.layout.item_slide_bar, R.id.title);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.red);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.black);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(mPresenter);
    }

    public void initViewPager(FragmentPagerAdapter pagerAdapter) {
        mViewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @OnClick(R.id.fab_refresh)
    void refresh() {
        mPresenter.refresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("pos", mViewPager.getCurrentItem());

    }
}
