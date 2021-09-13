package edu.missouri.cs.tigeraware.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.model.Survey;
import edu.missouri.cs.tigeraware.ui.SurveyFragment;

/**
 * Created by Siyang Liu on 2017/7/25.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {


    List<SurveyFragment> mFragments;

    public MainPagerAdapter(FragmentManager fm, List<SurveyFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public MainPagerAdapter(FragmentManager fm) {
        this(fm, new ArrayList<SurveyFragment>());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
