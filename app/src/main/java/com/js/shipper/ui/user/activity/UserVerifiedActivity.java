package com.js.shipper.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.js.shipper.R;
import com.js.frame.view.SimpleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/4/24.
 */
public class UserVerifiedActivity extends SimpleActivity {


    @BindView(R.id.driver_verified_status)
    TextView mDriverStatus;
    @BindView(R.id.park_verified_status)
    TextView mParkStatus;

    public static void action(Context context) {
        Intent intent = new Intent(context, UserVerifiedActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_verified_guide;
    }

    @Override
    protected void init() {

    }

    @Override
    public void setActionBar() {
        mTitle.setText("");
    }

    @OnClick({R.id.driver_verified_layout, R.id.park_verified_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.driver_verified_layout://司机认证
                DriverVerifiedActivity.action(mContext);
                break;
            case R.id.park_verified_layout://园区认证
                ParkUserVerifiedActivity.action(mContext);
                break;
        }
    }
}