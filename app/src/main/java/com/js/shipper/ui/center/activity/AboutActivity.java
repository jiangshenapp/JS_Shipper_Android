package com.js.shipper.ui.center.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.frame.view.SimpleActivity;
import com.base.frame.view.SimpleWebActivity;
import com.js.login.global.Const;
import com.js.shipper.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/22
 * desc   :
 * version: 3.0.0
 */
public class AboutActivity extends SimpleActivity {

    public static void action(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {

    }

    @Override
    public void setActionBar() {
        mTitle.setText("关于");
    }

    @OnClick({R.id.tv_protocal, R.id.tv_privacy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_protocal:
                SimpleWebActivity.action(this, Const.H5_RegisterProtocal_SHIPPER, "用户协议");
                break;
            case R.id.tv_privacy:
                SimpleWebActivity.action(this, Const.H5_PrivacyProtocal_SHIPPER, "隐私政策");
                break;
        }
    }
}
