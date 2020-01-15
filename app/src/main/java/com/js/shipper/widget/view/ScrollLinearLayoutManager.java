package com.js.shipper.widget.view;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/15
 * desc   :
 * version: 3.0.0
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean mCanVerticalScroll = true;
    private boolean mCanHorizontalScroll = true;

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        if (!mCanVerticalScroll) {
            return false;
        } else {
            return super.canScrollVertically();
        }
    }

    public void setmCanVerticalScroll(boolean b){
        mCanVerticalScroll = b;
    }

    @Override
    public boolean canScrollHorizontally() {
        if (!mCanHorizontalScroll) {
            return false;
        } else {
            return super.canScrollHorizontally();
        }
    }

    public void setmCanHorizontalScroll(boolean b){
        mCanHorizontalScroll = b;
    }
}
