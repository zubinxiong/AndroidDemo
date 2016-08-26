package me.ewriter.zhihuwelcome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zubin on 2016/8/25.
 */
public class IntroAdapter extends FragmentPagerAdapter {

    List<IntroFragment> mList = new ArrayList<>();

    public IntroAdapter(FragmentManager fm, Context context) {
        super(fm);

        String[] str = context.getResources().getStringArray(R.array.intro);
        for (int i = 0; i < str.length; i++) {
            mList.add(IntroFragment.newInstance(str[i], i));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
