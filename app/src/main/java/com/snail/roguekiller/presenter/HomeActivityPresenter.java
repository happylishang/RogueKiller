package com.snail.roguekiller.presenter;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.activity.HomeActivity;
import com.snail.roguekiller.adapter.HomePagerAdapter;
import com.snail.roguekiller.framework.BaseActivityPresenter;
import com.snail.roguekiller.utils.ResourcesUtil;

/**
 * Created by personal on 16/5/7.
 */
public class HomeActivityPresenter extends BaseActivityPresenter<HomeActivity>
        implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private HomePagerAdapter homePagerAdapter;

    public String[] sTabTexts = new String[]{
            ResourcesUtil.getString(R.string.process_list)
    };


    private int mCurrentPostion =0;

    public HomeActivityPresenter(HomeActivity target) {
        super(target);
    }


    public void initViewPager() {
        homePagerAdapter = new HomePagerAdapter(mTarget.getSupportFragmentManager(), sTabTexts);
        mTarget.initViewPager(homePagerAdapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPostion = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void refresh() {
        homePagerAdapter.getItem(mCurrentPostion).OnPageRefresh();
    }
}
