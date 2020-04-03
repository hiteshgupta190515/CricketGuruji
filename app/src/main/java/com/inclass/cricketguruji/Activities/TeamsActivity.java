package com.inclass.cricketguruji.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.model.TeamsModel;
import com.inclass.cricketguruji.ui.DomesticTeams;
import com.inclass.cricketguruji.ui.NationalTeams;

import java.util.ArrayList;
import java.util.List;

public class TeamsActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private TabLayout tab_layout;

    TeamsModel teamsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        initComponent();
    }

    private void initComponent() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(view_pager);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
    }


    private void setupViewPager(ViewPager viewPager) {
        TeamsPagerAdapter adapter = new TeamsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(NationalTeams.newInstance(), "National Teams");
        adapter.addFragment(DomesticTeams.newInstance(), "Domestic Teams");
        viewPager.setAdapter(adapter);
    }

    private class TeamsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TeamsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
