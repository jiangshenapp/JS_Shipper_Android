package com.js.driver.ui.main.adapter;

import android.widget.ImageView;

import com.base.http.global.Const;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.driver.R;
import com.js.driver.model.bean.MineMenu;
import com.js.driver.util.glide.CommonGlideImageLoader;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by huyg on 2019/4/29.
 */
public class MineMenuAdapter extends BaseQuickAdapter<MineMenu, BaseViewHolder> {

    public MineMenuAdapter(int layoutResId, @Nullable List<MineMenu> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineMenu item) {
        helper.setText(R.id.item_mine_title,item.getTitle());
        Object resource = item.getResouce();
        if (resource instanceof Integer) {
            helper.setImageResource(R.id.item_mine_img, (int)resource);
        } else if (resource instanceof String) {
            ImageView mIvImage = helper.getView(R.id.item_mine_img);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, Const.IMG_URL()  + resource, mIvImage);
        }
    }
}
