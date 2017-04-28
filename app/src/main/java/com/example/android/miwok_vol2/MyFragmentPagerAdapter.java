package com.example.android.miwok_vol2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tomi on 24.4.2017..
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles [] = new String[] {"NUMBERS","COLORS","FAMILY","PHRASES"};



    /**
     * {@link MyFragmentPagerAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
     * each list item based on a data source which is a list of {@link Word} objects.
     */

    // we want to customize the adapter to display our own fragments, so we have to use inheritance to subclass the FragmentPagerAdapter.
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // the default CategoryAdapter getPageTitle(int position) should return the proper category name per page.
    // We could return a hardcoded string such as “Numbers,” “Family,” and so on.
    // but we don’t want to restrict our app to only support the English language.
    // Instead, we should use the string resource for those category names.
    // that means we need a Context object in order to turn the string resource ID into an actual String.
    // So we modify the MyFragmentPagerAdapter constructor to also require a Context input so that we can get the proper text string.
    private Context mContext;

     // @param context is the context of the app
     // @param fm is the fragment manager that will keep each fragment's state in the adapter across swipes.

    public MyFragmentPagerAdapter (Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new ColorsFragment();
        } else if (position == 2) {
            return new FamilyFragment();
        } else {
            return  new PhrasesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 4;
    }

    // this method return the name of the tab from char sequence
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_numbers);
        } else if (position == 1) {
            return mContext.getString(R.string.category_family);
        } else if (position == 2) {
            return mContext.getString(R.string.category_colors);
        } else {
            return mContext.getString(R.string.category_phrases);
        }
    }
}