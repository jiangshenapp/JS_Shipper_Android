package com.js.shipper.model.bean;

/**
 * Created by huyg on 2019/4/29.
 */
public class MineMenu {
    private Object resouce;
    private String title;


    public MineMenu(Object resouce, String title) {
        this.resouce = resouce;
        this.title = title;
    }

    public Object getResouce() {
        return resouce;
    }

    public void setResouce(Object resouce) {
        this.resouce = resouce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
