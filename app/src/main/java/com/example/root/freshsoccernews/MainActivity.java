package com.example.root.freshsoccernews;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.root.freshsoccernews.DbModule.SinaPage.SinapageModule;
import com.example.root.freshsoccernews.DetailActivity.DetailActivity;
import com.example.root.freshsoccernews.Fragment.Fragment1;
import com.example.root.freshsoccernews.Fragment.Fragment2;
import com.example.root.freshsoccernews.Fragment.FragmentAdapter;
import com.example.root.freshsoccernews.MainAdapter.MainAdapter;
import com.example.root.freshsoccernews.Phoenix.PullToRefreshView;
import com.example.root.freshsoccernews.SlideMenu.SlideHolder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends FragmentActivity{
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private HeadBar mHeadBar;
    private FragmentManager mFragmentManager;
    private Fragment nowFragment;
    private Fragment fragment1;
    private Fragment fragment2;
    private List<Fragment> fragmentList;
    private RadioGroup mRadioGroup;
    private long exitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHeadBar();
        initRadio();
        initFragment();
        initView();
        viewPager.setCurrentItem(0);
        //  showPopupWindow();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.main_headbar);
        mHeadBar.setTitleNoVisiable();
        mHeadBar.setLeftButtonNoused();
        mHeadBar.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initFragment() {
        fragmentList = new ArrayList<Fragment>();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
    }
    private void initRadio() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobution_1) {
                    viewPager.setCurrentItem(0);
                } else if (checkedId == R.id.radiobution_2) {
                    viewPager.setCurrentItem(1);
                }
            }
        });
    }
    private void initView() {
        try {
            fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
            viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setAdapter(fragmentAdapter);
            viewPager.setPageTransformer(true, new RotateUpTransformer());
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        mRadioGroup.check(R.id.radiobution_1);
                    } else if (position == 1) {
                        mRadioGroup.check(R.id.radiobution_2);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {

            if((System.currentTimeMillis()-exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
