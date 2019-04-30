package com.js.shipper.ui.main.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.model.bean.MineMenu;
import com.js.shipper.model.bean.UserInfo;
import com.js.shipper.ui.main.adapter.MineMenuAdapter;
import com.js.shipper.ui.main.presenter.MinePresenter;
import com.js.shipper.ui.main.presenter.contract.MineContract;
import com.js.shipper.ui.order.activity.OrdersActivity;
import com.js.shipper.ui.user.activity.UserCenterActivity;
import com.js.shipper.ui.wallet.activity.WalletActivity;
import com.js.shipper.util.UIUtil;
import com.js.shipper.widget.adapter.DividerGridItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.js.frame.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private String[] titles = {"我的车辆", "我的司机", "我的路线", "我的客服", "我的发票", "推广达人"};
    private int[] resources = {R.mipmap.ic_center_cars, R.mipmap.ic_center_driver, R.mipmap.ic_center_route,
            R.mipmap.ic_center_service, R.mipmap.ic_center_invoice, R.mipmap.ic_center_collection};

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
    }

    private void initConfig() {
        mMineMenu = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mMineMenu.add(new MineMenu(resources[i], titles[i]));
        }
    }

    private void initData() {
        mPresenter.getUserInfo();
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
        switch (view.getId()) {
            case R.id.user_info_layout://用户详情
                UserCenterActivity.action(mContext);
                break;
            case R.id.order_all://我的所有运单
                OrdersActivity.action(mContext,0);
                break;
            case R.id.order_ing_layout://发布中
                OrdersActivity.action(mContext,1);
                break;
            case R.id.order_be_paid_layout://待支付
                OrdersActivity.action(mContext,2);
                break;
            case R.id.order_be_delivery_layout://待配送
                OrdersActivity.action(mContext,3);
                break;
            case R.id.order_be_receipt_layout://待收货
                OrdersActivity.action(mContext,4);
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
                WalletActivity.action(mContext);
                break;
            case R.id.mine_wallet_integral_layout://我的积分

                break;
        }
    }

    @Override
    public void onUserInfo(UserInfo userInfo) {
        if (!TextUtils.isEmpty(userInfo.getMobile())) {
            mUserPhone.setText(userInfo.getMobile());
        } else {
            mUserPhone.setText("未设置");
        }

        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            mUserName.setText(userInfo.getNickName());
        } else {
            mUserName.setText("未设置");
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
                break;
            case 1://我的司机
                break;
            case 2://我的路线
                break;
            case 3://我的客服
                break;
            case 4://我的发票
                break;
            case 5://推广达人
                break;
        }
    }
}
