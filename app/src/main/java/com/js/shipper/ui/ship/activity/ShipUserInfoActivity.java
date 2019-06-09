package com.js.shipper.ui.ship.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.js.frame.view.SimpleActivity;
import com.js.shipper.R;
import com.js.shipper.model.bean.ShipBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-07.
 */
public class ShipUserInfoActivity extends SimpleActivity {

    @BindView(R.id.address_name)
    TextView mAddressName;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.address_detail)
    EditText mAddressDetail;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.phone)
    EditText mPhone;
    private int type;//0。发货；1.收货
    private String address;
    private String addressName;


    public static void action(Activity context, int type, String address, String addressName) {
        Intent intent = new Intent(context, ShipUserInfoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("address", address);
        intent.putExtra("addressName", addressName);
        context.startActivityForResult(intent,888);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ship_user_info;
    }

    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
        address = getIntent().getStringExtra("address");
        addressName = getIntent().getStringExtra("addressName");
        mAddress.setText(address);
        mAddressName.setText(addressName);
    }

    private void initView() {
        switch (type) {
            case 0:
                mTitle.setText("发货人");
                mName.setHint("发货人姓名");
                mPhone.setHint("发货人手机号");
                break;
            case 1:
                mTitle.setText("收货人");
                mName.setHint("收货人姓名");
                mPhone.setHint("收货人手机号");
                break;
        }
    }

    @Override
    public void setActionBar() {

    }


    @OnClick(R.id.confirm)
    public void onViewClicked() {
        String phone = mPhone.getText().toString().trim();
        String name = mName.getText().toString().trim();
        String address = mAddressDetail.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            toast("请输入详细地址");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            toast("请输入姓名");
        }

        Intent intent = new Intent();
        ShipBean shipBean = new ShipBean(phone, name, address);
        intent.putExtra("ship", shipBean);
        setResult(999, intent);
        finish();
    }
}
