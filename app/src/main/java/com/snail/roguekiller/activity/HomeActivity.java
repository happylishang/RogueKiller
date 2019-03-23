package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.snail.roguekiller.R;
import com.snail.roguekiller.datamodel.ToolbarStates;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.jni.NdkCrash;
import com.snail.roguekiller.presenter.HomeActivityPresenter;
import com.snail.roguekiller.share.ShareDialogFragment;
import com.snail.roguekiller.utils.Constants;
import com.snail.roguekiller.view.SlidingTabLayout;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomeActivityPresenter> {

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private ShareDialogFragment mShareDialogFragment;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("R&K");
        initView();
        initListener();
        NdkCrash.nativecrash();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        try {
            mPresenter.refresh();
            throw new IllegalStateException(
                    "Can not perform this action after onSaveInstanceState");
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }


    private Menu mMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        mMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_setting:
                AboutActivity.startActivity(this);
                break;
            case R.id.menu_filter:
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    mPresenter.OnPageFilter(Constants.ProcessType.USER_ONLY);
                } else {
                    mPresenter.OnPageFilter(Constants.ProcessType.ALL);
                }
                break;
            case R.id.menu_share:
                mShareDialogFragment = new ShareDialogFragment();
                mShareDialogFragment.show(getSupportFragmentManager(), "");
                break;
        }
        return true;
    }

    public void refreshToolbar(ToolbarStates states) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.menu_filter);
            item.setChecked(states.processTyep == Constants.ProcessType.USER_ONLY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(mShareDialogFragment!=null && mShareDialogFragment.isDetached()){
//            mShareDialogFragment.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
