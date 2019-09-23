package com.js.community.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huyg on 2019-09-10.
 */
public class PostBean implements Parcelable {
    /**
     * auditBy : 0
     * auditRemark : string
     * auditStatus : string
     * auditTime : 2019-09-10T09:44:21.110Z
     * author : 0
     * avatar : string
     * circleId : 0
     * commentCount : 0
     * content : string
     * createTime : 2019-09-10T09:44:21.110Z
     * delFlag : string
     * id : 0
     * image : string
     * likeCount : 0
     * likeFlag : string
     * nickName : string
     * star : string
     * subject : string
     * title : string
     * type : string
     */

    private int auditBy;
    private String auditRemark;
    private String auditStatus;
    private String auditTime;
    private int author;
    private String avatar;
    private int circleId;
    private int commentCount;
    private int commentFlag;
    private String content;
    private String createTime;
    private String delFlag;
    private int id;
    private String image;
    private int likeCount;
    private int likeFlag;
    private String nickName;
    private int star;
    private String subject;
    private String title;
    private String type;

    protected PostBean(Parcel in) {
        auditBy = in.readInt();
        auditRemark = in.readString();
        auditStatus = in.readString();
        auditTime = in.readString();
        author = in.readInt();
        avatar = in.readString();
        circleId = in.readInt();
        commentCount = in.readInt();
        content = in.readString();
        createTime = in.readString();
        delFlag = in.readString();
        id = in.readInt();
        image = in.readString();
        likeCount = in.readInt();
        likeFlag = in.readInt();
        nickName = in.readString();
        star = in.readInt();
        subject = in.readString();
        title = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(auditBy);
        dest.writeString(auditRemark);
        dest.writeString(auditStatus);
        dest.writeString(auditTime);
        dest.writeInt(author);
        dest.writeString(avatar);
        dest.writeInt(circleId);
        dest.writeInt(commentCount);
        dest.writeString(content);
        dest.writeString(createTime);
        dest.writeString(delFlag);
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeInt(likeCount);
        dest.writeInt(likeFlag);
        dest.writeString(nickName);
        dest.writeInt(star);
        dest.writeString(subject);
        dest.writeString(title);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostBean> CREATOR = new Creator<PostBean>() {
        @Override
        public PostBean createFromParcel(Parcel in) {
            return new PostBean(in);
        }

        @Override
        public PostBean[] newArray(int size) {
            return new PostBean[size];
        }
    };

    public int getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(int auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(int likeFlag) {
        this.likeFlag = likeFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCommentFlag() {
        return commentFlag;
    }

    public void setCommentFlag(int commentFlag) {
        this.commentFlag = commentFlag;
    }
}