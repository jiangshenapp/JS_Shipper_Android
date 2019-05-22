package com.js.shipper.widget.window;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.js.shipper.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-05-22.
 */
public class CityWindow extends PopupWindow {

    @BindView(R.id.window_city)
    TextView mCity;
    @BindView(R.id.window_upper)
    TextView mUpper;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private Context mContext;

    public CityWindow(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_window_city, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);//内容可点击
        setOutsideTouchable(false); //点击外部popupWindow消失
        setClippingEnabled(false);
        setBackgroundDrawable(null);
        initData();
        initView();
    }

    private void initData() {
        String cityStr = getJson(mContext,"area.json");

    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {


    }

    @OnClick(R.id.window_upper)
    public void onViewClicked() {

    }


    public String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
