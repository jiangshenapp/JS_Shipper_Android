package com.js.shipper.ui.main.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.manager.CommonGlideImageLoader;

import java.util.List;

/**
 * Created by huyg on 2019-09-02.
 */
public class CircleAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public CircleAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        ImageView image = helper.getView(R.id.item_circle_img);
        CommonGlideImageLoader.getInstance().displayNetImage(mContext, "", image);
        helper.setText(R.id.item_circle_name, "");
    }
}
