package com.js.community.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.R;
import com.js.community.model.bean.TopicBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-10.
 */
public class TopicAdapter extends BaseQuickAdapter<TopicBean, BaseViewHolder> {

    private final int[] sources = {R.mipmap.social_circle_icon_green,
            R.mipmap.social_circle_icon_blue,
            R.mipmap.social_circle_icon_red,
            R.mipmap.social_circle_icon_yellow};

    public TopicAdapter(int layoutResId, @Nullable List<TopicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicBean item) {
        helper.setText(R.id.item_topic_name, item.getName());
        helper.setImageResource(R.id.item_topic_img, sources[helper.getLayoutPosition() % 4]);
        if (item.isChecked()){
            helper.setBackgroundRes(R.id.item_topic,R.drawable.shape_index_top_select);
        }else {
            helper.setBackgroundRes(R.id.item_topic,R.drawable.shape_index_topic);
        }
    }
}
