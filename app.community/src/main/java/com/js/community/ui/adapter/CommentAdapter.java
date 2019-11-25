package com.js.community.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.base.http.global.Const;
import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.R;
import com.js.community.model.bean.Comment;

import java.util.List;

/**
 * Created by huyg on 2019-09-16.
 */
public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {


    public CommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.setText(R.id.item_comment_name, item.getNickName())
                .setText(R.id.item_comment_content, item.getComment())
                .setText(R.id.item_comment_time, item.getCreateTime());
        ImageView avatar = helper.getView(R.id.item_comment_avatar);
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext, Const.IMG_URL() + item.getAvatar(), avatar);

    }
}
