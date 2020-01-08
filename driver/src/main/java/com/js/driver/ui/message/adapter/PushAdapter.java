package com.js.driver.ui.message.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.http.global.Const;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.model.bean.MessageBean;
import com.js.driver.model.bean.PushBean;
import com.js.driver.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/08
 * desc   :
 * version: 3.0.0
 */
public class PushAdapter extends BaseQuickAdapter<PushBean, BaseViewHolder> {

    public PushAdapter(int layoutResId, @Nullable List<PushBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PushBean item) {
        helper.setText(R.id.tv_time, item.getPushTime())
                .setText(R.id.tv_title, item.getTemplateName())
                .setText(R.id.tv_content, item.getPushContent());
        helper.getView(R.id.iv_image).setVisibility(View.GONE);
        TextView mTvState = helper.getView(R.id.tv_state);
        if (item.getState() == 0) {
            mTvState.setText("未读");
            mTvState.setBackgroundColor(App.getInstance().getResources().getColor(R.color._D0021B));
            mTvState.setTextColor(App.getInstance().getResources().getColor(R.color._FFFFFF));
        } else {
            mTvState.setText("已读");
            mTvState.setBackgroundColor(App.getInstance().getResources().getColor(R.color._F5F5F5));
            mTvState.setTextColor(App.getInstance().getResources().getColor(R.color._B4B4B4));
        }
    }
}
