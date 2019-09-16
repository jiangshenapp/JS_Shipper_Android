package com.js.community.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.base.util.TimeUtils;
import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.R;
import com.js.community.model.bean.PostBean;

import java.util.List;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {

    public PostAdapter(int layoutResId, @Nullable List<PostBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {
        ImageView avatar = helper.getView(R.id.item_avatar);
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext,com.base.http.global.Const.IMG_URL+item.getAvatar(),avatar);
        helper.setText(R.id.item_name, item.getNickName())
                .setText(R.id.item_post_content, item.getContent())
                .setText(R.id.item_post_time, TimeUtils.format(item.getCreateTime()))
                .setText(R.id.item_post_comment, String.valueOf(item.getCommentCount()))
                .setText(R.id.item_post_like, String.valueOf(item.getLikeCount()));

    }

}
