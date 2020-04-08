package com.inclass.cricketguruji.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.inclass.cricketguruji.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    String tabsposition = "0";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Matches");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle arguments = getArguments();
        if (arguments!=null)
             tabsposition = arguments.getString("tabs");

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
        viewPager.setAdapter(adapter);
        if(tabsposition.equals("1"))
            viewPager.setCurrentItem(1);
        else if(tabsposition.equals("2"))
            viewPager.setCurrentItem(2);
        else
            viewPager.setCurrentItem(0);
    }

    static class MatchesAdapters extends FragmentPagerAdapter {

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

