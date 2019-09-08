package com.js.shipper.ui.main.fragment;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.frame.view.SimpleFragment;
import com.js.shipper.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * Created by huyg on 2019/4/30.
 */
public class ParkFragment extends SimpleFragment {

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

    private CarSourceFragment mCarSourceFragment;
    private DeliveryFragment mDeliveryFragment;
    private BoutiqueFragment mBoutiqueFragment;
    private List<Fragment> mFragments;
    private Fragment mCurrentFragment;

    public static ParkFragment newInstance() {
        return new ParkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_park;
    }

    @Override
    protected void init() {
        initView();
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
        mCarSourceFragment = CarSourceFragment.newInstance();
        mDeliveryFragment = DeliveryFragment.newInstance();
        mBoutiqueFragment = BoutiqueFragment.newInstance();
        mFragments.add(mCarSourceFragment);
        mFragments.add(mDeliveryFragment);
        mFragments.add(mBoutiqueFragment);
    }


    private void showIndexFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(mCurrentFragment);
        if (!fragment.isAdded()) {
            transaction.add(R.id.frame, fragment);
        }
        transaction.show(fragment).commitAllowingStateLoss();
        mCurrentFragment = fragment;
    }
}
