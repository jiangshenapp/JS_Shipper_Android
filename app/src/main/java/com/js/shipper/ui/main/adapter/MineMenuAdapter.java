package com.js.shipper.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.shipper.R;
import com.js.shipper.model.bean.MineMenu;

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
        helper.setImageResource(R.id.item_mine_img,item.getResouce());
    }
}
