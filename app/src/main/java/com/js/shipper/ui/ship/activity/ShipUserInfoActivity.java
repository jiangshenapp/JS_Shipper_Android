package com.js.shipper.ui.ship.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.frame.view.SimpleActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.facebook.stetho.common.LogUtil;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.model.bean.AddressBean;
import com.js.shipper.model.bean.ShipBean;
import com.js.shipper.util.DataHelper;
import com.js.shipper.util.RegexUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.et_street)
    EditText mStreet;
    @BindView(R.id.ll_street)
    LinearLayout llStreet;

    private int type;//0。发货；1.收货
    private String address;
    private String addressName;
    private ShipBean mShipBean;
    private List<AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean> mStreetChildsBeans = new ArrayList<>();
    private List<AddressBean> mAddressBeans;
    private AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean mStreetChildsBean;

    public static void action(Activity context, int type, String address, String addressName, ShipBean shipBean) {
        Intent intent = new Intent(context, ShipUserInfoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("address", address);
        intent.putExtra("addressName", addressName);
        intent.putExtra("ship", shipBean);
        context.startActivityForResult(intent, 888);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ship_user_info;
    }

    @Override
    protected void init() {
        initIntent();
        initData();
        initView();
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
        address = getIntent().getStringExtra("address");
        addressName = getIntent().getStringExtra("addressName");
        mShipBean = getIntent().getParcelableExtra("ship");
    }

    private void initData() {
        mAddressBeans = DataHelper.getAddress(this);
        int adCode = mShipBean.getAddressCode();
        int provinceCode = adCode / 10000;
        int cityCode = adCode / 100;
        for (AddressBean addressBean : mAddressBeans) {
            if (Integer.parseInt(addressBean.getCode()) == provinceCode) {
                for (AddressBean.CityChildsBean cityChildsBean : addressBean.getChilds()) {
                    if (Integer.parseInt(cityChildsBean.getCode()) == cityCode) {
                        for (AddressBean.CityChildsBean.CountyChildsBean countyChildsBean : cityChildsBean.getChilds()) {
                            if (Integer.parseInt(countyChildsBean.getCode()) == adCode) {
                                mStreetChildsBeans = countyChildsBean.getChilds();
                                break;
                            }
                        }
                        continue;
                    }
                }
                continue;
            }
        }
        LogUtil.d("城市编码 ："+adCode+"  街道信息："+mStreetChildsBeans.toString());
    }

    private void initView() {
        mAddress.setText(address);
        mAddressName.setText(addressName);
        mStreet.setText(mShipBean.getStreetName());
        if (mShipBean != null) {
            mAddressDetail.setText(mShipBean.getAddressDetail());
            mName.setText(mShipBean.getName());
            mPhone.setText(mShipBean.getPhone());
        }
        switch (type) {
            case 0:
                mTitle.setText("发货人");
                mName.setHint("发货人姓名");
                mPhone.setHint("发货人手机号");
                if (TextUtils.isEmpty(mShipBean.getName())) {
                    mName.setText(App.getInstance().nickName);
                }
                if (TextUtils.isEmpty(mShipBean.getPhone())) {
                    mPhone.setText(App.getInstance().mobile);
                }
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
        String street = mStreet.getText().toString().trim();
        String address = mAddressDetail.getText().toString().trim();

        if (TextUtils.isEmpty(street)) {
            toast("请选择街道");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            toast("请输入姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }

        if (!RegexUtils.isMobile(phone)) {
            toast("请输入正确的手机号");
            return;
        }

        Intent intent = new Intent();
        ShipBean shipBean = new ShipBean(phone, name, address, mStreetChildsBean.getName(), mStreetChildsBean.getCode());
        intent.putExtra("ship", shipBean);
        setResult(999, intent);
        finish();
    }

    @OnClick({R.id.et_street, R.id.ll_street})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_street: //选择街道
            case R.id.ll_street:
                showStreetPickerView();
                break;
        }
    }

    /**
     * 展示街道选择器
     */
    public void showStreetPickerView() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                mStreetChildsBean = mStreetChildsBeans.get(options1);
                mStreet.setText(mStreetChildsBean.getName());
            }
        }).build();
        List<String> streetNames = new ArrayList<String>();
        for (AddressBean.CityChildsBean.CountyChildsBean.StreetChildsBean streetChildsBean : mStreetChildsBeans) {
            streetNames.add(streetChildsBean.getName());
        }
        pvOptions.setPicker(streetNames);
        pvOptions.show();
    }
}
