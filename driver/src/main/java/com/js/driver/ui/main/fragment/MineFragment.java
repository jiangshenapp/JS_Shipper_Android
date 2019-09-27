package com.js.driver.ui.main.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.frame.view.BaseFragment;
import com.base.frame.view.SimpleWebActivity;
import com.base.http.global.Const;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.model.bean.BannerBean;
import com.js.driver.model.bean.ServiceBean;
import com.js.driver.ui.main.presenter.ServicePresenter;
import com.js.driver.ui.main.presenter.contract.ServiceContract;
import com.js.driver.util.UserManager;
import com.js.driver.model.bean.AccountInfo;
import com.js.driver.model.bean.MineMenu;
import com.js.driver.model.bean.UserInfo;
import com.js.driver.model.event.UserStatusChangeEvent;
import com.js.driver.ui.center.activity.CarsActivity;
import com.js.driver.ui.center.activity.DriversActivity;
import com.js.driver.ui.center.activity.RoutesActivity;
import com.js.driver.ui.main.adapter.MineMenuAdapter;
import com.js.driver.ui.main.presenter.MinePresenter;
import com.js.driver.ui.main.presenter.contract.MineContract;
import com.js.driver.ui.order.activity.OrdersActivity;
import com.js.driver.ui.user.activity.UserCenterActivity;
import com.js.driver.ui.user.activity.UserVerifiedActivity;
import com.js.driver.ui.wallet.activity.WalletActivity;
import com.js.driver.util.UIUtil;
import com.js.driver.util.glide.CommonGlideImageLoader;
import com.js.driver.widget.adapter.DividerGridItemDecoration;
import com.js.driver.widget.dialog.AppDialogFragment;
import com.js.driver.widget.view.RatingBar;
import com.plugin.im.IMHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/1.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, BaseQuickAdapter.OnItemClickListener, ServiceContract.View {

    @BindView(R.id.user_img)
    ImageView mUserImg;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.auth_state)
    TextView authState;
    @BindView(R.id.user_phone)
    TextView mUserPhone;
    @BindView(R.id.wallet_money)
    TextView mMoney;
    @BindView(R.id.wallet_integral)
    TextView mIntegral;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;

    @Inject
    ServicePresenter mServicePresenter;

    private MineMenuAdapter mAdapter;
    private List<MineMenu> mMineMenu;
    private List<String> mTitles;
    private List<Object> mResources;
    private List<ServiceBean> mServiceBeans;


    public static MineFragment newInstance() {
        return new MineFragment();
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
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        mServicePresenter.attachView(this);
        mServicePresenter.getServiceList();
        mTitles = new ArrayList<>();
        mResources = new ArrayList<>();
        mTitles.add("我的车辆");
        mTitles.add("我的司机");
        mTitles.add("我的路线");
        mTitles.add("我的客服");
        mResources.add(R.mipmap.ic_center_cars);
        mResources.add(R.mipmap.ic_center_driver);
        mResources.add(R.mipmap.ic_center_route);
        mResources.add(R.mipmap.ic_center_service);
        initConfig();
        initRecycler();
        initRefresh();
        if (App.getInstance().token.isEmpty()) {
            authState.setText("请先登录");
        }
    }

    private void initConfig() {
        mMineMenu = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
            mMineMenu.add(new MineMenu(mResources.get(i), mTitles.get(i)));
        }
    }

    private void initData() {
        mPresenter.getUserInfo();
        mPresenter.getAccountInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    private void initRefresh() {
        mRefresh.autoRefresh();
        mRefresh.setEnableLoadMore(false);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    private void initRecycler() {
        mAdapter = new MineMenuAdapter(R.layout.item_mine_menu, mMineMenu);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(UIUtil.dip2px(1), Color.parseColor("#f2f2f2")));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setNewData(mMineMenu);
    }

    @OnClick({R.id.user_info_layout, R.id.order_all, R.id.order_ing_layout,
            R.id.order_be_paid_layout, R.id.order_be_delivery_layout,
            R.id.order_be_receipt_layout, R.id.mine_circle_layout,
            R.id.mine_community_layout, R.id.mine_invitation_layout,
            R.id.mine_draft_layout, R.id.mine_wallet_money_layout, R.id.mine_wallet_integral_layout})
    public void onViewClicked(View view) {

        if (App.getInstance().token.isEmpty()) {
            ARouter.getInstance().build("/user/login").navigation();
            return;
        }
        switch (view.getId()) {
            case R.id.user_info_layout://用户详情
                UserCenterActivity.action(mContext);
                break;
            case R.id.order_all://我的所有运单
                OrdersActivity.action(mContext, 0);
                break;
            case R.id.order_ing_layout://发布中
                OrdersActivity.action(mContext, 1);
                break;
            case R.id.order_be_paid_layout://待支付
                OrdersActivity.action(mContext, 2);
                break;
            case R.id.order_be_delivery_layout://待配送
                OrdersActivity.action(mContext, 3);
                break;
            case R.id.order_be_receipt_layout://待收货
                OrdersActivity.action(mContext, 4);
                break;
            case R.id.mine_circle_layout://我的圈子
                toast("该功能暂未开通，敬请期待");
                break;
            case R.id.mine_community_layout://我的社区
                toast("该功能暂未开通，敬请期待");
                break;
            case R.id.mine_invitation_layout://我的帖子
                toast("该功能暂未开通，敬请期待");
                break;
            case R.id.mine_draft_layout://我的草稿箱
                toast("该功能暂未开通，敬请期待");
                break;
            case R.id.mine_wallet_money_layout://我的钱包
                WalletActivity.action(mContext);
                break;
            case R.id.mine_wallet_integral_layout://我的积分

                break;
        }
    }

    @Override
    public void onUserInfo(UserInfo userInfo) {

        App.getInstance().putUserInfo(userInfo); //存储用户信息

        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, Const.IMG_URL + userInfo.getAvatar()
                , mUserImg, mContext.getResources().getDrawable(R.mipmap.ic_center_driver_head_land));

        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            mUserName.setText(userInfo.getNickName());
        } else {
            mUserName.setText("");
        }

        if (!TextUtils.isEmpty(userInfo.getMobile())) {
            mUserPhone.setText(userInfo.getMobile());
        } else {
            mUserPhone.setText("");
        }

        mRatingBar.setClickable(false);
        mRatingBar.setStar(userInfo.getScore());

        if (userInfo.getDriverVerified() == 3 || userInfo.getParkVerified() == 3) {
            authState.setText("认证失败");
            return;
        }

        if (userInfo.getDriverVerified() == 2 || userInfo.getParkVerified() == 2) {
            authState.setText("已认证");
            return;
        }

        if (userInfo.getDriverVerified() == 1 || userInfo.getParkVerified() == 1) {
            authState.setText("认证中");
            return;
        }

        if (userInfo.getDriverVerified() == 0 && userInfo.getParkVerified() == 0) {
            authState.setText("未提交");
            return;
        }
    }

    @Override
    public void onAccountInfo(AccountInfo accountInfo) {
        mMoney.setText(String.valueOf(accountInfo.getBalance()));
    }

    @Subscribe
    public void onEvent(UserStatusChangeEvent event) {
        switch (event.index) {
            case UserStatusChangeEvent.LOGIN_SUCCESS: //登录成功
                initData();
                break;
            case UserStatusChangeEvent.CHANGE_SUCCESS: //用户信息改变成功
                initData();
                break;
        }
    }

    @Override
    public void finishRefresh() {
        mRefresh.finishRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0://我的车辆
                if (!UserManager.getUserManager().isVerified()) {
                    showVerifiedDialog();
                    return;
                }
                CarsActivity.action(mContext);
                break;
            case 1://我的司机
                if (!UserManager.getUserManager().isVerified()) {
                    showVerifiedDialog();
                    return;
                }
                DriversActivity.action(mContext);
                break;
            case 2://我的路线
                if (!UserManager.getUserManager().isVerified()) {
                    showVerifiedDialog();
                    return;
                }
                RoutesActivity.action(mContext);
                break;
            case 3://我的客服
                IMHelper.getInstance().goIm(mContext);
                break;
        }
        if (position>3) { //服务配置H5
            ServiceBean serviceBean = mServiceBeans.get(position-4);
            SimpleWebActivity.action(getActivity(),serviceBean.getUrl(),serviceBean.getTitle());
        }
    }


    private void showVerifiedDialog() {
        AppDialogFragment appDialogFragment = AppDialogFragment.getInstance();
        appDialogFragment.setTitle("温馨提示");
        appDialogFragment.setMessage("您尚未认证通过");
        appDialogFragment.setPositiveButton("前往认证", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVerifiedActivity.action(mContext);
            }
        });
        appDialogFragment.show(getChildFragmentManager(), "appDialog");
    }

    @Override
    public void onBannerListFail() {

    }

    @Override
    public void onBannerList(List<BannerBean> mBannerBeans) {

    }

    @Override
    public void onServiceList(List<ServiceBean> serviceBeans) {
        mServiceBeans = serviceBeans;
        for (ServiceBean serviceBean : mServiceBeans) {
            mTitles.add(serviceBean.getTitle());
            mResources.add(serviceBean.getIcon());
        }
        initConfig();
        initRecycler();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mServicePresenter != null) {
            mServicePresenter.detachView();
        }
    }
}
