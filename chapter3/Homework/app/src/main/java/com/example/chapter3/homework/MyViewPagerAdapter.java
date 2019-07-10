package com.example.chapter3.homework;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public MyViewPagerAdapter(FragmentManager fm, List<String> titleList,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;

    }

    @Override
    public Fragment getItem(int i) {

        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public String getPageTitle(int i){
        return titleList.get(i);
    }

}
