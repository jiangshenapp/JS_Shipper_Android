package com.js.shipper.ui.main.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.base.frame.view.BaseFragment;
import com.base.http.global.Const;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.manager.CommonGlideImageLoader;
import com.js.shipper.manager.UserManager;
import com.js.shipper.model.bean.AccountInfo;
import com.js.shipper.model.bean.MineMenu;
import com.js.shipper.model.bean.UserInfo;
import com.js.shipper.model.event.UserStatusChangeEvent;
import com.js.shipper.ui.main.adapter.MineMenuAdapter;
import com.js.shipper.ui.main.presenter.MinePresenter;
import com.js.shipper.ui.main.presenter.contract.MineContract;
import com.js.shipper.ui.order.activity.OrdersActivity;
import com.js.shipper.ui.park.activity.CollectActivity;
import com.js.shipper.ui.user.activity.LoginActivity;
import com.js.shipper.ui.user.activity.UserCenterActivity;
import com.js.shipper.ui.user.activity.VerifiedActivity;
import com.js.shipper.ui.wallet.activity.WalletActivity;
import com.js.shipper.util.UIUtil;
import com.js.shipper.widget.adapter.DividerGridItemDecoration;
import com.js.shipper.widget.dialog.AppDialogFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/1.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View, BaseQuickAdapter.OnItemClickListener {

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

    private MineMenuAdapter mAdapter;
    private List<MineMenu> mMineMenu;
    private String[] titles = {"我的园区", "我的服务", "我的发票", "推广达人", "我的客服"};
    private int[] resources = {R.mipmap.ic_center_park, R.mipmap.ic_center_server, R.mipmap.ic_center_invoice,
            R.mipmap.ic_center_collection, R.mipmap.ic_center_service};

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
        initConfig();
        initView();
        if (App.getInstance().token.isEmpty()) {
            authState.setText("请先登录");
        }
    }

    private void initConfig() {
        mMineMenu = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mMineMenu.add(new MineMenu(resources[i], titles[i]));
        }
    }

    private void initData() {
        mPresenter.getUserInfo();
        mPresenter.getAccountInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            initData();
        }
    }

    private void initView() {
        initRecycler();
        initRefresh();
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
            LoginActivity.action(mContext);
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

                break;
            case R.id.mine_community_layout://我的社区

                break;
            case R.id.mine_invitation_layout://我的帖子

                break;
            case R.id.mine_draft_layout://我的草稿箱

                break;
            case R.id.mine_wallet_money_layout://我的钱包
                if (!UserManager.getUserManager().isVerified()) {
                    AppDialogFragment appDialogFragment = AppDialogFragment.getInstance();
                    appDialogFragment.setTitle("温馨提示");
                    appDialogFragment.setMessage("您尚未认证通过");
                    appDialogFragment.setPositiveButton("前往认证", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VerifiedActivity.action(mContext);
                        }
                    });
                    appDialogFragment.show(getActivity().getSupportFragmentManager(), "appDialog");
                    return;
                }
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
                , mUserImg, mContext.getResources().getDrawable(R.mipmap.ic_center_shipper_head_land));

        if (!TextUtils.isEmpty(userInfo.getMobile())) {
            mUserPhone.setText(userInfo.getMobile());
        } else {
            mUserPhone.setText("");
        }

        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            mUserName.setText(userInfo.getNickName());
        } else {
            mUserName.setText("");
        }

        if (userInfo.getPersonConsignorVerified() == 3 || userInfo.getCompanyConsignorVerified() == 3) {
            authState.setText("认证失败");
            return;
        }

        if (userInfo.getPersonConsignorVerified() == 2 || userInfo.getCompanyConsignorVerified() == 2) {
            authState.setText("已认证");
            return;
        }

        if (userInfo.getPersonConsignorVerified() == 1 || userInfo.getCompanyConsignorVerified() == 1) {
            authState.setText("认证中");
            return;
        }

        if (userInfo.getPersonConsignorVerified() == 0 && userInfo.getCompanyConsignorVerified() == 0) {
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
            case 0://我的园区
                CollectActivity.action(mContext, 0);
                break;
            case 1://我的服务
                break;
            case 2://我的发票
                break;
            case 3://推广达人
                break;
            case 4://我的客服
                break;
        }
    }


    public void showVerifiedDialog(){

    }
}
