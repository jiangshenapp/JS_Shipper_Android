package com.js.login.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huyg on 2019-09-30.
 */
public class WxLogin implements Parcelable {


    /**
     * unionid : oft4Q1Gj2xDja4OJqbF1R-La05qY
     * openid : oNNk21tNNcDJw5y8RaY3huw1jRQs
     * nickname : huyg
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIBGLz90RJLWMtwDKPgZicTEZOv81uE1CBXTf9QjKWvNOOQfAic3S1Nsv5zBRLzqJxqxhHpVKWm3qBQ/132
     */

    private String unionid;
    private String openid;
    private String nickname;
    private String headimgurl;

    protected WxLogin(Parcel in) {
        unionid = in.readString();
        openid = in.readString();
        nickname = in.readString();
        headimgurl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(unionid);
        dest.writeString(openid);
        dest.writeString(nickname);
        dest.writeString(headimgurl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WxLogin> CREATOR = new Creator<WxLogin>() {
        @Override
        public WxLogin createFromParcel(Parcel in) {
            return new WxLogin(in);
        }

        @Override
        public WxLogin[] newArray(int size) {
            return new WxLogin[size];
        }
    };

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
