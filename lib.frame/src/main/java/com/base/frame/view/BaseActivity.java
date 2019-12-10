package com.base.frame.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.base.frame.mvp.IBaseView;
import com.base.frame.mvp.IPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyg on 2018/8/22.
 */

public abstract class BaseActivity<T extends IPresenter> extends ToolbarActivity implements IBaseView {


    @Inject
    protected T mPresenter;
    protected Activity mContext;
    protected Unbinder mUnbinder;
    private static final String TAG = "BaseActivity";
    protected CircleProgressDialog mDialog;
    protected Bundle savedInstanceState;
    private String[] items = {"百度地图", "高德地图"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        this.savedInstanceState = savedInstanceState;
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        init();
        Log.d(getClass().getSimpleName(),"onCreate");
    }


    @Override
    protected void onDestroy() {

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
//        if (BuildConfig.DEBUG) {
//            RefWatcher refWatcher = MyApp.getRefWatcher(this);
//            refWatcher.watch(this);
//        }
        super.onDestroy();
        Log.d(getClass().getSimpleName(),"onDestroy");
    }


    @Override
    public void showProgress() {
        if (mDialog == null) {
            mDialog = new CircleProgressDialog(this);
        }
        if (mDialog != null && !mDialog.isShowing() && !this.isFinishing()) {
            mDialog.show();
        }
    }

    @Override
    public void closeProgress() {
        if (mDialog != null && mDialog.isShowing() && !this.isFinishing()) {
            mDialog.dismiss();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getClass().getSimpleName(),"onStart");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(),"onStop");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe
    public void onEvent(Object event) {

    }

    @Override
    public void toast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    protected abstract void init();

    protected abstract void initInject();

    protected abstract int getLayoutId();




}
