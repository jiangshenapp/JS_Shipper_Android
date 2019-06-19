package com.js.shipper.widget.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.R;
import com.js.shipper.model.event.PoiAddrInfoEvent;
import com.js.shipper.widget.window.adapter.SearchAddressApdater;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyg on 2019-05-22.
 */
public class SearchAddressWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler)
    RecyclerView mRecycler;


    private Context mContext;
    private SearchAddressApdater mAdapter;
    private List<PoiInfo> mPois;

    public SearchAddressWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_window_search_address, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);//内容可点击
        setOutsideTouchable(true); //点击外部popupWindow消失
        setBackgroundDrawable(null);
        initView();
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SearchAddressApdater(R.layout.item_search_address, mPois);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<PoiInfo> poiInfos = mAdapter.getData();
        PoiInfo poiInfo = poiInfos.get(position);
        if (poiInfo != null) {
            EventBus.getDefault().post(new PoiAddrInfoEvent(poiInfo));
            dismiss();
        }
    }


    public void setData(List<PoiInfo> data) {
        this.mPois = data;
        mAdapter.setNewData(data);
    }
}
