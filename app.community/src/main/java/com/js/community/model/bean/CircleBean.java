package com.js.community.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huyg on 2019-09-09.
 */
public class CircleBean implements Parcelable {

    /**
     * id : 1
     * name : 老司机发车
     * city : 330200
     * status : 1
     * showSide : 3
     * image : string
     * admin : null
     * stopWord : 共产党
     * subjects : 情感,开车,吐槽
     * applyStatus : 3
     */

    private int id;
    private String name;
    private String city;
    private String status;
    private String showSide;
    private String image;
    private Object admin;
    private String stopWord;
    private String subjects;
    private String applyStatus;

    protected CircleBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        city = in.readString();
        status = in.readString();
        showSide = in.readString();
        image = in.readString();
        stopWord = in.readString();
        subjects = in.readString();
        applyStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(status);
        dest.writeString(showSide);
        dest.writeString(image);
        dest.writeString(stopWord);
        dest.writeString(subjects);
        dest.writeString(applyStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CircleBean> CREATOR = new Creator<CircleBean>() {
        @Override
        public CircleBean createFromParcel(Parcel in) {
            return new CircleBean(in);
        }

        @Override
        public CircleBean[] newArray(int size) {
            return new CircleBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowSide() {
        return showSide;
    }

    public void setShowSide(String showSide) {
        this.showSide = showSide;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getAdmin() {
        return admin;
    }

    public void setAdmin(Object admin) {
        this.admin = admin;
    }

    public String getStopWord() {
        return stopWord;
    }

    public void setStopWord(String stopWord) {
        this.stopWord = stopWord;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }
}
