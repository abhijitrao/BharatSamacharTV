package com.wts.bharatsamachar.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wts.bharatsamachar.fragment.DynamicFragment;
import com.wts.bharatsamachar.model.CategoryModel;

import java.util.ArrayList;

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    ArrayList<CategoryModel> arrayList;
    String tocheck;

    public TabAdapter(FragmentManager fm, int NumOfTabs,ArrayList<CategoryModel> arrayList,String tocheck) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.arrayList = arrayList;
        this.tocheck = tocheck;
    }

    @Override
    public Fragment getItem(int position) {
            String id = arrayList.get(position).getId();
            return DynamicFragment.addfrag(id,tocheck);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position).getCatName();
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}