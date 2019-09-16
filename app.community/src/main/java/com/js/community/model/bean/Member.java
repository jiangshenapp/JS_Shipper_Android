package com.js.community.model.bean;

/**
 * Created by huyg on 2019-09-09.
 */
public class Member {

    /**
     * id : 11
     * circleId : 1
     * subscriberId : 40
     * status : 1
     * nickName : yg
     * avatar : 64b763dbc6264066b1d06a514b786e99.jpg
     */

    private int id;
    private int circleId;
    private int subscriberId;
    private String status;
    private String nickName;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
