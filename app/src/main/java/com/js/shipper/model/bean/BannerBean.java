package com.js.shipper.model.bean;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/09/15
 * desc   :
 * version: 3.0.0
 */
public class BannerBean {

    /**
     * url : baidu.com
     * sort : 2
     * beginTime : 2019-09-03 12:00:00
     * id : 1
     * endTime : 2019-09-14 11:26:15
     * title : veshi
     * image : 14573d05a61d4775b24fdf1f10fa8c99.jpg
     * createBy : 1
     * type : 1
     * state : 1
     * createTime : 2019-09-14 11:26:15
     */

    private String url;
    private int sort;
    private String beginTime;
    private int id;
    private String endTime;
    private String title;
    private String image;
    private int createBy;
    private int type;
    private int state;
    private String createTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCreateBy() {
        return createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
