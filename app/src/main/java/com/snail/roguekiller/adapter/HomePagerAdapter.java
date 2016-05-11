package com.snail.roguekiller.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.snail.roguekiller.R;
import com.snail.roguekiller.fragment.HomeFragmentItem;
import com.snail.roguekiller.fragment.ProcessListFragment;
import com.snail.roguekiller.fragment.ServiceListFragment;
import com.snail.roguekiller.utils.ResourcesUtil;

import java.util.HashMap;

/**
 * Created by personal on 16/5/7.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private HashMap<Integer, HomeFragmentItem> mFragmentHashMap = new HashMap<>();

    static private Class[] mFragments = {
            ProcessListFragment.class,
            ServiceListFragment.class
//            ,
//            ProcessListFragment.class,
//            ServiceListFragment.class,
//            ProcessListFragment.class,
//            ServiceListFragment.class
    };

    public String[] sTabTexts = new String[]{
            ResourcesUtil.getString(R.string.process_list),
            ResourcesUtil.getString(R.string.service_list)
//            ,
//            ResourcesUtil.getString(R.string.process_list),
//            ResourcesUtil.getString(R.string.process_list),
//            ResourcesUtil.getString(R.string.process_list),
//            ResourcesUtil.getString(R.string.process_list)
    };

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomePagerAdapter(FragmentManager fm, String[] sTabTexts) {
        super(fm);
    }

    @Override
    public HomeFragmentItem getItem(int position) {
        HomeFragmentItem fragment = null;
        if (mFragmentHashMap.get(position) == null) {
            Class frgClass = mFragments[position];
            try {
                frgClass.newInstance();
                fragment = (HomeFragmentItem) frgClass.newInstance();
                mFragmentHashMap.put(position, fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            fragment = mFragmentHashMap.get(position);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTabTexts[position];
    }

}
