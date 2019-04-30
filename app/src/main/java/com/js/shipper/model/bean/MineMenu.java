package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/4/29.
 */
public class MineMenu {
    private int resouce;
    private String title;


    public MineMenu(int resouce, String title) {
        this.resouce = resouce;
        this.title = title;
    }

    public int getResouce() {
        return resouce;
    }

    public void setResouce(int resouce) {
        this.resouce = resouce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
