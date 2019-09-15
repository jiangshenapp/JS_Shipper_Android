package com.js.community.ui.adapter;

import android.widget.ImageView;

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
        switch (item.getStatus()) {
            case "0"://已申请
                break;
            case "1"://已加入

                break;
            case "2":

                break;
            case "3":

            default:

                break;
        }
    }
}
