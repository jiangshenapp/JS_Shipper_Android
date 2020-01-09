package com.js.message.model.event;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/09
 * desc   :
 * version: 3.0.0
 */
public class MessageEvent {

    // 1：消息数量改变 2：推送数量改变
    public static final int MESSAGE_COUNT_CHANGE = 1;
    public static final int PUSH_COUNT_CHANGE = 2;

    public int index;

    public MessageEvent(int index) {
        this.index = index;
    }
}
