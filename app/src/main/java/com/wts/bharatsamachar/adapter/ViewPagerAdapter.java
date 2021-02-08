package com.wts.bharatsamachar.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amit on 8/30/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int count ;
    private final List<Fragment> mFragmentList = new ArrayList<>(count);
    private final List<String> mFragmentTitleList = new ArrayList<>(count);

    public ViewPagerAdapter(FragmentManager manager , int count) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.count = count ;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
