package com.js.message.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.http.global.Const;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.message.model.bean.MessageBean;
import com.js.message.MessageApp;
import com.js.message.R;
import com.js.message.model.bean.MessageBean;
import com.js.message.util.glide.CommonGlideImageLoader;

import java.util.List;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/13
 * desc   : 系统消息
 * version: 3.0.0
 */
public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {

    public MessageAdapter(int layoutResId, @Nullable List<MessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_time, item.getPublishTime())
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent());
        ImageView mIvImage = helper.getView(R.id.iv_image);
        if (!TextUtils.isEmpty(item.getImage())) {
            mIvImage.setVisibility(View.VISIBLE);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + item.getImage(), mIvImage);
        } else {
            mIvImage.setVisibility(View.GONE);
        }
        TextView mTvState = helper.getView(R.id.tv_state);
        if (item.isIsRead() == false) {
            mTvState.setText("未读");
            mTvState.setBackgroundColor(mContext.getResources().getColor(R.color._D0021B));
            mTvState.setTextColor(mContext.getResources().getColor(R.color._FFFFFF));
        } else {
            mTvState.setText("已读");
            mTvState.setBackgroundColor(mContext.getResources().getColor(R.color._F5F5F5));
            mTvState.setTextColor(mContext.getResources().getColor(R.color._B4B4B4));
        }
    }
}
