package com.js.community.model.bean;

/**
 * Created by huyg on 2019-09-10.
 */
public class Comment {
    /**
     * avatar : string
     * comment : string
     * createBy : 0
     * createTime : 2019-09-10T09:40:07.216Z
     * delFlag : string
     * id : 0
     * nickName : string
     * postId : 0
     */

    private String avatar;
    private String comment;
    private int createBy;
    private String createTime;
    private String delFlag;
    private int id;
    private String nickName;
    private int postId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public int getId() {
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

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
