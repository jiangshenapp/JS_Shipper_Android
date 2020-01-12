package com.js.shipper.ui.car.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.base.frame.view.SimpleActivity;
import com.google.android.material.tabs.TabLayout;
import com.js.shipper.R;
import com.js.shipper.ui.car.fragment.CarFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   : 我的运力
 * version: 3.0.0
 */
public class CarActivity extends SimpleActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private List<Fragment> mFragments;
    private String[] titles = {"全部", "自有车辆", "外调车辆"};
    private int type;//类型，0全部，1自由车辆，2外调车辆

    public static void action(Context context, int type) {
        Intent intent = new Intent(context, CarActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_wallet_bill;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
    }

    private void initView() {
        initFragment();
        initViewPager();
        mViewpager.setCurrentItem(type);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(CarFragment.newInstance(0));
        mFragments.add(CarFragment.newInstance(1));
        mFragments.add(CarFragment.newInstance(2));
    }

    private void initViewPager() {
        mViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的运力");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_center_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_car:
                AddCarActivity.action(mContext);
                break;
        }
        return true;
    }
}
