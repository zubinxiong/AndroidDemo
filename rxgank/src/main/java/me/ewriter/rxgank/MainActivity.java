package me.ewriter.rxgank;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import me.ewriter.rxgank.adapter.SimplePagerAdapter;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppBarLayout mAppbarLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mAppbarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        String[] tabName = {"Android", "iOS", "福利"};
        SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(SimpleFragment.newInstance("0", ""), tabName[0]);
        adapter.addFragment(SimpleFragment.newInstance("1", ""), tabName[1]);
        adapter.addFragment(PictureFragment.newInstance("2", ""), tabName[2]);

        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
