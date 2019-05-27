package com.js.shipper.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.js.frame.view.SimpleActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.global.Const;
import com.js.shipper.model.event.VerifiedChangeEvent;
import com.js.shipper.ui.user.fragment.CompanyVerifiedFragment;
import com.js.shipper.ui.user.fragment.PersonVerifiedFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/26
 * desc   : 货主身份认证
 * version: 3.0.0
 */
public class VerifiedActivity extends SimpleActivity {

    @BindView(R.id.verified_frame)
    FrameLayout mFrame;
    @BindView(R.id.tv_auth_state)
    TextView tvAuthState;
    @BindView(R.id.rb_person)
    RadioButton rbPerson;
    @BindView(R.id.rb_company)
    RadioButton rbCompany;

    private PersonVerifiedFragment mPersonVerifiedFragment;
    private CompanyVerifiedFragment mCompanyVerifiedFragment;
    private List<Fragment> mFragments;
    private Fragment mCurrentFragment = new Fragment();
    private int authState;

    public static void action(Context context) {
        context.startActivity(new Intent(context, VerifiedActivity.class));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_verified;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        authState = App.getInstance().personConsignorVerified;
        initAuthState();
        initFragment();
        switchFragment(VerifiedChangeEvent.VERIFIED_PERSON);
    }

    private void initAuthState() {
        if (authState == 0) { //未认证
            tvAuthState.setVisibility(View.GONE);
        } else {
            tvAuthState.setVisibility(View.VISIBLE);
            tvAuthState.setText(Const.AuthStateStr[authState]);
            tvAuthState.setTextColor(Color.parseColor(Const.AuthStateColor[authState]));
        }
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mPersonVerifiedFragment = PersonVerifiedFragment.newInstance();
        mCompanyVerifiedFragment = CompanyVerifiedFragment.newInstance();
        mFragments.add(mPersonVerifiedFragment);
        mFragments.add(mCompanyVerifiedFragment);
    }

    @Override
    public void setActionBar() {
        mTitle.setText("货主身份认证");
    }

    private void switchFragment(int index) {
        Fragment targetFragment = mFragments.get(index);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.add(R.id.verified_frame, targetFragment);
        } else {
            transaction.hide(mCurrentFragment).show(targetFragment);
        }
        transaction.commit();
        mCurrentFragment = targetFragment;
    }

    @OnClick({R.id.rb_person, R.id.rb_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_person:
                authState = App.getInstance().personConsignorVerified;
                initAuthState();
                rbPerson.setTextColor(getResources().getColor(R.color.white));
                rbCompany.setTextColor(getResources().getColor(R.color._000000));
                switchFragment(VerifiedChangeEvent.VERIFIED_PERSON);
                break;
            case R.id.rb_company:
                authState = App.getInstance().companyConsignorVerified;
                initAuthState();
                rbCompany.setTextColor(getResources().getColor(R.color.white));
                rbPerson.setTextColor(getResources().getColor(R.color._000000));
                switchFragment(VerifiedChangeEvent.VERIFIED_COMPANY);
                break;
        }
    }
}
