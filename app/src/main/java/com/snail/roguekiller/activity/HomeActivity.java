package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.presenter.HomePresenter;
import com.snail.roguekiller.view.SlidingTabLayout;

public class HomeActivity extends BaseActivity<HomePresenter> {

    private ViewPager mViewPager;
    private TabHost mTabHost;
    private SlidingTabLayout mSlidingTabLayout;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public int tabNormalIcons[] = {

    };

    public int mTabSelectedIcons[] = {

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initListener();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.setOffscreenPageLimit(3);
        mPresenter.initViewPager();
        mTabHost = (TabHost) findViewById(R.id.th_tabs);
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
        mTabHost.setOnTabChangedListener(mPresenter);
    }

    public void initViewPager(FragmentPagerAdapter pagerAdapter) {
        mViewPager.setAdapter(pagerAdapter);

    }

    public void setCurrentPager(int postion) {
        mViewPager.setCurrentItem(postion);

    }

    public void setCurrentTab(int postion) {
        mTabHost.setCurrentTab(postion);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
