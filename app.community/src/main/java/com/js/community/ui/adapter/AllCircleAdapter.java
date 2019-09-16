package com.js.community.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.http.global.Const;
import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.R;
import com.js.community.model.bean.CircleBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-09.
 */
public class AllCircleAdapter extends BaseQuickAdapter<CircleBean, BaseViewHolder> {

    public AllCircleAdapter(int layoutResId, @Nullable List<CircleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleBean item) {
        helper.setText(R.id.item_circle_name, item.getName());
        ImageView imageView  = helper.getView(R.id.item_circle_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL+item.getImage(),imageView);
        TextView apply = helper.getView(R.id.item_circle_apply);
        TextView status  = helper.getView(R.id.item_circle_status);
        switch (item.getStatus()) {
            case "0"://已申请
                apply.setVisibility(View.GONE);
                status.setVisibility(View.VISIBLE);
                status.setText("已申请");
                status.setTextColor(mContext.getResources().getColor(R.color._4A90E2));
                break;
            case "1"://已加入
                apply.setVisibility(View.GONE);
                status.setVisibility(View.VISIBLE);
                status.setText("已加入");
                status.setTextColor(mContext.getResources().getColor(R.color._ECA73F));
                break;
            case "3":
            default:
                status.setVisibility(View.GONE);
                apply.setVisibility(View.VISIBLE);
                break;
        }
    }
}
