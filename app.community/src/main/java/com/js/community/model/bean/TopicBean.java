package com.js.community.model.bean;

/**
 * Created by huyg on 2019-09-18.
 */
public class TopicBean {
    private boolean checked;
    private String name;

    public TopicBean(boolean checked, String name) {
        this.checked = checked;
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
