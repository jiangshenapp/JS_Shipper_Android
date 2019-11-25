package com.js.driver.ui.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.frame.view.BaseFragment;
import com.base.frame.view.SimpleWebActivity;
import com.base.http.global.Const;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.ui.main.presenter.ServicePresenter;
import com.js.driver.ui.main.presenter.contract.ServiceContract;
import com.js.driver.util.glide.CommonGlideImageLoader;
import com.js.driver.util.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   : 服务
 * version: 3.0.0
 */
public class ServiceFragment extends BaseFragment<ServicePresenter> implements ServiceContract.View {

    @BindView(R.id.iv_item1)
    ImageView ivItem1;
    @BindView(R.id.tv_item1)
    TextView tvItem1;
    @BindView(R.id.ll_item1)
    LinearLayout llItem1;
    @BindView(R.id.iv_item2)
    ImageView ivItem2;
    @BindView(R.id.tv_item2)
    TextView tvItem2;
    @BindView(R.id.ll_item2)
    LinearLayout llItem2;
    @BindView(R.id.iv_item3)
    ImageView ivItem3;
    @BindView(R.id.tv_item3)
    TextView tvItem3;
    @BindView(R.id.ll_item3)
    LinearLayout llItem3;
    @BindView(R.id.iv_item4)
    ImageView ivItem4;
    @BindView(R.id.tv_item4)
    TextView tvItem4;
    @BindView(R.id.ll_item4)
    LinearLayout llItem4;
    @BindView(R.id.ll_item0)
    LinearLayout llItem0;
    @BindView(R.id.iv_item5)
    ImageView ivItem5;
    @BindView(R.id.tv_item5)
    TextView tvItem5;
    @BindView(R.id.ll_item5)
    LinearLayout llItem5;
    @BindView(R.id.iv_item6)
    ImageView ivItem6;
    @BindView(R.id.tv_item6)
    TextView tvItem6;
    @BindView(R.id.ll_item6)
    LinearLayout llItem6;
    @BindView(R.id.iv_item7)
    ImageView ivItem7;
    @BindView(R.id.tv_item7)
    TextView tvItem7;
    @BindView(R.id.ll_item7)
    LinearLayout llItem7;

    @BindView(R.id.home_banner)
    Banner mBanner;
    private List<String> imgPaths;
    private List<BannerBean> mBannerBeans;
    private List<ServiceBean> mServiceBeans;

    public static ServiceFragment newInstance() {
        return new ServiceFragment();
    }

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void init() {
        initView();
        initBanner();
        initData();
    }

    public void initView() {
        llItem0.setVisibility(View.GONE);
        llItem5.setVisibility(View.GONE);
        llItem6.setVisibility(View.GONE);
        llItem7.setVisibility(View.GONE);
    }

    public void initBanner() {
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = mBannerBeans.get(position);
                SimpleWebActivity.action(getActivity(), bannerBean.getUrl(), bannerBean.getTitle());
            }
        });
        mBanner.setImageLoader(new GlideImageLoader());
    }

    public void initData() {
        mPresenter.getBannerList(1);
        mPresenter.getServiceList();
    }

    @Override
    public void onBannerListFail() {

    }

    @Override
    public void onBannerList(List<BannerBean> bannerBeans) {
        mBannerBeans = bannerBeans;
        if (mBannerBeans.size() == 0) {
            mBanner.setVisibility(View.GONE);
        } else {
            mBanner.setVisibility(View.VISIBLE);
            imgPaths = new ArrayList<>();
            for (int i = 0; i < mBannerBeans.size(); i++) {
                BannerBean bannerBean = mBannerBeans.get(i);
                imgPaths.add(Const.IMG_URL()  + bannerBean.getImage());
            }
            mBanner.setImages(imgPaths);
            mBanner.start();
        }
    }

    @Override
    public void onServiceList(List<ServiceBean> serviceBeans) {
        mServiceBeans = serviceBeans;
        int count = mServiceBeans.size();
        if (count > 0) {
            llItem0.setVisibility(View.VISIBLE);
            ServiceBean serviceBean = mServiceBeans.get(0);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem1);
            tvItem1.setText(serviceBean.getTitle());
        }
        if (count > 1) {
            ServiceBean serviceBean = mServiceBeans.get(1);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem2);
            tvItem2.setText(serviceBean.getTitle());
        }
        if (count > 2) {
            ServiceBean serviceBean = mServiceBeans.get(2);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem3);
            tvItem3.setText(serviceBean.getTitle());
        }
        if (count > 3) {
            ServiceBean serviceBean = mServiceBeans.get(3);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem4);
            tvItem4.setText(serviceBean.getTitle());
        }
        if (count > 4) {
            llItem5.setVisibility(View.VISIBLE);
            ServiceBean serviceBean = mServiceBeans.get(4);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem5);
            tvItem5.setText(serviceBean.getTitle());
        }
        if (count > 5) {
            llItem6.setVisibility(View.VISIBLE);
            ServiceBean serviceBean = mServiceBeans.get(5);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem6);
            tvItem6.setText(serviceBean.getTitle());
        }
        if (count > 6) {
            llItem7.setVisibility(View.VISIBLE);
            ServiceBean serviceBean = mServiceBeans.get(6);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + serviceBean.getIcon(), ivItem7);
            tvItem7.setText(serviceBean.getTitle());
        }
    }

    @OnClick({R.id.ll_item1, R.id.ll_item2, R.id.ll_item3, R.id.ll_item4, R.id.ll_item5, R.id.ll_item6, R.id.ll_item7})
    public void onClick(View view) {
        ServiceBean serviceBean = new ServiceBean();
        switch (view.getId()) {
            case R.id.ll_item1:
                serviceBean = mServiceBeans.get(0);
                break;
            case R.id.ll_item2:
                serviceBean = mServiceBeans.get(1);
                break;
            case R.id.ll_item3:
                serviceBean = mServiceBeans.get(2);
                break;
            case R.id.ll_item4:
                serviceBean = mServiceBeans.get(3);
                break;
            case R.id.ll_item5:
                serviceBean = mServiceBeans.get(4);
                break;
            case R.id.ll_item6:
                serviceBean = mServiceBeans.get(5);
                break;
            case R.id.ll_item7:
                serviceBean = mServiceBeans.get(6);
                break;
        }
        SimpleWebActivity.action(getActivity(), serviceBean.getUrl(), serviceBean.getTitle());
    }
}
