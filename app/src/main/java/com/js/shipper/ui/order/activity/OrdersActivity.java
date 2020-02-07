package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.tabs.TabLayout;
import com.js.shipper.R;
import com.js.shipper.ui.order.fragment.OrderFragment;
import com.base.frame.view.SimpleActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/29.
 */
public class OrdersActivity extends SimpleActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;


    public static void action(Context context,int type){
        Intent intent = new Intent(context,OrdersActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    private String[] titles = {"全部", "发布中", "待支付","待配送","待收货"};
    private int type;
    private List<Fragment> mFragments;


    @Override
    protected int getLayout() {
        return R.layout.activity_order;
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
        //1发布中，2待司机接单，3待司机确认，4待支付，5待司机接货, 6待收货，7待评价，8已完成，9已取消，10已关闭
        mFragments = new ArrayList<>();
        mFragments.add(OrderFragment.newInstance(""));
        mFragments.add(OrderFragment.newInstance("2"));
        mFragments.add(OrderFragment.newInstance("4"));
        mFragments.add(OrderFragment.newInstance("5"));
        mFragments.add(OrderFragment.newInstance("6,7"));
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
        mTitle.setText("我的运单");
    }

}
