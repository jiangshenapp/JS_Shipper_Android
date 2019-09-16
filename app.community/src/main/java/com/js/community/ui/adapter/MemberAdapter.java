package com.js.community.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.base.util.manager.CommonGlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.js.community.R;
import com.js.community.model.bean.Member;

import java.util.List;

/**
 * Created by huyg on 2019-09-11.
 */
public class MemberAdapter extends BaseQuickAdapter<Member, BaseViewHolder> {

    private boolean isAdmin = true;


    public MemberAdapter(int layoutResId, @Nullable List<Member> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Member item) {
        helper.setText(R.id.item_member_name, item.getNickName());
        TextView status = helper.getView(R.id.item_member_status);
        TextView agree = helper.getView(R.id.item_agree);
        TextView delete = helper.getView(R.id.item_delete);
        TextView refuse = helper.getView(R.id.item_refuse);
        ImageView imageView = helper.getView(R.id.item_member_img);
        CommonGlideImageLoader.getInstance().displayNetImageWithCircle(mContext,com.base.http.global.Const.IMG_URL+item.getAvatar(),imageView);
        if ("0".equals(item.getStatus())) {
            status.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);
            if (isAdmin) {
                agree.setVisibility(View.VISIBLE);
                refuse.setVisibility(View.VISIBLE);
            } else {
                agree.setVisibility(View.GONE);
                refuse.setVisibility(View.GONE);
            }
        } else {
            status.setVisibility(View.GONE);
            agree.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
            if (isAdmin) {
                delete.setVisibility(View.VISIBLE);
            } else {
                delete.setVisibility(View.GONE);
            }
        }
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
