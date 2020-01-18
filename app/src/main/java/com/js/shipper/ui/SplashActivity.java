package com.js.shipper.ui;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.frame.view.BaseActivity;
import com.base.frame.view.SimpleWebActivity;
import com.base.http.global.Const;
import com.base.util.manager.SpManager;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.BannerBean;
import com.js.shipper.model.bean.ServiceBean;
import com.js.shipper.presenter.SplashPresenter;
import com.js.shipper.presenter.contract.SplashContract;
import com.js.shipper.service.LocationService;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.main.presenter.ServicePresenter;
import com.js.shipper.ui.main.presenter.contract.ServiceContract;
import com.js.shipper.ui.user.activity.VerifiedActivity;
import com.js.shipper.util.glide.CommonGlideImageLoader;
import com.js.shipper.widget.dialog.AppDialogFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huyg on 2019-05-23.
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View, ServiceContract.View {

    @BindView(R.id.iv_ad)
    ImageView mIvAd;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    @Inject
    ServicePresenter mServicePresenter;

    private BannerBean mBannerBean;

    @Override
    protected void init() {
        mServicePresenter.attachView(this);
        initProtocal();

    }

    private void initProtocal() {
        StringBuilder content = new StringBuilder();
        content.append("感谢您使用匠神来运！为了帮助您安全使用产品和服务，在您同意并授权的基础上，我们可能会收集您的身份信息、联系信息、交易信息、位置信息等。请您务必仔细阅读并透彻理解");
        content.append("<font color=\"#ECA73F\"><a href=\"https://www.jiangshen56.com/privacyProtocal-shipper.html\">《隐私政策》</a></font>");
        content.append("和<font color=\"#ECA73F\"><a href=\"https://www.jiangshen56.com/registerProtocal-shipper.html\">《用户协议》</a></font>");
        if (SpManager.getInstance(mContext).getIntSP("protocal") == 0) {
            AppDialogFragment appDialogFragment = AppDialogFragment.getInstance();
            appDialogFragment.setTitle("用户协议与隐私保护声明");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                appDialogFragment.setMessage(Html.fromHtml(content.toString(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                appDialogFragment.setMessage(Html.fromHtml(content.toString()));
            }

            appDialogFragment.setPositiveButton("同意并继续", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SpManager.getInstance(mContext).putIntSP("protocal",1);
                    initPermission();
                    initLocation();
                }
            });
            appDialogFragment.setNegativeButton("不同意", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.action(mContext);
                    finish();
                }
            });
            appDialogFragment.setCancel(false);
            appDialogFragment.show(getSupportFragmentManager(), "appDialog");
        } else {
            initPermission();
            initLocation();
        }
    }

    private void initLocation() {
        Intent intent = new Intent(mContext, LocationService.class);
        startService(intent);
    }

    private void initPermission() {
        XXPermissions.with(this)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.ACCESS_COARSE_LOCATION,
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.CAMERA)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            mServicePresenter.getBannerList(3);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        MainActivity.action(mContext);
                        finish();
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
        return R.layout.activity_splash;
    }

    @Override
    public void setActionBar() {
        mToolbar.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_ad, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ad: //广告点击
                SimpleWebActivity.action(this, mBannerBean.getUrl(), mBannerBean.getTitle());
                break;
            case R.id.tv_skip: //跳过
                if (mServicePresenter != null) {
                    mServicePresenter.detachView();
                }
                MainActivity.action(mContext);
                finish();
                break;
        }
    }

    @Override
    public void onBannerListFail() {
        MainActivity.action(mContext);
        finish();
    }

    @Override
    public void onBannerList(List<BannerBean> mBannerBeans) {
        if (mBannerBeans.size() > 0) {
            mBannerBean = mBannerBeans.get(0);
            mIvAd.setVisibility(View.VISIBLE);
            mTvSkip.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL() + mBannerBean.getImage(), mIvAd);
            Observable.timer(3, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            MainActivity.action(mContext);
                            finish();
                        }
                    });
        } else {
            MainActivity.action(mContext);
            finish();
        }
    }

    @Override
    public void onServiceList(List<ServiceBean> mServiceBeans) {

    }

    @Override
    protected void onDestroy() {

        if (mServicePresenter != null) {
            mServicePresenter.detachView();
        }
        super.onDestroy();
    }
}
