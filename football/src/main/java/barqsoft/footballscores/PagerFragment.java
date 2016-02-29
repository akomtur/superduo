package barqsoft.footballscores;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yehya khaled on 2/27/2015.
 */
public class PagerFragment extends Fragment
{
    public static final int NUM_PAGES = 5;
    public ViewPager mPagerHandler;
    private MyPageAdapter mPagerAdapter;
    private MainScreenFragment[] viewFragments = new MainScreenFragment[5];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        mPagerHandler = (ViewPager) rootView.findViewById(R.id.pager);
        viewFragments = fillTabs(NUM_PAGES);
        mPagerAdapter = new MyPageAdapter(getChildFragmentManager());

        mPagerHandler.setAdapter(mPagerAdapter);
        mPagerHandler.setCurrentItem(MainActivity.current_fragment);
        return rootView;
    }

    private MainScreenFragment[] fillTabs(int pages) {
        List<MainScreenFragment> viewFragmentList = new ArrayList<>();
        for (int i = 0; i < pages; i++) {
            viewFragmentList.add(createMainScreenFragment(i));
        }

        if (getResources().getBoolean(R.bool.is_right_to_left)) {
            Collections.reverse(viewFragmentList);
        }

        return viewFragmentList.toArray(new MainScreenFragment[0]);
    }

    @NonNull
    private MainScreenFragment createMainScreenFragment(int index) {
        MainScreenFragment fragment = new MainScreenFragment();
        Date fragmentdate = new Date(System.currentTimeMillis()+((index -2)*86400000));
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        fragment.setFragmentDate(mformat.format(fragmentdate));
        fragment.setDate(fragmentdate);
        return fragment;
    }

    private class MyPageAdapter extends FragmentStatePagerAdapter
    {
        @Override
        public Fragment getItem(int i)
        {
            return viewFragments[i];
        }

        @Override
        public int getCount()
        {
            return NUM_PAGES;
        }

        public MyPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            MainScreenFragment viewFragment = PagerFragment.this.viewFragments[position];
            return getDayName(getActivity(), viewFragment.getDate().getTime());
        }
        public String getDayName(Context context, long dateInMillis) {
            // If the date is today, return the localized version of "Today" instead of the actual
            // day name.

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
            if (julianDay == currentJulianDay) {
                return context.getString(R.string.pager_tab_today);
            } else if ( julianDay == currentJulianDay +1 ) {
                return context.getString(R.string.pager_tab_tomorrow);
            }
             else if ( julianDay == currentJulianDay -1)
            {
                return context.getString(R.string.pager_tab_yesterday);
            }
            else
            {
                Time time = new Time();
                time.setToNow();
                // Otherwise, the format is just the day of the week (e.g "Wednesday".
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
            }
        }
    }
}
