package com.js.shipper.widget.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.js.shipper.R;
import com.js.shipper.model.event.SortEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-05-22.
 */
public class SortWindow extends PopupWindow {


    private Context mContext;

    public SortWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_window_sort, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);//内容可点击
        setOutsideTouchable(false); //点击外部popupWindow消失
        setBackgroundDrawable(null);
    }

    @OnClick({R.id.item_sort_default, R.id.item_sort_distance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_sort_default:
                EventBus.getDefault().post(new SortEvent(1));
                break;
            case R.id.item_sort_distance:
                EventBus.getDefault().post(new SortEvent(2));
                break;
        }
        dismiss();
    }
}
