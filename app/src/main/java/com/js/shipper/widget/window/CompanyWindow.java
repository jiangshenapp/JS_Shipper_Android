package com.js.shipper.widget.window;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.R;
import com.js.shipper.model.event.CompanyEvent;
import com.js.shipper.widget.window.adapter.CompanyAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyg on 2019-05-22.
 */
public class CompanyWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private Context mContext;
    private static final String[] company = {"全部", "服务中心", "车代点", "网点"};
    private CompanyAdapter mAdapter;

    public CompanyWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_window_company, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);//内容可点击
        setOutsideTouchable(false); //点击外部popupWindow消失
        setBackgroundDrawable(null);
        initView();
    }

    private void initView() {
        mAdapter = new CompanyAdapter(R.layout.item_window_company, Arrays.asList(company));
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EventBus.getDefault().post(new CompanyEvent(position, company[position]));
        dismiss();
    }
}
