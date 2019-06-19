package com.js.shipper.widget.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.R;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.widget.window.adapter.DictAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-05-22.
 */
public class FilterWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.user_car_type)
    RecyclerView mCarType;
    @BindView(R.id.car_length)
    RecyclerView mLength;
    @BindView(R.id.car_type)
    RecyclerView mType;
    private Context mContext;
    private DictAdapter mCarTypeAdapter;
    private DictAdapter mLengthAdapter;
    private DictAdapter mTypeAdapter;
    private List<DictBean> mCarTypeDict;
    private List<DictBean> mLengthDict;
    private List<DictBean> mTypeDict;

    public FilterWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_window_filter, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);//内容可点击
        setOutsideTouchable(false); //点击外部popupWindow消失
        setClippingEnabled(false);
        setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mCarTypeAdapter = new DictAdapter(R.layout.item_window_dict, mCarTypeDict);
        mLengthAdapter = new DictAdapter(R.layout.item_window_dict, mLengthDict);
        mTypeAdapter = new DictAdapter(R.layout.item_window_dict, mTypeDict);
        mCarTypeAdapter.setOnItemClickListener(this);
        mLengthAdapter.setOnItemClickListener(this);
        mTypeAdapter.setOnItemClickListener(this);
        mCarType.setAdapter(mCarTypeAdapter);
        mCarType.setLayoutManager(new GridLayoutManager(mContext, 4));
        mLength.setAdapter(mLengthAdapter);
        mLength.setLayoutManager(new GridLayoutManager(mContext, 4));
        mType.setAdapter(mTypeAdapter);
        mType.setLayoutManager(new GridLayoutManager(mContext, 4));
    }

    @OnClick({R.id.cancel, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.submit:
                break;
        }
    }


    public void setCarTypes(List<DictBean> data) {
        this.mCarTypeDict = data;
        if (mCarTypeDict != null && mCarTypeDict.size() > 0) {
            mCarTypeDict.get(0).setChecked(true);
            mCarTypeAdapter.setNewData(mCarTypeDict);
        }
    }


    public void setCarLengths(List<DictBean> data) {
        this.mLengthDict = data;
        if (mLengthDict != null && mLengthDict.size() > 0) {
            mLengthDict.get(0).setChecked(true);
            mLengthAdapter.setNewData(mLengthDict);
        }
    }

    public void setTypes(List<DictBean> data) {
        this.mTypeDict = data;
        if (mTypeDict != null && mTypeDict.size() > 0) {
            mTypeDict.get(0).setChecked(true);
            mTypeAdapter.setNewData(mTypeDict);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
