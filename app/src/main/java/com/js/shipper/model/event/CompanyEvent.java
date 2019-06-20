package com.js.shipper.model.event;

/**
 * Created by huyg on 2019-06-20.
 */
public class CompanyEvent {

    public int position;
    public String content;

    public CompanyEvent(int position, String content) {
        this.position = position;
        this.content = content;
    }
}
