package com.js.shipper.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.manager.UserManager;
import com.js.shipper.ui.main.fragment.CommunityFragment;
import com.js.shipper.ui.main.fragment.FindOrderFragment;
import com.js.shipper.ui.main.fragment.InformationFragment;
import com.js.shipper.ui.main.fragment.MineFragment;
import com.js.shipper.ui.main.fragment.ParkFragment;
import com.js.shipper.ui.main.fragment.ServiceFragment;
import com.js.shipper.ui.main.fragment.ShipFragment;
import com.js.shipper.ui.main.presenter.MainPresenter;
import com.js.shipper.ui.main.presenter.contract.MainContract;
import com.js.frame.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private ParkFragment mParkFragment;
    private ShipFragment mShipFragment;
    private InformationFragment mInformationFragment;
    private CommunityFragment mCommunityFragment;
    private MineFragment mMineFragment;
    private List<Fragment> mFragments;

    public static void action(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initFragment();
        initViewPager();
        initNV();
    }

    private void initNV() {
        mNavigation.setItemIconTintList(null);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color._787878),
                getResources().getColor(R.color._ECA73F)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigation.setItemTextColor(csl);
        mNavigation.setItemIconTintList(csl);
        mNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mParkFragment = ParkFragment.newInstance();
        mShipFragment = ShipFragment.newInstance();
        mInformationFragment = InformationFragment.newInstance();
        mCommunityFragment = CommunityFragment.newInstance();
        mMineFragment = MineFragment.newInstance();
        mFragments.add(mParkFragment);
        mFragments.add(mShipFragment);
        mFragments.add(mInformationFragment);
        mFragments.add(mCommunityFragment);
        mFragments.add(mMineFragment);
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
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigation.getMenu().getItem(position).setCheckable(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setActionBar() {

        mTitle.setText("找货");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_park:
                mViewpager.setCurrentItem(0);
                mToolbar.setVisibility(View.GONE);
                break;
            case R.id.navigation_ship:
                mViewpager.setCurrentItem(1);
                mToolbar.setVisibility(View.GONE);
                break;
            case R.id.navigation_information:
                mViewpager.setCurrentItem(2);
                mToolbar.setVisibility(View.VISIBLE);
                mTitle.setText("消息");
                break;
            case R.id.navigation_community:
                mViewpager.setCurrentItem(3);
                mToolbar.setVisibility(View.VISIBLE);
                mTitle.setText("社区");
                break;
            case R.id.navigation_mine:
                mViewpager.setCurrentItem(4);
                mToolbar.setVisibility(View.GONE);
                UserManager.getUserManager().isLogin(true, true);
                break;
        }
        return true;
    }

    private long exitTime = 0;

    /**
     * 点击两次退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}
