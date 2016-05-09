package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.presenter.HomePresenter;

public class HomeActivity extends BaseActivity<HomePresenter> {

    private ViewPager mViewPager;
    private TabHost mTabHost;


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
        mTabHost = (TabHost) findViewById(R.id.th_tabs);
        mPresenter.initViewPager();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.menu_setting:
                AboutActivity.startActivity(this);
                overridePendingTransition(R.anim.fade_in_300,R.anim.keep_still);
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
//        ToastUtils.show("pressed");
    }
}
