package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;
    private static final RequestOptions mRequestOptions = new RequestOptions().centerCrop()
            .priority(Priority.LOW)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(false);

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        EaseUser user = getUserInfo(username);
        if (user != null && user.getAvatar() != null) {
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar())
                        .apply(mRequestOptions.placeholder(R.drawable.ease_default_avatar))
                        .into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        String title = "";
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNickname() != null) {
                if (user.getNickname().contains("shipper")) {
                    title = user.getNickname().substring(7);
                } else if (user.getNickname().contains("driver")) {
                    title = user.getNickname().substring(6);
                } else if (username.contains("kefu")) {
                    title = "在线客服";
                } else {
                    title = user.getNickname();
                }
                textView.setText(title);
            } else {
                if (username.contains("shipper")) {
                    title = username.substring(7);
                } else if (username.contains("driver")) {
                    title = username.substring(6);
                } else if (username.contains("kefu")) {
                    title = "在线客服";
                } else {
                    title = user.getNickname();
                }
                textView.setText(title);
            }
        }
    }

}
