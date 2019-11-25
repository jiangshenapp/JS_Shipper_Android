package com.js.driver.ui.main.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.base.http.global.Const;
import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.model.bean.CircleBean;
import com.js.driver.R;

import java.util.List;

/**
 * Created by huyg on 2019-09-02.
 */
public class CircleAdapter extends BaseQuickAdapter<CircleBean, BaseViewHolder> {

    public CircleAdapter(int layoutResId, @Nullable List<CircleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleBean item) {
        ImageView image = helper.getView(R.id.item_circle_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + item.getImage(), image);
        helper.setText(R.id.item_circle_name, item.getName());
    }
}
