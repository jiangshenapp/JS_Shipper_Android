package com.js.shipper.ui.park.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.js.frame.view.SimpleActivity;
import com.js.shipper.R;
import com.js.shipper.ui.main.fragment.BoutiqueFragment;
import com.js.shipper.ui.main.fragment.CarSourceFragment;
import com.js.shipper.ui.main.fragment.DeliveryFragment;
import com.js.shipper.ui.park.fragment.CollectBoutiqueFragment;
import com.js.shipper.ui.park.fragment.CollectBranchFragment;
import com.js.shipper.ui.park.fragment.CollectCarSourceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-06-17.
 * 我的园区
 */
public class CollectActivity extends SimpleActivity {


    @BindView(R.id.rb_carsource)
    RadioButton mCarsource;
    @BindView(R.id.rb_delivery)
    RadioButton mDelivery;
    @BindView(R.id.rb_boutique)
    RadioButton mBoutique;
    @BindView(R.id.radio)
    RadioGroup mRadio;
    @BindView(R.id.frame)
    FrameLayout mFrame;

    private CollectCarSourceFragment mCarSourceFragment;
    private CollectBranchFragment mDeliveryFragment;
    private CollectBoutiqueFragment mBoutiqueFragment;
    private List<Fragment> mFragments;
    private Fragment mCurrentFragment;
    private int type;

    public static void action(Context context, int type) {
        Intent intent = new Intent(context, CollectActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void setActionBar() {
        mTitle.setText("我的园区");
    }


    private void initView() {
        initFragment();
        showIndexFragment();
        initRadioGroup();
    }

    private void initRadioGroup() {
        mCarsource.setChecked(true);
        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switchFragment(checkedId);
            }
        });
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mCarSourceFragment = CollectCarSourceFragment.newInstance();
        mDeliveryFragment = CollectBranchFragment.newInstance();
        mBoutiqueFragment = CollectBoutiqueFragment.newInstance();
        mFragments.add(mCarSourceFragment);
        mFragments.add(mDeliveryFragment);
        mFragments.add(mBoutiqueFragment);
    }


    private void showIndexFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mCarSourceFragment.isAdded()) {
            transaction.add(R.id.frame, mCarSourceFragment);
        }
        transaction.show(mCarSourceFragment).commit();
        mCurrentFragment = mCarSourceFragment;
    }

    private void switchFragment(int checkedId) {
        Fragment fragment = null;
        switch (checkedId) {
            case R.id.rb_carsource:
                fragment = mCarSourceFragment;
                mCarsource.setTextColor(getResources().getColor(R.color.white));
                mDelivery.setTextColor(getResources().getColor(R.color._000000));
                mBoutique.setTextColor(getResources().getColor(R.color._000000));
                break;
            case R.id.rb_delivery:
                fragment = mDeliveryFragment;
                mCarsource.setTextColor(getResources().getColor(R.color._000000));
                mDelivery.setTextColor(getResources().getColor(R.color.white));
                mBoutique.setTextColor(getResources().getColor(R.color._000000));
                break;
            case R.id.rb_boutique:
                fragment = mBoutiqueFragment;
                mCarsource.setTextColor(getResources().getColor(R.color._000000));
                mDelivery.setTextColor(getResources().getColor(R.color._000000));
                mBoutique.setTextColor(getResources().getColor(R.color.white));
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mCurrentFragment);
        if (!fragment.isAdded()) {
            transaction.add(R.id.frame, fragment);
        }
        transaction.show(fragment).commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }
}
