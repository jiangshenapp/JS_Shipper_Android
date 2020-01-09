package com.js.message.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/08
 * desc   :
 * version: 3.0.0
 */
public class PushBean implements Parcelable {

    /**
     * id : 0
     * pushContent : string
     * pushSide : 0
     * pushTarget : string
     * pushTime : 2020-01-08T01:31:28.548Z
     * state : 0
     * tempateKey : string
     * templateName : string
     */

    private int id; //
    private String pushContent; //推送内容
    private int pushSide; //推送来源，1运力，2货主
    private String pushTarget; //推送账号
    private String pushTime; //推送时间
    private int state; //状态，0未读，1已读
    private String tempateKey; //模板key
    private String templateName; //模板名称(推送标题)

    protected PushBean(Parcel in) {
        id = in.readInt();
        pushContent = in.readString();
        pushSide = in.readInt();
        pushTarget = in.readString();
        pushTime = in.readString();
        state = in.readInt();
        tempateKey = in.readString();
        templateName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pushContent);
        dest.writeInt(pushSide);
        dest.writeString(pushTarget);
        dest.writeString(pushTime);
        dest.writeInt(state);
        dest.writeString(tempateKey);
        dest.writeString(templateName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PushBean> CREATOR = new Creator<PushBean>() {
        @Override
        public PushBean createFromParcel(Parcel in) {
            return new PushBean(in);
        }

        @Override
        public PushBean[] newArray(int size) {
            return new PushBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public int getPushSide() {
        return pushSide;
    }

    public void setPushSide(int pushSide) {
        this.pushSide = pushSide;
    }

    public String getPushTarget() {
        return pushTarget;
    }

    public void setPushTarget(String pushTarget) {
        this.pushTarget = pushTarget;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTempateKey() {
        return tempateKey;
    }

    public void setTempateKey(String tempateKey) {
        this.tempateKey = tempateKey;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
