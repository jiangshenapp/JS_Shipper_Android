package com.js.shipper.ui.order.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.request.OrderEdit;
import com.js.shipper.ui.order.presenter.OrderEditPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderEditContract;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-17.
 */
public class OrderEditActivity extends BaseActivity<OrderEditPresenter> implements OrderEditContract.View {

    @BindView(R.id.fee)
    EditText mFee;
    @BindView(R.id.pay_way)
    TextView mPayWay;
    @BindView(R.id.pay_type)
    TextView mPayType;
    @BindView(R.id.receiveName)
    EditText mReceiveName;
    @BindView(R.id.phone)
    EditText mPhone;
    @BindView(R.id.status_layout)
    LinearLayout mStatusLayout;

    private OrderBean mOrderBean;
    private String[] payWay = {"线上支付", "线下支付"};
    private String[] payType = {"到付", "现付"};

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        mOrderBean = getIntent().getParcelableExtra("order");
    }

    private void initView() {
        mFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2+1);
                        mFee.setText(s);
                        mFee.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mFee.setText(s);
                    mFee.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mFee.setText(s.subSequence(0, 1));
                        mFee.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        showDetail();
    }

    private void showDetail() {
        if (mOrderBean != null) {
            if (mOrderBean.getFeeType() == 1) {
                mFee.setText(String.valueOf(mOrderBean.getFee()));
            }
            switch (mOrderBean.getPayWay()) {
                case 1:
                    mPayWay.setText("线上支付");
                    break;
                case 2:
                    mPayWay.setText("线下支付");
                    break;
            }

            switch (mOrderBean.getPayType()) {
                case 1:
                    mPayType.setText("到付");
                    break;
                case 2:
                    mPayType.setText("现付");
                    break;
            }

            mReceiveName.setText(mOrderBean.getReceiveName());
            mPhone.setText(mOrderBean.getReceiveMobile());

            switch (mOrderBean.getState()) {
                case 4:
                    mStatusLayout.setVisibility(View.GONE);
                    break;
                default:
                    mStatusLayout.setVisibility(View.VISIBLE);
                    break;
            }

        } else {
            finish();
        }
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
        return R.layout.activity_order_edit;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("修改订单信息");
    }

    @Override
    public void onEditOrder(boolean isOk) {
        if (isOk) {
            finish();
        }
    }


    @OnClick({R.id.pay_way_layout, R.id.pay_type_layout, R.id.edit_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_way_layout:
                showPayWay();
                break;
            case R.id.pay_type_layout:
                showPayType();
                break;
            case R.id.edit_submit:
                String fee = mFee.getText().toString().trim();
                String receiveName = mReceiveName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                mOrderBean.setFee(Double.parseDouble(fee));
                mOrderBean.setReceiveName(receiveName);
                mOrderBean.setReceiveMobile(phone);

                if (TextUtils.isEmpty(fee)) {
                    toast("请输入价格");
                    return;
                }

                if (TextUtils.isEmpty(receiveName)) {
                    toast("请输入收货人");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    toast("请输入联系方式");
                    return;
                }

                OrderEdit orderEdit = new OrderEdit();
                orderEdit.setFee(mOrderBean.getFee());
                orderEdit.setReceiveName(mOrderBean.getReceiveName());
                orderEdit.setReceiveMobile(mOrderBean.getReceiveMobile());
                orderEdit.setFeeType(2); //电议
//                orderEdit.setPayType(mOrderBean.getPayType());
//                orderEdit.setPayWay(mOrderBean.getPayWay());
                orderEdit.setPayType(2); //现付
                orderEdit.setPayWay(1); //线上支付
                mPresenter.editOrder(orderEdit, mOrderBean.getId());
                break;
        }
    }

    private void showPayType() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mPayType.setText(payType[options1]);
                mOrderBean.setPayType((options1 + 1));
            }
        }).build();
        pvOptions.setPicker(Arrays.asList(payType));
        pvOptions.show();
    }


    private void showPayWay() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mPayWay.setText(payWay[options1]);
                mOrderBean.setPayWay((options1 + 1));
            }
        }).build();
        pvOptions.setPicker(Arrays.asList(payWay));
        pvOptions.show();
    }

}
