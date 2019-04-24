package com.js.driver.ui.main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerFragmentComponent;
import com.js.driver.di.module.FragmentModule;
import com.js.driver.ui.main.presenter.MinePresenter;
import com.js.driver.ui.main.presenter.contract.MineContract;
import com.js.driver.ui.user.activity.UserCenterActivity;
import com.xlgcx.frame.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/1.
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {


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

    }

    @OnClick({R.id.user_info_layout, R.id.order_all, R.id.order_ing_layout, R.id.order_be_paid_layout, R.id.order_be_delivery_layout, R.id.order_be_receipt_layout, R.id.mine_circle_layout, R.id.mine_community_layout, R.id.mine_invitation_layout, R.id.mine_draft_layout, R.id.recycler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_info_layout:
                UserCenterActivity.action(mContext);
                break;
            case R.id.order_all:
                break;
            case R.id.order_ing_layout:
                break;
            case R.id.order_be_paid_layout:
                break;
            case R.id.order_be_delivery_layout:
                break;
            case R.id.order_be_receipt_layout:
                break;
            case R.id.mine_circle_layout:
                break;
            case R.id.mine_community_layout:
                break;
            case R.id.mine_invitation_layout:
                break;
            case R.id.mine_draft_layout:
                break;
            case R.id.recycler:
                break;
        }
    }
}
