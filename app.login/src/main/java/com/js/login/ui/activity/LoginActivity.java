package com.js.login.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.view.SimpleActivity;
import com.js.login.R;
import com.js.login.R2;
import com.js.login.model.event.LoginChangeEvent;
import com.js.login.ui.fragment.CodeLoginFragment;
import com.js.login.ui.fragment.PwdLoginFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019/4/21.
 */
@Route(path = "/user/login")
public class LoginActivity extends SimpleActivity {

    @BindView(R2.id.login_frame)
    FrameLayout mFrame;

    private CodeLoginFragment mCodeLoginFragment;
    private PwdLoginFragment mPwdLoginFragment;
    private List<Fragment> mFragments;
    private Fragment mCurrentFragment;

    public static void action(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void backAction() {
        ARouter.getInstance().build("/app/main").navigation();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initFragment();
        showFragment();
    }

    private void showFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mPwdLoginFragment.isAdded()) {
            transaction.add(R.id.login_frame, mPwdLoginFragment);
        }
        transaction.show(mPwdLoginFragment).commit();
        mCurrentFragment = mPwdLoginFragment;
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mCodeLoginFragment = CodeLoginFragment.newInstance();
        mPwdLoginFragment = PwdLoginFragment.newInstance();
        mFragments.add(mPwdLoginFragment);
        mFragments.add(mCodeLoginFragment);
    }

    @Override
    public void setActionBar() {
        mTitle.setText("");
        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_navigationbar_close_black));
    }

    @Subscribe
    public void onEvent(LoginChangeEvent event) {
        switchFragment(event.index);
    }

    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mCurrentFragment);
        if (!mFragments.get(index).isAdded()) {
            transaction.add(R.id.login_frame, mFragments.get(index));
        }
        transaction.show(mFragments.get(index)).commitAllowingStateLoss();
        mCurrentFragment = mFragments.get(index);
    }

}
