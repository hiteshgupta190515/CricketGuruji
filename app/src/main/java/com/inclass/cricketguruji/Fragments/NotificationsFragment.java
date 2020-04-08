package com.inclass.cricketguruji.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.inclass.cricketguruji.R;
import com.inclass.cricketguruji.Fragments.UpcomingMatchesFragment;
import com.inclass.cricketguruji.Fragments.LiveMatchesFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    AppBarLayout appBarLayout;
    Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

            view_pager = (ViewPager) root.findViewById(R.id.view_pager);
            tab_layout = (TabLayout) root.findViewById(R.id.tabs);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        MatchesAdapters adapter = new MatchesAdapters(getChildFragmentManager());
        adapter.addFragment(LiveMatchesFragment.newInstance(), "Live");
        adapter.addFragment(UpcomingMatchesFragment.newInstance(), "Upcoming");
        adapter.addFragment(RecentMatchesFragment.newInstance(), "Recent");
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(adapter);
    }

    static class MatchesAdapters extends FragmentPagerAdapter  {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MatchesAdapters(FragmentManager manager) {
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
