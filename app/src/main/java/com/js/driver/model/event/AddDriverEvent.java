package com.js.driver.model.event;

/**
 * Created by huyg on 2019/4/29.
 */
public class AddDriverEvent {
    public String name;
    public String phone;
    public String type;

    public AddDriverEvent(String name, String phone, String type) {
        this.name = name;
        this.phone = phone;
        this.type = type;
    }
}
