package com.example.macremote.demo_weixin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.macremote.demo_weixin.CustomView.ChangeColorIconWithTextView;
import com.example.macremote.demo_weixin.fragment.TabFragment;
import com.example.macremote.demo_weixin.fragment.TabFragment2;
import com.example.macremote.demo_weixin.fragment.TabFragment3;
import com.example.macremote.demo_weixin.fragment.TabFragment4;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private ViewPager mViewPager ;
    private List<Fragment> mTabs = new ArrayList<>() ;
    private FragmentPagerAdapter mAdapter ;
    private List<ChangeColorIconWithTextView> mTabIndicator =
            new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setOverflowShowingAlways();
//        getActionBar().setDisplayShowHomeEnabled(false);

        mViewPager=(ViewPager)findViewById(R.id.vp_viewpager) ;
        initData() ;
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initData() {
//        for(String title:mTitles){
//            TabFragment tabFragment = new TabFragment() ;
//            Bundle bundle = new Bundle() ;
//            bundle.putString("title",title);
//            tabFragment.setArguments(bundle);
//            mTabs.add(tabFragment);
//        }
        //初始化四个fragment
        mTabs.add(new TabFragment());
        mTabs.add(new TabFragment2());
        mTabs.add(new TabFragment3());
        mTabs.add(new TabFragment4());


        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mTabs.get(i);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
        initTabIndicator();
    }

    /**
     * 初始化底部指示器
     */
    private void initTabIndicator()
    {
        ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_one);
        ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_two);
        ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_three);
        ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_four);

        mTabIndicator.add(one);
        mTabIndicator.add(two);
        mTabIndicator.add(three);
        mTabIndicator.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);

        one.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //重置
        resetOtherTabs();
        switch (v.getId()){
            case R.id.id_one:
                mTabIndicator.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.id_two:
                mTabIndicator.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.id_three:
                mTabIndicator.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.id_four:
                mTabIndicator.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3,false);
                break;
        }
    }

    private void resetOtherTabs() {
        for(int i=0;i<mTabIndicator.size();i++){
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(positionOffset>0){
            ChangeColorIconWithTextView left = mTabIndicator.get(position) ;
            ChangeColorIconWithTextView right =mTabIndicator.get(position+1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int i) {
        //do nothing
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        //do nothing
    }

}
