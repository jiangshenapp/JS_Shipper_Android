package com.js.community.model.bean;

/**
 * Created by huyg on 2019-09-09.
 */
public class Member {

    /**
     * circleId : 0
     * id : 0
     * nickName : string
     * status : string
     * subscriberId : 0
     */

    private int circleId;
    private long id;
    private String nickName;
    private String status;
    private int subscriberId;

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }
}
